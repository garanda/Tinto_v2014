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

package tinto.code;

import java.io.PrintStream;

/**
 * Clase que describe toda la información asociada a la codificación
 * de una biblioteca de funciones.
 * 
 * @author Francisco José Moreno Velo
 */
public class LibraryCodification  {
	
	/**
	 * Nombre de la clase
	 */
	private String name;
	
	/**
	 * Lista de clases importadas
	 */
	private String[] imported;
	
	
	private boolean Sistema;

	
	
	/**
	 * Lista de métodos de la clase
	 */
	private MethodCodification[] method;
	
	/**
	 * Constructor
	 * @param label
	 */
	public LibraryCodification(String name, String[] imported, int methodcount) {
		this.name = name;
		this.imported = imported;
		this.method = new MethodCodification[methodcount];
		this.Sistema = false;
	}

	/**
	 * Asigna la codificación de un método en la posición indicada
	 * @param index
	 * @param mc
	 */
	public void setMethodCodification(int index, MethodCodification mc) {
		this.method[index] = mc;
	}
	
	/**
	 * Obtiene el nombre de la clase
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Obtiene la lista de clases importadas
	 * @return
	 */
	public String[] getImported() {
		return this.imported;
	}
	
	/**
	 * Obtiene la lista de métodos codificados
	 * @return
	 */
	public MethodCodification[] getMethodCodifications() {
		return this.method;
	}
	
	/**
	 * Escribe el código completo de la clase sobre el flujo
	 * 
	 * @return
	 */
	public void print(PrintStream stream) {
		for(int i=0; i<method.length; i++) {
			stream.println("; %%%%%%%%%%%%%%%");
			stream.println("; "+method[i].getMethodLabel());
			stream.println("; %%%%%%%%%%%%%%%");
			method[i].print(stream);
			stream.println(";");
			stream.println(";");
		}
	}


	/**
	 * @return the sistema
	 */
	public boolean isSistema() {
		return Sistema;
	}

	/**
	 * @param sistema the sistema to set
	 */
	public void setSistema(boolean sistema) {
		Sistema = sistema;
	}
}
