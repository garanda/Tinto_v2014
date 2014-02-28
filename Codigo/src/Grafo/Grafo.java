package Grafo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import GDA.*;
import tinto.ast.struct.Variable;
import tinto.code.*;

public class Grafo {
	
	private LibraryCodification codigoIntermedio;
	
	public LibraryCodification optimizar(LibraryCodification ci) {
		
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
		ArrayList<Bloque> estructura = generarGDA(bloques);
		
		//Generar codigo optimizado
		codigoIntermedio = generarCodigoOptimizado(estructura);
		
		//Grabamos los datos optimizados en un fichero
		grabarFicheroTMC("Optimizado");
		
		return codigoIntermedio;
		
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
	
	/**
	 * Genera el diagrama de flujo del programa
	 * @return
	 */
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
			actual.setType(mc[i].getType());
			actual.setArguments(mc[i].getArguments());
			actual.setVarArguments(mc[i].getVariableArguments());

			for (int j=1; j<code.length; j++) {
				if (code[j].lider) {
					Bloque aux = new Bloque("B"+numBloques++);
					aux.setType(mc[i].getType());
					aux.setArguments(mc[i].getArguments());
					aux.setVarArguments(mc[i].getVariableArguments());
					
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
	
	/**
	 * Elimina los bloques inaccesibles
	 * @param bloques
	 * @return
	 */
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
				for (int j=0; j<suc.length; j++) if (suc[j]!=null && !pend.contains(suc[j])) pend.add(suc[j]);
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
	
	/**
	 * Devuelve verdadero si la instruccion es un salto
	 * @param inst
	 * @return
	 */
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
	
	/**
	 * Genera un fichero .dot para representar el diagrama de flujo con el programa graphviz
	 * @param nombre
	 * @param lista
	 */
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
	
	/**
	 * Graba un fichero .tmc con el codigo intermedio del programa
	 * @param nombre
	 */
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
	
	/**
	 * Genera el grafo dirigido aciclico que representa el codigo intermedio de un bloque
	 * @param bb
	 * @return
	 */
	public ArrayList<NodoGDA> generarListaGDA(Bloque bb) {
		ArrayList<NodoGDA> all = new ArrayList<NodoGDA>();
		ArrayList<NodoGDA> devolver = new ArrayList<NodoGDA>();
		Hashtable<CodeAddress,NodoGDA> variables = new Hashtable<CodeAddress,NodoGDA>();
		Hashtable<CodeAddress,Integer> indices = new Hashtable<CodeAddress,Integer>();
		CodeInstruction[] ci = bb.getListaInstrucciones().getList();
		
		for (int i=0; i<bb.getArguments().length; i++) {
			CodeAddress a = bb.getArguments()[i];
			bb.addVariableViva(a);
			
			NodoHojaGDA nh = new NodoHojaGDA();
			nh.setEtiqueta(a);
			
			all.add(nh);
			devolver.add(nh);
			variables.put(a, nh);
			indices.put(a, 0);
		}
		
		
		for (int i=0; i<ci.length; i++) {
			CodeAddress t = ci[i].getTarget();
			CodeAddress s1 = ci[i].getSource1();
			CodeAddress s2 = ci[i].getSource2();
			NodoIntermedioGDA nt = null;
			
			// Añadimos a la lista de indices la nueva variable modificada
			if (!indices.containsKey(t)) {
				indices.put(t,0);
			} else {
				indices.put(t,indices.get(t)+1);
			}
			
			// Si existe el operando 1
			if (s1 != null) {
				// Añadimos a la lista de indices la nueva variable usada, si no exixtia antes
				if (!indices.containsKey(s1)) {
					indices.put(s1,0);
					bb.addVariableViva(s1);
				}
				NodoGDA ns1 = null;
				
				// Si el operando ya esta definido lo tomamos
				if(variables.containsKey(s1)) {
					ns1=variables.get(s1);
				} else { //En caso contrario, creamos un nuevo nodo hoja
					ns1=new NodoHojaGDA();
					((NodoHojaGDA)ns1).setEtiqueta(s1);
					variables.put(s1, ns1);
				}
				
				// Creamos el nodo de la instruccion
				nt = new NodoIntermedioGDA();
				nt.setEtiqueta(ci[i].getInstructionName());
				if (ci[i].getInstructionName().equalsIgnoreCase("CALL") || isJump(ci[i])) {
					nt.setVariableViva(true);
				}
				
				nt.setOperator(ci[i].getKind());
				nt.addVariable(t);
				nt.addOperando(ns1);
				if (ci[i].getInstructionName().equalsIgnoreCase("PARAM")) {
					nt.setVariableViva(true);
					nt.getVariables().clear();
					nt.addOperando(variables.get(t));
				}
				
				// Si existe el operando 2
				if (s2!=null) {
					// Añadimos a la lista de indices la nueva variable usada, si no exixtia antes
					if (!indices.containsKey(s2)) {
						indices.put(s2,0);
						bb.addVariableViva(s1);
					}
					NodoGDA ns2 = null;
					
					// Si el operando ya esta definido lo tomamos
					if(variables.containsKey(s2)) {
						ns2=variables.get(s2);
					} else { //En caso contrario, creamos un nuevo nodo hoja
						ns2=new NodoHojaGDA();
						((NodoHojaGDA)ns2).setEtiqueta(s2);
						variables.put(s2, ns2);
					}
					
					// Añadimos el operando al nodo
					nt.addOperando(ns2);
				} 
				
				// Comprobamos que el nodo que acabamos de crear no exista ya
				// Si existe añadimos la variable t al nodo ya existente
				NodoGDA ant = null;
				Iterator<NodoGDA> it = all.iterator();
				boolean cambio = false;
				while (it.hasNext() && !cambio) {
					NodoGDA gda = it.next();
					if (gda instanceof NodoIntermedioGDA) {
						NodoIntermedioGDA gda2 = (NodoIntermedioGDA)gda;
						if (gda2.expresionComun(nt) || (nt.getEtiqueta().equalsIgnoreCase("ASSIGN") && gda2.getVariables().contains(s1))) {
							gda2.addVariable(t);
							ant = variables.put(t, gda);
							
							cambio = true;
						}
					}
				}
				// Si no existia antes lo añadimos a la lista de nodos
				if (!cambio) {
					all.add(nt);
					ant = variables.put(t, nt);
					
					ArrayList<NodoGDA> op = nt.getOperandos();
					Iterator<NodoGDA> it2 = op.iterator();
					while (it2.hasNext()) {
						NodoGDA n = it2.next();
						for (int j=0; j<devolver.size(); j++) {
							if (devolver.get(j).expresionComun(n)) {
								devolver.remove(j);
								break;
							}
						}
					}
					devolver.add(nt);
				}
					
				if (!nt.getEtiqueta().equalsIgnoreCase("PARAM")) {
					// Renombramos la anterior definicion de t (si existe)
					if (ant !=null) {
						if (ant instanceof NodoIntermedioGDA) {
							NodoIntermedioGDA ant1 = (NodoIntermedioGDA)ant;
							ant1.getVariables().remove(t);
							ant1.getVariables().add(new CodeVariable(t.getDescription() + (indices.get(t)-1),t.getDescription() + (indices.get(t)-1)));
							
							//if (ant1.getVariables().isEmpty()) all.remove(ant1);
						} else {
							NodoHojaGDA ant1 = (NodoHojaGDA)ant;
							ant1.setEtiqueta(new CodeVariable(t.getDescription() + (indices.get(t)-1),t.getDescription() + (indices.get(t)-1)));
						}
					}
				}
			} else {
				// Si no tiene operandos, cramos un nuevo nodo con un solo hijo
				nt = new NodoIntermedioGDA();
				nt.setEtiqueta(ci[i].getInstructionName());
				if (ci[i].getInstructionName().equalsIgnoreCase("PRECALL") || ci[i].getInstructionName().equalsIgnoreCase("RETURN") || ci[i].getInstructionName().equalsIgnoreCase("") || isJump(ci[i])) {
					nt.setVariableViva(true);
				}
				
				nt.setOperator(ci[i].getKind());
				nt.addVariable(t);
				if (variables.get(t) != null) {
					nt.addOperando(variables.get(t));
				}
				
				all.add(nt);
				variables.put(t, nt);
				
				ArrayList<NodoGDA> op = nt.getOperandos();
				Iterator<NodoGDA> it = op.iterator();
				while (it.hasNext()) {
					NodoGDA n = it.next();
					for (int j=0; j<devolver.size(); j++) {
						if (devolver.get(j).expresionComun(n)) {
							devolver.remove(j);
							break;
						}
					}
				}
				devolver.add(nt);				
			}
		}
		
		return devolver;
	}

	/**
	 * Genera el grafo dirigido aciclico que representa el codigo intermedio de una lista de bloques
	 * @param bloques
	 * @return
	 */
	public ArrayList<Bloque> generarGDA(ArrayList<Bloque> bloques) {
		ArrayList<Bloque> nbs = new ArrayList<Bloque>();
		ArrayList<Bloque> visitados = new ArrayList<Bloque>();
		ArrayList<Bloque> devolver = new ArrayList<Bloque>();
		nbs.addAll(bloques);

		while(!nbs.isEmpty()) {
			Bloque b = nbs.remove(0);
			visitados.add(b);
			ArrayList<NodoGDA> gda = generarListaGDA(b);
			b.setGDA(gda);
			devolver.add(b);
			
			grabarFicheroGDA(b.getNombre(), gda);
				
			Bloque[] suc = b.getSucesores();
			for (int i=suc.length-1; i>=0;i--) {
				if (suc[i]!=null && !visitados.contains(suc[i])) nbs.add(0,suc[i]);
			}
		}
		
		devolver = eliminarVariablesMuertas(devolver);
		
		return devolver;
	}
	
	/**
	 * Elimina las variables muertas de un GDA y las intrucciones que las usen
	 * @param lista
	 * @return
	 */
	private ArrayList<Bloque> eliminarVariablesMuertas(ArrayList<Bloque> lista) {
		ArrayList<Bloque> devolver = new ArrayList<Bloque>();
		
		Iterator<Bloque> it = lista.iterator();
		while (it.hasNext()) {
			Bloque bloque = it.next();
			ArrayList<NodoGDA> listagda = bloque.getGDA();
			
			ArrayList<CodeAddress> variablesvivas = new ArrayList<CodeAddress>();
			Bloque[] suc = bloque.getSucesores();
			for (int i=0; i<suc.length;i++) {
				if (suc[i] != null)
					variablesvivas.addAll(suc[i].getVariablesVivas());
			}
			
			for (int j=0; j<listagda.size(); j++) {
				NodoGDA n = listagda.get(j);
				if (!n.getVariableViva()) {
					if (n instanceof NodoIntermedioGDA) {
						NodoIntermedioGDA ni = (NodoIntermedioGDA)n;
						
						ArrayList<CodeAddress> var = ni.getVariables();
						for (int i=0; i<var.size(); i++) {
							CodeAddress a = var.get(i);
							if (!variablesvivas.contains(a)) {
								var.remove(a);
								i--;
							}
						}
						if (var.isEmpty()) {
							listagda.remove(n);
							j--;
							listagda.addAll(ni.getOperandos());
						}
					} else {
						NodoHojaGDA nh = (NodoHojaGDA)n;
						if (!variablesvivas.contains(nh.getEtiqueta())) {
							listagda.remove(n);
							j--;
						}
					}
				}
			}
			
			bloque.setGDA(listagda);
			devolver.add(bloque);
		}
		
		return devolver;
	}

	/**
	 * Genera un fichero .gda para representar el gda con el programa graphviz
	 * @param nombre
	 * @param nodos
	 */
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
	
	/**
	 * Genera una nueva codificacion a partis de una serie de bloques y gdas
	 * @param estructura
	 * @return
	 */
	private LibraryCodification generarCodigoOptimizado(ArrayList<Bloque> estructura) {
		ArrayList<MethodCodification> lm = new ArrayList<MethodCodification>();
		ArrayList<Bloque> visitados = new ArrayList<Bloque>();
		
		Iterator<Bloque> it = estructura.iterator();
		while (it.hasNext()) {
			Bloque bloque = it.next();
			if (visitados.contains(bloque)) {
				continue;
			}
			
			ArrayList<CodeAddress> arguments = new ArrayList<CodeAddress>();
			ArrayList<CodeAddress> usadas = new ArrayList<CodeAddress>();
			CodeAddress[] arg = bloque.getArguments();
			for (int i=0; i<arg.length;i++) {
				arguments.add(arg[i]);
				usadas.add(arg[i]);
			}
			
			CodeInstructionList codelist = new CodeInstructionList();
			CodeInstructionList code = new CodeInstructionList();
			
			ArrayList<Bloque> porvisitar = new ArrayList<Bloque>();
			porvisitar.add(bloque);
			
			while(!porvisitar.isEmpty()) {
				Bloque b = porvisitar.remove(0);
				visitados.add(b);
				
				ArrayList<NodoGDA> pendientes = b.getGDA();
				ArrayList<NodoGDA> pendientes2 = new ArrayList<NodoGDA>();

				while (!pendientes.isEmpty()) {
					pendientes2.add(pendientes.remove(0));
					
					while (!pendientes2.isEmpty()) {
						NodoGDA ngda = pendientes2.remove(0);
	
						if (ngda instanceof NodoIntermedioGDA) {
							NodoIntermedioGDA n = (NodoIntermedioGDA)ngda;
							ArrayList<NodoGDA> operandos = n.getOperandos();
							for (int i=0; i<operandos.size(); i++) {
								if (!pendientes2.contains(operandos.get(i))) {
									pendientes2.add(0, operandos.get(i));
								}
							}
							
							CodeAddress op1 = null, op2 = null;
							CodeAddress tar = mejorVariable(usadas,n.getVariables());
							
							if (operandos.size()==2) {
								NodoGDA operando1 = operandos.get(0);
								NodoGDA operando2 = operandos.get(1);
								
								if (operando1 instanceof NodoIntermedioGDA) {
									op1 = mejorVariable(usadas,((NodoIntermedioGDA)operando1).getVariables());
								} else {
									op1 = ((NodoHojaGDA)operando1).getEtiqueta();
								}
								if (operando2 instanceof NodoIntermedioGDA) {
									op2 = mejorVariable(usadas,((NodoIntermedioGDA)operando2).getVariables());
								} else {
									op2 = ((NodoHojaGDA)operando2).getEtiqueta();
								}
								
							} else if (operandos.size()==1) {
								NodoGDA operando1 = operandos.get(0);
								op2 = null;
								
								if (operando1 instanceof NodoIntermedioGDA) {
									op1 = mejorVariable(usadas,((NodoIntermedioGDA)operando1).getVariables());
								} else {
									op1 = ((NodoHojaGDA)operando1).getEtiqueta();
								}
								
							}
							
							if (!n.getEtiqueta().equalsIgnoreCase("PARAM")) {
								code.addInstruction(new CodeInstruction(n.getOperator(),tar,op1,op2));
							} else {
								code.addInstruction(new CodeInstruction(n.getOperator(),op2,op1,null));
							}
						}
					}
					
					for (int i=code.getList().length; i>0; i--) {
						codelist.addInstruction(code.getList()[i-1]);
					}
					
					code = new CodeInstructionList();
				}
			
				Bloque[] suc = b.getSucesores();
				for (int i=0; i<suc.length;i++) {
					if (suc[i] != null && !visitados.contains(suc[i]))
						porvisitar.add(suc[i]);
				}
				
			}
			
			ArrayList<Variable> locals = new ArrayList<Variable>();
			for (int i=0; i<usadas.size(); i++) {
				if (!arguments.contains(usadas.get(i)) && usadas.get(i) instanceof CodeVariable) {
					locals.add(new Variable(0, usadas.get(i).getDescription()));
				}
			}
			
			Variable[] local = new Variable[locals.size()];
			for (int i=0; i<locals.size(); i++) {
				local[i] = (Variable) locals.get(i);
			}
			
			MethodCodification m = new MethodCodification(bloque.getEtiqueta(), bloque.getType(), bloque.getVarArguments(), local);
			m.setInstructionList(codelist);
			lm.add(m);
		}
		
		LibraryCodification ci = new LibraryCodification(codigoIntermedio.getName(),codigoIntermedio.getImported(),lm.size());
		for (int i=0; i<lm.size(); i++) {
			ci.setMethodCodification(i,lm.get(i));
		}
		
		return ci;
	}

	/**
	 * Elige la mejor variable que consevar cuando hay mas de una en un nodo del gda
	 * @param usadas
	 * @param variables
	 * @return
	 */
	private CodeAddress mejorVariable(ArrayList<CodeAddress> usadas, ArrayList<CodeAddress> variables) {
		CodeAddress var = null;
		
		Iterator<CodeAddress> it = variables.iterator();
		while (it.hasNext()) {
			var = it.next();
			
			if (usadas.contains(var)) {
				return var;
			}
		}
		
		usadas.add(var);
		return var;
	}

}