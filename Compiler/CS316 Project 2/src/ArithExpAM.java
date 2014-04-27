
public class ArithExpAM extends AbArithExp
{
	AbExpList ael;
	String sign;
	public ArithExpAM (String sign , AbExpList ael)
	{
		this.sign = sign;
		this.ael = ael;
	}
	
	public void printParseTree(String indent) 
	{
		super.printParseTree(indent);
		Parser.displayln((indent += "   ") + indent.length() + " " + sign);
		ael.printParseTree(indent);
	}

}
