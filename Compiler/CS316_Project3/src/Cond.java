import java.util.*;

class Cond extends ListExp
{
	CaseList caseList;
	
	Cond(CaseList c)
	{
		caseList = c;
	}
	
	void printParseTree(String indent)
	{		
		super.printParseTree(indent);

		String indent2 = indent+"  ";
		
		LexAnalyzer.displayln(indent2 + indent2.length() + " cond");
		LexAnalyzer.displayln(indent2 + indent2.length() + " <case list>");		
		caseList.printParseTree(indent2+" ");
	}


	Val Eval(HashMap<String, Val> state) 
	{

		return null;
	}


}