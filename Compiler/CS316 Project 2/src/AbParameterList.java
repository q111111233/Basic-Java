
public abstract class AbParameterList 
{
	abstract void printParseTree(String indent);
	
	
	
	public static void printParseTreeHeader(String indent)
	{

		Parser.displayln(indent + indent.length() + "<parameter list>");
	}
}
