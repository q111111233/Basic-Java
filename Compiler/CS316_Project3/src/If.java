import java.util.*;

class If extends ListExp
{
	Exp exp1;
	Exp exp2;
	Exp exp3;
	
	If(Exp e1, Exp e2, Exp e3)
	{
		exp1 = e1;
		exp2 = e2;
		exp3 = e3;
	}
	
	void printParseTree(String indent)
	{		
		super.printParseTree(indent);

		String indent2 = indent+"  ";
		
		LexAnalyzer.displayln(indent2 + indent2.length() + " if");		
		exp1.printParseTree(indent2);
		exp2.printParseTree(indent2);
		exp3.printParseTree(indent2);
	}
	
	Val Eval(HashMap<String, Val> state) 
	{
		Val v1 = this.exp1.Eval(state);
		if (v1.getClass() != BoolVal.class)
		{
			return new ErrVal ("Error: boolean condition of \"if\" evaluated to non-boolean value:condition " + v1.toString());
		}
		else if (((BoolVal)v1).val == true)
		{
			return exp2.Eval(state);
		}
		else
		{
			return exp3.Eval(state);
		}
	
	}
}