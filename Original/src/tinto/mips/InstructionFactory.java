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
 * Clase permite crear las instrucciones para el procesador MIPS-32
 * 
 * @author Francisco José Moreno Velo
 */
public class InstructionFactory implements InstructionSet {
	
	/**
	 * Crea una etiqueta "label:"
	 * @param label
	 * @return
	 */
	public static Instruction createLabel(String label) {
		return new LabelInstruction(LABEL,label);
	}

	//------------------------------------------------------------------//
	// 				  Aligned CPU Load/Store Instructions				//
	//------------------------------------------------------------------//

	/**
	 * Crea una instrucción "lw  target offset(source)"
	 * @param target
	 * @param reg
	 * @param disp
	 * @return
	 */
	public static Instruction createLW(Register target, Register reg, int offset) {
		return new RDRInstruction(LW, target, reg, offset);
	}

	/**
	 * Crea una instrucción "sw rt offset(base)"
	 * @param target
	 * @param reg
	 * @param offset
	 * @return
	 */
	public static Instruction createSW(Register source, Register reg, int offset) {
		return new RDRInstruction(SW, source, reg, offset);
	}

	//------------------------------------------------------------------//
	// 			  Unaligned CPU Load and Store Instructions				//
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	// 			  Atomic Update CPU Load and Store Instructions			//
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	// 			     Coprocessor Load and Store Instructions			//
	//------------------------------------------------------------------//

	/**
	 * Crea una instrucción "lwc1 ft, offset(base)"
	 * @param ft
	 * @param base
	 * @param offset
	 * @return
	 */
	public static Instruction createLWC1(Register ft, Register base, int offset) {
		return new RDRInstruction(LWC1, ft, base, offset);
	}

	/**
	 * Crea una instrucción "ldc1 ft, offset(base)" (Load Doubleword to Floating Point Register)
	 * @param ft Registro FPR (doble) de destino
	 * @param base Registro GPR que contiene la dirección base de memoria
	 * @param offset Desplazamiento desde la dirección base
	 * @return
	 */
	public static Instruction createLDC1(Register ft, Register base, int offset) {
		return new RDRInstruction(LDC1, ft, base, offset);
	}
	
	/**
	 * Crea una instrucción "swc1 ft, offset(base)"
	 * @param ft Registro FPR que contiene el valor a almacenar
	 * @param base Registro GPR que contiene la dirección base de memoria
	 * @param offset Desplazamiento desde la dirección base
	 * @return
	 */
	public static Instruction createSWC1(Register ft, Register base, int offset) {
		return new RDRInstruction(SWC1, ft, base, offset);
	}

	/**
	 * Crea una instrucción "sdc1 ft, offset(base)"
	 * @param ft Registro FPR que contiene el valor a almacenar
	 * @param base Registro GPR que contiene la dirección base de memoria
	 * @param offset Desplazamiento desde la dirección base
	 * @return
	 */
	public static Instruction createSDC1(Register ft, Register base, int offset) {
		return new RDRInstruction(SDC1, ft, base, offset);
	}

	//------------------------------------------------------------------//
	//FPU Load and Store Instructions Using Register+Register Addressing//
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//				ALU Instructions With an Immediate Operand			//
	//------------------------------------------------------------------//

	/**
	 * Crea una instrucción "addiu target source value"
	 * @param target
	 * @param source
	 * @param value
	 * @return
	 */
	public static Instruction createADDIU(Register target, Register source, int value) {
		return new RRIInstruction(ADDIU,target,source,value);
	}
	
	/**
	 * Crea una instrucción "li  target value".
	 * Es un alias de "ori target r0 value"
	 * @param target
	 * @param value
	 * @return
	 */
	public static Instruction createLI(Register target, int value) {
		return new RRIInstruction(ORI, target, RegisterSet.r0, value);
	}

	/**
	 * Crea una instrucción "ori target r0 value" 
	 * @param target
	 * @param source
	 * @param value
	 * @return
	 */
	public static Instruction createORI(Register target, Register source, int value) {
		return new RRIInstruction(ORI,target,source,value);
	}

