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

/**
 * Interfaz que describe los códigos de todas las instrucciones
 * 
 * @author Francisco José Moreno Velo
 */
public interface CodeConstants {
	
	//----------------------------------------------------------------//
	// Tipos de instrucciones en código intermedio                    //
	//----------------------------------------------------------------//

	/**
	 * Etiqueta
	 */
	public int LABEL = 1;
	
	/**
	 * Instrucción: Asignación
	 */
	public int ASSIGN = 2;
	
	/**
	 * Instrucción: Suma
	 */
	public int ADD = 3;
		
	/**
	 * Instrucción: Resta
	 */
	public int SUB = 4;
	
	/**
	 * Instrucción: Multiplicación
	 */
	public int MUL = 5;
	
	/**
	 * Instrucción: División
	 */
	public int DIV = 6;
	
	/**
	 * Instrucción: Módulo
	 */
	public int MOD = 7;
	
	/**
	 * Instrucción: Cambio de signo
	 */
	public int INV = 8;
	
	/**
	 * Instrucción: Conjunción de datos booleanos
	 */
	public int AND = 9;
	
	/**
	 * Instrucción: Disyunción de datos booleanos
	 */
	public int OR = 10;

	/**
	 * Instrucción: NOT
	 */
	public int NOT = 11;
	
	/**
	 * Instrucción: Salto incondicional
	 */
	public int JUMP = 12;

	/**
	 * Instrucción: Salto condicionado a la igualdad entre dos datos
	 */
	public int JMPEQ = 13;
	
	/**
	 * Instrucción: Salto condicionado a la desigualdad entre dos datos
	 */
	public int JMPNE = 14;
	
	/**
	 * Instrucción: Salto condicionado a la comparación "mayor" 
	 * entre dos datos
	 */
	public int JMPGT = 15;
	
	/**
	 * Instrucción: Salto condicionado a la comparación "mayor o igual" 
	 * entre dos datos
	 */
	public int JMPGE = 16;
	
	/**
	 * Instrucción: Salto condicionado a la comparación "menor" 
	 * entre dos datos
	 */
	public int JMPLT = 17;
	
	/**
	 * Instrucción: Salto condicionado a la comparación "menor o igual" 
	 * entre dos datos
	 */
	public int JMPLE = 18;
	
	/**
	 * Instrucción: Salto condicional cuando una variable es 1 (true)
	 */
	public int JMP1 = 19;
	
	/**
	 * Instrucción: Almacenamiento de un parámetro anterior a
	 * una llamada a una función
	 */
	public int PARAM = 20;

	/**
	 * Instrucción: Llamada a una función
	 */
	public int CALL = 21;

	/**
	 * Instrucción: Devolución de un valor en una función
	 */
	public int RETURN = 22;

	/**
	 * Instrucción: Notificación de una próxima llamada a función colocada
	 * antes del cálculo del valor de los argumentos
	 */
	public int PRECALL = 23;

	/**
	 * Instrucción: Complemento a 1
	 */
	public int COMP = 24;
	
	/**
	 * Instrucción: Conjunción de datos a nivel de bit
	 */
	public int BIT_AND = 25;
	
	/**
	 * Instrucción: Disyunción inclusiva de datos a nivel de bit
	 */
	public int BIT_OR = 26;
	
	/**
	 * Instrucción: Disyunción exclusiva de datos a nivel de bit
	 */
	public int XOR = 27;
	
	/**
	 * Instrucción: Desplazamiento de bit a la izquierda
	 */
	public int LSHIFT = 28;
	
	/**
	 * Instrucción: Desplazamiento de bit a la derecha conservando el signo
	 */
	public int RSIGNEDSHIFT = 29;
	
	/**
	 * Instrucción: Desplazamiento de bit a la derecha ignorando el signo
	 */
	public int RUNSIGNEDSHIFT = 30;
	
}
