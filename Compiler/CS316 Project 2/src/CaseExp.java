
public class CaseExp extends AbCaseList
{
	Exp e1, e2;

	public CaseExp (Exp e1, Exp e2)
	{
		this.e1 = e1;
		this.e2 = e2;
	}

	public void printParseTree(String indent) 
	{
		Parser.displayln(indent + indent.length() + " <case exp>");
		if (e1 == null)
			Parser.displayln((indent+=" ") + indent.length() + " else");
		else
			e1.printParseTree((indent+= " "));
		e2.printParseTree((indent));
	}

}
