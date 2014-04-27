
public class ExpBool extends Exp
{
	String b;
	
	public ExpBool (String t)
	{
		this.b = t;
	}
	
	public void printParseTree(String indent)
	{
		super.printParseTreeHeader(indent);
		Parser.displayln((indent+=" ") + indent.length() + " " + b);
	}
}
