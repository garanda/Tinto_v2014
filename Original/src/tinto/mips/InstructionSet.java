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
 * Interfaz que describe los códigos de todas las instrucciones del
 * procesador MIPS-32
 * 
 * @author Francisco José Moreno Velo
 */
public interface InstructionSet {
	
	/** Etiqueta */
	public int LABEL = 1;
		
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	//					CPU Load and Store Instructions					//
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	
	//------------------------------------------------------------------//
	// 				  Aligned CPU Load/Store Instructions				//
	//------------------------------------------------------------------//
	
	/** Load Byte */
	public int LB = 2;
	
	/** Load Byte Unsigned */
	public int LBU = 3;
	
	/** Load Halfword */
	public int LH = 4;
	
	/** Load Halfword Unsigned */
	public int LHU = 5;
	
	/** Load Word */
	public int LW = 6;
	
	/** Store Byte */ 
	public int SB = 7;
	
	/** Store Halfword */
	public int SH = 8;
	
	/** Store Word */
	public int SW = 9;
	
	//------------------------------------------------------------------//
	// 			  Unaligned CPU Load and Store Instructions				//
	//------------------------------------------------------------------//
	
	/** Load Word Left */
	public int LWL = 10;
	
	/** Load Word Right */
	public int LWR = 11;
	
	/** Store Word Left */
	public int SWL = 12;
	
	/** Store Word Right */
	public int SWR = 13;

	//------------------------------------------------------------------//
	// 			  Atomic Update CPU Load and Store Instructions			//
	//------------------------------------------------------------------//

	/** Load Linked Word */
	public int LL = 14;
	
	/** Store Conditional Word */
	public int SC = 15;

	//------------------------------------------------------------------//
	// 			     Coprocessor Load and Store Instructions			//
	//------------------------------------------------------------------//

	/** Load Doubleword to Coprocessor 1 */
	public int LDC1 = 16;
	
	/** Load Doubleword to Coprocessor 2 */
	public int LDC2 = 17;
	
	/** Load Word to Coprocessor 1 */
	public int LWC1 = 18;
	
	/** Load Word to Coprocessor 2 */
	public int LWC2 = 19;
	
	/** Store Doubleword from Coprocessor 1 */
	public int SDC1 = 20;
	
	/** Store Doubleword from Coprocessor 2 */
	public int SDC2 = 21;
	
	/** Store Word from Coprocessor 1 */
	public int SWC1 = 22;
	
	/** Store Word from Coprocessor 2 */
	public int SWC2 = 23;

	//------------------------------------------------------------------//
	//FPU Load and Store Instructions Using Register+Register Addressing//
	//------------------------------------------------------------------//

	/** Load Word Indexed to Floating Point */
	public int LWXC1 = 24;
	
	/** Store Word Indexed from Floating Point */
	public int SWXC1 = 25;
	
	/** Load Doubleword Indexed to Floating Point */
	public int LDXC1 = 26;
	
	/** Store Doubleword Indexed from Floating Point */
	public int SDXC1 = 27;
	
	/** Load Doubleword Indexed Unaligned to Floating Point */
	public int LUXC1 = 28;
	
	/** Store Doubleword Indexed Unaligned from Floating Point */
	public int SUXC1 = 29;
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	//						Computational Instructions					//
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

	//------------------------------------------------------------------//
	//				ALU Instructions With an Immediate Operand			//
	//------------------------------------------------------------------//

	/** Add Immediate Word */
	public int ADDI = 30;
	
	/** Add Immediate Unsigned Word */
	public int ADDIU = 31;
	
	/** And Immediate */
	public int ANDI = 32;
	
	/** Load Upper Immediate */
	public int LUI = 33;
	
	/** Or Immediate */
	public int ORI = 34;
	
	/** Set on Less Than Immediate */
	public int SLTI = 35;
	
	/** Set on Less Than Immediate Unsigned */
	public int SLTIU = 36;
	
	/** Exclusive Or Immediate */
	public int XORI = 37;
	
