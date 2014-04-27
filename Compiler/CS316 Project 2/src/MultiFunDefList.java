
public class MultiFunDefList extends AbFunDefList{
	FunDefSingle fd;
	AbFunDefList fdl;

	public MultiFunDefList(FunDefSingle fd, AbFunDefList fdl)
	{
		this.fd = fd;
		this.fdl = fdl;
	}
	
	public void printParseTree(String indent)
	{
		fd.printParseTree(indent);
		fdl.printParseTree(indent);
	}
}
