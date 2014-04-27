import java.util.*;

abstract class CompExpr extends ListExp
{
	void printParseTree(String indent)
	{
		super.printParseTree(indent);
		String indent2 = indent+"  ";
		LexAnalyzer.displayln(indent2 + indent2.length() + " <comp expr>");
	}
}