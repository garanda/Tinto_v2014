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
import tinto.ast.struct.Method;
import tinto.ast.struct.Program;
import tinto.ast.struct.Variable;
import tinto.code.CodeConstants;
import tinto.code.CodeGenerator;
import tinto.code.CodeInstruction;
import tinto.code.CodeInstructionList;
import tinto.code.CodeLiteral;
import tinto.code.LibraryCodification;
import tinto.code.MethodCodification;
import tinto.code.ProgramCodification;
import tinto.interm.ProgramIntermediate;
import tinto.mips.ApplicationAssembler;
import tinto.mips.LibraryAssembler;
import tinto.parser.TintoHeaderParser;
import tinto.parser.TintoParser;

/**
 * Clase que desarrolla el punto de entrada al compilador.
 * 
 */
@SuppressWarnings("rawtypes")
public class TintoCompiler_G {

	static String PATH = "";
	
	
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		if (args.length == 0) {
			System.out.println("Error: Debe indicar un fichero.");
			System.exit(-1);
		}
		// Busca el archivo Principal!!
		String path = args[0];
		File mainfile = new File(path);
		if(!mainfile.exists()) { 
			System.out.println("Error: El fichero no existe");
			return;
		}
		String workingdir = mainfile.getPath().substring(0, mainfile.getPath().lastIndexOf(mainfile.getName()));
		String mainfileName = mainfile.getName().substring(0,mainfile.getName().lastIndexOf("."));

		PATH = workingdir;
		
		
		// Archivo general de salida Application.s
		PrintStream stream = ApplicationFile();
		
		Program Libraries = parseHeader(args[0]);
		parse(Libraries);
		
		ProgramCodification PC = generateIntermediate(Libraries);

		PC.setVisited();
		
		PC.printVisited();
		
		
		
		ProgramIntermediate PI = new ProgramIntermediate(PC);
		

		PI.setVisited();
		
		//PI.print();
		
		
		
		generateAssembler(stream, PC);

		List lista = generateAssemblerList(PC);
		
		Iterator ii = lista.iterator();
		while (ii.hasNext()) {
			String i = (String)ii.next();
			
			System.out.println(i);
			
		}
		
		
		
