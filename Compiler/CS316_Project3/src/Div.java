import java.util.*;

class Div extends ArithExpr
{
	ExpList expList;
	
	Div(ExpList e)
	{
		expList = e;
	}
	
	void printParseTree(String indent)
	{
		super.printParseTree(indent);
		
		String indent3 = indent+"   ";
		LexAnalyzer.displayln(indent3 + indent3.length() + " /");
		expList.printParseTree(indent3);
	}

	@Override
	Val Eval(HashMap<String, Val> state) 
	{
		return null;
	}
}