
public class MultiCaseList extends AbCaseList
{
	AbCaseList acl; 
	CaseExp ce;
	public MultiCaseList (CaseExp ce ,AbCaseList acl)
	{
		this.acl = acl;
		this.ce = ce;
	}
	
	public void printParseTree(String indent) 
	{
		acl.printParseTree(indent);
		ce.printParseTree(indent);
	}

}
