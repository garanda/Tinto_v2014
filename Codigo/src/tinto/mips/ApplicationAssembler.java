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

import java.io.PrintStream;

/**
 * Clase que contiene la descripción de una clase en ensamblador
 * 
 * @author Francisco José Moreno Velo
 */
public class ApplicationAssembler  {
	
	/**
	 * Genera el código común de todas las aplicaciones: código de las
	 * interrupciones, código de la función malloc y salto a la etiqueta
	 * de comienzo de la aplicación.
	 * @param stream
	 */
	public static void printCommonCode(PrintStream stream) {
		printExceptionCode(stream);
		printStartCode(stream);
	}
	
	/**
	 * Genera el código de las interrupciones
	 * @param stream
	 */
	private static void printExceptionCode(PrintStream stream) {		
		stream.println("# SPIM S20 MIPS simulator.");
		stream.println("# The default exception handler for spim.");
		stream.println("#");
		stream.println("# Copyright (C) 1990-2004 James Larus, larus@cs.wisc.edu.");
		stream.println("# ALL RIGHTS RESERVED.");
		stream.println("#");
		stream.println("# SPIM is distributed under the following conditions:");
		stream.println("#");
		stream.println("# You may make copies of SPIM for your own use and modify those copies.");
		stream.println("#");
		stream.println("# All copies of SPIM must retain my name and copyright notice.");
		stream.println("#");
		stream.println("# You may not sell SPIM or distributed SPIM in conjunction with a commerical");
		stream.println("# product or service without the expressed written consent of James Larus.");
		stream.println("#");
		stream.println("# THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR");
		stream.println("# IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED");
		stream.println("# WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR");
		stream.println("# PURPOSE.");
		stream.println("#");
		stream.println();
		stream.println("# $Header: $");
		stream.println();
		stream.println();
		stream.println("# Define the exception handling code.  This must go first!");
		stream.println();
		stream.println("\t.kdata");
		stream.println("__m1_:\t.asciiz\t\"  Exception \"");
		stream.println("__m2_:\t.asciiz\t\" occurred and ignored\\n\"");
		stream.println("__e0_:\t.asciiz\t\"  [Interrupt] \"");
		stream.println("__e1_:\t.asciiz\t\"  [TLB]\"");
		stream.println("__e2_:\t.asciiz\t\"  [TLB]\"");
		stream.println("__e3_:\t.asciiz\t\"  [TLB]\"");
		stream.println("__e4_:\t.asciiz\t\"  [Address error in inst/data fetch] \"");
		stream.println("__e5_:\t.asciiz\t\"  [Address error in store] \"");
		stream.println("__e6_:\t.asciiz\t\"  [Bad instruction address] \"");
		stream.println("__e7_:\t.asciiz\t\"  [Bad data address] \"");
		stream.println("__e8_:\t.asciiz\t\"  [Error in syscall] \"");
		stream.println("__e9_:\t.asciiz\t\"  [Breakpoint] \"");
		stream.println("__e10_:\t.asciiz	\"  [Reserved instruction] \"");
		stream.println("__e11_:\t.asciiz	\"\"");
		stream.println("__e12_:\t.asciiz	\"  [Arithmetic overflow] \"");
		stream.println("__e13_:\t.asciiz	\"  [Trap] \"");
		stream.println("__e14_:\t.asciiz	\"\"");
		stream.println("__e15_:\t.asciiz	\"  [Floating point] \"");
		stream.println("__e16_:\t.asciiz	\"\"");
		stream.println("__e17_:\t.asciiz	\"\"");
		stream.println("__e18_:\t.asciiz	\"  [Coproc 2]\"");
		stream.println("__e19_:\t.asciiz	\"\"");
		stream.println("__e20_:\t.asciiz	\"\"");
		stream.println("__e21_:\t.asciiz	\"\"");
		stream.println("__e22_:\t.asciiz	\"  [MDMX]\"");
		stream.println("__e23_:\t.asciiz	\"  [Watch]\"");
		stream.println("__e24_:\t.asciiz	\"  [Machine check]\"");
		stream.println("__e25_:\t.asciiz	\"\"");
		stream.println("__e26_:\t.asciiz	\"\"");
		stream.println("__e27_:\t.asciiz	\"\"");
		stream.println("__e28_:\t.asciiz	\"\"");
		stream.println("__e29_:\t.asciiz	\"\"");
		stream.println("__e30_:\t.asciiz	\"  [Cache]\"");
		stream.println("__e31_:\t.asciiz	\"\"");
		stream.println("__excp:\t.word __e0_, __e1_, __e2_, __e3_, __e4_, __e5_, __e6_, __e7_, __e8_, __e9_");
		stream.println("\t.word __e10_, __e11_, __e12_, __e13_, __e14_, __e15_, __e16_, __e17_, __e18_,");
		stream.println("\t.word __e19_, __e20_, __e21_, __e22_, __e23_, __e24_, __e25_, __e26_, __e27_,");
		stream.println("\t.word __e28_, __e29_, __e30_, __e31_");
		stream.println("s1:\t.word 0");
		stream.println("s2:\t.word 0");
		stream.println();
		stream.println("# This is the exception handler code that the processor runs when");
		stream.println("# an exception occurs. It only prints some information about the");
		stream.println("# exception, but can server as a model of how to write a handler.");
		stream.println("#");
		stream.println("# Because we are running in the kernel, we can use $k0/$k1 without");
		stream.println("# saving their old values.");
		stream.println();
		stream.println("# This is the exception vector address for MIPS-1 (R2000):");
		stream.println("#	.ktext 0x80000080");
		stream.println("# This is the exception vector address for MIPS32:");
		stream.println("\t.ktext 0x80000180");
		stream.println("# Select the appropriate one for the mode in which SPIM is compiled.");
		stream.println("\t.set noat");
		stream.println("\tmove $k1 $at\t\t# Save $at");
		stream.println("\t.set at");
		stream.println("\tsw $v0 s1\t\t# Not re-entrant and we can't trust $sp");
		stream.println("\tsw $a0 s2\t\t# But we need to use these registers");
		stream.println();
		stream.println("\tmfc0 $k0 $13\t\t# Cause register");
		stream.println("\tsrl $a0 $k0 2\t\t# Extract ExcCode Field");
		stream.println("\tandi $a0 $a0 0x1f");
		stream.println();
		stream.println("# Print information about exception.");
		stream.println("#");
		stream.println("\tli $v0 4\t\t# syscall 4 (print_str)");
		stream.println("\tla $a0 __m1_");
		stream.println("\tsyscall");
		stream.println();
		stream.println("\tli $v0 1\t\t# syscall 1 (print_int)");
		stream.println("\tsrl $a0 $k0 2\t\t# Extract ExcCode Field");
		stream.println("\tandi $a0 $a0 0x1f");
		stream.println("\tsyscall");
		stream.println();
		stream.println("\tli $v0 4\t# syscall 4 (print_str)");
		stream.println("\tandi $a0 $k0 0x3c");
		stream.println("\tlw $a0 __excp($a0)");
		stream.println("\tnop");
		stream.println("\tsyscall");
		stream.println();
		stream.println("\tbne $k0 0x18 ok_pc	# Bad PC exception requires special checks");
		stream.println("\tnop");
		stream.println();
		stream.println("\tmfc0 $a0 $14\t\t# EPC");
		stream.println("\tandi $a0 $a0 0x3\t# Is EPC word-aligned?");
		stream.println("\tbeq $a0 0 ok_pc");
		stream.println("\tnop");
		stream.println();
		stream.println("\tli $v0 10\t\t# Exit on really bad PC");
		stream.println("\tsyscall");
		stream.println();
		stream.println("ok_pc:");
		stream.println("\tli $v0 4\t# syscall 4 (print_str)");
		stream.println("\tla $a0 __m2_");
		stream.println("\tsyscall");
		stream.println();
		stream.println("\tsrl $a0 $k0 2\t\t# Extract ExcCode Field");
		stream.println("\tandi $a0 $a0 0x1f");
		stream.println("\tbne $a0 0 ret\t\t# 0 means exception was an interrupt");
		stream.println("\tnop");
		stream.println();
		stream.println("# Interrupt-specific code goes here!");
		stream.println("# Don't skip instruction at EPC since it has not executed.");
		stream.println();
		stream.println();
		stream.println("ret:");
		stream.println("# Return from (non-interrupt) exception. Skip offending instruction");
		stream.println("# at EPC to avoid infinite loop.");
		stream.println("#");
		stream.println("\tmfc0 $k0 $14\t\t# Bump EPC register");
		stream.println("\taddiu $k0 $k0 4\t\t# Skip faulting instruction");
		stream.println("\t\t\t\t# (Need to handle delayed branch case here)");
		stream.println("\tmtc0 $k0 $14");
		stream.println();
		stream.println();
		stream.println("# Restore registers and reset procesor state");
		stream.println("#");
		stream.println("\tlw $v0 s1\t\t# Restore other registers");
		stream.println("\tlw $a0 s2");
		stream.println();
		stream.println("\t.set noat");
		stream.println("\tmove $at $k1\t\t# Restore $at");
		stream.println("\t.set at");
		stream.println();
		stream.println("\tmtc0 $0 $13\t\t# Clear Cause register");
		stream.println();
		stream.println("\tmfc0 $k0 $12\t\t# Set Status register");
		stream.println("\tori  $k0 0x1\t\t# Interrupts enabled");
		stream.println("\tmtc0 $k0 $12");
		stream.println();
		stream.println("# Return from exception on MIPS32:");
		stream.println("\teret");
		stream.println();
		stream.println("# Return sequence for MIPS-I (R2000):");
		stream.println("#\trfe\t\t\t# Return from exception handler");
		stream.println("\t\t\t\t# Should be in jr's delay slot");
		stream.println("#\tjr $k0");
		stream.println("#\tnop");
		stream.println();
		stream.println();
		stream.println();
	}
	
	/**
	 * Genera el código de comienzo de la aplicación
	 * @param stream
	 */
	private static void printStartCode(PrintStream stream) {
		stream.println("# Standard startup code.  Invoke the constructor of \"Main\" with arguments:");
		stream.println("#\tmain(argc, argv, envp)");
		stream.println("#");
		stream.println("\t.text");
		stream.println("\t.globl __start");
		stream.println("__start:");
		stream.println("\tori $27 $0 0");
		stream.println("\tlui $27 0x1000");
		stream.println("\tlw $a0 0($sp)\t\t# argc");
		stream.println("\taddiu $a1 $sp 4\t\t# argv");
		stream.println("\taddiu $a2 $a1 4\t\t# envp");
		stream.println("\tsll $v0 $a0 2");
		stream.println("\taddu $a2 $a2 $v0");
		stream.println("\tjal Main_Main");
		stream.println("\tnop");
		stream.println();
		stream.println("\tli $v0 10");
		stream.println("\tsyscall\t\t\t# syscall 10 (exit)");
		stream.println("\t.end\t__start");
		stream.println();
		stream.println();
		stream.println();
	}
	
}