	/**
	 * Crea una instrucción "xori target r0 value" 
	 * @param target
	 * @param source
	 * @param value
	 * @return
	 */
	public static Instruction createXORI(Register target, Register source, int value) {
		return new RRIInstruction(XORI,target,source,value);
	}

	/**
	 * Crea una instrucción "lui  target value"
	 * @param target
	 * @param value
	 * @return
	 */
	public static Instruction createLUI(Register target, int value) {
		return new RIInstruction(LUI, target, value);
	}

	/**
	 * Crea una instrucción "slti target r0 value" 
	 * @param target
	 * @param source
	 * @param value
	 * @return
	 */
	public static Instruction createSLTI(Register target, Register source, int value) {
		return new RRIInstruction(SLTI,target,source,value);
	}


	//------------------------------------------------------------------//
	//					  Three-Operand ALU Instructions				//
	//------------------------------------------------------------------//

	/**
	 * Crea una instrucción "addu target source1 source2"
	 * @param target
	 * @param source1
	 * @param source2
	 * @return
	 */
	public static Instruction createADDU(Register target, Register source1, Register source2) {
		return new RRRInstruction(ADDU,target,source1,source2);
	}

	/**
	 * Crea una instrucción "and target source1 source2"
	 * @param target
	 * @param source1
	 * @param source2
	 * @return
	 */
	public static Instruction createAND(Register target, Register source1, Register source2) {
		return new RRRInstruction(AND,target,source1,source2);
	}

	/**
	 * Crea una instrucción "nor target source1 source2"
	 * @param target
	 * @param source1
	 * @param source2
	 * @return
	 */
	public static Instruction createNOR(Register target, Register source1, Register source2) {
		return new RRRInstruction(NOR,target,source1,source2);
	}
	
	/**
	 * Crea una instrucción "or target source1 source2"
	 * @param target
	 * @param source1
	 * @param source2
	 * @return
	 */
	public static Instruction createOR(Register target, Register source1, Register source2) {
		return new RRRInstruction(OR,target,source1,source2);
	}

	/**
	 * Crea una instrucción "xor target source1 source2"
	 * @param target
	 * @param source1
	 * @param source2
	 * @return
	 */
	public static Instruction createXOR(Register target, Register source1, Register source2) {
		return new RRRInstruction(XOR,target,source1,source2);
	}

	/**
	 * Crea una instrucción "subu target source1 source2"
	 * @param target
	 * @param source1
	 * @param source2
	 * @return
	 */
	public static Instruction createSUBU(Register target, Register source1, Register source2) {
		return new RRRInstruction(SUBU,target,source1,source2);
	}

	/**
	 * Crea una instrucción "move target source"
	 * Es un alias de "or target r0 source"
	 * @param target
	 * @param source
	 * @return
	 */
	public static Instruction createMOVE(Register target, Register source) {
		return new RRRInstruction(OR, target, RegisterSet.r0, source);
	}

	/**
	 * Crea una instrucción "slt target source1 source2"
	 * @param target
	 * @param source1
	 * @param source2
	 * @return
	 */
	public static Instruction createSLT(Register target, Register source1, Register source2) {
		return new RRRInstruction(SLT, target, source1, source2);
	}

	/**
	 * Crea una instrucción "sltu target source1 source2"
	 * @param target
	 * @param source1
	 * @param source2
	 * @return
	 */
	public static Instruction createSLTU(Register target, Register source1, Register source2) {
		return new RRRInstruction(SLTU, target, source1, source2);
	}

	//------------------------------------------------------------------//
	//					   Two-Operand ALU Instructions					//
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//					   		Shift Instructions						//
	//------------------------------------------------------------------//

	/**
	 * Crea una instrucción "sll target source1 source2"
	 * @param target
	 * @param source1
	 * @param source2
	 * @return
	 */
	public static Instruction createSLL(Register target, Register source1, Register source2) {
		return new RRRInstruction(SLL, target, source1, source2);
	}

	/**
	 * Crea una instrucción "sra target source1 source2"
	 * @param target
	 * @param source1
	 * @param source2
	 * @return
	 */
	public static Instruction createSRA(Register target, Register source1, Register source2) {
		return new RRRInstruction(SRA, target, source1, source2);
	}

