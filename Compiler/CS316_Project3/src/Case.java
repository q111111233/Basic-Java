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

	@Override
	Val Eval(HashMap<String, Val> state) 
	{
		Val v1 = exp1.Eval(state);
		if (v1.getClass() != BoolVal.class)
		{
			return new ErrVal ("Error: boolean condition of \"cond\" evaluated to non-boolean value: " + v1.toString());
		}
		
		if ( ((BoolVal)v1).val)
		{
			return exp2.Eval(state);
		}
		else
		{
			return null;
		}
	}
}