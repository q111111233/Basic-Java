public class CompExpr extends ListExpInside 
{
	String sign;
	AbExpList abl;
	public CompExpr (String type, AbExpList abl)
	{
		if (type.equalsIgnoreCase("Lt"))
			sign = "<";
		else if (type.equalsIgnoreCase("Le"))
			sign = "<=";
		else if (type.equalsIgnoreCase("Gt"))
			sign = ">";
		else if (type.equalsIgnoreCase("Ge"))
			sign = ">=";
		else if (type.equalsIgnoreCase("Eq"))
			sign = "=";
		
		this.abl = abl;
	}
	
	public void printParseTree(String indent) 
	{
		super.printParseTree(indent);
		Parser.displayln((indent+="  ") + indent.length() + "<comp expr>");
		Parser.displayln((indent+=" ") + indent.length() + " " + sign);
		abl.printParseTree(indent);
		
	}
}