	/**
	 * Crea una instrucción "slt target source1 source2"
	 * @param target
	 * @param source1
	 * @param source2
	 * @return
	 */
	public static Instruction createSRL(Register target, Register source1, Register source2) {
		return new RRRInstruction(SRL, target, source1, source2);
	}

	//------------------------------------------------------------------//
	//					   	Multiply/Divide Instructions				//
	//------------------------------------------------------------------//

	/**
	 * Crea una instrucción "mult source1 source2"
	 * @param target
	 * @param source1
	 * @param source2
	 * @return
	 */
	public static Instruction createMULT(Register source1, Register source2) {
		return new RRInstruction(MULT,source1,source2);
	}
	
	/**
	 * Crea una instrucción "div source1 source2"
	 * @param target
	 * @param source1
	 * @param source2
	 * @return
	 */
	public static Instruction createDIV(Register source1, Register source2) {
		return new RRInstruction(DIV,source1,source2);
	}
	
	/**
	 * Crea una instrucción "mflo target"
	 * @param target
	 * @return
	 */
	public static Instruction createMFLO(Register target) {
		return new RInstruction(MFLO,target);
	}

	/**
	 * Crea una instrucción "mfhi target"
	 * @param target
	 * @return
	 */
	public static Instruction createMFHI(Register target) {
		return new RInstruction(MFHI,target);
	}

	
	//------------------------------------------------------------------//
	//			Unconditional Jump Within a 256 Megabyte Region			//
	//------------------------------------------------------------------//

	/**
	 * Crea una instrucción de salto incondicional "j label"
	 * @param target
	 * @return
	 */
	public static Instruction createJ(String label) {
		return new LabelInstruction(J,label);
	}

	/**
	 * Crea una instrucción de salto incondicional "jal label"
	 * @param target
	 * @return
	 */
	public static Instruction createJAL(String label) {
		return new LabelInstruction(JAL,label);
	}

	/**
	 * Crea una instrucción de salto incondicional "jr target"
	 * @param target
	 * @return
	 */
	public static Instruction createJR(Register target) {
		return new RInstruction(JR,target);
	}
	
	//------------------------------------------------------------------//
	//PC-Relative Conditional Branch Instructions Comparing Two Registers//
	//------------------------------------------------------------------//

	/**
	 * Crea una instrucción de salto condicional "beq s1 s2 label"
	 * @param s1 Primer regsitro
	 * @param s2 Segundo registro
	 * @param label Etiqueta de salto
	 * @return
	 */
	public static Instruction createBEQ(Register s1, Register s2, String label) {
		return new RRLInstruction(BEQ,s1,s2,label);
	}
	
	/**
	 * Crea una instrucción de salto condicional "bne s1 s2 label"
	 * @param s1 Primer regsitro
	 * @param s2 Segundo registro
	 * @param label Etiqueta de salto
	 * @return
	 */
	public static Instruction createBNE(Register s1, Register s2, String label) {
		return new RRLInstruction(BNE,s1,s2,label);
	}
	
	//------------------------------------------------------------------//
	//	PC-Relative Conditional Branch Instructions Comparing With Zero	//
	//------------------------------------------------------------------//

	/**
	 * Crea una instrucción de salto condicional "bgtz s1 label"
	 * @param s1 Registro
	 * @param label Etiqueta de salto
	 * @return
	 */
	public static Instruction createBGTZ(Register s1, String label) {
		return new RLInstruction(BGTZ,s1,label);
	}

	/**
	 * Crea una instrucción de salto condicional "bgtz s1 label"
	 * @param s1 Registro
	 * @param label Etiqueta de salto
	 * @return
	 */
	public static Instruction createBGEZ(Register s1, String label) {
		return new RLInstruction(BGEZ,s1,label);
	}

	/**
	 * Crea una instrucción de salto condicional "bltz s1 label"
	 * @param s1 Registro
	 * @param label Etiqueta de salto
	 * @return
	 */
	public static Instruction createBLTZ(Register s1, String label) {
		return new RLInstruction(BLTZ,s1,label);
	}