		stream.close();
	}
	
	
	private static Program parseHeader(String Main) throws IOException {

		Program devolverx = new Program(); 
		List<String> Cerrados = new ArrayList<String>();
		List<String> Abiertos = new ArrayList<String>(); 
		
		
		Abiertos.add(Main);		
		while (! Abiertos.isEmpty()) {
			String Actual = Abiertos.remove(0);
			Cerrados.add(Actual);
			Library library = processHeader(Actual);
			if (Actual.equalsIgnoreCase(Main))
				devolverx.setMain(library);
			else 
				devolverx.addLibrary(library);
			
			for (int i=0;i<library.getImported().length;i++) {
				String mia = library.getImported()[i];
				
				if (!Cerrados.contains(mia) && !Abiertos.contains(mia)) {
					Abiertos.add(mia);
				}
			}					
		}					
		
		return devolverx;

	}
	
	private static void parse(Program _prog) throws IOException {
		
		// Esto no se puede hacer antes porque no se saben todas las librerias
		List<Library> L2 =  _prog.getAllLibraries();
		Iterator<Library> it = L2.iterator();
		while(it.hasNext()) {
			Library l = it.next(); 
			Hashtable<String,Library> imported = new Hashtable<String,Library>();
			if (!l.isSystem()) {
				String[] lstr = l.getImported();
				for (int j=0;j<lstr.length;j++) {
					Library imp = _prog.getLibrary(lstr[j]);
					imported.put(lstr[j], imp);
				}
				
				File file = new File(PATH,l.getName()+".tinto");
				FileInputStream fis = new FileInputStream(file);
				TintoParser parser = new TintoParser(fis);
				
				parser.parse(l,imported);
				int errorCount = parser.getErrorCount();
				String errMsg = parser.getErrorMsg();
				
				if(errorCount>0) {
					System.err.println(""+errorCount+" errors found:");
					System.err.println(errMsg);
				}
			}
			
			
		}

	}
	
	
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public static ProgramCodification generateIntermediate(Program _prog) {
		
		ProgramCodification codif = new ProgramCodification();

		// Genera el código intermedio de la clase
		CodeGenerator translator = new CodeGenerator();
		codif.setMainCodification(translator.generateLibraryCodification(_prog.getMain()));

		List<Library> libs = _prog.getLibraries();
		Iterator<Library> it = libs.iterator();
		while (it.hasNext()) {
			Library l = it.next();
			
			if (! l.isSystem()) {
				translator = new CodeGenerator();
				LibraryCodification lc = translator.generateLibraryCodification(l);
				codif.addLibraryCodification(lc);
//				try {	
//					// Almacena el código intermedio en un archivo
//					File tmc = new File(PATH,l.getName()+".tmc");
//					FileOutputStream fos = new FileOutputStream(tmc);
//					PrintStream stream = new PrintStream(fos);
//					lc.print(stream);
//					stream.close();
//	
//				} catch (Exception ex) {
//					ex.printStackTrace();
//					return null;
//				}
			} else {
				LibraryCodification LC = new LibraryCodification(l.getName(), l.getImported(), l.getMethods().length);
				LC.setSistema(true);
				for (int i=0;i<l.getMethods().length;i++) {
					Method m = l.getMethods()[i];
					Variable[] v = {};
					MethodCodification mc = new MethodCodification(m.getLabel(), m.getType(), v, v); 
					CodeLiteral result = new CodeLiteral(0);
					CodeInstructionList cil = new CodeInstructionList();
					cil.addInstruction(new CodeInstruction(CodeConstants.RETURN,result,null,null));
					mc.setInstructionList(cil);
					LC.setMethodCodification(i, mc);
				}
				codif.addLibraryCodification(LC);
			}
		}
		return codif;
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
	









	
	private static PrintStream ApplicationFile(){
		FileOutputStream fos;
		try{ 
			fos = new FileOutputStream(new File(PATH,"Application.s"));
		} catch(Exception ex) { 
			ex.printStackTrace(); 
			return null; 
		}
		PrintStream stream = new PrintStream(fos);
		ApplicationAssembler.printCommonCode(stream);
		return stream;
	}
	
	private static Library processHeader(String _lib) {
		Library library = null;
		
		try {
			// Extrae en "library" la cabecera del fichero fuente
			boolean sist = false;
			File Aux = new File(PATH, _lib+".tinto");
			if (!Aux.exists()) {
				 Aux = new File(PATH, _lib+".h");
				 if (!Aux.exists()) {
					 return null;
				 } else 
					 sist = true;
			}
			FileInputStream fis = new FileInputStream(Aux);
			TintoHeaderParser header = new TintoHeaderParser(fis);
			library = header.CompilationUnit(_lib);
			int errorCount = header.getErrorCount();
			String errMsg = header.getErrorMsg();
			if(library == null) {
				System.err.println(""+errorCount+" errors found:");
				System.err.println(errMsg);
				return null;
			} 
			library.setSystem(sist);			
			
		} catch (Exception ee) {
			return null;
		}
		return library;

	}
	
	private static void generateAssembler(PrintStream stream, ProgramCodification PC) {
		/***
		 * GENERACION FINAL EN FICHEROS
		 */
		
		LibraryAssembler mainAssembler = new LibraryAssembler(PC.getMainLibraryCodification());
		mainAssembler.addFile(stream);
		
		Iterator<LibraryCodification> it = PC.getLibrariesCodification().iterator();
		while (it.hasNext()) {
			LibraryCodification LC = it.next();
			if (LC.isSistema()) {
				// Abrir fichero .s y a–adirlo al stream				
				appendFile(stream, new File(PATH,LC.getName()+".s"));	
				
			} else {
				mainAssembler = new LibraryAssembler(LC);
				mainAssembler.addFile(stream);
			}
		}

	}
	private static List generateAssemblerList(ProgramCodification PC) {

		/***
		 * GENERACION FINAL EN LISTA DE INSTRUCCIONES
		 */

		List Programa = new LinkedList();
		
		
		LibraryAssembler mainAssembler = new LibraryAssembler(PC.getMainLibraryCodification());
		mainAssembler.addFile(Programa);
		
		Iterator<LibraryCodification> it = PC.getLibrariesCodification().iterator();
		while (it.hasNext()) {
			LibraryCodification LC = it.next();
			if (LC.isSistema()) {
				// Abrir fichero .s y a–adirlo al stream				
				//appendFile(stream, new File(PATH,LC.getName()+".s"));	
				
			} else {
				mainAssembler = new LibraryAssembler(LC);
				mainAssembler.addFile(Programa);
			}
		}
		
		
		return Programa;

	}
}
