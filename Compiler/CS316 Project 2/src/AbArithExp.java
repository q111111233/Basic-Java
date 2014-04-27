
public abstract class AbArithExp extends ListExpInside
{
	public void printParseTree(String indent)
	{
		super.printParseTree(indent);
		Parser.displayln((indent+="  ") + indent.length() + " <arith exp>");
	}
}
