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
import tinto.code.*;
import java.util.Vector;
import java.util.Stack;


/**
 * Clase que contiene la descripción un método en ensamblador
 * 
 * @author Francisco José Moreno Velo
 */
public class MethodAssembler {
	
	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Etiqueta de comienzo del método
	 */
	private String label;
		
	/**
	 * Tamaño del registro de activación (en bytes)
	 */
	private int size;
	
	/**
	 * Pila de los tamaños de PRECALL. Es necesaria para conocer el desplazamiento
	 * del Stack Pointer a la vuelta de una llamada
	 */
	private Stack<CodeLiteral> callstack;
	
	/**
	 * Lista de instrucciones
	 */
	private Instruction[] list;
	
	//----------------------------------------------------------------//
	//                            Constructor                         //
	//----------------------------------------------------------------//

	/**
	 * Constructor
	 * @param label
	 */
	public MethodAssembler(MethodCodification codif) {
		this.label = codif.getMethodLabel();
		this.size = codif.getFrameSize();
		this.callstack = new Stack<CodeLiteral>(); 
		this.list = createAssembler(codif);
	}

	//----------------------------------------------------------------//
	//                        Métodos públicos                        //
	//----------------------------------------------------------------//

	/**
	 * Obtiene el nombre de la etiqueta del método
	 * @return
	 */
	public String getMethodLabel() {
		return this.label;
	}
	
	/**
	 * Obtiene la lista de instrucciones del método
	 * @return
	 */
	public Instruction[] getInstructionList() {
		return this.list;
	}
	
	/**
	 * Escribe el código completo del método sobre un flujo
	 * 
	 * @return
	 */
	public void print(PrintStream stream) {
		stream.println("#------------------------------------------------------------------");
		stream.println("# "+label);
		stream.println("#------------------------------------------------------------------");
		stream.println();
		stream.println("\t.globl\t"+label);
		stream.println("\t.ent\t"+label);
		
		for(int i=0; i<list.length; i++) stream.println(list[i].getAssembler());

		stream.println("\t.end\t"+label);
		stream.println();
	}
	
	//----------------------------------------------------------------//
	//                        Métodos  privados                       //
	//----------------------------------------------------------------//

	/**
	 * Traduce el código intermedio del método a código ensamblador
	 * @param codif
	 * @return
	 */
	private Instruction[] createAssembler(MethodCodification codif) {
		Vector<Instruction> vector = new Vector<Instruction>();
		int retsize = 4;
		
		// Instrucciones de entrada a una función	
		vector.add(InstructionFactory.createLabel(label));										// Etiqueta de salto a la función
		vector.add(InstructionFactory.createADDIU(RegisterSet.sp, RegisterSet.sp, -size));		// Reserva espacio para el registro de activación
		vector.add(InstructionFactory.createSW(RegisterSet.ra, RegisterSet.sp, size-retsize-4));	// Almacena la dirección de retorno
		vector.add(InstructionFactory.createSW(RegisterSet.fp, RegisterSet.sp, size-retsize-8));// Almacena el frame pointer
		vector.add(InstructionFactory.createMOVE(RegisterSet.fp, RegisterSet.sp));				// Asigna el nuevo frame pointer

		// Traduce a ensamblador el cuerpo del método
		CodeInstruction codelist[] = codif.getCodeInstructionList().getList();
		for(int i=0; i<codelist.length; i++) {
			createAssembler(vector,codelist[i]);
		}
		
		// Instrucciones de salida de una función
		vector.add(InstructionFactory.createLabel(label+"_ret"));                          			// Etiqueta de retorno de la función
		vector.add(InstructionFactory.createSW(RegisterSet.v0, RegisterSet.fp, (size-retsize)));	// Almacena el valor de retorno
		
		vector.add(InstructionFactory.createMOVE(RegisterSet.sp, RegisterSet.fp));         			// Libera todo el registro de activación
		vector.add(InstructionFactory.createLW(RegisterSet.ra, RegisterSet.sp, (size-retsize-4))); 	// Asigna la dirección de retorno
		vector.add(InstructionFactory.createLW(RegisterSet.fp, RegisterSet.sp, (size-retsize-8))); 	// Recupera el frame pointer anterior
		vector.add(InstructionFactory.createADDIU(RegisterSet.sp, RegisterSet.sp, size));  			// Recupera el stack pointer anterior
		vector.add(InstructionFactory.createJR(RegisterSet.ra));                           			// Salta a la dirección de retorno
		vector.add(InstructionFactory.createNOP());

		Instruction[] list = new Instruction[vector.size()];
		vector.toArray(list);
		return list;
	}
	