	//------------------------------------------------------------------//
	//					  Three-Operand ALU Instructions				//
	//------------------------------------------------------------------//
	
	/** Add Word */
	public int ADD = 38;
	
	/** Add Unsigned Word */
	public int ADDU = 39;
	
	/** And */
	public int AND = 40;
	
	/** Nor */
	public int NOR = 41;
	
	/** Or */ 
	public int OR = 42;
	
	/** Set on Less Than */
	public int SLT = 43;
	
	/** Set on Less Than Unsigned */
	public int SLTU = 44;
	
	/** Subtract Word */
	public int SUB = 45;
	
	/** Subtract Unsigned Word */
	public int SUBU = 46;
	
	/** Exclusive Or */
	public int XOR = 47;
	
	//------------------------------------------------------------------//
	//					   Two-Operand ALU Instructions					//
	//------------------------------------------------------------------//
	
	/** Count Leading Ones in Word */
	public int CLO = 48;
	
	/** Count Leading Zeros in Word */
	public int CLZ = 49;
	
	//------------------------------------------------------------------//
	//					   		Shift Instructions						//
	//------------------------------------------------------------------//
	
	/** Rotate Word Right */
	public int ROTR = 50;
	
	/** Rotate Word Right Variable */
	public int ROTRV = 51;
	
	/** Shift Word Left Logical */
	public int SLL = 52;
	
	/** Shift Word Left Logical Variable */
	public int SLLV = 53;
	
	/** Shift Word Right Arithmetic */
	public int SRA = 54;
	
	/** Shift Word Right Arithmetic Variable */
	public int SRAV = 55;
	
	/** Shift Word Right Logical */
	public int SRL = 56;
	
	/** Shift Word Right Logical Variable */
	public int SRLV = 57;
	
	//------------------------------------------------------------------//
	//					   	Multiply/Divide Instructions				//
	//------------------------------------------------------------------//
	
	/** Divide Word */
	public int DIV = 58;
	
	/** Divide Unsigned Word */
	public int DIVU = 59;
	
	/** Multiply and Add Word */
	public int MADD = 60;
	
	/** Multiply and Add Word Unsigned */
	public int MADDU = 61;
	
	/** Move From HI */ 
	public int MFHI = 62;
	
	/** Move From LO */
	public int MFLO = 63;
	
	/** Multiply and Subtract Word */
	public int MSUB = 64;
	
	/** Multiply and Subtract Word Unsigned */
	public int MSUBU = 65;
	
	/** Move To HI */ 
	public int MTHI = 66;
	
	/** Move To LO */
	public int MTLO = 67;
	
	/** Multiply Word to Register */
	public int MUL = 68;
	
	/** Multiply Word */
	public int MULT = 69;
	
	/** Multiply Unsigned Word */
	public int MULTU = 70;
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	//					   Jump and Branch Instructions					//
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

	//------------------------------------------------------------------//
	//			Unconditional Jump Within a 256 Megabyte Region			//
	//------------------------------------------------------------------//

	/** Jump 256 Megabyte Region */
	public int J = 71;
	
	/** Jump and Link 256 Megabyte Region */
	public int JAL = 72;
	
	/** Jump and Link Register Absolute Address */
	public int JALR = 73;
	
	/** Jump and Link Register with Hazard Barrier Absolute Address */
	public int JALR_HB = 74;
	
	/** Jump and Link Exchange Absolute Address */
	public int JALX = 75;
	
	/** Jump Register Absolute Address */
	public int JR = 76;
	
	/** Jump Register with Hazard Barrier Absolute Address */
	public int JR_HB = 77;
	
	//------------------------------------------------------------------//
	//PC-Relative Conditional Branch Instructions Comparing Two Registers//
	//------------------------------------------------------------------//
	
	/** Branch on Equal */
	public int BEQ = 78;
	
	/** Branch on Not Equal */
	public int BNE = 79;
	
	//------------------------------------------------------------------//
	//	PC-Relative Conditional Branch Instructions Comparing With Zero	//
	//------------------------------------------------------------------//
	
	/** Branch on Greater Than or Equal to Zero */
	public int BGEZ = 80;
	
