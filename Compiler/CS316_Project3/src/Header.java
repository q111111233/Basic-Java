import java.util.*;

class Header
{
	String funName;
	ParameterList parameterList;
	
	Header(String s, ParameterList p)
	{
		funName = s;
		parameterList = p;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		String indent2 = indent1 + " ";
		
		LexAnalyzer.displayln(indent + indent.length() + " <header>");
		LexAnalyzer.displayln(indent1 + indent1.length() + " <fun name> " + funName);
		if ( parameterList != null )	
			parameterList.printParseTree(indent2);
	}
}