package Grafo;

public class NodoBloque {

	/**
	 * Nombre del bloque
	 */
	protected String nombre;
	
	public NodoBloque(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
	public boolean equals(NodoBloque b) {		
		return nombre.equalsIgnoreCase(b.getNombre());
	}

}
