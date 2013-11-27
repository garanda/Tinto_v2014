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

/**
 * Clase que almacena la información sobre la declaración de una biblioteca
 * 
 * @author Francisco José Moreno Velo
 */
public class Library {

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Identificador de la biblioteca
	 */
	private String name;

	/**
	 * Lista de biblitecas importadas
	 */
	private Vector<String> imported;
	
	/**
	 * Lista de métodos
	 */
	private Vector<Method> method;

	//----------------------------------------------------------------//
	//                            Constructores                       //
	//----------------------------------------------------------------//

	/**
	 * Constructor de una declaración de biblioteca
	 * 
	 * @param type
	 * @param name
	 */
	public Library(String name) {
		this.name = name.toString();
		this.imported = new Vector<String>();
		this.method = new Vector<Method>();
	}
	
	//----------------------------------------------------------------//
	//                          Métodos públicos                      //
	//----------------------------------------------------------------//

	/**
	 * Obtiene el nombre de la biblioteca
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Añade una biblioteca a la lista de bibliotecas importadas
	 * @param libname
	 */
	public void addImportedLibrary(String libname) {
		if(!imported.contains(libname)) this.imported.add(libname);
	}
	
	/**
	 * Asigna la lista de bibliotecas importadas
	 * @param imported
	 */
	public void setImportedList(Vector<String> imported) {
		this.imported = imported;
	}

	/**
	 * Obtiene la lista de bibliotecas importadas
	 * @return
	 */
	public String[] getImported() {
		Object[] obj = imported.toArray();
		String[] imp = new String[obj.length];
		for(int i=0; i<imp.length; i++) imp[i] = (String) obj[i];
		return imp;
	}

	/**
	 * Añade la definición de un método
	 * @param con
	 */
	public void addMethod(Method method) {
		this.method.add(method);
	}
	
	/**
	 * Obtiene la lista de métodos públicos y privados
	 * @return
	 */
	public Method[] getMethods() {
		Object[] obj = method.toArray();
		Method[] all = new Method[obj.length];
		for(int i=0; i<all.length; i++) all[i] = (Method) obj[i];
		return all;
	}
	

	/**
	 * Busca un método público por su nombre y tipos de argumento
	 * @param name Nombre del método
	 * @param type Lista de tipos de los argumentos
	 * @return
	 */
	public Method getMethod(String name,int[] type) {
		int size = method.size();
		for(int i=0; i<size; i++) {
			Method mth = (Method) method.elementAt(i);
			if(mth.match(name,type) ) return mth;
		}
		return null;
	}
	
	/**
	 * Busca si un cierto nombre se encuentra entre las clases importadas
	 * @param importedname
	 * @return
	 */
	public boolean isImported(String importedname) {
		return imported.contains(importedname);
	}
}
