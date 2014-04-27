
public class ExpList1Single extends AbExpList1
{
	Exp e1;
	
	public ExpList1Single (Exp e1)
	{
		this.e1 = e1;
	}
	public void printParseTree(String indent) 
	{
		e1.printParseTree(indent);
	}

}