	/** Branch on Greater Than or Equal to Zero and Link */
	public int BGEZAL = 81;
	
	/** Branch on Greater Than Zero */
	public int BGTZ = 82;
	
	/** Branch on Less Than or Equal to Zero */
	public int BLEZ = 83;
	
	/** Branch on Less Than Zero */
	public int BLTZ = 84;
	
	/** Branch on Less Than Zero and Link */
	public int BLTZAL = 85;
	
	//------------------------------------------------------------------//
	//				Deprecated Branch Likely Instructions				//
	//------------------------------------------------------------------//
	
	/** Branch on Equal Likely */
	public int BEQL = 86;
	
	/** Branch on Greater Than or Equal to Zero and Link Likely */
	public int BGEZALL = 87;
	
	/** Branch on Greater Than or Equal to Zero Likely */
	public int BGEZL = 88;
	
	/** Branch on Greater Than Zero Likely */
	public int BGTZL = 89;
	
	/** Branch on Less Than or Equal to Zero Likely */
	public int BLEZL = 90;
	
	/** Branch on Less Than Zero and Link Likely */
	public int BLTZALL = 91;
	
	/** Branch on Less Than Zero Likely */
	public int BLTZL = 92;
	
	/** Branch on Not Equal Likely */
	public int BNEL = 93;
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	//						Miscellaneous Instructions					//
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

	//------------------------------------------------------------------//
	//						Serialization Instruction					//
	//------------------------------------------------------------------//

	/** Synchronize Shared Memory */
	public int SYNC = 94;
	
	/** Synchronize Caches to Make Instruction Writes Effective */
	public int SYNCI = 95;
	
	//------------------------------------------------------------------//
	//					System Call and Breakpoint Instructions			//
	//------------------------------------------------------------------//

	/** Breakpoint */
	public int BREAK = 96;
	
	/** System Call */
	public int SYSCALL = 97;
	
	//------------------------------------------------------------------//
	//		Trap-on-Condition Instructions Comparing Two Registers		//
	//------------------------------------------------------------------//

	/** Trap if Equal */
	public int TEQ = 98;
	
	/** Trap if Greater Than or Equal */
	public int TGE = 99;
	
	/** Trap if Greater Than or Equal Unsigned */
	public int TGEU = 100;
	
	/** Trap if Less Than */
	public int TLT = 101;
	
	/** Trap if Less Than Unsigned */
	public int TLTU = 102;
	
	/** Trap if Not Equal */
	public int TNE = 103;
	
	//------------------------------------------------------------------//
	//	  Trap-on-Condition Instructions Comparing an Immediate Value	//
	//------------------------------------------------------------------//

	/** Trap if Equal Immediate */
	public int TEQI = 104;
	
	/** Trap if Greater Than or Equal Immediate */
	public int TGEI = 105;
	
	/** Trap if Greater Than or Equal Immediate Unsigned */
	public int TGEIU = 106;
	
	/** Trap if Less Than Immediate */
	public int TLTI = 107;
	
	/** Trap if Less Than Immediate Unsigned */
	public int TLTIU = 108;
	
	/** Trap if Not Equal Immediate */
	public int TNEI = 109;
	
	//------------------------------------------------------------------//
	//				   CPU Conditional Move Instructions				//
	//------------------------------------------------------------------//

	/** Move Conditional on Floating Point False */
	public int MOVF = 110;
	
	/** Move Conditional on Not Zero */
	public int MOVN = 111;
	
	/** Move Conditional on Floating Point True */
	public int MOVT = 112;
	
	/** Move Conditional on Zero */
	public int MOVZ = 113;
	
	//------------------------------------------------------------------//
	//	  					Prefetch Instructions						//
	//------------------------------------------------------------------//

	/** Prefetch Register+Offset */
	public int PREF = 114;
	
	/** Prefetch Indexed Register+Register */
	public int PREFX = 115;
	
	//------------------------------------------------------------------//
	//	  					  NOP Instructions							//
	//------------------------------------------------------------------//

