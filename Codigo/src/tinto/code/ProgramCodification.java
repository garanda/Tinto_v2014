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

import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;

import tinto.ast.struct.Library;

/**
 * Clase que describe toda la información asociada a la codificación
 * de una biblioteca de funciones.
 * 
 * @author Francisco José Moreno Velo
 */
public class ProgramCodification  {
	
	/**
	 * Codificaci—n principal
	 */
	private LibraryCodification Main;
	
	/**
	 * codificacion de las librerias
	 */
	private List<LibraryCodification> Libs;
	
	
	/**
	 * Constructor
	 * @param label
	 */
	public ProgramCodification() {
		Main = null;
		Libs = new LinkedList<LibraryCodification>();
	}

	/**
	 * Asigna la codificación de la libreria principal
	 * @param mc
	 */
	public void setMainCodification(LibraryCodification mc) {
		this.Main = mc;
	}
	
	/**
	 * A–ade la codificacion de una libreria
	 * @param mc
	 */
	public void addLibraryCodification(LibraryCodification mc) {
		this.Libs.add(mc);
	}
	
	/**
	 * Obtiene la codificacion de la libreria principal
	 * @return mc
	 */
	public LibraryCodification getMainLibraryCodification() {
		return this.Main;
	}
	
	/**
	 * Obtiene la codificaci—n de las librerias
	 * @return mc
	 */
	public List<LibraryCodification> getLibrariesCodification() {
		return this.Libs;
	}
	
	/**
	 * Escribe el código completo de la clase sobre el flujo
	 * 
	 * @return
	 */
	public void print(File file) {

		PrintStream ps;
		try {
			ps = new PrintStream(file);
			Main.print(ps);
			Iterator<LibraryCodification> it = this.Libs.iterator();
			while (it.hasNext()) {
				(it.next()).print(ps);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
//		for(int i=0; i<method.length; i++) {
//			stream.println("; %%%%%%%%%%%%%%%");
//			stream.println("; "+method[i].getMethodLabel());
//			stream.println("; %%%%%%%%%%%%%%%");
//			method[i].print(stream);
//			stream.println(";");
//			stream.println(";");
//		}
	}

	public void escribir(PrintStream _stream) {	
		Main.print(_stream);
		Iterator<LibraryCodification> it = Libs.iterator();
		while (it.hasNext())
			(it.next()).print(_stream);

	}
	
	
	public void setVisited() {
		List<MethodCodification> Abiertos = new LinkedList<MethodCodification>();

		MethodCodification m = getMethod("Main_Main");		
		Abiertos.add(m);
		
		while (!Abiertos.isEmpty()) {
			MethodCodification origen = Abiertos.remove(0);
			if (origen.isVisited())
				continue;
			origen.setVisited(true);
			
			CodeInstruction[] cil = origen.getCodeInstructionList().getList();
			for (int i=0;i<cil.length;i++){
				if (cil[i].getKind() == CodeConstants.CALL) {
					MethodCodification llamada = getMethod(cil[i].getSource1().getDescription());
					if (llamada==null) {
						System.out.println("********** - NULO - " + cil[i].getSource1().getDescription());
					} else {
						Abiertos.add(llamada);
						System.out.println("ENLACE: " + origen.getMethodLabel() + " --> " + llamada.getMethodLabel());
					}				
				}				
			}
		}
	}
	
	
	private MethodCodification getMethodo(MethodCodification[] mc,String _label) {
		
		for (int i=0;i<mc.length;i++) {
			if (mc[i].getMethodLabel().equalsIgnoreCase(_label)) {
				return mc[i];
			}
		}
		
		
		return null;
		
	}
	

	
	private MethodCodification getMethod(String _label) {

		MethodCodification Aux = getMethodo(Main.getMethodCodifications(),_label);
		if (Aux==null) {
			Iterator<LibraryCodification> it = Libs.iterator();
			while (it.hasNext()) {
				LibraryCodification lc = it.next();
				Aux = getMethodo(lc.getMethodCodifications(),_label);
				if (Aux != null)
					return Aux;
			}
		} else
			return Aux;
		return null;
	}
	

	
	
	
	
	public void printVisited() {	
		System.out.println("===========  MAIN ==============");
		MethodCodification[] mc = Main.getMethodCodifications();
		for (int i=0;i<mc.length;i++) 
				System.out.println("(" + mc[i].isVisited() + ") " + mc[i].getMethodLabel());
		System.out.println();
		
		Iterator<LibraryCodification> it = Libs.iterator();
		while (it.hasNext()){
			LibraryCodification lc = (it.next());
			System.out.println("===========  " + lc.getName() + " ==============");
			mc = lc.getMethodCodifications();
			for (int i=0;i<mc.length;i++) 
				System.out.println("(" + mc[i].isVisited() + ") " + mc[i].getMethodLabel());
			System.out.println();
		}
	}
	

	
	
	
	//public get
	
	
	
	
//	/**
//	 * Escribe el código completo de la clase sobre el flujo
//	 * 
//	 * @return
//	 */
//	public void print(PrintStream stream) {
//		for(int i=0; i<method.length; i++) {
//			stream.println("; %%%%%%%%%%%%%%%");
//			stream.println("; "+method[i].getMethodLabel());
//			stream.println("; %%%%%%%%%%%%%%%");
//			method[i].print(stream);
//			stream.println(";");
//			stream.println(";");
//		}
//	}
}
