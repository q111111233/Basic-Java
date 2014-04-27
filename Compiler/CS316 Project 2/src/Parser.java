/**

This class is a top-down, recursive-descent parser for the following syntactic categories:

<assignment list> --> <assignment> | <assignment> <assignment list>
<assignment> --> <id> = <E> ";"
<E> --> <term> | <term> + <E> | <term> - <E>
<term> --> <primary> | <primary> * <term> | <primary> / <term>
<primary> --> <id> | <int> | <float> | <floatE> | "(" <E> ")" 

The definitions of the tokens are given in the lexical analyzer class file "lexArithArray.java".

The following variables and functions of the "lexArithArray" class are used:

static String t // holds an extracted token
static State state // the current state of the finite automaton
static int getToken() // extracts the next token
static void display(String s)
static void displayln(String s)
static void setIO(String inFile, String outFile)
static void closeIO()

An explicit parse tree is constructed in the form of nested class objects.

The main function will display the parse tree in linearly indented form.
Each syntactic category name labeling a node is displayed on a separate line, 
prefixed with the integer i representing the node's depth and indented by i blanks. 

 **/


public abstract class Parser extends LexAnalyzer
{
	static boolean errorFound = false;

	public static AbFunDefList funDefList()
	{
		FunDefSingle fd = funDef();

		if (state == State.LParen)
		{
			AbFunDefList fdl = funDefList();
			return new MultiFunDefList(fd, fdl);
		}
		else
			return fd;
	}

	public static FunDefSingle funDef()
	{
		if (state == State.LParen)
		{
			getToken();
			if (state == State.Keyword_define)
			{
				getToken();
				Header h = header();
				Exp e = exp();

				if (state == State.RParen)
				{
					getToken();
				}
				else
				{
					errorMsg(1);
					return null;
				}
				return new FunDefSingle(h, e);
			}
			else
				errorMsg(7);
		}
		else
			errorMsg(0);

		return null;
	}
	public static Header header() 
	{
		if (state == State.LParen)
		{
			getToken();
			FunName fn = funName();
			if (state == State.RParen)
			{
				getToken();
				return new Header(fn, new EmptyParameterList());
			}
			else if (state == State.Id)
			{
				AbParameterList pl = Parameter();

				if (state == State.RParen)
				{
					getToken();
					return new Header(fn,pl);
				}
				else
				{
					errorMsg(5);
				}
			}
			else
				errorMsg(4);
			//Add error message.
		}
		else
			errorMsg(1);	
		return null;
	}

	public static AbParameterList Parameter() 
	{
		ParameterListID pl = ParameterList();

		if (state == State.Id)
		{
			AbParameterList mpl = Parameter();
			return new MultiParameterList (pl, mpl);
		}
		else
			return pl;
	}

	public static ParameterListID ParameterList()
	{
		ParameterListID pid = new ParameterListID(t);
		getToken();
		return pid;
	}

	public static FunName funName() {
		if (state == State.Id)
		{
			FunName fn = new FunName(t);
			getToken();
			return fn;
		}
		else
		{
			errorMsg(5);
			return null;
		}
	}

	public static Exp exp ()
	{
		{

			switch ( state )
			{
			case Id:
				ExpId id = new ExpId(t);
				getToken();
				return id;

			case Int:
				ExpInt intElem = new ExpInt(Integer.parseInt(t));
				getToken();
				return intElem;

			case Float: 
			case FloatE:
				ExpFloat floatElem = new ExpFloat(t);
				getToken();
				return floatElem;

			case LParen:
				getToken();

				ListExpInside ele = ListExp_Exp();

				if ( state == State.RParen )
					getToken();
				else
				{
					errorMsg(1);
					return null;
				}
				return ele;



			case Keyword_false:
			case Keyword_true:	
				ExpBool boolElem = new ExpBool(t);
				getToken();
				return boolElem;
			default:
				errorMsg(2);
				return null;
			}
		}
	}

	public static ArithExpAM arithExpAM()
	{
		String sign = (t.equalsIgnoreCase("Add")) ? "+" : "*" ;
		getToken();
		AbExpList e = expList();
		return new ArithExpAM(sign, e);
	}

	public static ArithExpSD arithExpSD()
	{
		String sign = (t.equalsIgnoreCase("Div")) ? "/" : "-";
		getToken();
		AbExpList1 e = expList1();
		return new ArithExpSD (sign, e);

	}

