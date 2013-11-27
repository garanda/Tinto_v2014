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
 * Clase que describe la instrucción IF
 * 
 * @author Francisco José Moreno Velo
 */
public class IfStatement extends Statement {

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Condición de la instrucción IF
	 */
	private Expression condition;
	
	/**
	 * Instrucción a desarrollar cuando la condición se cumple
	 */
	private Statement thenInst;
	
	/**
	 * Instrucción a desarrollar cuando la condición no se cumple
	 */
	private Statement elseInst;

	//----------------------------------------------------------------//
	//                            Constructores                       //
	//----------------------------------------------------------------//

	/**
	 * Constructor
	 * @param type
	 */
	public IfStatement(Expression exp, Statement thenInst, Statement elseInst){
		this.condition = exp;
		this.thenInst = thenInst;
		this.elseInst = elseInst;
	}

	//----------------------------------------------------------------//
	//                          Métodos públicos                      //
	//----------------------------------------------------------------//

	/**
	 * Obtiene la condición de la instrucción
	 * @return
	 */
	public Expression getCondition() {
		return this.condition;
	}
	
	/**
	 * Obtiene la instrucción a realizar cuando la condición se cumple
	 * @return
	 */
	public Statement getThenInstruction() {
		return this.thenInst;
	}
	
	/**
	 * Obtiene la instrucción a realizar cuando la condición no se cumple
	 * @return
	 */
	public Statement getElseInstruction() {
		return this.elseInst;
	}
	
	/**
	 * Verifica si la instrucción termina de forma inesperada.
	 * @return
	 */
	public boolean isBroken() {
		if(elseInst == null) return false;
		boolean thenBranch = thenInst.isBroken();
		boolean elseBranch = elseInst.isBroken();			
		return thenBranch && elseBranch;
	}
	
	/**
	 * Verifica si la instrucción alcanza siempre un "return".
	 * @return
	 */
	public boolean returns() {
		if(elseInst == null) return false;
		boolean thenBranch = thenInst.returns();
		boolean elseBranch = elseInst.returns();			
		return thenBranch && elseBranch;
	}
}
