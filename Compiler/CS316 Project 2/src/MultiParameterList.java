
public class MultiParameterList extends AbParameterList
{
	ParameterListID id;
	AbParameterList pl;
	
	public MultiParameterList (ParameterListID id, AbParameterList pl)
	{
		this.id = id;
		this.pl = pl;
	}
	
	public void printParseTree(String indent) 
	{
		id.printParseTree(indent);
		pl.printParseTree(indent);
	}
	
}