	/**
	 * Crea una instrucción de salto condicional "blez s1 label"
	 * @param s1 Registro
	 * @param label Etiqueta de salto
	 * @return
	 */
	public static Instruction createBLEZ(Register s1, String label) {
		return new RLInstruction(BLEZ,s1,label);
	}

	//------------------------------------------------------------------//
	//				Deprecated Branch Likely Instructions				//
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//						Serialization Instruction					//
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//					System Call and Breakpoint Instructions			//
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//		Trap-on-Condition Instructions Comparing Two Registers		//
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//	  Trap-on-Condition Instructions Comparing an Immediate Value	//
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//				   CPU Conditional Move Instructions				//
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//	  					Prefetch Instructions						//
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//	  					  NOP Instructions							//
	//------------------------------------------------------------------//

	/**
	 * Crea una instrucción "nop"
	 * @return
	 */
	public static Instruction createNOP() {
		return new NInstruction(NOP);
	}

	//------------------------------------------------------------------//
	//					FPU Move To and From Instructions				//
	//------------------------------------------------------------------//

	/**
	 * Crea una instrucción "mtc1 rt, fs" (Move Word to Floating Point Register)
	 * @param rt Registro GPR de origen
	 * @param ft Registro FPR de destino
	 * @return
	 */
	public static Instruction createMTC1(Register rt, Register fs) {
		return new RRInstruction(MTC1,rt,fs);
	}
	
	/**
	 * Crea una instrucción "mthc1 rt, fs" (Move Word to High Half of Floating Point Register) 
	 * @param rt Registro GPR de origen
	 * @param ft Registro FPR de destino
	 * @return
	 */
	public static Instruction createMTHC1(Register rt, Register fs) {
		Register ft = RegisterSet.getRegister(fs.getCode()+1);
		return new RRInstruction(MTC1,rt,ft);
// PC-SPIM no soporta la instrucción MTHC1 por lo que se ha traducido por MTC1
// sobre el registro asociado a la parte alta del double
//		return new RRInstruction(MTHC1,rt,fs);
	}
	
	//------------------------------------------------------------------//
	//					  FPU IEEE Arithmetic Operations				//
	//------------------------------------------------------------------//

	/**
	 * Crea una instrucción "add.s target source1 source2"
	 * @param target
	 * @param source1
	 * @param source2
	 * @return
	 */
	public static Instruction createADD_S(Register target, Register source1, Register source2) {
		return new RRRInstruction(ADD_S,target,source1,source2);
	}

	/**
	 * Crea una instrucción "add.d target source1 source2"
	 * @param target
	 * @param source1
	 * @param source2
	 * @return
	 */
	public static Instruction createADD_D(Register target, Register source1, Register source2) {
		return new RRRInstruction(ADD_D,target,source1,source2);
	}
	
	/**
	 * Crea una instrucción "sub.s target source1 source2"
	 * @param target
	 * @param source1
	 * @param source2
	 * @return
	 */
	public static Instruction createSUB_S(Register target, Register source1, Register source2) {
		return new RRRInstruction(SUB_S,target,source1,source2);
	}

	/**
	 * Crea una instrucción "sub.d target source1 source2"
	 * @param target
	 * @param source1
	 * @param source2
	 * @return
	 */
	public static Instruction createSUB_D(Register target, Register source1, Register source2) {
		return new RRRInstruction(SUB_D,target,source1,source2);
	}

	/**
	 * Crea una instrucción "mul.s target source1 source2"
	 * @param target
	 * @param source1
	 * @param source2
	 * @return
	 */
	public static Instruction createMUL_S(Register target, Register source1, Register source2) {
		return new RRRInstruction(MUL_S,target,source1,source2);
	}
	
	/**
	 * Crea una instrucción "mul.d target source1 source2"
	 * @param target
	 * @param source1
	 * @param source2
	 * @return
	 */
	public static Instruction createMUL_D(Register target, Register source1, Register source2) {
		return new RRRInstruction(MUL_D,target,source1,source2);
	}
	