	/** No Operation */
	public int NOP = 116;
	
	/** Superscalar Inhibit NOP */
	public int SSNOP = 117;
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	//						    FPU Instructions						//
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

	//------------------------------------------------------------------//
	//					FPU Move To and From Instructions				//
	//------------------------------------------------------------------//
	
	/** Move Control Word From Floating Point */
	public int CFC1 = 118;
	
	/** Move Control Word To Floating Point */
	public int CTC1 = 119;
	
	/** Move Word From Floating Point */
	public int MFC1 = 120;
	
	/** Move Word from High Half of Floating Point Register */
	public int MFHC1 = 121;
	
	/** Move Word To Floating Point */
	public int MTC1 = 122;
	
	/** Move Word to High Half of Floating Point Register */
	public int MTHC1 = 123;
		
	//------------------------------------------------------------------//
	//					  FPU IEEE Arithmetic Operations				//
	//------------------------------------------------------------------//

	/** Floating Point Absolute Value (Single precision) */
	public int ABS_S = 124;

	/** Floating Point Absolute Value (Double precision) */
	public int ABS_D = 125;

	/** Floating Point Absolute Value (Paired Single) */
	public int ABS_PS = 126;
	
	/** Floating Point Add (Single precision) */
	public int ADD_S = 127;

	/** Floating Point Add (Double precision) */
	public int ADD_D = 128;

	/** Floating Point Add (Paired Single) */
	public int ADD_PS = 129;
	
	/** Floating Point Divide (Single precision) */
	public int DIV_S = 130;
	
	/** Floating Point Divide (Double precision) */
	public int DIV_D = 131;

	/** Floating Point Multiply (Single precision) */
	public int MUL_S = 132;

	/** Floating Point Multiply (Double precision) */
	public int MUL_D = 133;

	/** Floating Point Multiply (Paired Single) */
	public int MUL_PS = 134;
	
	/** Floating Point Negate (Single precision) */
	public int NEG_S = 135;
	
	/** Floating Point Negate (Double precision) */
	public int NEG_D = 136;

	/** Floating Point Negate (Paired Single) */
	public int NEG_PS = 137;
	
	/** Floating Point Square Root (Single precision) */
	public int SQRT_S = 138;
	
	/** Floating Point Square Root (Double precision) */
	public int SQRT_D = 139;
	
	/** Floating Point Subtract (Single precision) */
	public int SUB_S = 140;

	/** Floating Point Subtract (Double precision) */
	public int SUB_D = 141;

	/** Floating Point Subtract (Paired Single) */
	public int SUB_PS = 142;

	//------------------------------------------------------------------//
	//						FPU Comparing Operations					//
	//------------------------------------------------------------------//

	/** Floating Point Compare (False) (Single precision) */
	public int C_F_S = 143;
	
	/** Floating Point Compare (Unordered) (Single precision) */
	public int C_UN_S = 144;

	/** Floating Point Compare (Equal) (Single precision) */
	public int C_EQ_S = 145;
	
	/** Floating Point Compare (Unordered or equal) (Single precision) */
	public int C_UEQ_S = 146;

	/** Floating Point Compare (Ordered or less than) (Single precision) */
	public int C_OLT_S = 147;

	/** Floating Point Compare (Unordered or less than) (Single precision) */
	public int C_ULT_S = 148;

	/** Floating Point Compare (Ordered or less than or equal) (Single precision) */
	public int C_OLE_S = 149;

	/** Floating Point Compare (Unordered or less than or equal) (Single precision) */
	public int C_ULE_S = 150;

	/** Floating Point Compare (Signaling false) (Single precision) */
	public int C_SF_S = 151;

	/** Floating Point Compare (Not greater than or less than or equal) (Single precision) */
	public int C_NGLE_S = 152;

	/** Floating Point Compare (Signaling equal) (Single precision) */
	public int C_SEQ_S = 153;

	/** Floating Point Compare (Not greater than or less than) (Single precision) */
	public int C_NGL_S = 154;

	/** Floating Point Compare (Less than) (Single precision) */
	public int C_LT_S = 155;

