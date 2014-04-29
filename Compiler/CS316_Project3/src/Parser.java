/**

This class is a top-down, recursive-descent parser for the following syntactic categories:

<fun def list> --> <fun def> | <fun def> <fun def list> 
<fun def> --> "(" "define" <header> <exp> ")" 
<header> --> "(" <fun name> <parameter list> ")" 
<fun name> --> <id> 
<parameter list> --> epsilon | <id> <parameter list> 
<exp> --> <id> | <int> | <float> | <floatE> | "false" | "true" | <list exp>
<list exp> --> "(" <list exp inside> ")" 
<list exp inside> --> <if> | <cond> | <fun call> | <arith expr> | <bool expr> | <comp expr> 
<if> --> "if" <exp> <exp> <exp> 
<cond> --> "cond" <case list> 
<case list> --> <case exp> | <case exp> <case list> 
<case exp> --> "(" ( <exp> <exp> | "else" <exp> ) ")" 
<fun call> --> <fun name> <exp list> 
<arith expr> --> + <exp list> | * <exp list> | - <exp list1> | / <exp list1> 
<bool expr> --> "and" <exp list> | "or" <exp list> | "not" <exp> 
<comp expr> --> "<" <exp list> | "<=" <exp list> | ">" <exp list> | ">=" <exp list> | "=" <exp list> 
<exp list> --> epsilon | <exp> <exp list> 
<exp list1> --> <exp> | <exp> <exp list1>

The definitions of the tokens are given in the lexical analyzer class file "LexAnalyzer.java".
The following variables and functions of the "LexAnalyzer" class are used:

static String t // holds an extracted token
static State state // the current state of the finite automaton
static int getToken() // extracts the next token
static void display(String s)
static void displayln(String s)
static void setLex(String inFile, String outFile)
static void closeIO()

The program will display the parse tree in linearly indented form.
Each syntactic category name labeling a node is displayed on a separate line, 
prefixed with the integer i representing the node's depth and indented by i blanks. 
The string variable "indent" will keep track of the correct number of blanks for indentation and
will be passed to parse functions corresponding to syntactic categories.

**/

import java.io.*;
import java.util.*;

public abstract class Parser extends LexAnalyzer
{
	static False false_ = new False();
	static True true_  = new True();
	public static LinkedHashMap<String, Exp> expData = new LinkedHashMap<String, Exp>(); 
	
	static boolean syntaxErrorFound = false;
	
 
	public static FunDefList funDefList()
	
	// <fun def list> --> <fun def> | <fun def> <fun def list>
		
	{
		FunDef funDef = funDef();
		
		if ( state == State.LParen )
		{
			FunDefList funDefList = funDefList();
			return new FunDefList(funDef, funDefList);
		}
		else
			return new FunDefList(funDef, null);
	}

	public static FunDef funDef()

	// <fun def> --> "(" "define" <header> <exp> ")"

	{
		if ( state == State.LParen )
		{
			getToken();
			if ( state == State.Keyword_define )
			{
				getToken();
				Header header = header();
				Exp exp = exp();
				expData.put(header.funName, exp);
				if ( state == State.RParen )
				{
					getToken();
					return new FunDef(header, exp);
				}
				else
				{
					errorMsg(3);
					return null;
				}
			}
			else
			{
				errorMsg(2);
				return null;
			}
		}
		else
		{
			errorMsg(1);
			return null;
		}
	}

	public static Header header()

	// <header> --> "(" <fun name> <parameter list> ")" 
	// <fun name> --> <id> 

	{
		if ( state == State.LParen )
		{
			getToken();
			if ( state == State.Id )
			{
				String funName = t;
				getToken();
				ParameterList parameterList = parameterList();
				if ( state == State.RParen )
				{
					getToken();
					return new Header(funName, parameterList);
				}
				else
				{
					errorMsg(3);
					return null;
				}
			}
			else
			{
				errorMsg(4);
				return null;
			}
		}
		else
		{
			errorMsg(1);
			return null;
		}
	}

	public static ParameterList parameterList()

	// <parameter list> --> epsilon | <id> <parameter list> 

	{
		if ( state == State.Id )
		{
			String id = t;
			getToken();
			ParameterList parameterList = parameterList();
			return new ParameterList(id, parameterList);
		}		
		else // <parameter list> is epsilon
			return null;	
	}
	
	public static Exp exp()

	// <exp> --> <id> | <int> | <float> | <floatE> | "false" | "true" | <list exp>
	
	{
		switch ( state )
		{
			case Id:

				Id id = new Id(t);
				getToken();				
				return id;
				
			case Int:
				
				Int intElem = new Int(myParseInt(t));
				getToken();
				return intElem;
				
			case Float: case FloatE:
				
				Floatp floatElem = new Floatp(Float.parseFloat(t));
				getToken();
				return floatElem;

			case Keyword_false:

				getToken();
				return false_;

			case Keyword_true:

				getToken();
				return true_;

			case LParen:

				return listExp();
			
			default:

				errorMsg(6);
				return null;
		}

	} // end exp

	public static ListExp listExp()

	// <list exp> --> "(" <list exp inside> ")"

	{
		getToken();

		ListExp listExpInside = listExpInside();
		if ( state == State.RParen )
		{
			getToken();
			return listExpInside;
		}
		else
		{
			errorMsg(3);
			return null;
		}
	}

	public static ListExp listExpInside()
	
	// <list exp inside> --> <if> | <cond> | <fun call> | <arith expr> | <bool expr> | <comp expr>

