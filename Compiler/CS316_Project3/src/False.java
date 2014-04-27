import java.util.*;

class False extends Exp
{	
	void printParseTree(String indent)
	{
		super.printParseTree(indent);
		String indent1 = indent+" ";
		LexAnalyzer.displayln(indent1 + indent1.length() + " false");
	}
	
	Val Eval(HashMap<String,Val> state)
	{
		return new BoolVal(false);
	}
}