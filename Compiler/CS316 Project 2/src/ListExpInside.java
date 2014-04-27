
public abstract class ListExpInside extends Exp
{
	public void printParseTree(String indent)
	{
		super.printParseTreeHeader(indent);
		Parser.displayln((indent+=" ") + indent.length() + " <list exp> " );
	}
}
