import java.util.*;

class True extends Exp
{	
	void printParseTree(String indent)
	{
		super.printParseTree(indent);
		String indent1 = indent+" ";
		LexAnalyzer.displayln(indent1 + indent1.length() + " true");
	}
	
	Val Eval(HashMap<String,Val> state)
	{
		return new BoolVal(true);
	}
}