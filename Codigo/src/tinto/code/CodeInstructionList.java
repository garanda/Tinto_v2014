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


/**
 * Clase que describe una lista de instrucciones
 * 
 * @author Francisco José Moreno Velo
 */
public class CodeInstructionList {
	

	/**
	 * Lista de instrucciones
	 */
	private CodeInstruction[] list;
	
	/**
	 * Constructor
	 *
	 */
	public CodeInstructionList() {
		this.list = new CodeInstruction[0];
	}
	
	/**
	 * Obtiene la lista de instrucciones
	 * @return
	 */
	public CodeInstruction[] getList() {
		return this.list;
	}
	
	/**
	 * Añade una nueva instrucción a la lista
	 * 
	 * @param inst Instrucción a añadir
	 */
	public void addInstruction(CodeInstruction inst) {
		CodeInstruction[] aux = new CodeInstruction[list.length+1];
		System.arraycopy(list,0,aux,0,list.length);
		aux[list.length] = inst;
		list = aux;
	}

	/**
	 * Añade una lista de instrucciones a esta lista
	 * 
	 * @param instlist Lista de instrucciones a añadir
	 */
	public void addInstructionList(CodeInstruction[] instlist) {
		CodeInstruction[] aux = new CodeInstruction[list.length+instlist.length];
		System.arraycopy(list,0,aux,0,list.length);
		System.arraycopy(instlist,0,aux,list.length,instlist.length);
		list = aux;
	}
	
}
