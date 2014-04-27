
public class ExpListSingle 
{
	Exp e;
	public ExpListSingle (Exp e)
	{
		this.e = e;
	}
	
	public void printParseTree (String indent)
	{
		e.printParseTree(indent);
	}
}
