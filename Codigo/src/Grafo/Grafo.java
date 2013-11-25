package Grafo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

import tinto.code.*;

public class Grafo {
	
	private LibraryCodification codigoIntermedio;
	
	public NodoBloque generarGrafoInterferencia(LibraryCodification ci) {
		
		// Almacenamos el codigo intermedio
		codigoIntermedio=ci;
		//Grabamos los datos originales en un fichero
		grabarFicheroTMC("Original");
		
		//Indentificamos las instrucciones lideres
		marcarLideres();
		
		//Generamos la estructura de bloques
		BloqueInicial bloque = generarBloques();
		
		//Grabamos el grafo en un fichero
		grabarFicheroDOT("Original", bloque);
		
		return bloque;
		
	}
	
	/**
	 * Marca como lider todas las instrucciones que son destino de un salto o
	 * se tituan despues de un salto condicional, ademas de la primera instruccion
	 * 
	 * @param pendientes
	 * @param etiqueta
	 * @return
	 */
	private void marcarLideres() {
		ArrayList<CodeInstruction> etiquetasVistas;
		ArrayList<CodeAddress> etiquetasPendientes;
		
		MethodCodification[] mc = codigoIntermedio.getMethodCodifications();
		
		for (int i=0; i<mc.length; i++) {
			CodeInstruction[] code = mc[i].getCodeInstructionList().getList();
			etiquetasVistas = new ArrayList<CodeInstruction>();
			etiquetasPendientes = new ArrayList<CodeAddress>();
			code[0].lider=true;
			
			for (int j=0; j<code.length; j++) {
				if (isJump(code[j])) {
					if (j+1<code.length) code[j+1].lider=true;	
					CodeAddress etiqueta = code[j].getTarget();
					if (!marcarEtiqueta(etiquetasVistas,etiqueta)) etiquetasPendientes.add(etiqueta);
				} else if (code[j].getKind()==CodeConstants.RETURN) {
					if (j+1<code.length) code[j+1].lider=true;	
				} else if (code[j].getKind()==CodeConstants.LABEL) {
					etiquetasVistas.add(code[j]);
					marcarEtiqueta(etiquetasPendientes,code[j]);
				}
			}
			
		}
	}

	/**
	 * Marca como lider una instruccion de la lista de pendientes si tiene la misma etiqueta que el parametro
	 * 
	 * @param pendientes
	 * @param etiqueta
	 * @return
	 */
	private boolean marcarEtiqueta(ArrayList<CodeInstruction> pendientes, CodeAddress etiqueta) {
		
		Iterator<CodeInstruction> it = pendientes.iterator();
		
		while(it.hasNext()) {
			CodeInstruction inst = it.next();
			if (inst.getTarget().toString().equals(etiqueta.toString())) {
				inst.lider=true;
				return true;
			}	
		}
		
		return false;
	}
	
	/**
	 * Marca como lider la instruccion si su etiqueta aparece en la lista de pendientes
	 * 
	 * @param pendientes
	 * @param etiqueta
	 * @return
	 */
	private boolean marcarEtiqueta(ArrayList<CodeAddress> pendientes, CodeInstruction instruccion) {
		
		Iterator<CodeAddress> it = pendientes.iterator();
		
		while(it.hasNext()) {
			CodeAddress inst = it.next();
			if (inst.toString().equals(instruccion.getTarget().toString())) {
				instruccion.lider=true;
				return true;
			}	
		}
		
		return false;
	}
	
