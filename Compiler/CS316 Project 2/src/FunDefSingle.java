

public class FunDefSingle extends AbFunDefList
{
	Header h;
	Exp e;
	
	//Need exp here Implement later.
	public FunDefSingle (Header h, Exp e)
	{
		this.h = h;
		this.e = e;
	}
	
	public void printParseTree(String indent)
	{			

		Parser.displayln(indent + indent.length() + " <fun def>" );
		h.printParseTree(indent+" ");
		e.printParseTree(indent+" ");
	}
}
