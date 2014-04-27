
public abstract class AbBoolExpr extends ListExpInside
{
	public void printParseTree(String indent)
	{
		Parser.displayln(indent + indent.length() + "<list exp>");
		Parser.displayln( (indent +=" ") + indent.length() + "<bool expr>");
	}
	
}
