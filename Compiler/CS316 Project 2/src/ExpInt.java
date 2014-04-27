
public class ExpInt extends Exp
{
	int i;
	public ExpInt (int i)
	{
		this.i = i;
	}
	@Override
	void printParseTree(String indent) 
	{
		super.printParseTreeHeader(indent);
		Parser.displayln((indent+= " ") + indent.length() +" " +  i);
	}
}