	/**
	 * Genera la descripción en ensamblador de cada una de las instrucciones del código intermedio
	 * @param vector
	 * @param inst
	 */
	private void createAssembler(Vector<Instruction> vector, CodeInstruction inst ) {
		int kind = inst.getKind();
		CodeAddress target = inst.getTarget();
		CodeAddress source1 = inst.getSource1();
		CodeAddress source2 = inst.getSource2();
				
		switch(kind) {
			case CodeConstants.LABEL:    translateLabel(vector, target); break;
			case CodeConstants.ASSIGN:   translateASSIGN(vector,target,source1); break;
			case CodeConstants.ADD:      translateADD(vector,target,source1,source2); break;
			case CodeConstants.SUB:      translateSUB(vector,target,source1,source2); break;
			case CodeConstants.MUL:      translateMUL(vector,target,source1,source2); break;
			case CodeConstants.DIV:      translateDIV(vector,target,source1,source2); break;
			case CodeConstants.MOD:      translateMOD(vector,target,source1,source2); break;
			case CodeConstants.INV:      translateINV(vector,target,source1); break;
			case CodeConstants.AND:      translateAND(vector,target,source1,source2); break;
			case CodeConstants.OR:       translateOR(vector,target,source1,source2); break;
			case CodeConstants.NOT:      translateNOT(vector,target,source1); break;
			case CodeConstants.JMPEQ:    translateJMPEQ(vector,target,source1,source2); break;
			case CodeConstants.JMPNE:    translateJMPNE(vector,target,source1,source2); break;
			case CodeConstants.JMPGT:    translateJMPGT(vector,target,source1,source2); break;
			case CodeConstants.JMPGE:    translateJMPGE(vector,target,source1,source2); break;
			case CodeConstants.JMPLT:    translateJMPLT(vector,target,source1,source2); break;
			case CodeConstants.JMPLE:    translateJMPLE(vector,target,source1,source2); break;
			case CodeConstants.JUMP:     translateJUMP(vector,target); break;
			case CodeConstants.JMP1:     translateJMP1(vector,target,source1); break;
			case CodeConstants.PARAM:    translatePARAM(vector,target,source1); break;
			case CodeConstants.PRECALL:  translatePRECALL(vector,target); break;				
			case CodeConstants.CALL:     translateCALL(vector,target,source1); break;
			case CodeConstants.RETURN:   translateRETURN(vector,target); break;
			case CodeConstants.COMP:	 translateCOMP(vector,target,source1); break;
			case CodeConstants.BIT_AND:	 translateBITAND(vector,target,source1,source2); break;
			case CodeConstants.BIT_OR:	 translateBITOR(vector,target,source1,source2); break;
			case CodeConstants.XOR:	 	 translateXOR(vector,target,source1,source2); break;
			case CodeConstants.LSHIFT:	 translateLSHIFT(vector,target,source1,source2); break;
			case CodeConstants.RSIGNEDSHIFT:	translateRSIGNEDSHIFT(vector,target,source1,source2); break;
			case CodeConstants.RUNSIGNEDSHIFT:	translateRUNSIGNEDSHIFT(vector,target,source1,source2); break;
		}
	}
	
	//----------------------------------------------------------------//
	//                    Traducción de las etiquetas                 //
	//----------------------------------------------------------------//

