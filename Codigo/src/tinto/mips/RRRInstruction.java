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

/**
 * Clase que describe una instrucción sobre tres registros
 * 
 * @author Francisco José Moreno Velo
 */
public class RRRInstruction extends Instruction {
	
	/**
	 * Referencia al registro de destino de la instrucción
	 */
	private Register target;
	
	/**
	 * Referencia al registro del primer operando
	 */
	private Register source1;
	
	/**
	 * Referencia al registro del segundo operando
	 */
	private Register source2;
	
	/**
	 * Constructor basado en las referencias a los registros
	 * @param num
	 */
	public RRRInstruction(int code, Register target, Register s1, Register s2) {
		super(code);
		this.target = target;
		this.source1 = s1;
		this.source2 = s2;
	}
	
	/**
	 * Constructor basado en los códigos de los registros
	 * @param num
	 */
	public RRRInstruction(int code, int target, int s1, int s2) {
		super(code);
		this.target = new Register(target);
		this.source1 = new Register(s1);
		this.source2 = new Register(s2);
	}
	
	/**
	 * Obtiene la referencia al registro de destino
	 * @return
	 */
	public Register getTarget() {
		return this.target;
	}

	/**
	 * Obtiene la referencia al registro del primer operando
	 * @return
	 */
	public Register getSource1() {
		return this.source1;
	}
	
	/**
	 * Obtiene la referencia al registro del segundo operando
	 * @return
	 */
	public Register getSource2() {
		return this.source2;
	}
	
	/**
	 * Obtiene la representación de la instrucción en ensamblador
	 * @return
	 */
	public String getAssembler() {
		String asm = "\t"+getInstructionName();
		asm += " "+target.getName();
		asm += " "+source1.getName();
		asm += " "+source2.getName();
		return asm;
	}

}
