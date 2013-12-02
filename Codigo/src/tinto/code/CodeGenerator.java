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

package tinto.code;

import tinto.ast.*;
import tinto.ast.expression.*;
import tinto.ast.struct.*;
import tinto.ast.statement.*;

/**
 * Clase que traduce el árbol de sintaxis abstracta a código intermedio
 *  
 * @author Francisco José Moreno Velo
 *
 */
public class CodeGenerator implements CodeConstants {

	/**
	 * Constructor
	 *
	 */
	public CodeGenerator() {
	}
	
	//------------------------------------------------------------------------//
	// Métodos de generacion de código intermedio                             //
	//------------------------------------------------------------------------//
	
	//La variable etiquetas almacena en la posicion 0 la etiqueta a la que salta una instruccion break
	// y en la posicion 1 la etiqueta a la que salta una instruccion continue
	
	/**
	 * Genera el código intermedio de la biblioteca completa
	 * 
	 * @param decl Descripción de la biblioteca
	 */
	public LibraryCodification generateLibraryCodification(Library library) {
		String libname = library.getName();
		Method[] method = library.getMethods();
		String[] imported = library.getImported();

		LibraryCodification codif = new LibraryCodification(libname,imported,method.length);
		
		for(int i=0; i<method.length; i++) {
			MethodCodification mc = generateMethodCode(method[i]);
			codif.setMethodCodification(i,mc);
		}
		return codif;
	}
	
	/**
	 * Genera el código asociado a un método
	 * @param stream
	 * @param method
	 */
	private MethodCodification generateMethodCode(Method method) {
		Variable[] arg = method.getArguments();
		Variable[] local = method.getLocalVariables();
		
		MethodCodification mc = new MethodCodification(method.getLabel(),method.getType(),arg,local);
		BlockStatement body = method.getBody();
		CodeInstructionList list = generateCodeOfBlockStatement(mc,body,null);
		mc.setInstructionList(list);
		return mc;
	}

	/**
	 * Genera el código de una instrucción
	 * @param mc Descripción del método al que pertenece la instrucción
	 * @param inst Instrucción a tratar
	 */
	private CodeInstructionList generateCodeOfStatement(MethodCodification mc, Statement stm, CodeLabel[] etiquetas) {
		if(stm instanceof BlockStatement) {
			return generateCodeOfBlockStatement(mc,(BlockStatement) stm, etiquetas);
		} else if(stm instanceof IfStatement) {
			return generateCodeOfIfStatement(mc, (IfStatement) stm, etiquetas);
		} else if(stm instanceof SwitchStatement) {
			return generateCodeOfSwitchStatement(mc, (SwitchStatement) stm, etiquetas);
		} else if(stm instanceof WhileStatement) {
			return generateCodeOfWhileStatement(mc, (WhileStatement) stm); 
		} else if(stm instanceof DoWhileStatement) {
			return generateCodeOfDoWhileStatement(mc, (DoWhileStatement) stm); 
		} else if(stm instanceof ForStatement) {
			return generateCodeOfForStatement(mc, (ForStatement) stm); 
		} else if(stm instanceof ReturnStatement) {
			return generateCodeOfReturnStatement(mc, (ReturnStatement) stm);
		} else if(stm instanceof BreakStatement) {
			return generateCodeOfBreakStatement(mc, (BreakStatement) stm, etiquetas);
		} else if(stm instanceof ContinueStatement) {
			return generateCodeOfContinueStatement(mc, (ContinueStatement) stm, etiquetas);
		} else if(stm instanceof CallStatement) {
			return generateCodeOfCallStatement(mc, (CallStatement) stm);
		} else if(stm instanceof AssignStatement) {
			return generateCodeOfAssignStatement(mc, (AssignStatement) stm);
		} else {
			return new CodeInstructionList();
		}
	}
	
	/**
	 * Genera el código de un conjunto de instrucciones
	 * @param stream
	 * @param inst
	 */
	private CodeInstructionList generateCodeOfBlockStatement(MethodCodification mc, BlockStatement block, CodeLabel[] etiquetas) {
		CodeInstructionList list = new CodeInstructionList();
		Statement[] inst = block.getStatementList();
		for(int i=0; i<inst.length; i++) {
			CodeInstructionList instcode =  generateCodeOfStatement(mc,inst[i],etiquetas);
			list.addInstructionList(instcode.getList());
		}
		return list;
	}

	/**
	 * Genera el código de una instrucción IF
	 */
	private CodeInstructionList generateCodeOfIfStatement(MethodCodification mc, IfStatement inst, CodeLabel[] etiquetas) {
		Expression condition = inst.getCondition();
		Statement thenInst = inst.getThenInstruction();
		Statement elseInst = inst.getElseInstruction();
		CodeLabel lbTrue = mc.getNewLabel();
		CodeLabel lbFalse = mc.getNewLabel();
		CodeInstruction lbTrueInst = new CodeInstruction(LABEL,lbTrue,null,null);
		CodeInstruction lbFalseInst = new CodeInstruction(LABEL,lbFalse,null,null);
		
		CodeInstructionList condinst = generateCodeForCondition(mc,condition,lbTrue,lbFalse);
		CodeInstructionList theninst = generateCodeOfStatement(mc,thenInst, etiquetas);
		CodeInstructionList elseinst = generateCodeOfStatement(mc,elseInst, etiquetas);
		
		CodeInstructionList codelist = new CodeInstructionList();
		codelist.addInstructionList(condinst.getList());
		codelist.addInstruction(lbTrueInst);
		codelist.addInstructionList(theninst.getList());
		
		if(elseInst == null) {
			codelist.addInstruction(lbFalseInst);
		} else {
			CodeLabel lbEnd = mc.getNewLabel();
			CodeInstruction lbEndInst = new CodeInstruction(LABEL,lbEnd,null,null);	
			CodeInstruction gotoEnd = new CodeInstruction(JUMP,lbEnd,null,null);
			codelist.addInstruction(gotoEnd);
			codelist.addInstruction(lbFalseInst);
			codelist.addInstructionList(elseinst.getList());
			codelist.addInstruction(lbEndInst);
		}
		
		return codelist;
	}
	
