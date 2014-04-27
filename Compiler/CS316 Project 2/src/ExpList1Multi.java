
public class ExpList1Multi extends AbExpList1
{
	ExpList1Single els;
	AbExpList1 el;
	
	public ExpList1Multi (ExpList1Single els, AbExpList1 el)
	{
		this.els = els;
		this.el = el;
	}
	
	public void printParseTree(String indent) 
	{
		els.printParseTree(indent);
		el.printParseTree(indent);
	}

}
