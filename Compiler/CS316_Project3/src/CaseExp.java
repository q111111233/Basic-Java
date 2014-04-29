import java.util.*;

abstract class CaseExp
{
	void printParseTree(String indent)
	{
		LexAnalyzer.displayln(indent + indent.length() + " <case exp>");
	}

	abstract Val Eval(HashMap<String, Val> state) ;
}