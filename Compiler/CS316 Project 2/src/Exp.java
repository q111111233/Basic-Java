
public abstract class Exp {

	abstract void printParseTree(String indent);

	static void printParseTreeHeader(String indent)
	{
		Parser.displayln(indent + indent.length () + " <exp>");
	}
}