	/**
	 * Genera el código ensamblador para una etiqueta
	 * @param vector
	 * @param label
	 */
	private void translateLabel(Vector<Instruction> vector, CodeAddress label) {
		CodeLabel codelb = (CodeLabel) label;
		vector.add(InstructionFactory.createLabel(codelb.toString()));
	}

	//----------------------------------------------------------------//
	// Traducción de las instrucciones aritméticas de tipo int        //
	//----------------------------------------------------------------//

	/**
	 * Genera el código ensamblador para una asignación de tipo int
	 * @param vector
	 * @param target
	 * @param source
	 */
	private void translateASSIGN(Vector<Instruction> vector, CodeAddress target, CodeAddress source) {
		Register target_reg = getTargetRegister(target, RegisterSet.a0);
		Register reg = translateDelayedLoadIntValue(vector,source,target_reg);
		translateStoreIntValue(vector,target,reg);
	}
	
	/**
	 * Genera el código ensamblador para una suma entera
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateADD(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) {
		Register source1_reg = translateLoadIntValue(vector,source1,RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector,source2,RegisterSet.a1);
		Register target_reg = getTargetRegister(target,RegisterSet.v0);
		vector.add(InstructionFactory.createADDU(target_reg, source1_reg, source2_reg));
		translateStoreIntValue(vector,target,target_reg);		
	}

	/**
	 * Genera el código ensamblador para una resta entera
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateSUB(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) {
		Register source1_reg = translateLoadIntValue(vector,source1,RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector,source2,RegisterSet.a1);
		Register target_reg = getTargetRegister(target,RegisterSet.v0);
		vector.add(InstructionFactory.createSUBU(target_reg, source1_reg, source2_reg));
		translateStoreIntValue(vector,target,target_reg);		
	}

	/**
	 * Genera el código ensamblador para una multiplicación entera
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateMUL(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) {
		Register source1_reg = translateLoadIntValue(vector,source1,RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector,source2,RegisterSet.a1);
		Register target_reg = getTargetRegister(target,RegisterSet.v0);
		vector.add(InstructionFactory.createMULT(source1_reg, source2_reg));
		vector.add(InstructionFactory.createMFLO(target_reg));
		translateStoreIntValue(vector,target,target_reg);		
	}

	/**
	 * Genera el código ensamblador para una división entera
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateDIV(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) {
		Register source1_reg = translateLoadIntValue(vector,source1,RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector,source2,RegisterSet.a1);
		Register target_reg = getTargetRegister(target,RegisterSet.v0);
		vector.add(InstructionFactory.createDIV(source1_reg, source2_reg));
		vector.add(InstructionFactory.createMFLO(target_reg));
		translateStoreIntValue(vector,target,target_reg);		
	}
	
	/**
	 * Genera el código ensamblador para el resto de una división entera
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateMOD(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) {
		Register source1_reg = translateLoadIntValue(vector,source1,RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector,source2,RegisterSet.a1);
		Register target_reg = getTargetRegister(target,RegisterSet.v0);
		vector.add(InstructionFactory.createDIV(source1_reg, source2_reg));
		vector.add(InstructionFactory.createMFHI(target_reg));
		translateStoreIntValue(vector,target,target_reg);		
	}

	/**
	 * Genera el código ensamblador para un cambio de signo
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateINV(Vector<Instruction> vector, CodeAddress target, CodeAddress source) {
		Register source_reg = translateDelayedLoadIntValue(vector,source,RegisterSet.a0);
		Register target_reg = getTargetRegister(target,RegisterSet.v0);
		vector.add(InstructionFactory.createSUBU(target_reg, RegisterSet.r0, source_reg));
		translateStoreIntValue(vector,target,target_reg);		
	}

	//----------------------------------------------------------------//
	// Traducción de las instrucciones booleanas                      //
	//----------------------------------------------------------------//

	/**
	 * Genera el código ensamblador para una conjunción lógica
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateAND(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) {
		Register source1_reg = translateLoadIntValue(vector,source1,RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector,source2,RegisterSet.a1);
		Register target_reg = getTargetRegister(target,RegisterSet.v0);
		vector.add(InstructionFactory.createAND(target_reg, source1_reg, source2_reg));
		translateStoreIntValue(vector,target,target_reg);		
	}

	/**
	 * Genera el código ensamblador para una disyunción lógica
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateOR(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) {
		Register source1_reg = translateLoadIntValue(vector,source1,RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector,source2,RegisterSet.a1);
		Register target_reg = getTargetRegister(target,RegisterSet.v0);
		vector.add(InstructionFactory.createOR(target_reg, source1_reg, source2_reg));
		translateStoreIntValue(vector,target,target_reg);		
	}
	
	/**
	 * Genera el código ensamblador para una negación lógica
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateNOT(Vector<Instruction> vector, CodeAddress target, CodeAddress source1) {
		Register source1_reg = translateDelayedLoadIntValue(vector,source1,RegisterSet.a0);
		Register target_reg = getTargetRegister(target,RegisterSet.v0);
		vector.add(InstructionFactory.createSLTI(target_reg, source1_reg, 1));
		translateStoreIntValue(vector,target,target_reg);		
	}

	//----------------------------------------------------------------//
	// Instrucciones de salto condicional para el tipo int           //
	//----------------------------------------------------------------//

	/**
	 * Genera el código ensamblador para un salto condicional si source1 == source2 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateJMPEQ(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) {
		Register source1_reg = translateLoadIntValue(vector,source1,RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector,source2,RegisterSet.a1);
		String label = target.toString();
		vector.add(InstructionFactory.createBEQ(source1_reg, source2_reg,label));
		vector.add(InstructionFactory.createNOP());
	}
	
	/**
	 * Genera el código ensamblador para un salto condicional si source1 != source2 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateJMPNE(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) {
		Register source1_reg = translateLoadIntValue(vector,source1,RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector,source2,RegisterSet.a1);
		String label = target.toString();
		vector.add(InstructionFactory.createBNE(source1_reg, source2_reg,label));
		vector.add(InstructionFactory.createNOP());
	}
	
	/**
	 * Genera el código ensamblador para un salto condicional si source1 > source2 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateJMPGT(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) {
		Register source1_reg = translateLoadIntValue(vector,source1,RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector,source2,RegisterSet.a1);
		String label = target.toString();
		vector.add(InstructionFactory.createSLT(RegisterSet.v0, source2_reg, source1_reg));
		vector.add(InstructionFactory.createBNE(RegisterSet.v0, RegisterSet.r0, label));
		vector.add(InstructionFactory.createNOP());
	}

	/**
	 * Genera el código ensamblador para un salto condicional si source1 >= source2 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateJMPGE(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) {
		Register source1_reg = translateLoadIntValue(vector,source1,RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector,source2,RegisterSet.a1);
		String label = target.toString();
		vector.add(InstructionFactory.createSLT(RegisterSet.v0, source1_reg, source2_reg));
		vector.add(InstructionFactory.createBEQ(RegisterSet.v0, RegisterSet.r0, label));
		vector.add(InstructionFactory.createNOP());
	}

	/**
	 * Genera el código ensamblador para un salto condicional si source1 < source2 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateJMPLT(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) {
		Register source1_reg = translateLoadIntValue(vector,source1,RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector,source2,RegisterSet.a1);
		String label = target.toString();
		vector.add(InstructionFactory.createSLT(RegisterSet.v0, source1_reg, source2_reg));
		vector.add(InstructionFactory.createBNE(RegisterSet.v0, RegisterSet.r0, label));
		vector.add(InstructionFactory.createNOP());
	}

	/**
	 * Genera el código ensamblador para un salto condicional si source1 <= source2 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateJMPLE(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) {
		Register source1_reg = translateLoadIntValue(vector,source1,RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector,source2,RegisterSet.a1);
		String label = target.toString();
		vector.add(InstructionFactory.createSLT(RegisterSet.v0, source2_reg, source1_reg));
		vector.add(InstructionFactory.createBEQ(RegisterSet.v0, RegisterSet.r0, label));
		vector.add(InstructionFactory.createNOP());
	}

	//----------------------------------------------------------------//
	// Otras instrucciones de salto                                    //
	//----------------------------------------------------------------//

	/**
	 * Genera el código ensamblador para un salto incondicional
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateJUMP(Vector<Instruction> vector, CodeAddress target) {
		String label = target.toString();
		vector.add(InstructionFactory.createJ(label));
		vector.add(InstructionFactory.createNOP());
	}


	/**
	 * Genera el código ensamblador para un salto condicional si source != 0
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateJMP1(Vector<Instruction> vector, CodeAddress target, CodeAddress source1) {
		Register source1_reg = translateDelayedLoadIntValue(vector,source1,RegisterSet.a0);
		String label = target.toString();
		vector.add(InstructionFactory.createBNE(source1_reg, RegisterSet.r0,label));
		vector.add(InstructionFactory.createNOP());
	}

	//----------------------------------------------------------------//
	// Instrucciones de manejo de funciones                           //
	//----------------------------------------------------------------//

	/**
	 * Genera el código ensamblador para una instrucción "return temp"
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateRETURN(Vector<Instruction> vector, CodeAddress target) {
		Register r = translateLoadIntValue(vector,target,RegisterSet.v0);
		if(r != RegisterSet.v0) vector.add(InstructionFactory.createMOVE(RegisterSet.v0, r));
		vector.add(InstructionFactory.createJ(this.label+"_ret"));
		vector.add(InstructionFactory.createNOP());
	}

	/**
	 * Genera el código para almacenar el valor de un argumento de la próxima llamada
	 * @param vector Lista de instrucciones
	 * @param target Variable que contiene el valor del argumento
	 * @param source Posición del argumento respecto al Stack Pointer
	 */
	private void translatePARAM(Vector<Instruction> vector, CodeAddress target, CodeAddress source) {
		Register r = translateDelayedLoadIntValue(vector,target,RegisterSet.a0);
		String hex = ((CodeLiteral) source).getHexDescription();
		int offset = Integer.parseInt(hex, 16);
		vector.add(InstructionFactory.createSW(r, RegisterSet.sp, offset));
	}
	
