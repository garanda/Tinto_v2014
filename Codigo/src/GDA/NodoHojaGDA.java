package GDA;

import java.io.PrintStream;

import tinto.code.CodeAddress;

public class NodoHojaGDA extends NodoGDA {
	
	private CodeAddress etiqueta;

	public NodoHojaGDA() {
	}
	
	public CodeAddress getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(CodeAddress etiqueta) {
		this.etiqueta = etiqueta;
	}
	
	public void print(PrintStream stream) {
		stream.println();
	}

	@Override
	public String toString() {
		String cadena = etiqueta.getDescription();
		
		return cadena;
	}
	
	public boolean expresionComun(NodoGDA n) {
		
		if (n instanceof NodoHojaGDA) {
			NodoHojaGDA nh = (NodoHojaGDA)n;
			
			return etiqueta.getDescription().equalsIgnoreCase(nh.getEtiqueta().getDescription());
		}
		
		return false;
	}
	
}
