import java.util.*;

class FunDefList
{
	FunDef funDef;
	FunDefList funDefList;
	
	FunDefList(FunDef f, FunDefList fl)
	{
		funDef = f;
		funDefList = fl;
	}
	
	/*void printParseTree(String indent)
	{
		funDef.printParseTree(indent);
		if ( funDefList != null )
			funDefList.printParseTree(indent);
	}*/

	void printParseTree(String indent)
	{
		FunDefList p = this;

		do
		{
			p.funDef.printParseTree(indent);
			p = p.funDefList;
		} while ( p != null );
	}
	
	void M(HashMap<String,Val> state) // function to interpret this list of multiple assignments
	{
		funDef.M(state);
		
		if (funDefList != null)
			funDefList.M(state);
	}
	
}