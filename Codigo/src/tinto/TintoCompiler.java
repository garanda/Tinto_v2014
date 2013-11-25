//------------------------------------------------------------------//
//                        COPYRIGHT NOTICE                          //
//------------------------------------------------------------------//
// Copyright (c) 2008, Francisco José Moreno Velo                   //
// All rights reserved.                                             //
//                                                                  //
// Redistribution and use in source and binary forms, with or       //
// without modification, are permitted provided that the following  //
// conditions are met:                                              //
//                                                                  //
// * Redistributions of source code must retain the above copyright //
//   notice, this list of conditions and the following disclaimer.  // 
//                                                                  //
// * Redistributions in binary form must reproduce the above        // 
//   copyright notice, this list of conditions and the following    // 
//   disclaimer in the documentation and/or other materials         // 
//   provided with the distribution.                                //
//                                                                  //
// * Neither the name of the University of Huelva nor the names of  //
//   its contributors may be used to endorse or promote products    //
//   derived from this software without specific prior written      // 
//   permission.                                                    //
//                                                                  //
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND           // 
// CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,      // 
// INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF         // 
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE         // 
// DISCLAIMED. IN NO EVENT SHALL THE COPRIGHT OWNER OR CONTRIBUTORS //
// BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,         // 
// EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED  //
// TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,    //
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND   // 
// ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT          //
// LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING   //
// IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF   //
// THE POSSIBILITY OF SUCH DAMAGE.                                  //
//------------------------------------------------------------------//

//------------------------------------------------------------------//
//                      Universidad de Huelva                       //
//          Departamento de Tecnologías de la Información           //
//   Área de Ciencias de la Computación e Inteligencia Artificial   //
//------------------------------------------------------------------//
//                     PROCESADORES DE LENGUAJE                     //
//------------------------------------------------------------------//
//                                                                  //
//          Compilador del lenguaje Tinto [Versión 0.1]             //
//                                                                  //
//------------------------------------------------------------------//

package tinto;

import java.io.*;
import java.util.*;

import tinto.ast.struct.Library;
import tinto.code.CodeGenerator;
import tinto.code.LibraryCodification;
import tinto.mips.ApplicationAssembler;
import tinto.mips.LibraryAssembler;
import tinto.parser.TintoHeaderParser;
import tinto.parser.TintoParser;
import Grafo.*;

/**
 * Clase que desarrolla el punto de entrada al compilador.
 * 
 * @author Francisco José Moreno Velo
 *
 */
public class TintoCompiler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Busca el archivo "Main.tinto"
		String path = (args.length == 0? System.getProperty("user.dir") : args[0] );
		File workingdir = new File(path);
		File mainfile = new File(workingdir,"Main.tinto");
		if(!mainfile.exists()) return;
		
		// Genera el archivo de salida del compilador
		FileOutputStream fos;
		try{ fos = new FileOutputStream(new File(workingdir,"Application.s"));}
		catch(Exception ex) { ex.printStackTrace(); return; }
		PrintStream stream = new PrintStream(fos);
		ApplicationAssembler.printCommonCode(stream);
		
		// Genera el archivo "Main.s"
		LibraryCodification maincodif = parse(mainfile);
		if(maincodif == null) return; // Error en el fichero Main.tinto
		Grafo g = new Grafo();
		g.generarGrafoInterferencia(maincodif);
		LibraryAssembler mainAssembler = new LibraryAssembler(maincodif);
		mainAssembler.generateFile();
		appendFile(stream,new File(workingdir,"Main.s"));
		
		// Genera una pila con las bibliotecas importadas por "Main.tinto"
		String[] imported = maincodif.getImported();
		Stack<String> stack = new Stack<String>();
		for(int i=0; i<imported.length; i++) stack.push(imported[i]);
		Vector<String> visited = new Vector<String>();
		visited.add("Main");
		
		// Compila recursivamente las bibliotecas importadas por la clase Main
		while(!stack.isEmpty()) {
			String libname = (String) stack.pop();
			if(visited.contains(libname)) continue;
			File file = new File(workingdir,libname+".s");
			if(file.exists()) appendFile(stream,file);
			else {
				File libSource = new File(workingdir,libname+".tinto");
				LibraryCodification libCodif = parse(libSource);
				LibraryAssembler libAssembler = new LibraryAssembler(libCodif);
				libAssembler.generateFile();
				appendFile(stream,new File(workingdir,libname+".s"));
				String[] moreimp = libCodif.getImported();
				for(int i=0; i<moreimp.length; i++) stack.push(moreimp[i]);				
			}
			visited.add(libname);
		}
		
		stream.close();
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public static LibraryCodification parse(File file) {

		try {
			String libname = file.getName().replaceFirst(".tinto", "");
			// Extrae en "library" la cabecera del fichero fuente
			FileInputStream fis = new FileInputStream(file);
			TintoHeaderParser header = new TintoHeaderParser(fis);
			Library library = header.CompilationUnit(libname);
			int errorCount = header.getErrorCount();
			String errMsg = header.getErrorMsg();
			if(library == null) {
				System.err.println(""+errorCount+" errors found:");
				System.err.println(errMsg);
				return null;
			}

			// Obtiene las cabeceras de las clases necesarias
			Hashtable<String,Library> imported = generateImportedLibraries(library);

			// Analiza el contenido de los métodos y constructores
			fis.close();
			fis = new FileInputStream(file);
			TintoParser parser = new TintoParser(fis);
			parser.parse(library,imported);
			errorCount += parser.getErrorCount();
			errMsg += parser.getErrorMsg();

			if(errorCount>0) {
				System.err.println(""+errorCount+" errors found:");
				System.err.println(errMsg);
				return null;
			}

			// Genera el código intermedio de la clase
			CodeGenerator translator = new CodeGenerator();
			LibraryCodification codif = translator.generateLibraryCodification(library);

			// Almacena el código intermedio en un archivo
	 		FileOutputStream fos = new FileOutputStream(library.getName()+".tmc");
	 		PrintStream stream = new PrintStream(fos);
	 		codif.print(stream);
	 		stream.close();

			return codif;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Obtiene la lista de declaraciones de las clases importadas en el
	 * fichero fuente
	 * 
	 * @param decl
	 * @return
	 * @throws OHFSintaxException
	 * @throws SintaxException
	 * @throws IOException
	 */
	private static Hashtable<String,Library> generateImportedLibraries(Library library) 
	throws IOException {
 		String[] imported_name = library.getImported();
 		Hashtable<String,Library> hash = new Hashtable<String,Library>();
 		hash.put(library.getName(),library);
 		Stack<String> stack = new Stack<String>();
 		for(int i=0; i<imported_name.length; i++) {
 			stack.push(imported_name[i]);
 		}
 		while(!stack.empty()) {
 			String libname = (String) stack.pop();
 			if(hash.containsKey(libname)) continue;
 			
 			FileInputStream fis = new FileInputStream(libname+".tinto");
 			TintoHeaderParser header = new TintoHeaderParser(fis);
 			Library lib = header.parse(libname);
 			hash.put(libname,lib);
 			String[] imp = lib.getImported();
 			for(int i=0; i<imp.length; i++) stack.push(imp[i]);
 		}
 		
 		return hash;
	}
	
	/**
	 * Añade el contenido del archivo file al resultado final del
	 * proceso de compilación
	 * @param stream
	 * @param file
	 */
	private static void appendFile(PrintStream stream, File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] content = new byte[fis.available()];
			fis.read(content);
			stream.println();
			stream.write(content);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
