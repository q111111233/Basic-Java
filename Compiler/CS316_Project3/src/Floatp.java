import java.util.*;

class Floatp extends Exp
{
	float floatElem;
	
	Floatp(float f)
	{
		floatElem = f;
	}
	
	void printParseTree(String indent)
	{
		super.printParseTree(indent);
		String indent1 = indent+" ";
		LexAnalyzer.displayln(indent1 + indent1.length() + " " + floatElem);
	}
	
	Val Eval(HashMap<String,Val> state)
	{
		return new FloatVal(floatElem);
	}
}