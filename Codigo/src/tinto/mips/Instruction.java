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
 * Clase abstracta que describe una instrucción
 * 
 * @author Francisco José Moreno Velo
 */
public abstract class Instruction implements InstructionSet {
	
	/**
	 * Código de la instrucción
	 */
	private int code;

	/**
	 * Constructor basado en el código de la instrucción
	 * @param num
	 */
	public Instruction(int code) {
		this.code = code;
	}
	
	/**
	 * Obtiene el código de la instrucción
	 * @return
	 */
	public int getCode() {
		return this.code;
	}
	
	/**
	 * Obtiene el nombre en ensamblador de la instrucción
	 * @return
	 */
	public String getInstructionName() {		
		switch(code) {
			case LB:		return "lb";
			case LBU:		return "lbu";
			case LH:		return "lh";
			case LHU:		return "lhu";
			case LW:		return "lw";
			case SB:		return "sb";
			case SH:		return "sh";
			case SW:		return "sw";
			case LWL:		return "lwl";
			case LWR:		return "lwr";
			case SWL:		return "swl";
			case SWR:		return "swr";
			case LL:		return "ll";
			case SC:		return "sc";
			case LDC1:		return "ldc1";
			case LDC2:		return "ldc2";
			case LWC1:		return "lwc1";
			case LWC2:		return "lwc2";
			case SDC1:		return "sdc1";
			case SDC2:		return "sdc2";
			case SWC1:		return "swc1";
			case SWC2:		return "swc2";
			case LWXC1:		return "lwxc1";
			case SWXC1:		return "swxc1";
			case LDXC1:		return "ldxc1";
			case SDXC1:		return "sdxc1";
			case LUXC1:		return "luxc1";
			case SUXC1:		return "suxc1";
			case ADDI:		return "addi";
			case ADDIU:		return "addiu";
			case ANDI:		return "andi";
			case LUI:		return "lui";
			case ORI:		return "ori";
			case SLTI:		return "slti";
			case SLTIU:		return "sltiu";
			case XORI:		return "xori";
			case ADD:		return "add";
			case ADDU:		return "addu";
			case AND:		return "and";
			case NOR:		return "nor";
			case OR:		return "or";
			case SLT:		return "slt";
			case SLTU:		return "sltu";
			case SUB:		return "sub";
			case SUBU:		return "subu";
			case XOR:		return "xor";
			case CLO:		return "clo";
			case CLZ:		return "clz";
			case ROTR:		return "rotr";
			case ROTRV:		return "rotrv";
			case SLL:		return "sll";
			case SLLV:		return "sllv";
			case SRA:		return "sra";
			case SRAV:		return "srav";
			case SRL:		return "srl";
			case SRLV:		return "srlv";
			case DIV:		return "div";
			case DIVU:		return "divu";
			case MADD:		return "madd";
			case MADDU:		return "maddu";
			case MFHI:		return "mfhi";
			case MFLO:		return "mflo";
			case MSUB:		return "msub";
			case MSUBU:		return "msubu";
			case MTHI:		return "mthi";
			case MTLO:		return "mtlo";
			case MUL:		return "mul";
			case MULT:		return "mult";
			case MULTU:		return "multu";
			case J:			return "j"; 
			case JAL:		return "jal";
			case JALR:		return "jalr";
			case JALR_HB:	return "jalr.hb";
			case JALX:		return "jalx";
			case JR:		return "jr";
			case JR_HB:		return "jr.hb";
			case BEQ:		return "beq";
			case BNE:		return "bne";
			case BGEZ:		return "bgez";
			case BGEZAL:	return "bgezal";
			case BGTZ:		return "bgtz";
			case BLEZ:		return "blez";
			case BLTZ:		return "bltz";
			case BLTZAL:	return "bltzal";
			case BEQL:		return "beql";
			case BGEZALL:	return "bgezall";
			case BGEZL:		return "bgezl";
			case BGTZL:		return "bgtzl";
			case BLEZL:		return "blezl";
			case BLTZALL:	return "bltzall";
			case BLTZL:		return "bltzl";
			case BNEL:		return "bnel";
			case SYNC:		return "sync";
			case SYNCI:		return "synci";
			case BREAK:		return "break";
			case SYSCALL:	return "syscall";
			case TEQ:		return "teq";
			case TGE:		return "tge";
			case TGEU:		return "tgeu";
			case TLT:		return "tlt";
			case TLTU:		return "tltu";
			case TNE:		return "tne";
			case TEQI:		return "teqi";
			case TGEI:		return "tgei";
			case TGEIU:		return "tgeiu";
			case TLTI:		return "tlti";
			case TLTIU:		return "tltiu";
			case TNEI:		return "tnei";
			case MOVF:		return "movf";
			case MOVN:		return "movn";
			case MOVT:		return "movt";
			case MOVZ:		return "movz";
			case PREF:		return "pref";
			case PREFX:		return "prefx";
			case NOP:		return "nop";
			case SSNOP:		return "ssnop";
			case CFC1:		return "cfc1";
			case CTC1:		return "ctc1";
			case MFC1:		return "mfc1";
			case MFHC1:		return "mfhc1";
			case MTC1:		return "mtc1";
			case MTHC1:		return "mthc1";
			case ABS_S:		return "abs.s";
			case ABS_D:		return "abs.d";
			case ABS_PS:	return "abs.ps";
			case ADD_S:		return "add.s";
			case ADD_D:		return "add.d";
			case ADD_PS:	return "add.ps";
			case DIV_S:		return "div.s"; 
			case DIV_D:		return "div.d";
			case MUL_S:		return "mul.s";
			case MUL_D:		return "mul.d";
			case MUL_PS:	return "mul.ps";
			case NEG_S:		return "neg.s";
			case NEG_D:		return "neg.d";
			case NEG_PS:	return "neg.ps";
			case SQRT_S:	return "sqrt.s";
			case SQRT_D:	return "sqrt.d";
			case SUB_S:		return "sub.s";
			case SUB_D:		return "sub.d";
			case SUB_PS:	return "sub.ps";
			case C_F_S:		return "c.f.s";
			case C_UN_S:	return "c.un.s";
			case C_EQ_S:	return "c.eq.s";
			case C_UEQ_S:	return "c.ueq.s";
			case C_OLT_S:	return "c.olt.s";
			case C_ULT_S:	return "c.ult.s";
			case C_OLE_S:	return "c.ole.s";
			case C_ULE_S:	return "c.ule.s";
			case C_SF_S:	return "c.sf.s";
			case C_NGLE_S:	return "c.ngle.s";
			case C_SEQ_S:	return "c.seq.s";
			case C_NGL_S:	return "c.ngl.s";
			case C_LT_S:	return "c.lt.s";
			case C_NGE_S:	return "c.nge.s";
			case C_LE_S:	return "c.le.s";
			case C_NGT_S:	return "c.ngt.s";
			case C_F_D:		return "c.f.d";
			case C_UN_D:	return "c.un.d";
			case C_EQ_D:	return "c.eq.d";
			case C_UEQ_D:	return "c.ueq.d";
			case C_OLT_D:	return "c.olt.d";
			case C_ULT_D:	return "c.ult.d";
			case C_OLE_D:	return "c.ole.d";
			case C_ULE_D:	return "c.ule.d";
			case C_SF_D:	return "c.sf.d";
			case C_NGLE_D:	return "c.ngle.d";
			case C_SEQ_D:	return "c.seq.d";
			case C_NGL_D:	return "c.ngl.d";
			case C_LT_D:	return "c.lt.d";
			case C_NGE_D:	return "c.nge.d";
			case C_LE_D:	return "c.le.d";
			case C_NGT_D:	return "c.ngt.d";
			case C_F_PS:	return "c.f.ps"; 
			case C_UN_PS:	return "c.un.ps";
			case C_EQ_PS:	return "c.eq.ps";
			case C_UEQ_PS:	return "c.ueq.ps";
			case C_OLT_PS:	return "c.olt.ps";
			case C_ULT_PS:	return "c.ult.ps";
			case C_OLE_PS:	return "c.ole.ps";
			case C_ULE_PS:	return "c.ule.ps";
			case C_SF_PS:	return "c.sf.ps";
			case C_NGLE_PS:	return "c.ngle.ps";
			case C_SEQ_PS:	return "c.seq.ps";
			case C_NGL_PS:	return "c.ngl.ps";
			case C_LT_PS:	return "c.lt.ps";
			case C_NGE_PS:	return "c.nge.ps";
			case C_LE_PS:	return "c.le.ps";
			case C_NGT_PS:	return "c.ngt.ps";
			case RECIP_S:	return "recip.s";
			case RECIP_D:	return "recip.d";
			case RECIP_PS:	return "recip.ps";
			case RSQRT_S:	return "rsqrt.s";
			case RSQRT_D:	return "rsqrt.d";
			case RSQRT_PS:	return "rsqrt.ps";
			case MADD_S:	return "madd.s"; 
			case MADD_D:	return "madd.d";
			case MADD_PS:	return "madd.ps";
			case MSUB_S:	return "msub.s";
			case MSUB_D:	return "msub.d";
			case MSUB_PS:	return "msub.ps";
			case NMADD_S:	return "nmadd.s";
			case NMADD_D:	return "nmadd.d";
			case NMADD_PS:	return "nmadd.ps";
			case NMSUB_S:	return "nmsub.s";
			case NMSUB_D:	return "nmsub.d";
			case NMSUB_PS:	return "nmsub.ps";
			case CVT_D_S:	return "cvt.d.s";
			case CVT_D_W:	return "cvt.d.w";
			case CVT_D_L:	return "cvt.d.l";
			case CVT_L_S:	return "cvt.l.s";
			case CVT_L_D:	return "cvt.l.d";
			case CVT_PS_S:	return "cvt.ps.s";
			case CVT_S_D:	return "cvt.s.d";
			case CVT_S_W:	return "cvt.s.w";
			case CVT_S_L:	return "cvt.s.l";
			case CVT_S_PL:	return "cvt.s.pl";
			case CVT_S_PU:	return "cvt.s.pu";
			case CVT_W_S:	return "cvt.w.s";
			case CVT_W_D:	return "cvt.w.d";
			case CEIL_L_S:	return "ceil.l.s";
			case CEIL_L_D:	return "ceil.l.d";
			case CEIL_W_S:	return "ceil.w.s";
			case CEIL_W_D:	return "ceil.w.d";
			case FLOOR_L_S:	return "floor.l.s";
			case FLOOR_L_D:	return "floor.l.d";
			case FLOOR_W_S:	return "floor.w.s";
			case FLOOR_W_D:	return "floor.w.d";
			case ROUND_L_S:	return "round.l.s";
			case ROUND_L_D:	return "round.l.d";
			case ROUND_W_S:	return "round.w.s";
			case ROUND_W_D:	return "round.w.d";
			case TRUNC_L_S:	return "trunc.l.s";
			case TRUNC_L_D:	return "trunc.l.d";
			case TRUNC_W_S:	return "trunc.w.s";
			case TRUNC_W_D:	return "trunc.w.d";
			case MOV_S:		return "mov.s";
			case MOV_D:		return "mov.d";
			case MOV_PS:	return "mov.ps";
			case MOVF_S:	return "movf.s";
			case MOVF_D:	return "movf.d";
			case MOVF_PS:	return "movf.ps";
			case MOVT_S:	return "movt.s";
			case MOVT_D:	return "movt.d";
			case MOVT_PS:	return "movt.ps";
			case MOVN_S:	return "movn.s";
			case MOVN_D:	return "movn.d";
			case MOVN_PS:	return "movn.ps";
			case MOVZ_S:	return "movz.s";
			case MOVZ_D:	return "movz.d";
			case MOVZ_PS:	return "movz.ps";
			case BC1F:		return "bc1f";
			case BC1T:		return "bc1t";
			case BC1FL:		return "bc1fl";
			case BC1TL:		return "bc1tl";
			case ALNV_PS:	return "alnv.ps";
			case PLL_PS:	return "pll.ps";
			case PLU_PS:	return "plu.ps";
			case PUL_PS:	return "pul.ps";
			case PUU_PS:	return "puu.ps";
			default: 		return "";
		}
	}
	
	/**
	 * Obtiene la representación de la instrucción en ensamblador
	 * @return
	 */
	public abstract String getAssembler();

}
