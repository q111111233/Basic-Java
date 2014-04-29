import java.util.*;

class FunCall extends ListExp
{
	String funName;
	ExpList expList;
	
	FunCall(String s, ExpList e)
	{
		funName = s;
		expList = e;
	}
	
	void printParseTree(String indent)
	{
		super.printParseTree(indent);

		String indent2 = indent+"  ";
		String indent3 = indent2+" ";
		
		LexAnalyzer.displayln(indent2 + indent2.length() + " <fun call>");
		LexAnalyzer.displayln(indent3 + indent3.length() + " " + funName);
		if ( expList != null )
			expList.printParseTree(indent3);
	}
	
	Val Eval(HashMap<String, Val> state) 
	{
		if (Parser.expData.get(funName) == null)
		{
			return new ErrVal("Error: undefined function: " + funName);
		}
		else
		{
			return Parser.expData.get(funName).Eval(state);	 
		}
	}
}