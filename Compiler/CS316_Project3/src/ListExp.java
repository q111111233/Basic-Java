import java.util.*;

abstract class ListExp extends Exp
{
	void printParseTree(String indent)
	{
		super.printParseTree(indent);
		String indent1 = indent+" ";
		LexAnalyzer.displayln(indent1 + indent1.length() + " <list exp>");
	}
	
	
}