
public class ExpFloat extends Exp
{
	float f;
	
	public ExpFloat(String f)
	{
		this.f = Float.parseFloat(f);
	}

	void printParseTree(String indent) 
	{
		super.printParseTreeHeader(indent);
		Parser.displayln((indent+=" ") + indent.length() + " " + f);
	}
}
