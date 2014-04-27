
public class ParameterListID extends AbParameterList
{
	String id;
	
	public ParameterListID (String id)
	{
		this.id = id;
	}
	
	public void printParseTree(String indent) 
	{
		Parser.displayln(indent + indent.length() + " "+ id);
	}
}
