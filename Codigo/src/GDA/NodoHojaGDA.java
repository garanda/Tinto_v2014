package GDA;

import java.io.PrintStream;

import tinto.code.CodeAddress;

public class NodoHojaGDA extends NodoGDA {
	
	private CodeAddress etiqueta;

	public CodeAddress getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(CodeAddress etiqueta) {
		this.etiqueta = etiqueta;
	}
	
	public void print(PrintStream stream) {
		stream.println(etiqueta.getDescription());
	}

	@Override
	public String toString() {
		return etiqueta.getDescription();
	}
	
	public boolean expresionComun(NodoGDA n) {
		
		if (n instanceof NodoHojaGDA) {
			NodoHojaGDA nh = (NodoHojaGDA)n;
			
			return etiqueta.getDescription().equalsIgnoreCase(nh.getEtiqueta().getDescription());
		}
		
		return false;
	}
	
}
