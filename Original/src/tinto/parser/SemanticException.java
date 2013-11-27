//------------------------------------------------------------------//
//                        COPYRIGHT NOTICE                          //
//------------------------------------------------------------------//
// Copyright (c) 2007, Francisco José Moreno Velo                   //
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
//          Compilador del lenguaje Odiel [Versión 0.3]             //
//                                                                  //
//------------------------------------------------------------------//

package tinto.parser;


/**
 * Clase que describe un excepción sintáctica
 * 
 * @author Francisco José Moreno Velo
 */
public class SemanticException extends Exception {
	
	/**
	 * Número de serie
	 */
	private static final long serialVersionUID = 8318848237596856683L;

	//--------------------------------------------------------------//
	// Constantes que describen a los errores semánticos            //
	//--------------------------------------------------------------//

	/**
	 * Error: Nombre de biblioteca distinto del nombre de fichero
	 */
	public static final int LIBRARY_NAME_EXCEPTION = 1;

	/**
	 * Error: Método duplicado (mismo nombre y mismo tipo de argumentos)
	 */
	public static final int DUPLICATED_METHOD_EXCEPTION = 2;

	/**
	 * Error: Argumento duplicado (mismo nombre)
	 */
	public static final int DUPLICATED_ARGUMENT_EXCEPTION = 3;

	/**
	 * Error: Tipo de dato incorrecto
	 */
	public static final int TYPE_MISMATCH_EXCEPTION = 4;
	
	/**
	 * Error: Valor numérico incorrecto
	 */
	public static final int NUMBER_FORMAT_EXCEPTION = 5;
	
	/**
	 * Error: Referencia a una biblioteca no declarada en las importaciones
	 */
	public static final int UNKNOWN_LIBRARY_EXCEPTION = 6;

	/**
	 * Error: Expresión no booleana en una condición
	 */
	public static final int INVALID_CONDITION_EXCEPTION = 7;

	/**
	 * Error: Tipo de dato no válido en instrucción return 
	 */
	public static final int INVALID_RETURN_EXCEPTION = 8;

	/**
	 * Error: Variable duplicada (mismo nombre en el ámbito activo)
	 */
	public static final int DUPLICATED_VARIABLE_EXCEPTION = 9;
	
	/**
	 * Error: Método desconocido
	 */
	public static final int UNKNOWN_METHOD_EXCEPTION = 10;
	
	/**
	 * Error: Referencia a una variable desconocida
	 */
	public static final int UNKNOWN_VARIABLE_EXCEPTION = 11;
	
	/**
	 * Error: Código no alcanzable por situarse detrás de un break o return.
	 */
	public static final int UNREACHABLE_CODE = 12;

	/**
	 * Error: El método debe devolver un valor
	 */
	public static final int UNFINISHED_METHOD = 13;
	
	/**
	 * Error: Expresión no entera en una condición
	 */
	public static final int INVALID_INTEGER_EXPRESION_EXCEPTION = 14;
	
	/**
	 * Error: Clausula case o default duplicada
	 */
	public static final int DUPLICATED_CASE_EXCEPTION = 15;
	
	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Mensaje de error
	 */
	private String msg;

	//----------------------------------------------------------------//
	//                           Constructores                        //
	//----------------------------------------------------------------//

	/**
	 * Constructor con un solo tipo esperado
	 * @param token
	 * @param expected
	 */
	public SemanticException(int code, Token token) {
		this.msg = "Parse exception at row "+token.beginLine;
		msg += ", column "+token.beginColumn+".\n";
		msg += getExplanationForCode(code)+"\n";
	}
	
	//----------------------------------------------------------------//
	//                          Métodos públicos                      //
	//----------------------------------------------------------------//

	/**
	 * Obtiene el mensaje de error
	 */
	public String toString() {
		return this.msg;
	}
	
	/**
	 * Obtiene la descripción del error
	 * @param code
	 * @return
	 */
	private static String getExplanationForCode(int code) {
		switch(code) {
			case TYPE_MISMATCH_EXCEPTION: 
				return "  Type mismatch.";
			case NUMBER_FORMAT_EXCEPTION:
				return "  Invalid literal value.";
			case LIBRARY_NAME_EXCEPTION:
				return "  Library name does not corresponds to file name.";
			case DUPLICATED_METHOD_EXCEPTION:
				return "  Duplicated method.";
			case UNKNOWN_LIBRARY_EXCEPTION:
				return "  Unknown library.";
			case DUPLICATED_ARGUMENT_EXCEPTION:
				return "  Duplicated argument.";
			case INVALID_CONDITION_EXCEPTION:
				return "  Non-boolean expression."; 
			case INVALID_RETURN_EXCEPTION:
				return "  Type mistmacth in return instruction.";
			case DUPLICATED_VARIABLE_EXCEPTION:
				return "  Duplicated variable.";
			case UNKNOWN_METHOD_EXCEPTION:
				return "  Unknown method.";
			case UNKNOWN_VARIABLE_EXCEPTION:
				return "  Unknown variable.";
			case UNREACHABLE_CODE:
				return "  Unreachable code";
			case UNFINISHED_METHOD:
				return "  The method must returns a value.";
			case INVALID_INTEGER_EXPRESION_EXCEPTION:
				return "  Non-integer expression";
			case DUPLICATED_CASE_EXCEPTION:
				return "  Duplicated case";
			default: return "";
		}
	}
}
