import java.util.*;

class ParameterList
{
	String id;
	ParameterList parameterList;
	
	ParameterList(String s, ParameterList p)
	{
		id = s;
		parameterList = p;
	}
	
	/*void printParseTree(String indent)
	{
		LexAnalyzer.displayln(indent + indent.length() + " " + id);
		if ( parameterList != null )
			parameterList.printParseTree(indent);
	}*/

	void printParseTree(String indent)
	{
		LexAnalyzer.displayln(indent + indent.length() + " <parameter list>");
		String indent1 = indent+" ";

		ParameterList p = this;

		do
		{
			LexAnalyzer.displayln(indent1 + indent1.length() + " " + p.id);
			p = p.parameterList;
		} while ( p != null );
	}
}