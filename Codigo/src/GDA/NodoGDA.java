package GDA;

import java.io.PrintStream;

public abstract class NodoGDA {
	
	public abstract void print(PrintStream stream);
	
	public abstract String toString();
	
	public abstract boolean expresionComun(NodoGDA n);
	
}
