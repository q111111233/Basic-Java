
public class FunName 
{
	String id;
	public FunName (String id)
	{
		this.id = id;
	}
	public void printParseTree(String indent) 
	{
		Parser.displayln(indent+indent.length() + " <fun name> " + id);
		
	}
	
}