	{
		switch ( state )
		{
		case Keyword_if:

			return if_();
		
		case Keyword_cond:
		
			return cond();

		case Id:

			return funCall();

		case Add: case Sub: case Mul: case Div:

			return arithExpr();

		case Keyword_and: case Keyword_or: case Keyword_not:			

			return boolExpr();

		case Lt: case Le: case Gt: case Ge: case Eq:

			return compExpr();

		default:
			errorMsg(5);
			return null;
		}
	} // end listExpInside

	public static If if_()

	// <if> --> "if" <exp> <exp> <exp>

	{
		getToken();
		Exp exp1 = exp();
		Exp exp2 = exp();
		Exp exp3 = exp();
		return new If(exp1, exp2, exp3);
	}

	public static Cond cond()

	// <cond> --> "cond" <case list> 

	{
		getToken();
		CaseList caseList = caseList();
		return new Cond(caseList);
	}

	public static CaseList caseList()

	// <case list> --> <case exp> | <case exp> <case list>

	{
		CaseExp caseExp = caseExp();
		if ( state == State.LParen )
		{
			CaseList caseList = caseList();
			return new CaseList(caseExp, caseList);
		}
		else
			return new CaseList(caseExp, null);
	}

	public static CaseExp caseExp()

	// <case exp> --> "(" ( <exp> <exp> | "else" <exp> ) ")"

	{
		getToken();

		CaseExp caseExp;

		if ( state == State.Keyword_else )
		{
			getToken();
			Exp exp = exp();
			caseExp = new ElseCase(exp);
		}
		else
		{
			Exp exp1 = exp();
			Exp exp2 = exp();
			caseExp = new Case(exp1, exp2);
		}
		if ( state == State.RParen )
		{
			getToken();
			return caseExp;
		}
		else
		{
			errorMsg(3);
			return null;
		}
	}

	public static FunCall funCall()

	// <fun call> --> <fun name> <exp list>

	{
		String funName = t;
		getToken();
		ExpList expList= expList();
		return new FunCall(funName, expList);
	}

	public static ArithExpr arithExpr()

	// <arith expr> --> + <exp list> | * <exp list> | - <exp list1> | / <exp list1>

	{
		switch ( state )
		{
		case Add:

			getToken();
			ExpList expList = expList();
			return new Add(expList);

		case Mul:

			getToken();
			expList = expList();
			return new Mul(expList);

		case Sub:

			getToken();
			ExpList expList1 = expList1();
			return new Sub(expList1);

		default: // case Div:

			getToken();
			expList1 = expList1();
			return new Div(expList1);
		}
	}

	public static BoolExpr boolExpr()

	// <bool expr> --> "and" <exp list> | "or" <exp list> | "not" <exp>

	{
		switch ( state )
		{
		case Keyword_and:

			getToken();
			ExpList expList = expList();
			return new And(expList);

		case Keyword_or:

			getToken();
			expList = expList();
			return new Or(expList);

		default: // case Keyword_not:

			getToken();
			Exp exp = exp();
			return new Not(exp);
		}
	}

	public static CompExpr compExpr()

	// <comp expr> --> "<" <exp list> | "<=" <exp list> | ">" <exp list> | ">=" <exp list> | "=" <exp list>

	{
		State saved_state = state;
		getToken();
		ExpList expList = expList();

		switch ( saved_state )
		{
		case Lt:

			return new Lt(expList);

		case Le:

			return new Le(expList);

		case Gt:

			return new Gt(expList);

		case Ge:

			return new Ge(expList);

		default: // case Eq:

			return new Eq(expList);
		}
	}

	public static ExpList expList()

	// <exp list> --> epsilon | <exp> <exp list>

	{
		if ( ( state.compareTo(State.Id) >= 0 && state.compareTo(State.LParen) <= 0 ) ||
		       state == State.Keyword_false || state == State.Keyword_true )
		{
			Exp exp = exp();
			ExpList expList = expList();
			return new ExpList(exp, expList);
		}		
		else // <exp list> is epsilon
			return null;	
	}

	public static ExpList expList1()

	// <exp list1> --> <exp> | <exp> <exp list1>

	{
		Exp exp = exp();

		if ( ( state.compareTo(State.Id) >= 0 && state.compareTo(State.LParen) <= 0 ) ||
		       state == State.Keyword_false || state == State.Keyword_true )
		{
			ExpList expList1 = expList1();
			return new ExpList(exp, expList1);
		}		
		else
		{
			return new ExpList(exp, null);
		}	
	}

	public static void errorMsg(int i)
	{
		syntaxErrorFound = true;

		display(t + ": unexpected symbol where");

		switch ( i )
		{
		case 1:	displayln(" ( expected"); return;
		case 2: displayln(" define expected"); return;
		case 3: displayln(" ) expected"); return;
		case 4: displayln(" function name expected"); return;
		case 5: displayln(" if, cond, function name, or operator expected"); return;		
		case 6: displayln(" identifier, integer, float, bool literal, or ( expected"); return;
		}
	}

	public static int myParseInt(String t)
	{
		if ( t.charAt(0) == '+' )
			return Integer.parseInt(t.substring(1));
		else
			return Integer.parseInt(t);
	}

	public static void main(String argv[])

	// The input/output file names must be passed as argv[0] and argv[1].

	{
		setLex( argv[0], argv[1] );

		getToken();

		FunDefList funDefList = funDefList();
		if ( ! t.isEmpty() )
			displayln(t + "  -- unexpected symbol");
		else if ( ! syntaxErrorFound )
			funDefList.printParseTree("");
		
		closeIO();
	}
}