	/**
	 * Genera el código de una instrucción WHILE
	 * @param stream
	 * @param inst
	 */
	private CodeInstructionList generateCodeOfWhileStatement(MethodCodification mc, WhileStatement inst) {
		Expression condition = inst.getCondition();
		Statement block = inst.getInstruction();
		
		CodeLabel lbBegin = mc.getNewLabel();
		CodeLabel lbTrue = mc.getNewLabel();
		CodeLabel lbFalse = mc.getNewLabel();
		
		CodeInstruction lbBeginInst = new CodeInstruction(LABEL,lbBegin,null,null);
		CodeInstruction lbTrueInst = new CodeInstruction(LABEL,lbTrue,null,null);
		CodeInstruction lbFalseInst = new CodeInstruction(LABEL,lbFalse,null,null);
		CodeInstruction jmpBegin = new CodeInstruction(JUMP,lbBegin,null,null);
				
		CodeInstructionList condinst = generateCodeForCondition(mc,condition,lbTrue,lbFalse);
		CodeLabel[] etiqueta = new CodeLabel[2];
		etiqueta[0] = lbFalse;
		etiqueta[1] = lbBegin;
		CodeInstructionList blockinst = generateCodeOfStatement(mc,block,etiqueta);
		
		CodeInstructionList codelist = new CodeInstructionList();
		codelist.addInstruction(lbBeginInst);
		codelist.addInstructionList(condinst.getList());
		codelist.addInstruction(lbTrueInst);
		codelist.addInstructionList(blockinst.getList());
		codelist.addInstruction(jmpBegin);
		codelist.addInstruction(lbFalseInst);
		
		return codelist;		
	}
	
	/**
	 * Genera el código de una instrucción DOWHILE
	 * @param stream
	 * @param inst
	 */
	private CodeInstructionList generateCodeOfDoWhileStatement(MethodCodification mc, DoWhileStatement inst) {
		Expression condition = inst.getCondition();
		Statement block = inst.getInstruction();
		
		CodeLabel lbBegin = mc.getNewLabel();
		CodeLabel lbCond = mc.getNewLabel();
		CodeLabel lbFalse = mc.getNewLabel();
		
		CodeInstruction lbBeginInst = new CodeInstruction(LABEL,lbBegin,null,null);
		CodeInstruction lbCondInst = new CodeInstruction(LABEL,lbCond,null,null);
		CodeInstruction lbFalseInst = new CodeInstruction(LABEL,lbFalse,null,null);
				
		CodeInstructionList condinst = generateCodeForCondition(mc,condition,lbBegin,lbFalse);
		CodeLabel[] etiqueta = new CodeLabel[2];
		etiqueta[0] = lbFalse;
		etiqueta[1] = lbCond;
		CodeInstructionList blockinst = generateCodeOfStatement(mc,block,etiqueta);
		
		CodeInstructionList codelist = new CodeInstructionList();
		codelist.addInstruction(lbBeginInst);
		codelist.addInstructionList(blockinst.getList());
		codelist.addInstruction(lbCondInst);
		codelist.addInstructionList(condinst.getList());
		codelist.addInstruction(lbFalseInst);
		
		return codelist;		
	}
	
	/**
	 * Genera el código de una instrucción For
	 * @param stream
	 * @param inst
	 */
	private CodeInstructionList generateCodeOfForStatement(MethodCodification mc, ForStatement inst) {
		Statement inicialization = inst.getInicializacion();
		Expression condition = inst.getCondition();
		Statement increment = inst.getIncremento();
		Statement block = inst.getInstruction();
		
		CodeLabel lbBegin = mc.getNewLabel();
		CodeLabel lbInc = mc.getNewLabel();
		CodeLabel lbTrue = mc.getNewLabel();
		CodeLabel lbFalse = mc.getNewLabel();
		CodeInstruction jmpBegin = new CodeInstruction(JUMP,lbBegin,null,null);
		
		
		CodeInstruction lbBeginInst = new CodeInstruction(LABEL,lbBegin,null,null);
		CodeInstruction lbIncInst = new CodeInstruction(LABEL,lbInc,null,null);
		CodeInstruction lbTrueInst = new CodeInstruction(LABEL,lbTrue,null,null);
		CodeInstruction lbFalseInst = new CodeInstruction(LABEL,lbFalse,null,null);
				
		CodeInstructionList iniinst = generateCodeOfStatement(mc,inicialization,null);
		CodeInstructionList condinst = generateCodeForCondition(mc,condition,lbTrue,lbFalse);
		CodeInstructionList incinst = generateCodeOfStatement(mc,increment,null);
		CodeLabel[] etiqueta = new CodeLabel[2];
		etiqueta[0] = lbFalse;
		etiqueta[1] = lbInc;
		CodeInstructionList blockinst = generateCodeOfStatement(mc,block,etiqueta);
		
		CodeInstructionList codelist = new CodeInstructionList();
		codelist.addInstructionList(iniinst.getList());
		codelist.addInstruction(lbBeginInst);
		codelist.addInstructionList(condinst.getList());
		codelist.addInstruction(lbTrueInst);
		codelist.addInstructionList(blockinst.getList());
		codelist.addInstruction(lbIncInst);
		codelist.addInstructionList(incinst.getList());
		codelist.addInstruction(jmpBegin);
		codelist.addInstruction(lbFalseInst);
		
		return codelist;		
	}
	
	/**
	 * Genera el código de una instrucción SWITCH
	 */
	private CodeInstructionList generateCodeOfSwitchStatement(MethodCodification mc, SwitchStatement inst, CodeLabel[] etiquetas) {
		Expression expression = inst.getExpression();
		DefaultCaseStatement[] caseList = inst.getBloquesCase();
		
		CodeLabel lbEndSwitch = mc.getNewLabel();
		CodeInstruction lbEndSwitchInst = new CodeInstruction(LABEL,lbEndSwitch,null,null);
		if (etiquetas!=null)
			etiquetas[0] = lbEndSwitch;
		else {
			etiquetas = new CodeLabel[2];
			etiquetas[0] = lbEndSwitch;
		}
		
		CodeInstructionList codelist = new CodeInstructionList();
		CodeAddress exp = generateCodeForExpression(mc,expression,codelist);
		CodeLabel lbDefault = mc.getNewLabel();
		CodeInstruction lbDefaultInst = new CodeInstruction(LABEL,lbDefault,null,null);
		
		CodeInstructionList codecase = new CodeInstructionList();
		for (int i=0;i<caseList.length;i++) {
			
			if (caseList[i].getValue()!=null) {
				CodeLabel lbCase = mc.getNewLabel();
				CodeInstruction lbCaseInst = new CodeInstruction(LABEL,lbCase,null,null);
				CodeLiteral value = new CodeLiteral(caseList[i].getValue().getValue());
			
				CodeInstruction jmpCase = new CodeInstruction(JMPEQ,lbCase,exp,value);
				codelist.addInstruction(jmpCase);
				
				CodeInstructionList blockcase = generateCodeOfStatement(mc,caseList[i].getInstruction(), etiquetas);
				codecase.addInstruction(lbCaseInst);
				codecase.addInstructionList(blockcase.getList());
			} else {
				CodeInstructionList blockcase = generateCodeOfStatement(mc,caseList[i].getInstruction(), etiquetas);
				codecase.addInstruction(lbDefaultInst);
				codecase.addInstructionList(blockcase.getList());
			}
		
		}
		
		CodeInstruction jmpDefault = new CodeInstruction(JUMP,lbDefault,null,null);
		codelist.addInstruction(jmpDefault);
		codelist.addInstructionList(codecase.getList());
		codelist.addInstruction(lbEndSwitchInst);
		
		return codelist;
	}
	
