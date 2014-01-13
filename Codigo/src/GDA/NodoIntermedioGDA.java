package GDA;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

import tinto.code.CodeAddress;
import tinto.code.CodeConstants;

public class NodoIntermedioGDA extends NodoGDA {
	
	private ArrayList<CodeAddress> variables;
	private ArrayList<NodoGDA> operandos;
	private String etiqueta;

	public NodoIntermedioGDA() {
		variables = new ArrayList<CodeAddress>();
		operandos = new ArrayList<NodoGDA>();
		etiqueta = "";
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
	
	public void addOperando(NodoGDA var) {
		operandos.add(var);
	}
	
	public ArrayList<NodoGDA> getOperandos() {
		return operandos;
	}
	
	public void addVariable(CodeAddress var) {
		variables.add(var);
	}
	
	public ArrayList<CodeAddress> getVariables() {
		return variables;
	}
	
	public boolean expresionComun(NodoGDA n) {
		
		if (n instanceof NodoIntermedioGDA) {
			NodoIntermedioGDA ni = (NodoIntermedioGDA)n;
			if (etiqueta.equalsIgnoreCase(ni.getEtiqueta())) {
				ArrayList<NodoGDA> op = ni.getOperandos();
			
				if (op.size()==operandos.size()) {
					Iterator<NodoGDA> it1 = operandos.iterator();
					Iterator<NodoGDA> it2 = op.iterator();
				
					while (it1.hasNext()) {
						NodoGDA n1 = it1.next();
						NodoGDA n2 = it2.next();
						if (!n1.expresionComun(n2)) return false;
						
					}
					return true;
				}
			
			}
		}
		return false;
	}
	
	public void print(PrintStream stream) {
		String cadena = etiqueta;
		
		for (int i=0; i<variables.size();i++) {
			cadena += "_"+variables.get(i).getDescription();
		}
		
		stream.println(cadena);
		
		for (int i=0; i<operandos.size();i++) {
			stream.println(cadena + "-> " + operandos.get(i).toString());
		}
	}

	@Override
	public String toString() {
		String cadena = etiqueta;
		
		for (int i=0; i<variables.size();i++) {
			cadena += "_"+variables.get(i).getDescription();
		}
		
		return cadena;
	}
	
}
