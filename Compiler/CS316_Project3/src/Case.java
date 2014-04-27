import java.util.*;

class Case extends CaseExp
{
	Exp exp1;
	Exp exp2;
	
	Case(Exp e1, Exp e2)
	{
		exp1 = e1;
		exp2 = e2;
	}
	
	void printParseTree(String indent)
	{		
		super.printParseTree(indent);

		String indent1 = indent+" ";
				
		exp1.printParseTree(indent1);
		exp2.printParseTree(indent1);
	}
}