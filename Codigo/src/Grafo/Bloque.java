package Grafo;

import java.util.ArrayList;

import tinto.code.*;

public class Bloque{

	/**
	 * Etiqueta con la que comienza el bloque, si la hubiera
	 */
	private String etiqueta;
	
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
	
	public Bloque(String nombre) {
		this.nombre = nombre;
		Sucesores = new Bloque[2];
		etiqueta = "";
		ListaInstrucciones = new CodeInstructionList();
		metodos = new ArrayList<String>();
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
