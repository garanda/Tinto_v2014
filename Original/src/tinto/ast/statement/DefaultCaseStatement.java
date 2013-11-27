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

import tinto.ast.expression.IntegerLiteralExpression;

/**
 * Clase que describe la instrucción CASE
 * 
 * @author Pedro Morón Fernández
 */
public class DefaultCaseStatement extends Statement {
	
	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Expresion de la instrucción CASE
	 */
	private IntegerLiteralExpression value;
	
	/**
	 * Instrucción a desarrollar cuando la expresion se cumple
	 */
	private Statement instruction;

	//----------------------------------------------------------------//
	//                            Constructores                       //
	//----------------------------------------------------------------//

	/**
	 * Constructor
	 * @param type
	 */
	public DefaultCaseStatement(IntegerLiteralExpression value, Statement inst){
		this.value = value;
		this.instruction = inst;
	}

	//----------------------------------------------------------------//
	//                          Métodos públicos                      //
	//----------------------------------------------------------------//

	/**
	 * Obtiene la expresion de la instrucción
	 * @return
	 */
	public IntegerLiteralExpression getValue() {
		return this.value;
	}
	
	/**
	 * Obtiene la instrucción a realizar cuando la expresion se cumple
	 * @return
	 */
	public Statement getInstruction() {
		return this.instruction;
	}
	
	public boolean isEqual(DefaultCaseStatement onecase) {
		return this.value.getValue() == onecase.getValue().getValue();
	}
	
	/**
	 * Verifica si la instrucción termina de forma inesperada.
	 * @return
	 */
	public boolean isBroken() {
		return instruction.isBroken();
	}
	
	/**
	 * Verifica si la instrucción alcanza siempre un "return".
	 * @return
	 */
	public boolean returns() {
		return instruction.returns();
	}
}
