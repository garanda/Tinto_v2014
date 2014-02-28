package GDA;

import java.io.PrintStream;

public abstract class NodoGDA {
	
	protected boolean variableViva = false;
	
	protected int identificador;
	
	public static int numnodos = 0;
	
	public abstract void print(PrintStream stream);
	
	public abstract String toString();
	
	public abstract boolean expresionComun(NodoGDA n);
	
	public void setVariableViva(boolean viva) { variableViva = viva;}
	
	public boolean getVariableViva() {return variableViva;}
}
