import java.util.*;

class And extends BoolExpr
{
	ExpList expList;
	
	And(ExpList e)
	{
		expList = e;
	}
	
	void printParseTree(String indent)
	{
		super.printParseTree(indent);
		
		String indent3 = indent+"   ";
		LexAnalyzer.displayln(indent3 + indent3.length() + " and");
		if ( expList != null )
			expList.printParseTree(indent3);
	}
	
	Val Eval(HashMap<String, Val> state) 
	{
		//Make it work like a like list.
		ExpList t = expList;
		Val i = new BoolVal (true);
		while (t!= null)
		{
			Val e = t.exp.Eval(state);
			
			Class eClass = e.getClass();
			
			
			if ( eClass != BoolVal.class )
			{
				return null;
			}
			else // termClass == FloatVal.class
			{
				((BoolVal)i).val = ((BoolVal)i).val && ((BoolVal)e).val;

			}
			t = t.expList;
		}
		return i;
	}
}