	/**
	 * Genera el código de una instrucción RETURN
	 * @param mc
	 * @param inst
	 */
	private CodeInstructionList generateCodeOfReturnStatement(MethodCodification mc, ReturnStatement inst) {
		Expression exp = inst.getExpression();
		
		CodeInstructionList codelist = new CodeInstructionList();
		
		if(exp == null) {
			CodeLiteral result = new CodeLiteral(0);
			codelist.addInstruction(new CodeInstruction(RETURN,result,null,null));
		} else {
			CodeAddress result = generateCodeForExpression(mc,exp,codelist);
			codelist.addInstruction(new CodeInstruction(RETURN,result,null,null));
		}
		
		return codelist;
	}
	
	/**
	 * Genera el código de una instrucción BREAK
	 * @param mc
	 * @param inst
	 */
	private CodeInstructionList generateCodeOfBreakStatement(MethodCodification mc, BreakStatement inst, CodeLabel[] etiquetas) {
		
		CodeInstructionList codelist = new CodeInstructionList();
		CodeInstruction jmpBegin = new CodeInstruction(JUMP,etiquetas[0],null,null);
		codelist.addInstruction(jmpBegin);
		return codelist;
	}
	
	/**
	 * Genera el código de una instrucción CONTINUE
	 * @param mc
	 * @param inst
	 */
	private CodeInstructionList generateCodeOfContinueStatement(MethodCodification mc, ContinueStatement inst, CodeLabel[] etiquetas) {
		
		CodeInstructionList codelist = new CodeInstructionList();
		CodeInstruction jmpBegin = new CodeInstruction(JUMP,etiquetas[1],null,null);
		codelist.addInstruction(jmpBegin);
		return codelist;
	}
		
	/**
	 * Genera el código de una instrucción de llamada a una función
	 */
	private CodeInstructionList generateCodeOfCallStatement(MethodCodification mc, CallStatement inst) {
		CallExpression action = inst.getExpression();
		CodeInstructionList codelist = new CodeInstructionList();			
		generateCodeForExpression(mc,action,codelist);
		return codelist;
	}
	
	/**
	 * Genera el código de una instrucción de asignación
	 */
	private CodeInstructionList generateCodeOfAssignStatement(MethodCodification mc, AssignStatement inst) {
		Variable var = inst.getLeftHand();
		Expression exp = inst.getExpression();
		CodeVariable target = mc.getVariable(var);
		CodeInstructionList codelist = new CodeInstructionList();		
		CodeAddress result = generateCodeForExpression(mc,exp,codelist);
		
		if (result instanceof CodeLiteral) {
			target.setValue(((CodeLiteral)result).getDescription());
		} else if (result instanceof CodeVariable) {
			String value = ((CodeVariable)result).getValue();		
			if (value != null) {
				target.setValue(value);
				result = new CodeLiteral(value);
			} else {
				target.setValue(null);
			}
		} 
		
		CodeInstruction assign = new CodeInstruction(ASSIGN, target, result, null);			
		codelist.addInstruction(assign);
		return codelist;
	}
	
	// Fin de la generacion de codigo de los Statement
	
	/**
	 * Genera el código para calcular el resultado de una expresión
	 * @param stream
	 * @param exp
	 * @return
	 */
	private CodeAddress generateCodeForExpression(MethodCodification mc, Expression exp, CodeInstructionList codelist) {
		int type = exp.getType();
		
		if(exp instanceof IntegerLiteralExpression) {
			return generateCodeForIntegerLiteralExpression(mc,(IntegerLiteralExpression) exp,codelist);
		} else if(exp instanceof CharLiteralExpression) {
			return generateCodeForCharLiteralExpression(mc,(CharLiteralExpression) exp,codelist);
		} else if(exp instanceof BooleanLiteralExpression) {
			return generateCodeForBooleanLiteralExpression(mc,(BooleanLiteralExpression) exp,codelist);
		} else if(type == Type.BOOLEAN_TYPE && exp instanceof BinaryExpression) {
			return generateCodeForBooleanExpression(mc,exp,codelist);
		} else if(type == Type.BOOLEAN_TYPE && exp instanceof UnaryExpression) {
			return generateCodeForBooleanExpression(mc,exp,codelist);
		} else if(exp instanceof UnaryExpression) {
			return generateCodeForUnaryExpression(mc, (UnaryExpression) exp, codelist);
		} else if(exp instanceof BinaryExpression) {
			return generateCodeForBinaryExpression(mc, (BinaryExpression) exp, codelist);
		} else if(exp instanceof CallExpression) {
			return generateCodeForCallExpression(mc,(CallExpression) exp, codelist);
		} else if(exp instanceof VariableExpression) {
			return generateCodeForVariableExpression(mc,(VariableExpression) exp, codelist);
		}
		return null;
	}
	
