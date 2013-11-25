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
 * Clase que describe una instrucción con dos registros y una etiqueta
 * 
 * @author Francisco José Moreno Velo
 */
public class RRLInstruction extends Instruction {
	
	/**
	 * Referencia al primer registro
	 */
	private Register reg1;
	
	/**
	 * Referencia al segundo registro
	 */
	private Register reg2;

	/**
	 * Etiqueta
	 */
	private String label;
	
	/**
	 * Constructor basado en las referencias a los registros
	 * @param num
	 */
	public RRLInstruction(int code, Register r1, Register r2, String lb) {
		super(code);
		this.reg1 = r1;
		this.reg2 = r2;
		this.label = lb;
	}
	
	/**
	 * Constructor basado en los códigos de los registros
	 * @param num
	 */
	public RRLInstruction(int code, int s1, int s2, String lb) {
		super(code);
		this.reg1 = new Register(s1);
		this.reg2 = new Register(s2);
		this.label = lb;
	}
	
	/**
	 * Obtiene el valor de la etiqueta
	 * @return
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * Obtiene la referencia al primer registro
	 * @return
	 */
	public Register getRegister1() {
		return this.reg1;
	}
	
	/**
	 * Obtiene la referencia al segundo registro
	 * @return
	 */
	public Register getRegister2() {
		return this.reg2;
	}
	
	/**
	 * Obtiene la representación de la instrucción en ensamblador
	 * @return
	 */
	public String getAssembler() {
		String asm = "\t"+getInstructionName();
		asm += " "+reg1.getName();
		asm += " "+reg2.getName();
		asm += " "+label;
		return asm;
	}

}