	/** Floating Point Compare (Not greater than or equal) (Single precision) */
	public int C_NGE_S = 156;

	/** Floating Point Compare (Less than or equal) (Single precision) */
	public int C_LE_S = 157;

	/** Floating Point Compare (Not greater than) (Single precision) */
	public int C_NGT_S = 158;
	
	/** Floating Point Compare (False) (Double precision) */
	public int C_F_D = 159;
	
	/** Floating Point Compare (Unordered) (Double precision) */
	public int C_UN_D = 160;

	/** Floating Point Compare (Equal) (Double precision) */
	public int C_EQ_D = 161;
	
	/** Floating Point Compare (Unordered or equal) (Double precision) */
	public int C_UEQ_D = 162;

	/** Floating Point Compare (Ordered or less than) (Double precision) */
	public int C_OLT_D = 163;

	/** Floating Point Compare (Unordered or less than) (Double precision) */
	public int C_ULT_D = 164;

	/** Floating Point Compare (Ordered or less than or equal) (Double precision) */
	public int C_OLE_D = 165;

	/** Floating Point Compare (Unordered or less than or equal) (Double precision) */
	public int C_ULE_D = 166;

	/** Floating Point Compare (Signaling false) (Double precision) */
	public int C_SF_D = 167;

	/** Floating Point Compare (Not greater than or less than or equal) (Double precision) */
	public int C_NGLE_D = 168;

	/** Floating Point Compare (Signaling equal) (Double precision) */
	public int C_SEQ_D = 169;

	/** Floating Point Compare (Not greater than or less than) (Double precision) */
	public int C_NGL_D = 170;

	/** Floating Point Compare (Less than) (Double precision) */
	public int C_LT_D = 171;

	/** Floating Point Compare (Not greater than or equal) (Double precision) */
	public int C_NGE_D = 172;

	/** Floating Point Compare (Less than or equal) (Double precision) */
	public int C_LE_D = 173;

	/** Floating Point Compare (Not greater than) (Double precision) */
	public int C_NGT_D = 174;
	
	/** Floating Point Compare (False) (Paired Single) */
	public int C_F_PS = 175;
	
	/** Floating Point Compare (Unordered) (Paired Single) */
	public int C_UN_PS = 176;

	/** Floating Point Compare (Equal) (Paired Single) */
	public int C_EQ_PS = 177;
	
	/** Floating Point Compare (Unordered or equal) (Paired Single) */
	public int C_UEQ_PS = 178;

	/** Floating Point Compare (Ordered or less than) (Paired Single) */
	public int C_OLT_PS = 179;

	/** Floating Point Compare (Unordered or less than) (Paired Single) */
	public int C_ULT_PS = 180;

	/** Floating Point Compare (Ordered or less than or equal) (Paired Single) */
	public int C_OLE_PS = 181;

	/** Floating Point Compare (Unordered or less than or equal) (Paired Single) */
	public int C_ULE_PS = 182;

	/** Floating Point Compare (Signaling false) (Paired Single) */
	public int C_SF_PS = 183;

	/** Floating Point Compare (Not greater than or less than or equal) (Paired Single) */
	public int C_NGLE_PS = 184;

	/** Floating Point Compare (Signaling equal) (Paired Single) */
	public int C_SEQ_PS = 185;

	/** Floating Point Compare (Not greater than or less than) (Paired Single) */
	public int C_NGL_PS = 186;

	/** Floating Point Compare (Less than) (Paired Single) */
	public int C_LT_PS = 187;

	/** Floating Point Compare (Not greater than or equal) (Paired Single) */
	public int C_NGE_PS = 188;

	/** Floating Point Compare (Less than or equal) (Paired Single) */
	public int C_LE_PS = 189;

	/** Floating Point Compare (Not greater than) (Paired Single) */
	public int C_NGT_PS = 190;
	
	//------------------------------------------------------------------//
	//					FPU-Approximate Arithmetic Operations			//
	//------------------------------------------------------------------//

