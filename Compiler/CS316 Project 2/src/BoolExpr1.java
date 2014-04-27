
public class BoolExpr1 extends AbBoolExpr
{
	AbExpList a;
	String b; 
	public BoolExpr1 (AbExpList a, String b)
	{
		this.a = a;
		this.b = b;
	}

	public void printParseTree(String indent) 
	{
		super.printParseTree(indent);
		Parser.displayln((indent+=" ") + indent.length ()  +  b );
		a.printParseTree(indent);
	}
	
}
