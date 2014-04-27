import java.util.*;

class Id extends Exp
{
	String id;
	
	Id(String s)
	{
		id = s;
	}
	
	void printParseTree(String indent)
	{
		super.printParseTree(indent);
		String indent1 = indent+" ";
		LexAnalyzer.displayln(indent1 + indent1.length() + " " + id);
	}
	
	Val Eval(HashMap<String, Val> state) 
	{
		//Make it work like a like list.

		return state.get(id);
	}
}