	/** Floating Point Reciprocal Approximation (Single precision) */
	public int RECIP_S = 191;

	/** Floating Point Reciprocal Approximation (Double precision) */
	public int RECIP_D = 192;

	/** Floating Point Reciprocal Approximation (Paired precision) */
	public int RECIP_PS = 193;

	/** Floating Point Reciprocal Square Root Approximation (Single precision) */
	public int RSQRT_S = 194;

	/** Floating Point Reciprocal Square Root Approximation (Double precision) */
	public int RSQRT_D = 195;

	/** Floating Point Reciprocal Square Root Approximation (Paired Single) */
	public int RSQRT_PS = 196;

	//------------------------------------------------------------------//
	//			FPU Multiply-Accumulate Arithmetic Operations			//
	//------------------------------------------------------------------//

	/** Floating Point Multiply Add (Single precision) */
	public int MADD_S = 197;

	/** Floating Point Multiply Add (Double precision) */
	public int MADD_D = 198;

	/** Floating Point Multiply Add (Paired Single) */
	public int MADD_PS = 199;
	
	/** Floating Point Multiply Subtract (Single precision) */
	public int MSUB_S = 200;

	/** Floating Point Multiply Subtract (Double precision) */
	public int MSUB_D = 201;

	/** Floating Point Multiply Subtract (Paired Single) */
	public int MSUB_PS = 202;
	
	/** Floating Point Negative Multiply Add (Single precision) */
	public int NMADD_S = 203;

	/** Floating Point Negative Multiply Add (Double precision) */
	public int NMADD_D = 204;

	/** Floating Point Negative Multiply Add (Paired Single) */
	public int NMADD_PS = 205;
	
	/** Floating Point Negative Multiply Subtract (Single precision) */
	public int NMSUB_S = 206;

	/** Floating Point Negative Multiply Subtract (Double precision) */
	public int NMSUB_D = 207;

	/** Floating Point Negative Multiply Subtract (Paired Single) */
	public int NMSUB_PS = 208;
	
	//------------------------------------------------------------------//
	//		FPU Conversion Operations Using the FCSR Rounding Mode		//
	//------------------------------------------------------------------//

	/** Floating Point (Single format) Convert to Double Floating Point */
	public int CVT_D_S = 209;

	/** Floating Point (Word fixed point) Convert to Double Floating Point */
	public int CVT_D_W = 210;

	/** Floating Point (Long fixed point) Convert to Double Floating Point */
	public int CVT_D_L = 211;

	/** Floating Point (Single format) Convert to Long Fixed Point */
	public int CVT_L_S = 212;

	/** Floating Point (Double format) Convert to Long Fixed Point */
	public int CVT_L_D = 213;

	/** Floating Point Convert Pair to Paired Single */
	public int CVT_PS_S = 214;
	
	/** Floating Point (Double format) Convert to Single Floating Point */
	public int CVT_S_D = 215;

	/** Floating Point (Word fixed point) Convert to Single Floating Point */
	public int CVT_S_W = 216;

	/** Floating Point (Long fixed point) Convert to Single Floating Point */
	public int CVT_S_L = 217;

	/** Floating Point Convert to Single Floating Point (Paired Lower) */
	public int CVT_S_PL = 218;

	/** Floating Point Convert to Single Floating Point (Paired Upper) */
	public int CVT_S_PU = 219;

	/** Floating Point (Single format) Convert to Word Fixed Point */
	public int CVT_W_S = 220;
	
	/** Floating Point (Double format) Convert to Word Fixed Point */
	public int CVT_W_D = 221;

	//------------------------------------------------------------------//
	//		FPU Conversion Operations Using a Directed Rounding Mode	//
	//------------------------------------------------------------------//

	/** Floating Point Ceiling to Long Fixed Point */
	public int CEIL_L_S = 222;

	/** Floating Point Ceiling to Long Fixed Point */
	public int CEIL_L_D = 223;

	/** Floating Point Ceiling to Word Fixed Point */
	public int CEIL_W_S = 224;

	/** Floating Point Ceiling to Word Fixed Point */
	public int CEIL_W_D = 225;

