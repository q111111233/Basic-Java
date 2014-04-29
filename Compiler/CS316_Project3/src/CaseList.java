import java.util.*;

class CaseList
{
	CaseExp caseExp;
	CaseList caseList;

	CaseList(CaseExp ce, CaseList cl)
	{
		caseExp = ce;
		caseList = cl;
	}

	void printParseTree(String indent)
	{
		CaseList p = this;

		do
		{
			p.caseExp.printParseTree(indent);
			p = p.caseList;
		} while ( p != null );
	}

	public Val Eval(HashMap<String, Val> state) 
	{

		CaseList t = caseList;
		Val i = caseExp.Eval(state);
		if (i == null && t == null)
		{
			return new ErrVal ("");
		}
		else if (i != null)
		{
			return i;
		}
		while (t != null)
		{
			i = t.caseExp.Eval(state);
			
			if (i != null)
			{
				return i;
			}
			t = t.caseList;
			if (caseExp.Eval(state) == null && t == null)
			{
				return new ErrVal ("");
			}
		}
		return new ErrVal ("");
		

	}
}