	/**
	 * Genera el código para analizar una condición
	 * @param stream
	 * @param cond
	 * @param lbtrue
	 * @param lbfalse
	 */
	private CodeInstructionList generateCodeForCondition(MethodCodification mc, Expression cond, CodeLabel lbtrue, CodeLabel lbfalse) {
		if(cond instanceof BooleanLiteralExpression) { // LITERALES TRUE O FALSE
			boolean val = ( (BooleanLiteralExpression) cond).getValue();
			CodeLabel lb = ( val? lbtrue : lbfalse);
			CodeInstructionList codelist = new CodeInstructionList();
			CodeInstruction jmp = new CodeInstruction(JUMP,lb,null,null);
			codelist.addInstruction(jmp);
			return codelist;
		} else if(cond instanceof UnaryExpression) { // NEGACIÓN
			Expression exp = ((UnaryExpression) cond).getExpression(); 
			return generateCodeForCondition(mc,exp,lbfalse,lbtrue);
		} else if(cond instanceof BinaryExpression) { // AND, OR Y COMPARACIONES
			BinaryExpression exp = (BinaryExpression) cond;
			Expression left = exp.getLeftExpression();
			Expression right = exp.getRightExpression();
			int operator = exp.getOperator();
			if(operator == BinaryExpression.AND) {
				CodeLabel lb = mc.getNewLabel();
				CodeInstruction lbInst = new CodeInstruction(LABEL,lb,null,null);
				CodeInstructionList leftcode = generateCodeForCondition(mc,left,lb,lbfalse);
				CodeInstructionList rightcode = generateCodeForCondition(mc,right,lbtrue,lbfalse);
				CodeInstructionList code = new CodeInstructionList();
				code.addInstructionList(leftcode.getList());
				code.addInstruction(lbInst);
				code.addInstructionList(rightcode.getList());
				return code;
			} else if(operator == BinaryExpression.OR) {
				CodeLabel lb = mc.getNewLabel();
				CodeInstruction lbInst = new CodeInstruction(LABEL,lb,null,null);
				CodeInstructionList leftcode = generateCodeForCondition(mc,left,lbtrue,lb);
				CodeInstructionList rightcode = generateCodeForCondition(mc,right,lbtrue,lbfalse);
				CodeInstructionList code = new CodeInstructionList();
				code.addInstructionList(leftcode.getList());
				code.addInstruction(lbInst);
				code.addInstructionList(rightcode.getList());
				return code;
			} else {
				int codekind = getBinaryCode(operator);
				CodeInstructionList leftcode = new CodeInstructionList();
				CodeInstructionList rightcode = new CodeInstructionList();
				CodeAddress source1 = generateCodeForExpression(mc,left,leftcode);
				CodeAddress source2 = generateCodeForExpression(mc,right,rightcode);
				
				CodeInstructionList code = new CodeInstructionList();
				code.addInstructionList(leftcode.getList());
				code.addInstructionList(rightcode.getList());
				code.addInstruction(new CodeInstruction(codekind,lbtrue,source1,source2));
				code.addInstruction(new CodeInstruction(JUMP,lbfalse,null,null));
				return code;
			}
		} else { // expresiones de tipo boolean (metodos o variables)
			CodeInstructionList code = new CodeInstructionList();
			CodeAddress target = generateCodeForExpression(mc,cond,code);
			code.addInstruction(new CodeInstruction(JMP1,lbtrue,target,null));
			code.addInstruction(new CodeInstruction(JUMP,lbfalse,null,null));
			return code;
		}
	}
	
	/**
	 * Genera el código asociado a una constante entera.
	 */
	private CodeAddress generateCodeForIntegerLiteralExpression(MethodCodification mc, IntegerLiteralExpression exp, CodeInstructionList codelist) {
		return new CodeLiteral(exp.getValue());
	}
	
	/**
	 * Genera el código asociado a una constante char.
	 */
	private CodeAddress generateCodeForCharLiteralExpression(MethodCodification mc, CharLiteralExpression exp, CodeInstructionList codelist) {
		return new CodeLiteral(exp.getValue());
	}
	
	/**
	 * Genera el código asociado a una constante booleana.
	 */
	private CodeAddress generateCodeForBooleanLiteralExpression(MethodCodification mc, BooleanLiteralExpression exp, CodeInstructionList codelist) {
		int bvalue = (exp.getValue()? 1 : 0);
		return new CodeLiteral(bvalue);
	}

