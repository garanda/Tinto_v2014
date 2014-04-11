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

package tinto.mips;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import tinto.code.MethodCodification;
import tinto.code.LibraryCodification;

/**
 * Clase que contiene la descripción de una biblioteca en ensamblador
 * 
 * @author Francisco José Moreno Velo
 */
public class LibraryAssembler  {
	
	/**
	 * Nombre de la biblioteca
	 */
	private String name;
	
	/**
	 * Lista de métodos de la biblioteca
	 */
	private MethodAssembler[] method;

	/**
	 * Constructor
	 * @param libCodif
	 */
	public LibraryAssembler(LibraryCodification libCodif) {
		this.name = libCodif.getName();
		MethodCodification[] methodCodif = libCodif.getMethodCodifications();
		
		this.method = new MethodAssembler[methodCodif.length];
		for(int i=0; i<methodCodif.length; i++) {
			method[i] = new MethodAssembler(methodCodif[i]);
		}	
	}

	/**
	 * Genera el fichero ensamblador
	 */
	public void generateFile() {
		FileOutputStream fos;
		try { fos = new FileOutputStream(this.name+".s"); }
		catch(Exception ex) { ex.printStackTrace(); return; }
		PrintStream stream = new PrintStream(fos);

		printCopyright(stream);

		for(int i=0; i<method.length; i++) {
			method[i].print(stream);
		}
		stream.close();
	}
	
	
	/**
	 * Escribe el copyright en el flujo
	 * @param stream
	 */
	private void printCopyright(PrintStream stream) {
		stream.println("#------------------------------------------------------------------");
		stream.println("# Copyright (c) 2010, Francisco José Moreno Velo                   ");
		stream.println("# All rights reserved.                                             ");
		stream.println("#------------------------------------------------------------------");
	}
	
	/**
	 * Genera el fichero ensamblador volcandolo en un stream
	 */
	public void addFile(List ps) {
		for(int i=0; i<method.length; i++) {
			if (method[i] != null)
				method[i].print(ps);
		}
	}
	

	/**
	 * Genera el fichero ensamblador volcandolo en un stream
	 */
	public void addFile(PrintStream ps) {
		for(int i=0; i<method.length; i++) {
			if (method[i] != null)
				method[i].print(ps);
		}
	}
	

}
