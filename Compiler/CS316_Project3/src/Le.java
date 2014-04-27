import java.util.*;

class Le extends CompExpr
{
	ExpList expList;
	
	Le(ExpList e)
	{
		expList = e;
	}
	
	void printParseTree(String indent)
	{
		super.printParseTree(indent);
		
		String indent3 = indent+"   ";
		LexAnalyzer.displayln(indent3 + indent3.length() + " <=");
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
		}
		
		while (t!= null)
		{
			Val e = t.exp.Eval(state);

			Class eClass = e.getClass();
			Class iClass = i.getClass();

			if ( iClass == IntVal.class && eClass == IntVal.class )
			{
				if (((IntVal)i).val  >= ((IntVal)e).val)
				{
					return new BoolVal (false);
				}
			}
			else if ( iClass == IntVal.class ) // eClass == FloatVal.class
			{
				if (((IntVal)i).val  >=  ((FloatVal)e).val)
				{
					return new BoolVal (false);
				}
			}
			else // termClass == FloatVal.class
			{
				if (((FloatVal)i).val  >=  ((FloatVal)e).val)
				{
					return new BoolVal (false);
				}
			}
			t = t.expList;
			e = i;
		}
		return new BoolVal (true);
	}
}