	private BloqueInicial generarBloques() {
		int numBloques = 0;
		ArrayList<NodoBloque> devolver = new ArrayList<NodoBloque>();
		ArrayList<Bloque> todos = new ArrayList<Bloque>();
		MethodCodification[] mc = codigoIntermedio.getMethodCodifications();
		BloqueInicial inicio = new BloqueInicial();
		NodoBloque fin = new NodoBloque("FINAL");
		Bloque actual = null;
		
		// Genera los bloques
		
		for (int i=0; i<mc.length; i++) {
			
			CodeInstruction[] code = mc[i].getCodeInstructionList().getList();		
			actual = new Bloque("B"+numBloques++);
			devolver.add(actual);
			
			actual.addInstruction(code[0]);
			if (code[0].getKind()==CodeConstants.LABEL) {
				actual.setEtiqueta(code[0].getTarget().toString());
			}
			
			for (int j=1; j<code.length; j++) {
				if (code[j].lider) {
					Bloque aux = new Bloque("B"+numBloques++);
					aux.addInstruction(code[j]);
					if (code[j].getKind()==CodeConstants.LABEL) {
						aux.setEtiqueta(code[j].getTarget().toString());
					} 
					
					if (actual.getLastIstruction().getKind()==CodeConstants.RETURN) {
						actual.setSucesor(0, fin);
					} else if (actual.getLastIstruction().getKind()!=CodeConstants.JUMP) {
						actual.setSucesor(0, aux);
					}
					
					todos.add(actual);
					actual=aux;
				} else {
					actual.addInstruction(code[j]);
				}
			}	
			
			todos.add(actual);
			actual.setSucesor(0, fin);		
		}
		
		//Añade los sucesores en caso de una istruccion de salto
		Iterator<Bloque> it1 = todos.iterator();
		while (it1.hasNext()) {
			Bloque b = it1.next();
			
			if (isJump(b.getLastIstruction())) {
				Iterator<Bloque> it2 = todos.iterator();
				String etiqueta = b.getLastIstruction().getTarget().toString();
				
				while (it2.hasNext()) {
					Bloque c = it2.next();
				
					if (c.getEtiqueta().equals(etiqueta)) {
						b.setSucesor(1, c);
						break;
					}
				}
			}
		}
		
		inicio.setSucesores(devolver);
		
		return inicio;
	}
	
	private boolean isJump (CodeInstruction inst) {
		switch (inst.getKind()) {
		case CodeConstants.JMP1:
		case CodeConstants.JMPEQ:
		case CodeConstants.JMPGE:
		case CodeConstants.JMPGT:
		case CodeConstants.JMPLE:
		case CodeConstants.JMPLT:
		case CodeConstants.JMPNE:
		case CodeConstants.JUMP:
			return true;
		default: return false;
		}
	}
	
	private void grabarFicheroDOT(String nombre, BloqueInicial inicial ) {
		try {
			FileOutputStream fos = new FileOutputStream(nombre+".dot");
			PrintStream stream = new PrintStream(fos);
			String transacciones = "";
			
			stream.println("digraph pgn {");
			stream.println("rankdir=TD;");
			stream.println("node [shape = rectangle];");
			
			ArrayList<NodoBloque> pendientes = inicial.getSucesores();
			Iterator<NodoBloque> it = pendientes.iterator();
			NodoBloque b;
			while (it.hasNext()) {
				b = it.next();
				transacciones += inicial.getNombre() + " -> " + b.getNombre() + "\n";
			}
			
			ArrayList<NodoBloque> vistos = new ArrayList<NodoBloque>();
			while (!pendientes.isEmpty()) {
				b = pendientes.remove(0);
				vistos.add(b);
				
				if (b instanceof Bloque) {
					stream.println(b.getNombre() + " [label = \"" + ((Bloque)b).toString() + "\"]");
					NodoBloque[] Sucesores = ((Bloque)b).getSucesores();
					
					if (Sucesores[0]!=null) {
						transacciones +=(b.getNombre() + " -> " + Sucesores[0].getNombre() + "\n");
						if (!pendientes.contains(Sucesores[0]) && !vistos.contains(Sucesores[0])) pendientes.add(Sucesores[0]);
					}
					
					if (Sucesores[1]!=null) {
						transacciones +=(b.getNombre() + " -> " + Sucesores[1].getNombre() + "\n");
						if (!pendientes.contains(Sucesores[1]) && !vistos.contains(Sucesores[1])) pendientes.add(Sucesores[1]);
					}

				}
			}
			
			stream.print(transacciones);
			stream.println("}");
			
			stream.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void grabarFicheroTMC(String nombre) {
		try {
			FileOutputStream fos = new FileOutputStream(nombre+".tmc");
			PrintStream stream = new PrintStream(fos);
			
			codigoIntermedio.print(stream);
			
			stream.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}