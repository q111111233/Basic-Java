
public class LEIIf extends ListExpInside 
{
	Exp e1;
	Exp e2;
	Exp e3;

	public LEIIf(Exp e1, Exp e2, Exp e3) 
	{
		this.e1 = e1;
		this.e2 = e2;
		this.e3 = e3;
	}

	public void printParseTree(String indent) 
	{
		super.printParseTree(indent);


		Parser.displayln((indent+="  ") + indent.length() + " if");

		e1.printParseTree(indent);
		e2.printParseTree(indent);
		e3.printParseTree(indent);
	}
}
