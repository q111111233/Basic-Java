
public class ExpListMulti extends AbExpList
{
	ExpListSingle els;
	AbExpList ael;

	public ExpListMulti(ExpListSingle els, AbExpList ael)
	{
		this.els = els;
		this.ael = ael;
	}
	
	public void printParseTree(String indent) 
	{
		els.printParseTree (indent);
		ael.printParseTree (indent);
	}

}
