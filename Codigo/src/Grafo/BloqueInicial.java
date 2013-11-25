package Grafo;

import java.util.ArrayList;

public class BloqueInicial extends NodoBloque {

	/**
	 * Bloques sucesores
	 */
	ArrayList<NodoBloque> Sucesores;
	
	public BloqueInicial() {
		super("INICIO");
		Sucesores = new ArrayList<NodoBloque>();
	}

	public ArrayList<NodoBloque> getSucesores() {
		return Sucesores;
	}
	
	public void addSucesor(NodoBloque hijo) {
		Sucesores.add(hijo);
	}
	
	public void setSucesores(ArrayList<NodoBloque> Sucesores) {
		this.Sucesores = Sucesores;
	}
	
}
