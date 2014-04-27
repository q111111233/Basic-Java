import java.util.*;

class ElseCase extends CaseExp
{
	Exp exp;
	
	ElseCase(Exp e)
	{
		exp = e;
	}
	
	void printParseTree(String indent)
	{		
		super.printParseTree(indent);

		String indent1 = indent+" ";
				
		LexAnalyzer.displayln(indent1 + indent1.length() + " else");
		exp.printParseTree(indent1);
	}
}