import java.util.*;

class Add extends ArithExpr
{
	ExpList expList;
	
	Add(ExpList e)
	{
		expList = e;
	}
	
	void printParseTree(String indent)
	{
		super.printParseTree(indent);
		
		String indent3 = indent+"   ";
		LexAnalyzer.displayln(indent3 + indent3.length() + " +");
		if ( expList != null )
			expList.printParseTree(indent3);
	}

	@Override
	Val Eval(HashMap<String, Val> state) 
	{
		//Make it work like a like list.
		ExpList t = expList;
		Val i = new IntVal (0);
		while (t!= null)
		{
			Val e = t.exp.Eval(state);
			Class eClass = e.getClass();
			Class iClass = i.getClass();
			
			if (eClass != IntVal.class && eClass != FloatVal.class)
			{
				return e;
			}
			
			if ( iClass == IntVal.class && eClass == IntVal.class )
			{
				((IntVal)i).val = ((IntVal)i).val + ((IntVal)e).val;
				//return i;
			}
			else if ( iClass == IntVal.class ) // eClass == FloatVal.class
			{
				((FloatVal)e).val = ((IntVal)i).val + ((FloatVal)e).val;
				i = e;
			}
			else // termClass == FloatVal.class
			{
				((FloatVal)i).val = i.floatVal() + e.floatVal();
				//return i;
			}
			t = t.expList;
		}
		
			

		return i;
	}
	
	
}