	/**
	 * Genera el código previo a una llamada a una función (desplazamiento del Stack Pointer)
	 * conocido los argumentos de la llamada
	 * @param vector
	 * @param target
	 */
	private void translatePRECALL(Vector<Instruction> vector, CodeAddress target) {
		callstack.push((CodeLiteral) target);
		String hex = ((CodeLiteral) target).getHexDescription();
		int offset = Integer.parseInt(hex, 16);
		vector.add(InstructionFactory.createADDIU(RegisterSet.sp, RegisterSet.sp, -offset));
	}
	
	/**
	 * Genera el código ensamblador para una instrucción "call target function"
	 * @param vector	Lista de instrucciones
	 * @param target	Variable en la que almacenar el resultado
	 * @param function	Etiqueta de salto a la función
	 */
	private void translateCALL(Vector<Instruction> vector, CodeAddress target, CodeAddress function) {
		vector.add(InstructionFactory.createJAL(function.toString()));
		vector.add(InstructionFactory.createNOP());
		vector.add(InstructionFactory.createLW(RegisterSet.v0, RegisterSet.sp, -4));
		vector.add(InstructionFactory.createNOP());
		translateStoreIntValue(vector,target,RegisterSet.v0);
		CodeLiteral argsize = callstack.pop();
		String hex = argsize.getHexDescription();
		int offset = Integer.parseInt(hex,16);
		vector.add(InstructionFactory.createADDIU(RegisterSet.sp, RegisterSet.sp, offset));
	}

