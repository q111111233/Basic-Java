import java.util.*;

class Mul extends ArithExpr
{
	ExpList expList;
	
	Mul(ExpList e)
	{
		expList = e;
	}
	
	void printParseTree(String indent)
	{
		super.printParseTree(indent);
		
		String indent3 = indent+"   ";
		LexAnalyzer.displayln(indent3 + indent3.length() + " *");
		if ( expList != null )
			expList.printParseTree(indent3);
	}

	@Override
	Val Eval(HashMap<String, Val> state) 
	{
		return null;
	}
}