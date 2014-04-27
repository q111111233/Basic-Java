import java.util.*;

class Not extends BoolExpr
{
	Exp exp;

	Not(Exp e)
	{
		exp = e;
	}

	void printParseTree(String indent)
	{
		super.printParseTree(indent);

		String indent3 = indent+"   ";
		LexAnalyzer.displayln(indent3 + indent3.length() + " not");
		exp.printParseTree(indent3);
	}

	Val Eval(HashMap<String, Val> state) 
	{
		//Make it work like a like list.
		
		Val e = exp.Eval(state);
		Class eClass = e.getClass();

		if ( eClass != BoolVal.class )
		{
			return null;
		}
		else // termClass == FloatVal.class
		{
			((BoolVal)e).val = !((BoolVal)e).val;
		}
		return e;

	}

}