	/** Floating Point Floor to Long Fixed Point */
	public int FLOOR_L_S = 226;

	/** Floating Point Floor to Long Fixed Point */
	public int FLOOR_L_D = 227;

	/** Floating Point Floor to Word Fixed Point */
	public int FLOOR_W_S = 228;

	/** Floating Point Floor to Word Fixed Point */
	public int FLOOR_W_D = 229;

	/** Floating Point Round to Long Fixed Point */
	public int ROUND_L_S = 230;

	/** Floating Point Round to Long Fixed Point */
	public int ROUND_L_D = 231;

	/** Floating Point Round to Word Fixed Point */
	public int ROUND_W_S = 232;

	/** Floating Point Round to Word Fixed Point */
	public int ROUND_W_D = 233;

	/** Floating Point Truncate to Long Fixed Point */
	public int TRUNC_L_S = 234;

	/** Floating Point Truncate to Long Fixed Point */
	public int TRUNC_L_D = 235;

	/** Floating Point Truncate to Word Fixed Point */
	public int TRUNC_W_S = 236;
	
	/** Floating Point Truncate to Word Fixed Point */
	public int TRUNC_W_D = 237;
	
	//------------------------------------------------------------------//
	//				FPU Formatted Operand Move Instructions				//
	//------------------------------------------------------------------//

	/** Floating Point Move (Single precision) */
	public int MOV_S = 238;

	/** Floating Point Move (Double precision) */
	public int MOV_D = 239;

	/** Floating Point Move (Paired Single) */
	public int MOV_PS = 240;

	//------------------------------------------------------------------//
	//			FPU Conditional Move on True/False Instructions			//
	//------------------------------------------------------------------//

	/** Floating Point Move Conditional on FP False (Single precision) */
	public int MOVF_S = 241;

	/** Floating Point Move Conditional on FP False (Double precision) */
	public int MOVF_D = 242;

	/** Floating Point Move Conditional on FP False (Paired Single) */
	public int MOVF_PS = 243;
	
	/** Floating Point Move Conditional on FP True (Single precision) */
	public int MOVT_S = 244;

	/** Floating Point Move Conditional on FP True (Double precision) */
	public int MOVT_D = 245;

	/** Floating Point Move Conditional on FP True (Paired Single) */
	public int MOVT_PS = 246;
	
	/** Floating Point Move Conditional on Nonzero (Single precision) */
	public int MOVN_S = 247;

	/** Floating Point Move Conditional on Nonzero (Double precision) */
	public int MOVN_D = 248;

	/** Floating Point Move Conditional on Nonzero (Paired Single) */
	public int MOVN_PS = 249;
	
	/** Floating Point Move Conditional on Zero (Single precision) */
	public int MOVZ_S = 250;

	/** Floating Point Move Conditional on Zero (Double precision) */
	public int MOVZ_D = 251;

	/** Floating Point Move Conditional on Zero (Paired Single) */
	public int MOVZ_PS = 252;
	
	//------------------------------------------------------------------//
	//					FPU Conditional Branch Instructions				//
	//------------------------------------------------------------------//

	/** Branch on FP False */
	public int BC1F = 253;
	
	/** Branch on FP True */
	public int BC1T = 254;

	//------------------------------------------------------------------//
	//		Deprecated FPU Conditional Branch Likely Instructions		//
	//------------------------------------------------------------------//

	/** Branch on FP False Likely */
	public int BC1FL = 255;
	
	/** Branch on FP True Likely */
	public int BC1TL = 256;

	//------------------------------------------------------------------//
	//		CPU Conditional Move on FPU True/False Instructions			//
	//------------------------------------------------------------------//

	/** FP Align Variable */
	public int ALNV_PS = 257;
		
	/** Pair Lower Lower */
	public int PLL_PS = 258;
	
	/** Pair Lower Upper */
	public int PLU_PS = 259;
	
	/** Pair Upper Lower */
	public int PUL_PS = 260;
	
	/** Pair Upper Upper */
	public int PUU_PS = 261;
}
