package tinto.interm;

import tinto.code.CodeAddress;
import tinto.code.CodeInstruction;

public class InstructionIntermed extends CodeInstruction {
	
	
	boolean visited;
	
	public InstructionIntermed(int kind, CodeAddress target, CodeAddress source1, CodeAddress source2) {
		super(kind, target, source1, source2);
		visited = false;
	}
	
	public InstructionIntermed(CodeInstruction ci) {
		super(ci.getKind(),ci.getTarget(), ci.getSource1(), ci.getSource2());
		visited = false;
	}
	
	public String toString() {
		
		if (visited) 
			return "(x) " + super.toString();
		else
			return "( ) " + super.toString();
		
	}
	

}
