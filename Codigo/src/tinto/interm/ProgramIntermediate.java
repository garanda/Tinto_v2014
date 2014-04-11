package tinto.interm;

import java.util.*;
import tinto.code.*;


@SuppressWarnings("rawtypes")
public class ProgramIntermediate {
	
	InstructionIntermed[] Lista;
	ProgramCodification PC;
	
	public ProgramIntermediate(ProgramCodification pc) {
		this.PC = pc;
		List<InstructionIntermed> Aux = new LinkedList<InstructionIntermed>();	
		getInstructions(pc.getMainLibraryCodification(),Aux);
		Iterator<LibraryCodification> it = pc.getLibrariesCodification().iterator();
		while (it.hasNext())
			getInstructions(it.next(),Aux);		
		Lista = new  InstructionIntermed[Aux.size()];
		Aux.toArray(Lista);
	}
	
	public void print() {
		for (int i=0;i<Lista.length;i++) {
			System.out.println(Lista[i].toString());
		}
	}
	
	public InstructionIntermed[] getListInstructions() {
		return this.Lista;
	}
	
	
	private void getInstructions(LibraryCodification lc, List<InstructionIntermed> Aux) {
		for (int i=0;i<lc.getMethodCodifications().length;i++) { 
			MethodCodification mc = lc.getMethodCodifications()[i];
			InstructionIntermed ci = new InstructionIntermed(CodeConstants.LABEL, new CodeLabel(mc.getMethodLabel()), null, null);
			Aux.add(ci);
			CodeInstruction[] cil = mc.getCodeInstructionList().getList();
			for (int j=0;j<cil.length;j++)
				Aux.add(new InstructionIntermed(cil[j]));
			Aux.add(new InstructionIntermed(CodeConstants.LABEL, new CodeLabel(".end\t" + mc.getMethodLabel()), null, null));
		}
	}
	
	public void setVisited() {
		
//		Hay que implementar el recorrido en un grafo.... 
		
		
		
		List Abiertos = new LinkedList();
		Abiertos.add(0);
		
		while (!Abiertos.isEmpty()) {
			int Actual = (Integer) Abiertos.remove(0);
			
			if (Actual>=Lista.length || Lista[Actual].visited)
				continue;
			Lista[Actual].visited = true;
			
			switch(Lista[Actual].getKind()) {			
			case CodeConstants.CALL:
				Abiertos.add(getLabel(Lista[Actual].getSource1().getDescription()));
				Abiertos.add(Actual+1);
				break;

			case CodeConstants.JMP1:
			case CodeConstants.JMPEQ:
			case CodeConstants.JMPGE:
			case CodeConstants.JMPGT:
			case CodeConstants.JMPLE:
			case CodeConstants.JMPLT:
			case CodeConstants.JMPNE:
				Abiertos.add(getLabel(Lista[Actual].getTarget().getDescription()));
				Abiertos.add(Actual+1);
				break;

			case CodeConstants.JUMP:
				Abiertos.add(getLabel(Lista[Actual].getTarget().getDescription()));
				break;
				
			case CodeConstants.RETURN:
				break;
				
			case CodeConstants.LABEL:
				if (!Lista[Actual].getTarget().getDescription().startsWith(".end"))
					Abiertos.add(Actual+1);
				break;
				
			default:
				Abiertos.add(Actual+1);				
			}

		}
		
	}
	
	
	private int getLabel(String _label) {
		int i=0;
		boolean encontrado = false;
		while(!encontrado && i<Lista.length) {
			if (Lista[i].getKind() == CodeConstants.LABEL && Lista[i].getTarget().getDescription().equalsIgnoreCase(_label)) {
				encontrado = true;
				return i;
			} else {
				i++;
			}
		}
		
		return -1;
	}
	
	
	
	
	
}
