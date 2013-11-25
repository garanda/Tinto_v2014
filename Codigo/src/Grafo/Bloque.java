package Grafo;

import tinto.code.*;

public class Bloque extends NodoBloque{
	
	/**
	 * Etiqueta con la que comienza el bloque, si la hubiera
	 */
	private String etiqueta;
	
	/**
	 * Lista de instrucciones
	 */
	private CodeInstructionList ListaInstrucciones;
	
	/**
	 * Bloques sucesores
	 */
	protected NodoBloque[] Sucesores;

	public Bloque(String nombre) {
		super(nombre);
		Sucesores = new NodoBloque[2];
		etiqueta = "";
		ListaInstrucciones = new CodeInstructionList();
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
	
	public NodoBloque[] getSucesores() {
		return Sucesores;
	}

	public void setSucesor(int pos, NodoBloque sucesor) {
		Sucesores[pos] = sucesor;
	}
	
	public String toString() {
		
		CodeInstruction[] lista = ListaInstrucciones.getList();
		String devolver = "";
		for (int i=0; i<lista.length; i++) {
			devolver += lista[i].toString2() + "\n";
		}
		
		return devolver;
	}
	
}
