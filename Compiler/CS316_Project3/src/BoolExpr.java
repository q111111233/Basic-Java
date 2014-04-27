import java.util.*;

abstract class BoolExpr extends ListExp
{
	abstract Val Eval(HashMap<String,Val> state);
	void printParseTree(String indent)
	{
		super.printParseTree(indent);
		String indent2 = indent+"  ";
		LexAnalyzer.displayln(indent2 + indent2.length() + " <bool expr>");
	}
}