	/**
	 * Genera el código asociado a una expresión booleana asignando el
	 * resultado a una variable
	 */
	private CodeAddress generateCodeForBooleanExpression(MethodCodification mc, Expression exp, CodeInstructionList codelist) {
		CodeAddress target = null;
		
		if (exp instanceof UnaryExpression) { // NEGACION
			Expression e = ((UnaryExpression) exp).getExpression();
			CodeAddress source = generateCodeForExpression(mc, e, codelist);
			
			if (source instanceof CodeLiteral) {
				int value = Integer.parseInt(((CodeLiteral) source).getDescription());
				value = (value==1 ? 0 : 1);
				target = new CodeLiteral(value);
				return target;
			} else {
				String value = ((CodeVariable) source).getValue();
				if (value!=null) {
					int val = Integer.parseInt(((CodeVariable) source).getValue());
					val = (val==1 ? 0 : 1);
					target = new CodeLiteral(val);
					return target;
				} else {
					CodeLabel lbTrue = mc.getNewLabel();
					CodeLabel lbFalse = mc.getNewLabel();
					CodeLabel lbNext = mc.getNewLabel();
					
					CodeInstruction lbTrueInst = new CodeInstruction(LABEL, lbTrue, null, null);
					CodeInstruction lbFalseInst = new CodeInstruction(LABEL, lbFalse, null, null);
					CodeInstruction lbNextInst = new CodeInstruction(LABEL, lbNext, null, null);
					
					target = mc.getNewTemp();
					CodeLiteral valueTrue = new CodeLiteral(1);
					CodeLiteral valueFalse = new CodeLiteral(0);
					
					CodeInstructionList code = new CodeInstructionList();
					code.addInstruction(new CodeInstruction(JMP1,lbFalse,source, null));
					code.addInstruction(new CodeInstruction(JUMP,lbTrue, null, null));
					code.addInstruction(lbTrueInst);
					code.addInstruction(new CodeInstruction(ASSIGN,target,valueTrue, null));
					code.addInstruction(new CodeInstruction(JUMP,lbNext, null, null));
					code.addInstruction(lbFalseInst);
					code.addInstruction(new CodeInstruction(ASSIGN,target,valueFalse, null));
					code.addInstruction(lbNextInst);
					
					codelist.addInstructionList(code.getList());
					return target;
				}
			}
			
		} else { // AND, OR Y COMPARACIONES
			Expression left = ((BinaryExpression) exp).getLeftExpression();
			Expression right = ((BinaryExpression) exp).getRightExpression();
			CodeAddress source1 = generateCodeForExpression(mc,left,codelist);
			CodeAddress source2 = generateCodeForExpression(mc,right,codelist);
			
			int op = ((BinaryExpression) exp).getOperator();
			
			switch (op) {
				case BinaryExpression.OR: 
				case BinaryExpression.AND: 
						if (source1 instanceof CodeLiteral) { //Si el primer operando es un literal
							int value = Integer.parseInt(source1.getDescription());
							boolean s1 = (value==1);
							
							// Intentamos resolver usando el primer operando
							if (s1 == false && op == BinaryExpression.AND) {
								target = new CodeLiteral(0);
								return target;
							}
							if (s1 == true && op == BinaryExpression.OR) {
								target = new CodeLiteral(1);
								return target;
							}
							
							//Resolvemos usando el segundo operando
							if (source2 instanceof CodeLiteral) { //Si el segundo operando es un literal
								value = Integer.parseInt(source2.getDescription());
								target = new CodeLiteral(value);
								return target;
							} else { //Si el segundo operando es una variable
								String val = ((CodeVariable)source2).getValue();
								if (val != null) { //Si tiene valor
									value = Integer.parseInt(val);
									target = new CodeLiteral(value);
									return target;
								} else {
									return source2;
								}
							}
							
						} else { //Si el primer operando es una variable
							String val = ((CodeVariable)source1).getValue();
							
							if (val != null) { //Si tiene valor
								int value = Integer.parseInt(val);
								boolean s1 = (value==1);
								
								// Intentamos resolver usando el primer operando
								if (s1 == false && op == BinaryExpression.AND) {
									target = new CodeLiteral(0);
									return target;
								}
								if (s1 == true && op == BinaryExpression.OR) {
									target = new CodeLiteral(1);
									return target;
								}
								
								//Resolvemos usando el segundo operando
								if (source2 instanceof CodeLiteral) { //Si el segundo operando es un literal
									value = Integer.parseInt(source2.getDescription());
									target = new CodeLiteral(value);
									return target;
								} else { //Si el segundo operando es una variable
									val = ((CodeVariable)source2).getValue();
									if (val != null) { //Si tiene valor
										value = Integer.parseInt(val);
										target = new CodeLiteral(value);
										return target;
									} else {
										return source2;
									}
								}
								
							} else { //Si no tiene valor
								if (source2 instanceof CodeLiteral) { //Si el segundo operando es un literal
									// Intentamos resolver usando el segundo operando
									int value = Integer.parseInt(source2.getDescription());
									boolean s2 = (value==1);
									if (s2 == false && op == BinaryExpression.AND) {
										target = new CodeLiteral(0);
										return target;
									}
									if (s2 == true && op == BinaryExpression.OR) {
										target = new CodeLiteral(1);
										return target;
									}
									
									// Lo dejamos en funcion del primer operando
									return source1;
									
								} else { //Si el segundo operando es una variable
									val = ((CodeVariable)source2).getValue();
									if (val != null) { //Si tiene valor
										// Intentamos resolver usando el segundo operando
										int value = Integer.parseInt(val);
										boolean s2 = (value==1);
										if (s2 == false && op == BinaryExpression.AND) {
											target = new CodeLiteral(0);
											return target;
										}
										if (s2 == true && op == BinaryExpression.OR) {
											target = new CodeLiteral(1);
											return target;
										}
										
										// Lo dejamos en funcion del primer operando
										return source1;
									} else { //Si ninguno tiene valor
										// Lo dejamos en funcion de ambos
										
										target = mc.getNewTemp();
										CodeLiteral valueTrue = new CodeLiteral(1);
										CodeLiteral valueFalse = new CodeLiteral(0);
										CodeInstructionList code = new CodeInstructionList();
										
										if(op == BinaryExpression.AND) {	
											CodeLabel lbTrue1 = mc.getNewLabel();
											CodeLabel lbTrue2 = mc.getNewLabel();
											CodeLabel lbFalse = mc.getNewLabel();
											CodeLabel lbNext = mc.getNewLabel();
											
											CodeInstruction lbTrue1Inst = new CodeInstruction(LABEL, lbTrue1, null, null);
											CodeInstruction lbTrue2Inst = new CodeInstruction(LABEL, lbTrue2, null, null);
											CodeInstruction lbFalseInst = new CodeInstruction(LABEL, lbFalse, null, null);
											CodeInstruction lbNextInst = new CodeInstruction(LABEL, lbNext, null, null);
											
											code.addInstruction(new CodeInstruction(JMP1,lbTrue1,source1, null));
											code.addInstruction(new CodeInstruction(JUMP,lbFalse, null, null));
											code.addInstruction(lbTrue1Inst);
											code.addInstruction(new CodeInstruction(JMP1,lbTrue2,source2, null));
											code.addInstruction(new CodeInstruction(JUMP,lbFalse, null, null));
											code.addInstruction(lbTrue2Inst);
											code.addInstruction(new CodeInstruction(ASSIGN,target,valueTrue, null));
											code.addInstruction(new CodeInstruction(JUMP,lbNext, null, null));
											code.addInstruction(lbFalseInst);
											code.addInstruction(new CodeInstruction(ASSIGN,target,valueFalse, null));
											code.addInstruction(lbNextInst);
										} else if(op == BinaryExpression.OR) {
											CodeLabel lbTrue = mc.getNewLabel();
											CodeLabel lbNext = mc.getNewLabel();
											
											CodeInstruction lbTrueInst = new CodeInstruction(LABEL, lbTrue, null, null);
											CodeInstruction lbNextInst = new CodeInstruction(LABEL, lbNext, null, null);
											
											code.addInstruction(new CodeInstruction(JMP1,lbTrue,source1, null));
											code.addInstruction(new CodeInstruction(JMP1,lbTrue,source2, null));
											code.addInstruction(new CodeInstruction(ASSIGN,target,valueFalse, null));
											code.addInstruction(new CodeInstruction(JUMP,lbNext, null, null));
											code.addInstruction(lbTrueInst);
											code.addInstruction(new CodeInstruction(ASSIGN,target,valueTrue, null));
											code.addInstruction(lbNextInst);
										}
										codelist.addInstructionList(code.getList());
										return target;
									}
								}
							}							
						}
				case BinaryExpression.GE:
				case BinaryExpression.GT:
				case BinaryExpression.LE:
				case BinaryExpression.LT:
				case BinaryExpression.EQ:
				case BinaryExpression.NEQ:
					if (source1 instanceof CodeLiteral) { //Si el primer operando es un literal
						int s1 = Integer.parseInt(source1.getDescription());
						
						if (source2 instanceof CodeLiteral) { //Si el segundo operando es un literal
							int s2 = Integer.parseInt(source2.getDescription());
							switch (op) {
								case BinaryExpression.GE: target = new CodeLiteral((s1>=s2? 1:0));
									return target;
								case BinaryExpression.GT: target = new CodeLiteral((s1>s2? 1:0));
									return target;
								case BinaryExpression.LE: target = new CodeLiteral((s1<=s2? 1:0));
									return target;
								case BinaryExpression.LT: target = new CodeLiteral((s1<s2? 1:0));
									return target;
								case BinaryExpression.EQ: target = new CodeLiteral((s1==s2? 1:0));
									return target;
								case BinaryExpression.NEQ: target = new CodeLiteral((s1!=s2? 1:0));
									return target;
							}
						} else { //Si el segundo operando es una variable
							String val = ((CodeVariable)source2).getValue();
							if (val != null) { //Si tiene valor
								int s2 = Integer.parseInt(val);
								switch (op) {
									case BinaryExpression.GE: target = new CodeLiteral((s1>=s2? 1:0));
										return target;
									case BinaryExpression.GT: target = new CodeLiteral((s1>s2? 1:0));
										return target;
									case BinaryExpression.LE: target = new CodeLiteral((s1<=s2? 1:0));
										return target;
									case BinaryExpression.LT: target = new CodeLiteral((s1<s2? 1:0));
										return target;
									case BinaryExpression.EQ: target = new CodeLiteral((s1==s2? 1:0));
										return target;
									case BinaryExpression.NEQ: target = new CodeLiteral((s1!=s2? 1:0));
										return target;
								}
							} else {
								target = mc.getNewTemp();
								CodeLiteral valueTrue = new CodeLiteral(1);
								CodeLiteral valueFalse = new CodeLiteral(0);
								
								CodeLabel lbTrue = mc.getNewLabel();
								CodeLabel lbNext = mc.getNewLabel();
								
								CodeInstruction lbTrueInst = new CodeInstruction(LABEL, lbTrue, null, null);
								CodeInstruction lbNextInst = new CodeInstruction(LABEL, lbNext, null, null);
								
								int operation = getBinaryCode(op);
								
								codelist.addInstruction(new CodeInstruction(operation,lbTrue,source1,source2));
								codelist.addInstruction(new CodeInstruction(ASSIGN,target,valueFalse, null));
								codelist.addInstruction(new CodeInstruction(JUMP,lbNext,null,null));
								codelist.addInstruction(lbTrueInst);
								codelist.addInstruction(new CodeInstruction(ASSIGN,target,valueTrue, null));
								codelist.addInstruction(lbNextInst);
								
								return target;							
							}
						}
						
					} else { //Si el primer operando es una variable
						String val = ((CodeVariable)source1).getValue();
						
						if (val != null) { //Si tiene valor
							int s1 = Integer.parseInt(val);
				
							if (source2 instanceof CodeLiteral) { //Si el segundo operando es un literal
								int s2 = Integer.parseInt(source2.getDescription());
								switch (op) {
									case BinaryExpression.GE: target = new CodeLiteral((s1>=s2? 1:0));
										return target;
									case BinaryExpression.GT: target = new CodeLiteral((s1>s2? 1:0));
										return target;
									case BinaryExpression.LE: target = new CodeLiteral((s1<=s2? 1:0));
										return target;
									case BinaryExpression.LT: target = new CodeLiteral((s1<s2? 1:0));
										return target;
									case BinaryExpression.EQ: target = new CodeLiteral((s1==s2? 1:0));
										return target;
									case BinaryExpression.NEQ: target = new CodeLiteral((s1!=s2? 1:0));
										return target;
								}
							} else { //Si el segundo operando es una variable
								val = ((CodeVariable)source2).getValue();
								if (val != null) { //Si tiene valor
									int s2 = Integer.parseInt(val);
									switch (op) {
										case BinaryExpression.GE: target = new CodeLiteral((s1>=s2? 1:0));
											return target;
										case BinaryExpression.GT: target = new CodeLiteral((s1>s2? 1:0));
											return target;
										case BinaryExpression.LE: target = new CodeLiteral((s1<=s2? 1:0));
											return target;
										case BinaryExpression.LT: target = new CodeLiteral((s1<s2? 1:0));
											return target;
										case BinaryExpression.EQ: target = new CodeLiteral((s1==s2? 1:0));
											return target;
										case BinaryExpression.NEQ: target = new CodeLiteral((s1!=s2? 1:0));
											return target;
									}
								} else {
									source1 = new CodeLiteral(s1);
									target = mc.getNewTemp();
									CodeLiteral valueTrue = new CodeLiteral(1);
									CodeLiteral valueFalse = new CodeLiteral(0);
									
									CodeLabel lbTrue = mc.getNewLabel();
									CodeLabel lbNext = mc.getNewLabel();
									
									CodeInstruction lbTrueInst = new CodeInstruction(LABEL, lbTrue, null, null);
									CodeInstruction lbNextInst = new CodeInstruction(LABEL, lbNext, null, null);
									
									int operation = getBinaryCode(op);
									
									codelist.addInstruction(new CodeInstruction(operation,lbTrue,source1,source2));
									codelist.addInstruction(new CodeInstruction(ASSIGN,target,valueFalse, null));
									codelist.addInstruction(new CodeInstruction(JUMP,lbNext,null,null));
									codelist.addInstruction(lbTrueInst);
									codelist.addInstruction(new CodeInstruction(ASSIGN,target,valueTrue, null));
									codelist.addInstruction(lbNextInst);
									
									return target;	
								}
							}
							
						} else { //Si no tiene valor
							if (source2 instanceof CodeLiteral) { //Si el segundo operando es un literal

								target = mc.getNewTemp();
								CodeLiteral valueTrue = new CodeLiteral(1);
								CodeLiteral valueFalse = new CodeLiteral(0);
								
								CodeLabel lbTrue = mc.getNewLabel();
								CodeLabel lbNext = mc.getNewLabel();
								
								CodeInstruction lbTrueInst = new CodeInstruction(LABEL, lbTrue, null, null);
								CodeInstruction lbNextInst = new CodeInstruction(LABEL, lbNext, null, null);
								
								int operation = getBinaryCode(op);
								
								codelist.addInstruction(new CodeInstruction(operation,lbTrue,source1,source2));
								codelist.addInstruction(new CodeInstruction(ASSIGN,target,valueFalse, null));
								codelist.addInstruction(new CodeInstruction(JUMP,lbNext,null,null));
								codelist.addInstruction(lbTrueInst);
								codelist.addInstruction(new CodeInstruction(ASSIGN,target,valueTrue, null));
								codelist.addInstruction(lbNextInst);
								
								return target;	
								
							} else { //Si el segundo operando es una variable
								val = ((CodeVariable)source2).getValue();
								if (val != null) { //Si tiene valor
									// Intentamos resolver usando el segundo operando
									int s2 = Integer.parseInt(val);

									source2 = new CodeLiteral(s2);
									target = mc.getNewTemp();
									CodeLiteral valueTrue = new CodeLiteral(1);
									CodeLiteral valueFalse = new CodeLiteral(0);
									
									CodeLabel lbTrue = mc.getNewLabel();
									CodeLabel lbNext = mc.getNewLabel();
									
									CodeInstruction lbTrueInst = new CodeInstruction(LABEL, lbTrue, null, null);
									CodeInstruction lbNextInst = new CodeInstruction(LABEL, lbNext, null, null);
									
									int operation = getBinaryCode(op);
									
									codelist.addInstruction(new CodeInstruction(operation,lbTrue,source1,source2));
									codelist.addInstruction(new CodeInstruction(ASSIGN,target,valueFalse, null));
									codelist.addInstruction(new CodeInstruction(JUMP,lbNext,null,null));
									codelist.addInstruction(lbTrueInst);
									codelist.addInstruction(new CodeInstruction(ASSIGN,target,valueTrue, null));
									codelist.addInstruction(lbNextInst);
									
									return target;	
								} else { //Si ninguno tiene valor
									target = mc.getNewTemp();
									CodeLiteral valueTrue = new CodeLiteral(1);
									CodeLiteral valueFalse = new CodeLiteral(0);
									
									CodeLabel lbTrue = mc.getNewLabel();
									CodeLabel lbNext = mc.getNewLabel();
									
									CodeInstruction lbTrueInst = new CodeInstruction(LABEL, lbTrue, null, null);
									CodeInstruction lbNextInst = new CodeInstruction(LABEL, lbNext, null, null);
									
									int operation = getBinaryCode(op);
									
									codelist.addInstruction(new CodeInstruction(operation,lbTrue,source1,source2));
									codelist.addInstruction(new CodeInstruction(ASSIGN,target,valueFalse, null));
									codelist.addInstruction(new CodeInstruction(JUMP,lbNext,null,null));
									codelist.addInstruction(lbTrueInst);
									codelist.addInstruction(new CodeInstruction(ASSIGN,target,valueTrue, null));
									codelist.addInstruction(lbNextInst);
									
									return target;	
								}
							}
						}							
					}
					break;
			}
		}
		
		return target;
		
	}
		