	//----------------------------------------------------------------//
	// Instrucciones de intercambio a memoria para el tipo int        //
	//----------------------------------------------------------------//

	/**
	 * Genera el código para volcar un valor (un literal o una variable) en un registro 
	 * @param vector 	Lista de instrucciones
	 * @param address 	Referencia al valor a almacenar
	 * @param r 		Registro propuesto para el destino
	 * @return			Registro en el que se coloca el valor
	 */
	private Register translateLoadIntValue(Vector<Instruction> vector, CodeAddress address, Register r) {
		if(address instanceof CodeLiteral) {  // Referencia a un valor constante
			return translateLoadLiteral(vector,(CodeLiteral) address, r);
		} else { // Referencia a una variable
			CodeVariable svar = (CodeVariable) address;
			if(svar.inRegister()) { // Variables almacenadas en registro
				return RegisterSet.getRegister(svar.getLocation());
			} else { // Variables almacenadas en el registro de activación de una función
				vector.add(InstructionFactory.createLW(r,RegisterSet.fp,svar.getLocation()));
				return r;
			}
		}
	}

	/**
	 * Genera el código para volcar un valor (un literal o una variable) en un registro 
	 * @param vector 	Lista de instrucciones
	 * @param address 	Referencia al valor a almacenar
	 * @param r 		Registro propuesto para el destino
	 * @return			Registro en el que se coloca el valor
	 */
	private Register translateDelayedLoadIntValue(Vector<Instruction> vector, CodeAddress address, Register r) {
		if(address instanceof CodeLiteral) {  // Referencia a un valor constante
			return translateLoadLiteral(vector,(CodeLiteral) address, r);
		} else { // Referencia a una variable
			CodeVariable svar = (CodeVariable) address;
			if(svar.inRegister()) { // Variables almacenadas en registro
				return RegisterSet.getRegister(svar.getLocation());
			} else { // Variables almacenadas en el registro de activación de una función
				vector.add(InstructionFactory.createLW(r,RegisterSet.fp,svar.getLocation()));
				vector.add(InstructionFactory.createNOP());
				return r;
			}
		}
	}