	/**
	 * Crea una instrucción "div.s target source1 source2"
	 * @param target
	 * @param source1
	 * @param source2
	 * @return
	 */
	public static Instruction createDIV_S(Register target, Register source1, Register source2) {
		return new RRRInstruction(DIV_S,target,source1,source2);
	}
	
	/**
	 * Crea una instrucción "div.d target source1 source2"
	 * @param target
	 * @param source1
	 * @param source2
	 * @return
	 */
	public static Instruction createDIV_D(Register target, Register source1, Register source2) {
		return new RRRInstruction(DIV_D,target,source1,source2);
	}
	
	/**
	 * Crea una instrucción "neg.s target source"
	 * @param target
	 * @param source
	 * @return
	 */
	public static Instruction createNEG_S(Register target, Register source) {
		return new RRInstruction(NEG_S,target,source);
	}
	
	/**
	 * Crea una instrucción "neg.d target source"
	 * @param target
	 * @param source
	 * @return
	 */
	public static Instruction createNEG_D(Register target, Register source) {
		return new RRInstruction(NEG_D,target,source);
	}

	//------------------------------------------------------------------//
	//						FPU Comparing Operations					//
	//------------------------------------------------------------------//

	/**
	 * Crea una instrucción "c.eq.s fs ft"  (fs == ft)
	 * @param fs (Registro FPU)
	 * @param ft (Registro FPU)
	 * @return
	 */
	public static Instruction createC_EQ_S(Register fs, Register ft) {
		return new RRInstruction(C_EQ_S, fs, ft);
	}

	/**
	 * Crea una instrucción "c.lt.s fs ft"  (fs < ft)
	 * @param fs (Registro FPU)
	 * @param ft (Registro FPU)
	 * @return
	 */
	public static Instruction createC_LT_S(Register fs, Register ft) {
		return new RRInstruction(C_LT_S, fs, ft);
	}

	/**
	 * Crea una instrucción "c.nge.s fs ft"  !(fs >= ft)
	 * @param fs (Registro FPU)
	 * @param ft (Registro FPU)
	 * @return
	 */
	public static Instruction createC_NGE_S(Register fs, Register ft) {
		return new RRInstruction(C_NGE_S, fs, ft);
	}

	/**
	 * Crea una instrucción "c.le.s fs ft"  (fs <= ft)
	 * @param fs (Registro FPU)
	 * @param ft (Registro FPU)
	 * @return
	 */
	public static Instruction createC_LE_S(Register fs, Register ft) {
		return new RRInstruction(C_LE_S, fs, ft);
	}

	/**
	 * Crea una instrucción "c.ngt.s fs ft"  !(fs > ft)
	 * @param fs (Registro FPU)
	 * @param ft (Registro FPU)
	 * @return
	 */
	public static Instruction createC_NGT_S(Register fs, Register ft) {
		return new RRInstruction(C_NGT_S, fs, ft);
	}

	/**
	 * Crea una instrucción "c.eq.d fs ft"  (fs == ft)
	 * @param fs (Registro FPU)
	 * @param ft (Registro FPU)
	 * @return
	 */
	public static Instruction createC_EQ_D(Register fs, Register ft) {
		return new RRInstruction(C_EQ_D, fs, ft);
	}

	/**
	 * Crea una instrucción "c.lt.d fs ft"  (fs < ft)
	 * @param fs (Registro FPU)
	 * @param ft (Registro FPU)
	 * @return
	 */
	public static Instruction createC_LT_D(Register fs, Register ft) {
		return new RRInstruction(C_LT_D, fs, ft);
	}

	/**
	 * Crea una instrucción "c.nge.d fs ft"  !(fs >= ft)
	 * @param fs (Registro FPU)
	 * @param ft (Registro FPU)
	 * @return
	 */
	public static Instruction createC_NGE_D(Register fs, Register ft) {
		return new RRInstruction(C_NGE_D, fs, ft);
	}

	/**
	 * Crea una instrucción "c.le.d fs ft"  (fs <= ft)
	 * @param fs (Registro FPU)
	 * @param ft (Registro FPU)
	 * @return
	 */
	public static Instruction createC_LE_D(Register fs, Register ft) {
		return new RRInstruction(C_LE_D, fs, ft);
	}

