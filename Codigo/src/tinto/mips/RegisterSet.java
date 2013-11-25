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
 * Clase que permite contiene todos los registros del procesador MIPS-32
 * 
 * @author Francisco José Moreno Velo
 */
public class RegisterSet implements RegisterConstants {
	
	public static Register r0 = new Register(GPR_R0);
	public static Register at = new Register(GPR_AT);
	public static Register v0 = new Register(GPR_V0);
	public static Register v1 = new Register(GPR_V1);
	public static Register a0 = new Register(GPR_A0);
	public static Register a1 = new Register(GPR_A1);
	public static Register a2 = new Register(GPR_A2);
	public static Register a3 = new Register(GPR_A3);
	public static Register t0 = new Register(GPR_T0);
	public static Register t1 = new Register(GPR_T1);
	public static Register t2 = new Register(GPR_T2);
	public static Register t3 = new Register(GPR_T3);
	public static Register t4 = new Register(GPR_T4);
	public static Register t5 = new Register(GPR_T5);
	public static Register t6 = new Register(GPR_T6);
	public static Register t7 = new Register(GPR_T7);
	public static Register s0 = new Register(GPR_S0);
	public static Register s1 = new Register(GPR_S1);
	public static Register s2 = new Register(GPR_S2);
	public static Register s3 = new Register(GPR_S3);
	public static Register s4 = new Register(GPR_S4);
	public static Register s5 = new Register(GPR_S5);
	public static Register s6 = new Register(GPR_S6);
	public static Register s7 = new Register(GPR_S7);
	public static Register t8 = new Register(GPR_T8);
	public static Register t9 = new Register(GPR_T9);
	public static Register k0 = new Register(GPR_K0);
	public static Register k1 = new Register(GPR_K1);
	public static Register gp = new Register(GPR_GP);
	public static Register sp = new Register(GPR_SP);
	public static Register fp = new Register(GPR_FP);
	public static Register ra = new Register(GPR_RA);
	
	public static Register f0 = new Register(FPR_0);
	public static Register f1 = new Register(FPR_1);
	public static Register f2 = new Register(FPR_2);
	public static Register f3 = new Register(FPR_3);
	public static Register f4 = new Register(FPR_4);
	public static Register f5 = new Register(FPR_5);
	public static Register f6 = new Register(FPR_6);
	public static Register f7 = new Register(FPR_7);
	public static Register f8 = new Register(FPR_8);
	public static Register f9 = new Register(FPR_9);
	public static Register f10 = new Register(FPR_10);
	public static Register f11 = new Register(FPR_11);
	public static Register f12 = new Register(FPR_12);
	public static Register f13 = new Register(FPR_13);
	public static Register f14 = new Register(FPR_14);
	public static Register f15 = new Register(FPR_15);
	public static Register f16 = new Register(FPR_16);
	public static Register f17 = new Register(FPR_17);
	public static Register f18 = new Register(FPR_18);
	public static Register f19 = new Register(FPR_19);
	public static Register f20 = new Register(FPR_20);
	public static Register f21 = new Register(FPR_21);
	public static Register f22 = new Register(FPR_22);
	public static Register f23 = new Register(FPR_23);
	public static Register f24 = new Register(FPR_24);
	public static Register f25 = new Register(FPR_25);
	public static Register f26 = new Register(FPR_26);
	public static Register f27 = new Register(FPR_27);
	public static Register f28 = new Register(FPR_28);
	public static Register f29 = new Register(FPR_29);
	public static Register f30 = new Register(FPR_30);
	public static Register f31 = new Register(FPR_31);

	
	/**
	 * Obtiene el registro a partir de su código
	 * @param code
	 * @return
	 */
	public static Register getRegister(int code) {
		switch(code) {
			case GPR_R0: return r0;
			case GPR_AT: return at;
			case GPR_V0: return v0;
			case GPR_V1: return v1;
			case GPR_A0: return a0;
			case GPR_A1: return a1;
			case GPR_A2: return a2;
			case GPR_A3: return a3;
			case GPR_T0: return t0;
			case GPR_T1: return t1;
			case GPR_T2: return t2;
			case GPR_T3: return t3;
			case GPR_T4: return t4;
			case GPR_T5: return t5;
			case GPR_T6: return t6;
			case GPR_T7: return t7;
			case GPR_S0: return s0;
			case GPR_S1: return s1;
			case GPR_S2: return s2;
			case GPR_S3: return s3;
			case GPR_S4: return s4;
			case GPR_S5: return s5;
			case GPR_S6: return s6;
			case GPR_S7: return s7;
			case GPR_T8: return t8;
			case GPR_T9: return t9;
			case GPR_K0: return k0;
			case GPR_K1: return k1;
			case GPR_GP: return gp;
			case GPR_SP: return sp;
			case GPR_FP: return fp;
			case GPR_RA: return ra;
			case FPR_0:  return f0;
			case FPR_1:  return f1;
			case FPR_2:  return f2;
			case FPR_3:  return f3;
			case FPR_4:  return f4;
			case FPR_5:  return f5;
			case FPR_6:  return f6;
			case FPR_7:  return f7;
			case FPR_8:  return f8;
			case FPR_9:  return f9;
			case FPR_10: return f10;
			case FPR_11: return f11;
			case FPR_12: return f12;
			case FPR_13: return f13;
			case FPR_14: return f14;
			case FPR_15: return f15;
			case FPR_16: return f16;
			case FPR_17: return f17;
			case FPR_18: return f18;
			case FPR_19: return f19;
			case FPR_20: return f20;
			case FPR_21: return f21;
			case FPR_22: return f22;
			case FPR_23: return f23;
			case FPR_24: return f24;
			case FPR_25: return f25;
			case FPR_26: return f26;
			case FPR_27: return f27;
			case FPR_28: return f28;
			case FPR_29: return f29;
			case FPR_30: return f30;
			case FPR_31: return f31;
			default: return null;
		}
	}
}
