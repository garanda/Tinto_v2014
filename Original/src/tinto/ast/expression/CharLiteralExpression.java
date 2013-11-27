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

package tinto.ast.expression;

import tinto.ast.Type;

/**
 * Clase que describe una constante de tipo caracter
 * 
 * @author Francisco José Moreno Velo
 */
public class CharLiteralExpression extends Expression {

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Valor de un literal char
	 */
	private char value;
	
	
	//----------------------------------------------------------------//
	//                            Constructores                       //
	//----------------------------------------------------------------//

	/**
	 * Constructor basado en el lexema
	 * 
	 * @param lexeme
	 * @throws IntegerOutOfValueException
	 */
	public CharLiteralExpression(String lexeme) {
		super(Type.CHAR_TYPE);
		this.value = getCharFromString(lexeme);
	}
		
	/**
	 * Contrutor de tipo char
	 * @param value
	 */
	public CharLiteralExpression(char value) {
		super(Type.CHAR_TYPE);
		this.value = value;
	}
	
	//----------------------------------------------------------------//
	//                          Métodos públicos                      //
	//----------------------------------------------------------------//

	/**
	 * Obtiene el valor del literal char
	 * @return
	 */
	public char getValue() {
		return this.value;
	}
	
	//----------------------------------------------------------------//
	//                          Métodos privados                      //
	//----------------------------------------------------------------//

	/**
	 * Obtiene el caracter a partir del lexema para los 
	 * diferentes formatos
	 * @param lexeme
	 * @return
	 */
	private char getCharFromString(String lexeme) {
		char[] content = lexeme.toCharArray();
		
		// Caracteres editables
		if(content.length == 3) return content[1];
		
		// Caracteres especiales
		if(content[2] == 'n') return '\n';
		if(content[2] == 'r') return '\r';
		if(content[2] == 't') return '\t';
		if(content[2] == 'b') return '\b';
		if(content[2] == 'f') return '\f';
		if(content[2] == '\\') return '\\';
		if(content[2] == '\'') return '\'';
		if(content[2] == '\"') return '\"';
		
		// Caracteres en formato octal
		if(content[2] >= '0' && content[2] <= '7') {
			int val = content[2]-'0';
			if(content[3] != '\'') {
				val *= 8;
				val += content[3]-'0';
				if(content[4] != '\'') {
					val *= 8;
					val += content[4]-'0';
				}
			}
			return (char) val;
		}
		
		// Caracteres en formato Unicode 
		if(content[2] == 'u' || content[2] == 'U') {
			String sval = "0x"+content[3]+content[4]+content[5]+content[6];
			int val = Integer.parseInt(sval);
			return (char) val;
		}
		
		// Caso imposible. Se evita a nivel léxico
		return '\0';
	}
}
