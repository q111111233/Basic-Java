import java.io.*;
import java.util.*;

public abstract class Interpreter extends Parser
{
	public static LinkedHashMap<String, Val> varState = new LinkedHashMap<String, Val>(); 
	// program state, i.e., virtual memory for variables


	public static void main(String argv[])
	{
		// argv[0]: input file containing the source code of an assignment list
		// argv[1]: output file to display the parse tree

		setLex( argv[0], argv[1] );

		getToken();
		FunDefList funDefList = funDefList();
		if ( ! t.isEmpty() )
			displayln(t + "  -- unexpected symbol");
		else if ( ! syntaxErrorFound )
		{
			funDefList.printParseTree("");
			funDefList.M(varState);
			
			
			Set<String> key = varState.keySet();
			for (String k:key)
			{
				System.out.println(k + "\n" + varState.get(k) + '\n');
			}

		}
		closeIO();
	}
}