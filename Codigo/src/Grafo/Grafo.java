package Grafo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import GDA.*;
import tinto.code.*;

public class Grafo {
	
	private LibraryCodification codigoIntermedio;
	
	public ArrayList<Bloque> generarGrafoInterferencia(LibraryCodification ci) {
		
		// Almacenamos el codigo intermedio
		codigoIntermedio=ci;
		//Grabamos los datos originales en un fichero
		grabarFicheroTMC("Original");
		
		//Indentificamos las instrucciones lideres
		marcarLideres();
		
		//Generamos la estructura de bloques
		ArrayList<Bloque> bloques = generarBloques();

		//Eliminamos los bloques innecesarios
		bloques = barrer(bloques);

		//Grabamos el grafo en un fichero
		grabarFicheroDOT("Original", bloques);
		
		//Generar GDA de cada bloque
		generarGDA(bloques);
		
		return bloques;
		
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
			if (code.length==0) continue;
			
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
	
	private ArrayList<Bloque> generarBloques() {
		int numBloques = 0;
		ArrayList<Bloque> devolver = new ArrayList<Bloque>();
		ArrayList<Bloque> todos = new ArrayList<Bloque>();
		MethodCodification[] mc = codigoIntermedio.getMethodCodifications();
		Bloque actual = null;
		
		// Genera los bloques
		for (int i=0; i<mc.length; i++) {
			CodeInstruction[] code = mc[i].getCodeInstructionList().getList();	
			if (code.length==0) continue;
			
			actual = new Bloque("B"+numBloques++);
			devolver.add(actual);
			
			actual.addInstruction(code[0]);
			actual.setEtiqueta(mc[i].getMethodLabel());

			for (int j=1; j<code.length; j++) {
				if (code[j].lider) {
					Bloque aux = new Bloque("B"+numBloques++);
					aux.addInstruction(code[j]);
					if (code[j].getKind()==CodeConstants.LABEL) {
						aux.setEtiqueta(code[j].getTarget().toString());
					} else if(code[j].getKind()==CodeConstants.CALL) {
						aux.addMetodo(code[j].getSource1().toString());
					}
					
					if (actual.getLastIstruction().getKind()!=CodeConstants.JUMP) {
						actual.setSucesor(0, aux);
					}
					
					todos.add(actual);
					actual=aux;
				} else {
					actual.addInstruction(code[j]);
					
					if(code[j].getKind()==CodeConstants.CALL) actual.addMetodo(code[j].getSource1().toString());
				}
			}	
			
			todos.add(actual);	
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
		
		return devolver;
	}
	
	private ArrayList<Bloque> barrer(ArrayList<Bloque> bloques) {
		ArrayList<Bloque> devolver = new ArrayList<Bloque>();
		ArrayList<Bloque> proximos = new ArrayList<Bloque>();
		Bloque main = bloques.remove(0);
		devolver.add(main);
		proximos.add(main);
		
		while (!proximos.isEmpty()) {
			Bloque a = proximos.remove(0);
			
			ArrayList<String> accesibles = new ArrayList<String>();
			ArrayList<Bloque> pend = new ArrayList<Bloque>();
			pend.add(a);
			for (int i=0; i<pend.size(); i++) {
				Bloque b = pend.get(i);
				accesibles.addAll(b.getMetodos());
				
				Bloque[] suc = b.getSucesores();
				for (int j=0; j<suc.length; j++) if (suc[i]!=null && !pend.contains(suc[i])) pend.add(suc[i]);
			}
			
			int i=0;
			while (i<bloques.size()) {
				if (accesibles.contains(bloques.get(i).getEtiqueta())) { 
					Bloque b = bloques.remove(i);
					devolver.add(b);
					proximos.add(b);
				} else {
					i++;
				}
			}
			
		}
		
		return devolver;
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
	
	private void grabarFicheroDOT(String nombre, ArrayList<Bloque> lista) {
		try {
			FileOutputStream fos = new FileOutputStream(nombre+".dot");
			PrintStream stream = new PrintStream(fos);
			String transacciones = "";
			
			stream.println("digraph pgn {");
			stream.println("rankdir=TD;");
			stream.println("node [shape = rectangle];");
			
			
			for (int index=0; index<lista.size(); index++) {
				Bloque inicial = lista.get(index);
				
				stream.println(inicial.getNombre() + " [label = \"" + inicial.toString() + "\"]");
				Bloque[] ab = inicial.getSucesores();
				ArrayList<Bloque> pendientes = new ArrayList<Bloque>();
				for (int i=0; i<ab.length; i++) if(ab[i]!=null) pendientes.add(ab[i]);
				
				Iterator<Bloque> it = pendientes.iterator();
				Bloque b;
				while (it.hasNext()) {
					b = it.next();
					transacciones += inicial.getNombre() + " -> " + b.getNombre() + "\n";
				}
				
				ArrayList<Bloque> vistos = new ArrayList<Bloque>();
				while (!pendientes.isEmpty()) {
					b = pendientes.remove(0);
					vistos.add(b);
					
					stream.println(b.getNombre() + " [label = \"" + b.toString() + "\"]");
					Bloque[] Sucesores = b.getSucesores();
						
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
	
	public ArrayList<NodoGDA> generarListaGDA(Bloque bb) {
		ArrayList<NodoGDA> devolver = new ArrayList<NodoGDA>();
		Hashtable<CodeAddress,NodoGDA> variables = new Hashtable<CodeAddress,NodoGDA>();
		CodeInstruction[] ci = bb.getListaInstrucciones().getList();
		
		for (int i=0; i<ci.length; i++) {
			CodeAddress t = ci[i].getTarget();
			CodeAddress s1 = ci[i].getSource1();
			CodeAddress s2 = ci[i].getSource2();
			NodoIntermedioGDA nt = null;
			
			if (s1 != null) {
				NodoGDA ns1 = null;
				
				if(variables.containsKey(s1)) {
					ns1=variables.get(s1);
				} else {
					ns1=new NodoHojaGDA();
					((NodoHojaGDA)ns1).setEtiqueta(s1);
					variables.put(s1, ns1);
				}
				
				nt = new NodoIntermedioGDA();
				nt.setEtiqueta(ci[i].getInstructionName());
				nt.addVariable(t);
				nt.addOperando(ns1);
				
				if (s2!=null) {
					NodoGDA ns2 = null;
					
					if(variables.containsKey(s2)) {
						ns2=variables.get(s2);
					} else {
						ns2=new NodoHojaGDA();
						((NodoHojaGDA)ns2).setEtiqueta(s2);
						variables.put(s2, ns2);
					}
					
					nt.addOperando(ns2);
				} 
				
				NodoGDA ant = null;
				Iterator<NodoGDA> it = devolver.iterator();
				boolean cambio = false;
				while (it.hasNext() && !cambio) {
					NodoIntermedioGDA gda = (NodoIntermedioGDA)it.next();
					if (gda.expresionComun(nt) || (nt.getEtiqueta().equalsIgnoreCase("ASSIGN") && gda.getVariables().contains(s1))) {
						gda.addVariable(t);
						ant = variables.put(t, gda);
						cambio = true;
					}
				}
				if (!cambio) {
					devolver.add(nt);
					ant = variables.put(t, nt);
				}
				
				if (ant !=null) {
					if (ant instanceof NodoIntermedioGDA) {
						NodoIntermedioGDA ant1 = (NodoIntermedioGDA)ant;
						ant1.getVariables().remove(t);
						
						if (ant1.getVariables().isEmpty()) devolver.remove(ant1);
					}
				}
			}
		}
		
		return devolver;
	}

	public void generarGDA(ArrayList<Bloque> bloques) {
		ArrayList<Bloque> nbs = new ArrayList<Bloque>();
		nbs.addAll(bloques);
		
		for (int j=0; j<nbs.size(); j++) {
			Bloque b = nbs.get(j);
			ArrayList<NodoGDA> gda = generarListaGDA(b);
			grabarFicheroGDA(b.getNombre(), gda);
				
			Bloque[] suc = b.getSucesores();
			for (int i=0; i<suc.length;i++) {
				if (suc[i]!=null && !nbs.contains(suc[i])) nbs.add(suc[i]);
			}
		}
	}
	
	private void grabarFicheroGDA(String nombre, ArrayList<NodoGDA> nodos) {
		try {
			FileOutputStream fos = new FileOutputStream(nombre+".gda");
			PrintStream stream = new PrintStream(fos);
			
			stream.println("digraph pgn {");
			stream.println("rankdir=TD;");
			stream.println("node [shape = rectangle];");
			
			Iterator<NodoGDA> it = nodos.iterator();
			while(it.hasNext()) {
				NodoGDA n = it.next();
				n.print(stream);
			}
			
			stream.println("}");
			
			stream.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}