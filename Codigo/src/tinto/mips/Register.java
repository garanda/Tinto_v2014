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
 * Clase que permite referenciar un registro del procesador MIPS-32
 * 
 * @author Francisco José Moreno Velo
 */
public class Register implements RegisterConstants {
	
	/**
	 * Número del registro
	 * 0-31 para los registros de propósito general (GPR)
	 * 32-63 para los registros en coma flotante (FPR) 
	 */
	private int num;

	/**
	 * Constructor basado en el número de registro
	 * @param num
	 */
	public Register(int num) {
		this.num = num;
	}
	
	/**
	 * Obtiene el número de registro
	 * @return
	 */
	public int getCode() {
		return this.num;
	}
	
	/**
	 * Obtiene la representación en ensamblador basada en el
	 * nombre que se asigna a cada registro por convenio.
	 * @return
	 */
	public String getName() {
		switch(num) {
			case GPR_R0: return "$0";
			case GPR_AT: return "$at";
			case GPR_V0: return "$v0";
			case GPR_V1: return "$v1";
			case GPR_A0: return "$a0";
			case GPR_A1: return "$a1";
			case GPR_A2: return "$a2";
			case GPR_A3: return "$a3";
			case GPR_T0: return "$t0";
			case GPR_T1: return "$t1";
			case GPR_T2: return "$t2";
			case GPR_T3: return "$t3";
			case GPR_T4: return "$t4";
			case GPR_T5: return "$t5";
			case GPR_T6: return "$t6";
			case GPR_T7: return "$t7";
			case GPR_S0: return "$s0";
			case GPR_S1: return "$s1";
			case GPR_S2: return "$s2";
			case GPR_S3: return "$s3";
			case GPR_S4: return "$s4";
			case GPR_S5: return "$s5";
			case GPR_S6: return "$s6";
			case GPR_S7: return "$s7";	
			case GPR_T8: return "$t8";
			case GPR_T9: return "$t9";
			case GPR_K0: return "$k0";
			case GPR_K1: return "$k1";
			case GPR_GP: return "$gp";
			case GPR_SP: return "$sp";
			case GPR_FP: return "$fp";
			case GPR_RA: return "$ra";
			case FPR_0:  return "$f0";
			case FPR_1:  return "$f1";
			case FPR_2:  return "$f2";
			case FPR_3:  return "$f3";
			case FPR_4:  return "$f4";
			case FPR_5:  return "$f5";
			case FPR_6:  return "$f6";
			case FPR_7:  return "$f7";
			case FPR_8:  return "$f8";
			case FPR_9:  return "$f9";
			case FPR_10: return "$f10";
			case FPR_11: return "$f11";
			case FPR_12: return "$f12";
			case FPR_13: return "$f13";
			case FPR_14: return "$f14";
			case FPR_15: return "$f15";
			case FPR_16: return "$f16";
			case FPR_17: return "$f17";
			case FPR_18: return "$f18";
			case FPR_19: return "$f19";
			case FPR_20: return "$f20";
			case FPR_21: return "$f21";
			case FPR_22: return "$f22";
			case FPR_23: return "$f23";
			case FPR_24: return "$f24";
			case FPR_25: return "$f25";
			case FPR_26: return "$f26";
			case FPR_27: return "$f27";
			case FPR_28: return "$f28";
			case FPR_29: return "$f29";
			case FPR_30: return "$f30";
			case FPR_31: return "$f31";
			default: return "$"+num;
		}
	}
}
