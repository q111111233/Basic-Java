import java.util.*;

class Sub extends ArithExpr
{
	ExpList expList;

	Sub(ExpList e)
	{
		expList = e;
	}

	void printParseTree(String indent)
	{
		super.printParseTree(indent);

		String indent3 = indent+"   ";
		LexAnalyzer.displayln(indent3 + indent3.length() + " -");
		expList.printParseTree(indent3);
	}

	@Override
	Val Eval(HashMap<String, Val> state) 
	{
		//Make it work like a like list.
		ExpList t = expList;
		
		Val i = t.exp.Eval(state);
		t = t.expList;

		if (t == null)
		{
			if (i.getClass() == IntVal.class)
				((IntVal)i).val *= -1;
			else if (i.getClass() == IntVal.class)
				((FloatVal)i).val *= -1.0;
			return i;
		}
		while (t!= null)
		{

			Val e = t.exp.Eval(state);

			Class iClass = i.getClass();
			Class eClass = e.getClass();
			/*
			if (eClass == IntVal.class)
			{
				((IntVal)e).val = ((IntVal)e).val*-1;
			}
			else
			{
				((FloatVal)e).val = ((FloatVal)e).val*-1;
			}
			 */
			
			if (eClass != IntVal.class && eClass != FloatVal.class)
			{
				return e;
			}

			if ( iClass == IntVal.class && eClass == IntVal.class )
			{
				
				((IntVal)i).val = ((IntVal)i).val - ((IntVal)e).val;
				//return i;
			}
			else if ( iClass == IntVal.class ) // eClass == FloatVal.class
			{
				((FloatVal)e).val = ((IntVal)i).val - ((FloatVal)e).val;
				i = e;
			}
			else // termClass == FloatVal.class
			{
				((FloatVal)i).val = i.floatVal() - e.floatVal();
				//return i;
			}
			t = t.expList;
		}



		return i;
	}
}