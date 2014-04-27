
public class LEICond extends ListExpInside
{
	AbCaseList cl;
		
	public LEICond (AbCaseList cl)
	{
		this.cl = cl;
	}
	
	public void printParseTree(String indent) 
	{
		super.printParseTree(indent);
		Parser.displayln((indent+="  ") + indent.length() + " cond");
		Parser.displayln((indent) + indent.length() + " <case list>");
		cl.printParseTree(indent+=" ");
	}
	
}
