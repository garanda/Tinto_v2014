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

package tinto.ast.struct;

import java.util.Vector;
import tinto.ast.statement.BlockStatement;

/**
 * Clase que almacena la información sobre un método
 * 
 * @author Francisco José Moreno Velo
 */
public class Method  {

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Identificador de la biblioteca a la que pertenece el método
	 */
	private String libname;
	
	/**
	 * Identificador del método
	 */
	private String name;
	
	/**
	 * Tipo de dato que devuelve el método
	 */
	private int type;
	
	/**
	 * Lista de argumentos del método
	 */
	private Variable[] argument;
	
	/**
	 * Lista de variables locales definidas en el método
	 */
	private Variable[] localVar;
	
	/**
	 * Conjunto de instrucciones del cuerpo del método
	 */
	private BlockStatement body;
	
	/**
	 * Tabla de símbolos
	 */
	private SymbolTable tos;
	
	//----------------------------------------------------------------//
	//                            Constructores                       //
	//----------------------------------------------------------------//

	/**
	 * Constructor
	 * 
	 * @param name Nombre del método o constructor
	 * @param classname Nombre de la clase a la que pertenece
	 */
	public Method(int type, String name, String libname) {
		this.type = type;
		this.name = name.toString();
		this.libname = libname.toString();
		this.argument = new Variable[0];
		this.localVar = new Variable[0];
		this.tos = new SymbolTable();
		this.tos.createContext();
		this.body = null;
	}
	
	//----------------------------------------------------------------//
	//                          Métodos públicos                      //
	//----------------------------------------------------------------//

	/**
	 * Obtiene el nombre del método
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Obtiene el tipo de dato que devuelve el método
	 * @return
	 */
	public int getType() {
		return this.type;
	}
	
	/**
	 * Añade un argumento al método
	 * @param var
	 */
	public void addArgument(Variable var) {
		Variable[] aux = new Variable[argument.length+1];
		System.arraycopy(argument,0,aux,0,argument.length);
		aux[argument.length] = var;
		argument = aux;
		tos.addVariable(var);
	}
	
	/**
	 * Obtiene la lista de argumentos del método
	 * @return
	 */
	public Variable[] getArguments() {
		return this.argument;
	}
	
	/**
	 * Busca un argumento en base a su nombre 
	 * @return
	 */
	public Variable getArgument(String argname) {
		for(int i=0; i<this.argument.length; i++) {
			if(argument[i].equals(argname)) return argument[i];
		}
		return null;
	}
	
	/**
	 * Asigna la lista de argumentos completa
	 * @param vector
	 */
	public void setArguments(Vector<Variable> vector) {
		tos.deleteContext();
		tos.createContext();
		int size = vector.size();
		this.argument = new Variable[size];
		for(int i=0; i<size; i++) {
			argument[i] = vector.elementAt(i);
			tos.addVariable(vector.elementAt(i));
		}
	}
	
	/**
	 * Asigna la lista de argumentos (sólo los tipos)
	 * @param vector
	 */
	public void setArgumentTypes(int[] argTypes) {
		tos.deleteContext();
		tos.createContext();
		int size = argTypes.length;
		this.argument = new Variable[size];
		for(int i=0; i<size; i++) {
			argument[i] = new Variable(argTypes[i],"arg"+i);
			tos.addVariable(argument[i]);
		}
	}
	
	/**
	 * Obtiene la lista de tipos de argumentos
	 * @param vector
	 */
	public int[] getArgumentTypes() {
		int[] type = new int[argument.length];
		for(int i=0; i<argument.length; i++) {
			type[i] = argument[i].getType();
		}
		return type;
	}
	
	/**
	 * Compara los tipos de datos con los del método,
	 * devolviendo true si los datos corresponden al método
	 * 
	 * @param name
	 * @param arg
	 * @return 
	 */
	public boolean match(String name, int[] argTypes) {
		if(argTypes.length != argument.length) return false;
		if(!this.name.equals(name)) return false;
		for(int i=0; i<argument.length; i++) {
			if(argument[i].getType() != argTypes[i]) return false;
		}
		return true;
	}
	
	/**
	 * Añade una declaración de variable local al método
	 * @param var
	 */
	public void addLocalVariable(Variable var) {
		Variable[] aux = new Variable[localVar.length+1];
		System.arraycopy(localVar,0,aux,0,localVar.length);
		aux[localVar.length] = var;
		localVar = aux;
		tos.addVariable(var);
	}
	
	/**
	 * Obtiene la lista de variables locales
	 * @return
	 */
	public Variable[] getLocalVariables() {
		return this.localVar;
	}
	
	/**
	 * Crea un nuevo ámbito de declaración de variables locales
	 */
	public void createContext() {
		this.tos.createContext();
	}
	
	/**
	 * Elimina un ámbito de declaración de variables locales
	 *
	 */
	public void deleteContext() {
		this.tos.deleteContext();
	}
	
	/**
	 * Asigna el bloque de instrucciones del método
	 * @param blockInst
	 */
	public void setBody(BlockStatement blockInst) {
		this.body = blockInst;
	}
	
	/**
	 * Obtiene el bloque de instrucciones del método
	 * @return
	 */
	public BlockStatement getBody() {
		return this.body;
	}
	
	/**
	 * Obtiene una variable del método a partir de su nombre
	 * @param varname Nombre de la variable
	 * @return
	 */
	public Variable getVariable(String varname) {
		return tos.getVariable(varname);
	}
	
	/**
	 * Verifica si en el último ámbito se ha definido una variable
	 * con un cierto nombre.
	 * @param varname Nombre de la variable
	 * @return true si está declarada en el ámbito activo.
	 */
	public boolean existsInContext(String varname) {
		return (tos.getVariableInContext(varname) != null);
	}
	
	/**
	 * Obtiene el nombre de la etiqueta que identifica al método 
	 * en el código intermedio
	 * @return
	 */
	public String getLabel() {
		String label = libname+"_"+name;
		for(int i=0; i<argument.length;i++) {
			label += "_"+argument[i].getType();
		}
		return label;
	}

	/**
	 * Obtiene la tabla de simbolos asociada al metodo
	 * @return
	 */
	public SymbolTable getSymbolTable() {
		return this.tos;
	}

}