	/**
	 * Genera el código de una operación aritmética unaria
	 * @param stream
	 * @param exp
	 * @return
	 */
	private CodeAddress generateCodeForUnaryExpression(MethodCodification mc, UnaryExpression exp, CodeInstructionList codelist) {
		Expression operand = exp.getExpression();
		
		CodeInstructionList code = new CodeInstructionList();
		CodeAddress source = generateCodeForExpression(mc,operand,code);
		CodeAddress target = null;
		
		if (exp.getOperator() == UnaryExpression.MINUS) { //Operacion cambio de signo
			if (source instanceof CodeLiteral) { //Si es un literal
				int value = Integer.parseInt(source.getDescription());
				value = -value;
				target = new CodeLiteral(value);	
			} else { //Si es una variable
				String value = ((CodeVariable)source).getValue();
				if (value != null) { //Si tiene un valor asignado
					int val = Integer.parseInt(value);
					val = -val;
					target = new CodeLiteral(val);
				} else { //Si no tiene un valor asignado
					target = mc.getNewTemp();
					code.addInstruction(new CodeInstruction(INV,target,source,null));
				}
			}
		} else if (exp.getOperator() == UnaryExpression.TILDE) { //Operacion complemento a 1
			if (source instanceof CodeLiteral) { //Si es un literal
				int value = Integer.parseInt(source.getDescription());
				value = ~value;
				target = new CodeLiteral(value);	
			} else { //Si es una variable
				String value = ((CodeVariable)source).getValue();
				if (value != null) { //Si tiene un valor asignado
					int val = Integer.parseInt(value);
					val = ~val;
					target = new CodeLiteral(val);
				} else { //Si no tiene un valor asignado
					target = mc.getNewTemp();
					code.addInstruction(new CodeInstruction(COMP,target,source,null));
				}
			}
		}

		codelist.addInstructionList(code.getList());
	
		return target;		
	}
		
