import java.util.*;

class Int extends Exp
{
	int intElem;
	
	Int(int i)
	{
		intElem = i;
	}
	
	void printParseTree(String indent)
	{
		super.printParseTree(indent);
		String indent1 = indent+" ";
		LexAnalyzer.displayln(indent1 + indent1.length() + " " + intElem);
	}
	
	Val Eval(HashMap<String,Val> state)
	{
		return new IntVal(intElem);
	}
}