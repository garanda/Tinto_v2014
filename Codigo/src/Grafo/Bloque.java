package Grafo;

import java.util.ArrayList;

import GDA.NodoGDA;

import tinto.ast.struct.Variable;
import tinto.code.*;

public class Bloque{

	/**
	 * Etiqueta con la que comienza el bloque, si la hubiera
	 */
	private String etiqueta;
	
	/**
	 * Tipo de dato que devuelve el bloque, en caso de que sea el primero de un metodo
	 */
	private int type;
	
	/**
	 * Lista de argumentos del bloque, en caso de que sea el primero de un metodo
	 */
	private CodeAddress[] argument;
	
	/**
	 * Lista de argumentos del bloque, en caso de que sea el primero de un metodo
	 */
	private Variable[] var;
	
	/**
	 * Lista de instrucciones
	 */
	private CodeInstructionList ListaInstrucciones;

	/**
	 * Nombre del bloque
	 */
	private String nombre;
	
	/**
	 * Bloques sucesores
	 */
	private Bloque[] Sucesores;

	/**
	 * Metodos a los que llama el bloque
	 */
	private ArrayList<String> metodos;
	
	/**
	 * Variables que son usadas dentro del bloque
	 */
	private ArrayList<CodeAddress> variablesvivas;
	
	/**
	 * Grafo Dirigido Aciclico asociado al bloque
	 */
	private ArrayList<NodoGDA> gda;
	
	public Bloque(String nombre) {
		this.nombre = nombre;
		Sucesores = new Bloque[2];
		etiqueta = "";
		ListaInstrucciones = new CodeInstructionList();
		metodos = new ArrayList<String>();
		variablesvivas = new ArrayList<CodeAddress>();
	}

	public String getNombre() {
		return nombre;
	}
	
 	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
	
 	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

 	public CodeAddress[] getArguments() {
		return argument;
	}

	public void setArguments(CodeAddress[] argument) {
		this.argument = argument;
	}

 	public Variable[] getVarArguments() {
		return var;
	}

	public void setVarArguments(Variable[] argument) {
		this.var = argument;
	}

	public CodeInstructionList getListaInstrucciones() {
		return ListaInstrucciones;
	}

	public void addInstruction(CodeInstruction inst) {
		ListaInstrucciones.addInstruction(inst);
	}
	
	public CodeInstruction getLastIstruction() {
		CodeInstruction[] aux = ListaInstrucciones.getList();
		return aux[aux.length-1];
	}
	
	public Bloque[] getSucesores() {
		return Sucesores;
	}

	public void setSucesor(int pos, Bloque sucesor) {
		Sucesores[pos] = sucesor;
	}
	
	public void addMetodo(String m) {
		metodos.add(m);
	}

	public ArrayList<String> getMetodos() {
		return metodos;
	}
	
	public void addVariableViva(CodeAddress m) {
		variablesvivas.add(m);
	}

	public ArrayList<CodeAddress> getVariablesVivas() {
		return variablesvivas;
	}
	
	public void setGDA(ArrayList<NodoGDA> gda) {
		this.gda = gda;
	}

	public ArrayList<NodoGDA> getGDA() {
		return gda;
	}
	
	public String toString() {
		
		CodeInstruction[] lista = ListaInstrucciones.getList();
		String devolver = "";
		for (int i=0; i<lista.length; i++) {
			devolver += lista[i].toString2() + "\n";
		}
		
		return devolver;
	}
	
	public boolean equals(Bloque b) {		
		return nombre.equalsIgnoreCase(b.getNombre());
	}
	
}