	/**
	 * Genera el código para colocar un valor constante (literal) en un registro
	 * @param vector
	 * @param literal
	 * @param r
	 * @return
	 */
	private Register translateLoadLiteral(Vector<Instruction> vector, CodeLiteral literal, Register r) {
		String  dec = literal.getDescription();
		int value = Integer.parseInt(dec, 10);
		if(value>0x0FFFF) { // Valores mayores de 16 bits
			int upper = (value & 0xFFFF0000)>>16;
	        int lower = (value & 0x0000FFFF);
			vector.add(InstructionFactory.createLUI(r, upper));
			vector.add(InstructionFactory.createORI(r, r, lower));
		} else { // Valores menores de 16 bits
			vector.add(InstructionFactory.createLI(r, value));
		}
		return r;
	}
	
	/**
	 * Obtiene el registro donde almacenar el resultado de una operación.
	 * Si la variable de destino se almacena en un registro entonces devuelve
	 * dicho registro. Si la variable de destino se almacena en memoria devuelve
	 * el registro destino propuesto.
	 * @param address Variable de destino de la operación
	 * @param r Registro propuesto como destino
	 * @return
	 */
	private Register getTargetRegister(CodeAddress address, Register r) {
		CodeVariable tvar = (CodeVariable) address;
		if(tvar.inRegister()) return RegisterSet.getRegister(tvar.getLocation());
		else return r;
	}
	