	/**
	 * Genera el código de una expresión aritmética binaria
	 * @param stream
	 * @param exp
	 * @return
	 */
	private CodeAddress generateCodeForBinaryExpression(MethodCodification mc, BinaryExpression exp, CodeInstructionList codelist) {
		int operator = exp.getOperator();
		Expression left = exp.getLeftExpression();
		Expression right = exp.getRightExpression();
		
		CodeAddress target = null;
		
		CodeInstructionList code = new CodeInstructionList();
		CodeAddress source1 = generateCodeForExpression(mc,left,code);
		CodeAddress source2 = generateCodeForExpression(mc,right,code);
		
		int op = getBinaryCode(operator);
		
		if (source1 instanceof CodeLiteral) { // Si el primer operando es un literal
			int s1 = Integer.parseInt(source1.getDescription());
			
			if (source2 instanceof CodeLiteral) { // Si el segundo operando es un literal	
				int s2 = Integer.parseInt(source2.getDescription());
			
				switch (op) {
				case ADD: target = new CodeLiteral(s1+s2);
					break;
				case SUB: target = new CodeLiteral(s1-s2);
					break;
				case MUL: target = new CodeLiteral(s1*s2);
					break;
				case DIV: target = new CodeLiteral(s1/s2);
					break;
				case MOD: target = new CodeLiteral(s1%s2);
					break;
				case BIT_AND: target = new CodeLiteral(s1&s2);
					break;
				case BIT_OR: target = new CodeLiteral(s1|s2);
					break;
				case XOR: target = new CodeLiteral(s1^s2);
					break;
				case LSHIFT: target = new CodeLiteral(s1<<s2);
					break;
				case RSIGNEDSHIFT: target = new CodeLiteral(s1>>s2);
					break;
				case RUNSIGNEDSHIFT: target = new CodeLiteral(s1>>>s2);
					break;
				}
			} else { // Si el segundo operando es una variable
				String value = ((CodeVariable)source2).getValue();
				if (value != null) { //Si tiene un valor asignado
					int s2 = Integer.parseInt(value);
					
					switch (op) {
					case ADD: target = new CodeLiteral(s1+s2);
						break;
					case SUB: target = new CodeLiteral(s1-s2);
						break;
					case MUL: target = new CodeLiteral(s1*s2);
						break;
					case DIV: target = new CodeLiteral(s1/s2);
						break;
					case MOD: target = new CodeLiteral(s1%s2);
						break;
					case BIT_AND: target = new CodeLiteral(s1&s2);
						break;
					case BIT_OR: target = new CodeLiteral(s1|s2);
						break;
					case XOR: target = new CodeLiteral(s1^s2);
						break;
					case LSHIFT: target = new CodeLiteral(s1<<s2);
						break;
					case RSIGNEDSHIFT: target = new CodeLiteral(s1>>s2);
						break;
					case RUNSIGNEDSHIFT: target = new CodeLiteral(s1>>>s2);
						break;
					}
				} else { // Si no tiene un valor asignado
					target = mc.getNewTemp();
					code.addInstruction(new CodeInstruction(op,target,source1,source2));
					codelist.addInstructionList(code.getList());
				}
			}
			
		} else { // Si el primer operando es una variable
			String value1 = ((CodeVariable)source1).getValue();
			
			if (value1 != null) { // Si tiene valor
				int s1 = Integer.parseInt(value1);
				
				if (source2 instanceof CodeLiteral) { // Si el segundo operando es un literal
					int s2 = Integer.parseInt(source2.getDescription());
			
					switch (op) {
						case ADD: target = new CodeLiteral(s1+s2);
							break;
						case SUB: target = new CodeLiteral(s1-s2);
							break;
						case MUL: target = new CodeLiteral(s1*s2);
							break;
						case DIV: target = new CodeLiteral(s1/s2);
							break;
						case MOD: target = new CodeLiteral(s1%s2);
							break;
						case BIT_AND: target = new CodeLiteral(s1&s2);
							break;
						case BIT_OR: target = new CodeLiteral(s1|s2);
							break;
						case XOR: target = new CodeLiteral(s1^s2);
							break;
						case LSHIFT: target = new CodeLiteral(s1<<s2);
							break;
						case RSIGNEDSHIFT: target = new CodeLiteral(s1>>s2);
							break;
						case RUNSIGNEDSHIFT: target = new CodeLiteral(s1>>>s2);
							break;
					}
					
				} else { // Si el segundo operando es una variable
					
					String value2 = ((CodeVariable)source2).getValue();
					
					if (value2 != null) { //Si tiene un valor asignado
						int s2 = Integer.parseInt(value2);
					
						switch (op) {
						case ADD: target = new CodeLiteral(s1+s2);
							break;
						case SUB: target = new CodeLiteral(s1-s2);
							break;
						case MUL: target = new CodeLiteral(s1*s2);
							break;
						case DIV: target = new CodeLiteral(s1/s2);
							break;
						case MOD: target = new CodeLiteral(s1%s2);
							break;
						case BIT_AND: target = new CodeLiteral(s1&s2);
							break;
						case BIT_OR: target = new CodeLiteral(s1|s2);
							break;
						case XOR: target = new CodeLiteral(s1^s2);
							break;
						case LSHIFT: target = new CodeLiteral(s1<<s2);
							break;
						case RSIGNEDSHIFT: target = new CodeLiteral(s1>>s2);
							break;
						case RUNSIGNEDSHIFT: target = new CodeLiteral(s1>>>s2);
							break;
						}
						
					} else { // Si no tiene un valor asignado
						target = mc.getNewTemp();
						CodeLiteral source = new CodeLiteral(s1);
						code.addInstruction(new CodeInstruction(op,target,source,source2));
						codelist.addInstructionList(code.getList());
					}
				}
			} else  { // Si el primer operando no tiene un valor asignado
				target = mc.getNewTemp();
				
				if (source2 instanceof CodeVariable) {
					String value2 = ((CodeVariable)source2).getValue();	
					if (value2 != null) {
						source2 = new CodeLiteral(Integer.parseInt(value2));
					}
				}
				
				code.addInstruction(new CodeInstruction(op,target,source1,source2));
				codelist.addInstructionList(code.getList());
			}
			
		}
		
		return target;		
	}
	
