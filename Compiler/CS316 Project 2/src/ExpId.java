
public class ExpId extends Exp
{
	String id;

	public ExpId(String id)
	{
		this.id = id;
	}

	void printParseTree(String indent) 
	{
		if (id.equalsIgnoreCase("else"))
		{
			Parser.displayln((indent) + indent.length() + " " + id);
		}
		else
		{
			super.printParseTreeHeader(indent);
			Parser.displayln((indent+=" ") + indent.length() + " " + id);
		}
	}
}
