
public class ArithExpSD extends AbArithExp
{
	String sign;
	AbExpList1 el1;
	public ArithExpSD (String sign, AbExpList1 el1)
	{
		this.sign = sign;
		this.el1 = el1;
	}
	
	public void printParseTree(String indent) 
	{
		super.printParseTree(indent);
		Parser.displayln((indent += "   ") + indent.length() + sign);
		el1.printParseTree(indent);
	}

}
