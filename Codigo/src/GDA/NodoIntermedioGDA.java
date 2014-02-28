package GDA;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

import tinto.code.CodeAddress;

public class NodoIntermedioGDA extends NodoGDA {
	
	private ArrayList<CodeAddress> variables;
	private ArrayList<NodoGDA> operandos;
	private String etiqueta;
	private int operator;

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

	public int getOperator() {
		return operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
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
	
	@SuppressWarnings("static-access")
	public void print(PrintStream stream) {
		if (super.identificador == 0) {
			super.numnodos++;
			super.identificador = super.numnodos;
		}
		
		String cadena = "N" + super.identificador + " [label = \" Op: "+ etiqueta + "\n Var:";
		
		for (int i=0; i<variables.size();i++) {
			cadena += " " + variables.get(i).getDescription();
		}
		
		cadena += "\"]";
		
		stream.println(cadena);
		
		for (int i=0; i<operandos.size();i++) {
			if (operandos.get(i) == null) {
				stream.println("N" + super.identificador + "-> null");
			} else {
				stream.println("N" + super.identificador + "-> " + operandos.get(i).toString());
				operandos.get(i).print(stream);
			}
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public String toString() {
		if (super.identificador == 0) {
			super.numnodos++;
			super.identificador = super.numnodos;
		}
		
		String cadena = "N" + super.identificador;
		
		return cadena;
	}
	
}
