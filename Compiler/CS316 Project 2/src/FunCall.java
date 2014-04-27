
public class FunCall extends ListExpInside
{
	FunName fn ;
	AbExpList el;
	public FunCall (FunName fn , AbExpList el)
	{
		this.fn = fn;
		this.el = el;
	}
	
	public void printParseTree (String indent)
	{
		super.printParseTree(indent);
		Parser.displayln((indent+="  ") + indent.length() + " <fun call>");
		Parser.displayln((indent+=" ") + indent.length() + " "+ fn.id);
		this.el.printParseTree((indent));
	}
}
