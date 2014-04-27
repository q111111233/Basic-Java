
public class Header 
{
	FunName fn;
	AbParameterList pl;
	public Header (FunName fn, AbParameterList pl)
	{
		this.fn = fn;
		this.pl = pl;
	}
	public void printParseTree(String indent) 
	{
		Parser.displayln(indent + indent.length() + " <header> ");
		fn.printParseTree(indent+ " ");
		if (!this.pl.getClass().getSimpleName().equalsIgnoreCase("EmptyParameterList"))
		{
			AbParameterList.printParseTreeHeader(indent+ "  ");
			pl.printParseTree(indent+ "   ");
		}
	}
}
