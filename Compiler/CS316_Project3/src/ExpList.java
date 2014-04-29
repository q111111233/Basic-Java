import java.util.*;

class ExpList
{
	Exp exp;
	ExpList expList;
	
	ExpList(Exp e, ExpList el)
	{
		exp = e;
		expList = el;
	}
	
	void printParseTree(String indent)
	{
		ExpList p = this;
		
		do
		{
			p.exp.printParseTree(indent);
			p = p.expList;
		} while ( p != null );
	}
	
	Val Eval(HashMap<String,Val> state)
	{
		Val i = exp.Eval(state);
		//expList.Eval(state);
		System.out.println("Here");
		return i;
	}
}