	public static ListExpInside ListExp_Exp() 
	{

		switch (state)
		{
		case Keyword_if:
			getToken();
			return LEIIf();
		case Keyword_cond:
			getToken();
			return new LEICond (LEICord());
		case Id:
			FunName fn = funName();

			if (state == State.RParen)
			{
				AbExpList ele = new ExpListEmpty();
				return new FunCall (fn, ele);
			}
			else
			{
				AbExpList ael = expList();
				return new FunCall (fn, ael);
			}
		case Add:
		case Mul:
			//Go to ArithExp AM
			ArithExpAM aeam  = arithExpAM();
			return aeam;
		case Sub:
		case Div:
			ArithExpSD aesd = arithExpSD();
			return aesd;
			//Go to ArithExp SD
		case Keyword_and:
		case Keyword_or:
			BoolExpr1 be1 = boolExpr1();
			return be1;
		case Keyword_not:
			BoolExpr2 be2 = boolExpr2();
			return be2;
		case Lt:
		case Le:
		case Gt:
		case Ge:
		case Eq:
			CompExpr ce = compExpr();
			return ce;
		default:
			return null;
		}
	}

	public static CompExpr compExpr()
	{

		String sign = state.toString();
		getToken();
		AbExpList ael = expList();
		return new CompExpr (sign, ael);
	}

	public static BoolExpr1 boolExpr1()
	{
		String sign = t;
		getToken();
		AbExpList ael = expList();
		return new BoolExpr1(ael, sign);
	}

	public static BoolExpr2 boolExpr2()
	{
		getToken();
		Exp e = exp();
		return new BoolExpr2(e);
	}





	public static AbExpList1 expList1() 
	{
		Exp e = exp();
		ExpList1Single els = new ExpList1Single (e);
		if (state ==State.Int || state == State.Id 
				|| state == State.Float || state == State.FloatE
				|| state == State.LParen || state == State.Keyword_true
				|| state == State.Keyword_false)
		{

			AbExpList1 e1 = expList1();
			return new ExpList1Multi(els, e1);
		}
		else 
		{
			return els;
		}
	}

	public static AbExpList expList() 
	{
		Exp e = exp();
		ExpListSingle els = new ExpListSingle (e);
		if (state ==State.Int || state == State.Id 
				|| state == State.Float || state == State.FloatE
				|| state == State.LParen || state == State.Keyword_true
				|| state == State.Keyword_false)
		{
			AbExpList e1 = expList();
			return new ExpListMulti(els,e1);
		}
		else
		{
			AbExpList e1 = new ExpListEmpty ();
			return new ExpListMulti(els,e1);
		}
	}

	public static AbCaseList LEICord() 
	{
		CaseExp cl = caseList();
		if (state == State.LParen)
		{
			AbCaseList acl = caseList();
			return new MultiCaseList(cl, acl);
		}
		else
			return cl;

	}

	public static CaseExp caseList()
	{
		if (state == State.LParen)
		{
			getToken();
			Exp e1 = null;
			if (state != State.Keyword_else)
				e1 = exp();
			else if (state == State.Keyword_else)
			{
				e1 = new ExpId ("else");
				getToken();
			}

			Exp e2 = exp();

			if (state==State.RParen)

				getToken();
			else
				errorMsg(5);
			return new CaseExp (e1, e2);
		}
		else
			errorMsg(6);
		return null;
	}

	public static LEIIf LEIIf()
	{
		Exp e1 = exp();
		Exp e2 = exp();
		Exp e3 = exp();
		return new LEIIf (e1, e2, e3);
	}
	public static void errorMsg(int i)
	{
		errorFound = true;
		display(t + ": unexpected symbol where");
		switch( i )
		{
		case 0: displayln(" ( expected ");
		case 1:	displayln(" arith op or ) expected"); return;
		case 2: displayln(" identifier, integer, float, bool literal, or ( expected"); return;
		case 3:	displayln(" = expected"); return;
		case 4: displayln(" id, ) expected");
		case 5:	displayln(" id expected"); return;	
		case 6: displayln(" ) expected");
		case 7: displayln(" define expected");
		}
	}


	public static void main(String argv[])
	{
		// argv[0]: input file containing the source code of an assignment list
		// argv[1]: output file to display the parse tree
		setLex( argv[0], argv[1] );
		getToken();
		AbFunDefList fd = funDefList();

		if ( ! t.isEmpty() )
			displayln(t + "  -- unexpected symbol");
		else if ( ! errorFound )
		{
			fd.printParseTree(""); // print the parse tree in linearly indented form in argv[1] file
		}
		closeIO();
	}
}