	/**
	 * Genera el código de una expresión de llamada a un método
	 * @param stream
	 * @param exp
	 * @return
	 */
	private CodeVariable generateCodeForCallExpression(MethodCodification mc, CallExpression exp, CodeInstructionList codelist) {
		Method method = exp.getMethod();
		CallParameters call = exp.getCallParameters();
		Expression[] paramexp = call.getParameters();
		
		int argsize = 4*paramexp.length; // Espacio para los argumentos
		
		CodeInstructionList code = new CodeInstructionList();
		code.addInstruction(new CodeInstruction(PRECALL, new CodeLiteral(argsize), null, null));
		
		CodeAddress[] param = new CodeAddress[paramexp.length];
		for(int i=0; i<param.length; i++) {
			param[i] = generateCodeForExpression(mc,paramexp[i],code);
			CodeLiteral pos = new CodeLiteral(4*i);
			code.addInstruction(new CodeInstruction(PARAM,param[i],pos,null));
		}
		
		CodeVariable target = mc.getNewTemp();
		CodeLabel methodlabel = new CodeLabel(method.getLabel());
		code.addInstruction(new CodeInstruction(CALL,target,methodlabel,null));
		
		codelist.addInstructionList(code.getList());
		return target;
	}
			
	/**
	 * Genera el código de una expresión de referencia a una variable local.
	 * Esto no genera código. Tan solo devuelve la referencia a la variable.
	 * @param mc
	 * @param exp
	 * @param codelist
	 * @return
	 */
	private CodeVariable generateCodeForVariableExpression(MethodCodification mc, VariableExpression exp, CodeInstructionList codelist) {
		Variable var = exp.getVariable();
		return mc.getVariable(var);
	}
		
	/**
	 * Obtiene el código de una instrucción a partir del código
	 * de una expresión binaria.
	 * @param op
	 * @param type
	 * @return
	 */
	private int getBinaryCode(int op) {
		switch(op) {
			case BinaryExpression.EQ: return JMPEQ;
			case BinaryExpression.NEQ: return JMPNE;
			case BinaryExpression.GE: return JMPGE;
			case BinaryExpression.GT: return JMPGT;
			case BinaryExpression.LE: return JMPLE;
			case BinaryExpression.LT: return JMPLT;
			case BinaryExpression.PLUS: return ADD;
			case BinaryExpression.MINUS: return SUB;
			case BinaryExpression.PROD: return MUL;
			case BinaryExpression.DIV: return DIV;
			case BinaryExpression.MOD: return MOD;
			case BinaryExpression.BIT_AND: return BIT_AND;
			case BinaryExpression.BIT_OR: return BIT_OR;
			case BinaryExpression.XOR: return XOR;
			case BinaryExpression.LSHIFT: return LSHIFT;
			case BinaryExpression.RSIGNEDSHIFT: return RSIGNEDSHIFT;
			case BinaryExpression.RUNSIGNEDSHIFT: return RUNSIGNEDSHIFT;
			default: return 0;
		}		
	}
	
}
