import java.util.*;

abstract class Exp
{
	void printParseTree(String indent)
	{
		LexAnalyzer.displayln(indent + indent.length() + " <exp>");
	}

	abstract Val Eval(HashMap<String, Val> state) ;

}