	/**
	 * Genera el código para almacenar un valor en una variable
	 * @param vector	Lista de instrucciones
	 * @param address	Referencia a la variable
	 * @param r			Registro en el que se encuentra el valor a almacenar
	 */
	private void translateStoreIntValue(Vector<Instruction> vector, CodeAddress address, Register r) {
		CodeVariable tvar = (CodeVariable) address;
		if(tvar.inRegister() && tvar.getLocation() != r.getCode()) { // Variables almacenadas en registro
			Register target = RegisterSet.getRegister(tvar.getLocation());
			vector.add(InstructionFactory.createMOVE(target, r));
		} else { // Variables almacenadas en el registro de activación de una función
			vector.add(InstructionFactory.createSW(r, RegisterSet.fp, tvar.getLocation()));
		}
	}
	
	//----------------------------------------------------------------//
	// Instrucciones a nivel de bit	   						          //
	//----------------------------------------------------------------//

	/**
	 * Genera el código ensamblador para un complemento a 1
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateCOMP(Vector<Instruction> vector, CodeAddress target, CodeAddress source) {
		Register source_reg = translateDelayedLoadIntValue(vector,source,RegisterSet.a0);
		Register target_reg = getTargetRegister(target,RegisterSet.v0);
		vector.add(InstructionFactory.createNOR(target_reg, RegisterSet.r0, source_reg));
		translateStoreIntValue(vector,target,target_reg);		
	}

	/**
	 * Genera el código ensamblador para una operacion and a nivel de bit
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateBITAND(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) {
		Register source1_reg = translateLoadIntValue(vector,source1,RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector,source2,RegisterSet.a1);
		Register target_reg = getTargetRegister(target,RegisterSet.v0);
		vector.add(InstructionFactory.createAND(target_reg, source1_reg, source2_reg));
		translateStoreIntValue(vector,target,target_reg);		
	}

	/**
	 * Genera el código ensamblador para una operacion or a nivel de bit
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateBITOR(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) {
		Register source1_reg = translateLoadIntValue(vector,source1,RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector,source2,RegisterSet.a1);
		Register target_reg = getTargetRegister(target,RegisterSet.v0);
		vector.add(InstructionFactory.createOR(target_reg, source1_reg, source2_reg));
		translateStoreIntValue(vector,target,target_reg);		
	}

	/**
	 * Genera el código ensamblador para una operacion xor a nivel de bit
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateXOR(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) {
		Register source1_reg = translateLoadIntValue(vector,source1,RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector,source2,RegisterSet.a1);
		Register target_reg = getTargetRegister(target,RegisterSet.v0);
		vector.add(InstructionFactory.createXOR(target_reg, source1_reg, source2_reg));
		translateStoreIntValue(vector,target,target_reg);		
	}

	/**
	 * Genera el código ensamblador para una operacion de despalzamiento de bit a la izquierda
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateLSHIFT(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) {
		Register source1_reg = translateLoadIntValue(vector,source1,RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector,source2,RegisterSet.a1);
		Register target_reg = getTargetRegister(target,RegisterSet.v0);
		vector.add(InstructionFactory.createSLL(target_reg, source1_reg, source2_reg));
		translateStoreIntValue(vector,target,target_reg);		
	}

	/**
	 * Genera el código ensamblador para una operacion de despalzamiento de bit a la deracha conservando el signo
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateRSIGNEDSHIFT(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) {
		Register source1_reg = translateLoadIntValue(vector,source1,RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector,source2,RegisterSet.a1);
		Register target_reg = getTargetRegister(target,RegisterSet.v0);
		vector.add(InstructionFactory.createSRA(target_reg, source1_reg, source2_reg));
		translateStoreIntValue(vector,target,target_reg);		
	}

	/**
	 * Genera el código ensamblador para una operacion de despalzamiento de bit a la derecha ignorando el signo
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateRUNSIGNEDSHIFT(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) {
		Register source1_reg = translateLoadIntValue(vector,source1,RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector,source2,RegisterSet.a1);
		Register target_reg = getTargetRegister(target,RegisterSet.v0);
		vector.add(InstructionFactory.createSRL(target_reg, source1_reg, source2_reg));
		translateStoreIntValue(vector,target,target_reg);		
	}	
	
}
