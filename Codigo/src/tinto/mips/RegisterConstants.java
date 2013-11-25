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
 * Interfaz que asocia los nombres y los números de los registros del 
 * procesador MIPS-32 
 * 
 * @author Francisco José Moreno Velo
 */
public interface RegisterConstants {
	
	//--------------------------------------------------------------//
	//					Registros de propósito general				//
	//--------------------------------------------------------------//
	
	public int GPR_R0 = 0;
	public int GPR_AT = 1;
	public int GPR_V0 = 2;
	public int GPR_V1 = 3;
	public int GPR_A0 = 4;
	public int GPR_A1 = 5;
	public int GPR_A2 = 6;
	public int GPR_A3 = 7;
	public int GPR_T0 = 8;
	public int GPR_T1 = 9;
	public int GPR_T2 = 10;
	public int GPR_T3 = 11;
	public int GPR_T4 = 12;
	public int GPR_T5 = 13;
	public int GPR_T6 = 14;
	public int GPR_T7 = 15;
	public int GPR_S0 = 16;
	public int GPR_S1 = 17;
	public int GPR_S2 = 18;
	public int GPR_S3 = 19;
	public int GPR_S4 = 20;
	public int GPR_S5 = 21;
	public int GPR_S6 = 22;
	public int GPR_S7 = 23;
	public int GPR_T8 = 24;
	public int GPR_T9 = 25;
	public int GPR_K0 = 26;
	public int GPR_K1 = 27;
	public int GPR_GP = 28;
	public int GPR_SP = 29;
	public int GPR_FP = 30;
	public int GPR_RA = 31;
	
	//--------------------------------------------------------------//
	//					Registros en coma flotante					//
	//--------------------------------------------------------------//

	public int FPR_0 = 32;
	public int FPR_1 = 33;
	public int FPR_2 = 34;
	public int FPR_3 = 35;
	public int FPR_4 = 36;
	public int FPR_5 = 37;
	public int FPR_6 = 38;
	public int FPR_7 = 39;
	public int FPR_8 = 40;
	public int FPR_9 = 41;
	public int FPR_10 = 42;
	public int FPR_11 = 43;
	public int FPR_12 = 44;
	public int FPR_13 = 45;
	public int FPR_14 = 46;
	public int FPR_15 = 47;
	public int FPR_16 = 48;
	public int FPR_17 = 49;
	public int FPR_18 = 50;
	public int FPR_19 = 51;
	public int FPR_20 = 52;
	public int FPR_21 = 53;
	public int FPR_22 = 54;
	public int FPR_23 = 55;
	public int FPR_24 = 56;
	public int FPR_25 = 57;
	public int FPR_26 = 58;
	public int FPR_27 = 59;
	public int FPR_28 = 60;
	public int FPR_29 = 61;
	public int FPR_30 = 62;
	public int FPR_31 = 63;
	
}
