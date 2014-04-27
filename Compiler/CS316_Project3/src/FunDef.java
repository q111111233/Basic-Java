import java.util.*;

class FunDef
{
	Header header;
	Exp exp;
	
	FunDef(Header h, Exp e)
	{
		header = h;
		exp = e;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		
		LexAnalyzer.displayln(indent + indent.length() + " <fun def>");		
		header.printParseTree(indent1);
		exp.printParseTree(indent1);
	}

	void M(HashMap<String, Val> state) 
	{
		Val eVal = exp.Eval(state); 
		if ( eVal != null )
			state.put(header.funName, eVal); 
	}
}