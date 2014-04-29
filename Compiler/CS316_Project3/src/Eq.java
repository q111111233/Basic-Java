import java.util.*;

class Eq extends CompExpr
{
	ExpList expList;

	Eq(ExpList e)
	{
		expList = e;
	}

	void printParseTree(String indent)
	{
		super.printParseTree(indent);

		String indent3 = indent+"   ";
		LexAnalyzer.displayln(indent3 + indent3.length() + " =");
		if ( expList != null )
			expList.printParseTree(indent3);
	}

	Val Eval(HashMap<String, Val> state) 
	{
		//Make it work like a like list.
		ExpList t = expList;
		Val i = null;
		if (t != null)
		{
			i = t.exp.Eval(state);
			t = t.expList;
			if ( i.getClass() == ErrVal.class)
			{
				return i ;
			}
			if (i.getClass() == BoolVal.class) 
			{
				return new ErrVal ("== operator cannot be applied to non-number: " + i.toString());
			}
		}
		
		while (t!= null)
		{
			Val e = t.exp.Eval(state);

			Class eClass = e.getClass();
			Class iClass = i.getClass();

			if (e.getClass() == BoolVal.class) 
			{
				return new ErrVal ("== operator cannot be applied to non-number: " + e.toString());
			}
			
			if ( iClass == IntVal.class && eClass == IntVal.class )
			{
				if (((IntVal)i).val != ((IntVal)e).val)
				{
					return new BoolVal (false);
				}
			}
			else if ( iClass == IntVal.class ) // eClass == FloatVal.class
			{
				if (((IntVal)i).val !=  ((FloatVal)e).val)
				{
					return new BoolVal (false);
				}
			}
			else // termClass == FloatVal.class
			{
				if (((FloatVal)i).val !=  ((FloatVal)e).val)
				{
					return new BoolVal (false);
				}
			}
			t = t.expList;
		}
		return new BoolVal (true);
	}
}