	/**
	 * Crea una instrucción "c.ngt.d fs ft"  !(fs > ft)
	 * @param fs (Registro FPU)
	 * @param ft (Registro FPU)
	 * @return
	 */
	public static Instruction createC_NGT_D(Register fs, Register ft) {
		return new RRInstruction(C_NGT_D, fs, ft);
	}

	//------------------------------------------------------------------//
	//					FPU-Approximate Arithmetic Operations			//
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//			FPU Multiply-Accumulate Arithmetic Operations			//
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//		FPU Conversion Operations Using the FCSR Rounding Mode		//
	//------------------------------------------------------------------//

	/**
	 * Crea una instrucción "cvt.d.s fd, fs" (Convierte float a double)
	 * @param fd Registro FPR de destino
	 * @param fs Registro FPR de origen
	 * @return
	 */
	public static Instruction createCVT_D_S(Register fd, Register fs) {
		return new RRInstruction(CVT_D_S,fd,fs);
	}

	/**
	 * Crea una instrucción "cvt.d.w fd, fs" (Convierte int a double)
	 * @param fd Registro FPR de destino
	 * @param fs Registro FPR de origen
	 * @return
	 */
	public static Instruction createCVT_D_W(Register fd, Register fs) {
		return new RRInstruction(CVT_D_W,fd,fs);
	}

	/**
	 * Crea una instrucción "cvt.d.l fd, fs" (Convierte long a double)
	 * @param fd Registro FPR de destino
	 * @param fs Registro FPR de origen
	 * @return
	 */
	public static Instruction createCVT_D_L(Register fd, Register fs) {
		return new RRInstruction(CVT_D_L,fd,fs);
	}

	/**
	 * Crea una instrucción "cvt.s.w fd, fs" (Convierte int a float)
	 * @param fd Registro FPR de destino
	 * @param fs Registro FPR de origen
	 * @return
	 */
	public static Instruction createCVT_S_W(Register fd, Register fs) {
		return new RRInstruction(CVT_S_W,fd,fs);
	}

	/**
	 * Crea una instrucción "cvt.s.l fd, fs" (Convierte long a float)
	 * @param fd Registro FPR de destino
	 * @param fs Registro FPR de origen
	 * @return
	 */
	public static Instruction createCVT_S_L(Register fd, Register fs) {
		return new RRInstruction(CVT_S_L,fd,fs);
	}

	//------------------------------------------------------------------//
	//		FPU Conversion Operations Using a Directed Rounding Mode	//
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//				FPU Formatted Operand Move Instructions				//
	//------------------------------------------------------------------//

	/**
	 * Crea una instrucción "mov.s target source"
	 * @param target
	 * @param source
	 */
	public static Instruction createMOV_S(Register target, Register source) {
		return new RRInstruction(MOV_S,target,source);
	}
	
	/**
	 * Crea una instrucción "mov.d target source"
	 * @param target
	 * @param source
	 */
	public static Instruction createMOV_D(Register target, Register source) {
		return new RRInstruction(MOV_D,target,source);
	}
	
	//------------------------------------------------------------------//
	//			FPU Conditional Move on True/False Instructions			//
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//					FPU Conditional Branch Instructions				//
	//------------------------------------------------------------------//

	/**
	 * Crea una instrucción "bc1f label"  (branch on FP false)
	 * @param label Etiqueta de salto
	 * @return
	 */
	public static Instruction createBC1F(String label) {
		return new LabelInstruction(BC1F, label);
	}

	/**
	 * Crea una instrucción "bc1t label"  (branch on FP true)
	 * @param label Etiqueta de salto
	 * @return
	 */
	public static Instruction createBC1T(String label) {
		return new LabelInstruction(BC1T, label);
	}

	//------------------------------------------------------------------//
	//		Deprecated FPU Conditional Branch Likely Instructions		//
	//------------------------------------------------------------------//

	//------------------------------------------------------------------//
	//		CPU Conditional Move on FPU True/False Instructions			//
	//------------------------------------------------------------------//
	





	
}
