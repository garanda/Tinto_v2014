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

package tinto.ast.statement;

import tinto.ast.expression.Expression;

/**
 * Clase que describe la instrucción SWITCH
 * 
 * @author Pedro Morón Fernández
 */
public class SwitchStatement extends Statement {
	
	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Condición de la instrucción SWITCH
	 */
	private Expression expresion;
	
	/**
	 * Lista de bloques case
	 */
	private DefaultCaseStatement[] listcase;
	
	/**
	 * Bloque de instrucciones default
	 */
	private boolean othercase;

	//----------------------------------------------------------------//
	//                            Constructores                       //
	//----------------------------------------------------------------//

	/**
	 * Constructor
	 * @param type
	 */
	public SwitchStatement(Expression exp){
		this.expresion = exp;
		this.listcase = new DefaultCaseStatement[0];
		this.othercase = false;
	}

	//----------------------------------------------------------------//
	//                          Métodos públicos                      //
	//----------------------------------------------------------------//

	/**
	 * Obtiene la expresion de la instrucción
	 * @return
	 */
	public Expression getExpression() {
		return this.expresion;
	}
	
	/**
	 * Obtiene todos los bloques case
	 * @return
	 */
	public DefaultCaseStatement[] getBloquesCase() {
		return this.listcase;
	}
	
	/**
	 * Obtiene el bloque default
	 * @return
	 */
	public boolean haveDefault() {
		return this.othercase;
	}
	
	/**
	 * Añade una clausula case en caso de que no exista una con el mismo valor
	 * 
	*/
	public void addCase(DefaultCaseStatement onecase) {
		if (!contains(onecase)) {
			DefaultCaseStatement[] temp = new DefaultCaseStatement[listcase.length+1];
			System.arraycopy(listcase, 0, temp, 0, listcase.length);
			temp[listcase.length+1] = onecase;
			listcase = temp;
		}	
	}
	
	/**
	 * Añade una clausula default en caso de no haberla
	 * 
	*/
	public void addDefault(DefaultCaseStatement onecase) {
		if (!othercase) {
			othercase = true;
			DefaultCaseStatement[] temp = new DefaultCaseStatement[listcase.length+1];
			System.arraycopy(listcase, 0, temp, 0, listcase.length);
			temp[listcase.length+1] = onecase;
			listcase = temp;
		}
	}
	
	/**
	 * Verifica si ya hay una clausula case con ese valor
	 * @return
	*/
	public boolean contains(DefaultCaseStatement onecase) {
		for (int i=0; i<listcase.length; i++) if (listcase[i].isEqual(onecase)) return true;
		return false;
	}
	
	/**
	 * Verifica si la instrucción termina de forma inesperada.
	 * @return
	 */
	public boolean isBroken() {
		return false;
	}
	
	/**
	 * Verifica si la instrucción alcanza siempre un "return".
	 * @return
	 */
	public boolean returns() {		
		return false;
	}
}
