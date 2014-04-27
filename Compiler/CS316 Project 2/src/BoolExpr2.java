
public class BoolExpr2 extends AbBoolExpr
{
	Exp a;
	public BoolExpr2 (Exp a)
	{
		this.a = a;
	}

	public void printParseTree(String indent) 
	{
		super.printParseTree(indent);
		Parser.displayln((indent+" ") + indent.length () + "not" );
		a.printParseTree(indent);
	}
	
}
