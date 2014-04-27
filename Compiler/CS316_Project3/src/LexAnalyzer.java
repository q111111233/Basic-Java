/**
 
This program uses the Enum type introduced in JDK 1.5.0.

This class is a lexical analyzer for the 15 token categories <id> through <RParen> defined by:

<letter> --> a | b | ... | z | A | B | ... | Z
<digit> --> 0 | 1 | ... | 9
<basic id> --> <letter> { <letter> | <digit> }
<letters and digits> --> { <letter> | <digit> }+
<id> --> <basic id> { ("_"|"-") <letters and digits> }
<int> --> [+|-] {<digit>}+
<float> --> [+|-] ( {<digit>}+ "." {<digit>} | "." {<digit>}+ )
<floatE> --> <float> (e|E) [+|-] {<digit>}+
<add> --> +
<sub> --> -
<mul> --> *
<div> --> /
<lt> --> "<"
<le> --> "<="
<gt> --> ">"
<ge> --> ">="
<eq> --> "="
<LParen> --> "("
<RParen> --> ")"

This class implements a DFA that will accept the above tokens.
The DFA has 15 final and 5 non-final states represented by enum-type literals.

There are also 9 special states for the keywords
"define", "if", "cond", "else", "and", "or", "not", "false", "true".
The keywords are initially extracted as identifiers.
The keywordCheck() function checks if the extracted token is a keyword,
and if so, moves the DFA to the corresponding special state.

The states are represented by an Enum type called "State".
The function "driver" is the driver to operate the DFA. 
The array "nextState" returns the next state given
the current state and the input character.

To modify this lexical analyzer for a different token set,
modify the following:

  the enum type "State",
  the size of the array "nextState", 
  the functions "isFinal", "setNextState", "keywordCheck". 

The function "driver" and the other utility functions remain the same.

**/

import java.io.*;

public abstract class LexAnalyzer
{
	public enum State 
       	{ 
	  // final states     ordinal number  token accepted 

		Add,             // 0          +
		Sub,             // 1          -
		Mul,             // 2          *
		Div,             // 3          /
		Lt,              // 4          <
		Le,              // 5          <=
		Gt,              // 6          >
		Ge,              // 7          >=
		Eq,              // 8          =
		Id,              // 9          identifiers
		Int,             // 10         integers
		Float,           // 11         floats without exponentiation part
		FloatE,          // 12         floats with exponentiation part
		LParen,          // 13         (
		RParen,          // 14         )

	  // non-final states              string recognized    

		Start,           // 15      the empty string
		Period,          // 16      ".", "+.", "-."
		E,               // 17      float parts ending with E or e
		EPlusMinus,      // 18      float parts ending with + or - in exponentiation part
		UnderscoreMinus, // 19      identifiers followed by "_" or "-"

	  // keyword states

		Keyword_define,
		Keyword_if,
		Keyword_cond,
		Keyword_else,
		Keyword_and,
		Keyword_or,
		Keyword_not,
		Keyword_false,
		Keyword_true,

		UNDEF
	}

	// By enumerating the final states first and then the non-final states,
	// test for a final state can be done by testing if the state's ordinal number
	// is less than or equal to that of RParen.

	public static String t; // holds an extracted token
	public static State state; // the current state of the FA
	private static int a; // the current input character
	private static char c; // used to convert the variable "a" to 
	                       // the char type whenever necessary

	private static State nextState[][] = new State[20][128];
 
          // This array implements the state transition function
          // State x (ASCII char set) --> State.
          // The state argument is converted to its ordinal number used as
          // the first array index from 0 through 19. 

	private static String define_  = "define";
	private static String if_      = "if";
	private static String cond_    = "cond";
	private static String else_    = "else";
	private static String and_     = "and";
	private static String or_      = "or";
	private static String not_     = "not";
	private static String false_   = "false";
	private static String true_    = "true";

	private static BufferedReader inStream;
	private static PrintWriter outStream;

	private static int getNextChar()

	// Returns the next character on the input stream.

	{
		try
		{
			return inStream.read();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return -1;
		}
	} //end getNextChar

	private static int getChar()

	// Returns the next non-whitespace character on the input stream.
	// Returns -1, end-of-stream, if the end of the input stream is reached.

	{
		int i = getNextChar();
		while ( Character.isWhitespace((char) i) )
			i = getNextChar();
		return i;
	} // end getChar

	private static int driver()

	// This is the driver of the FA. 
	// If a valid token is found, assigns it to "t" and returns 1.
	// If an invalid token is found, assigns it to "t" and returns 0.
	// If end-of-stream is reached without finding any non-whitespace character, returns -1.

	{
		State nextSt; // the next state of the FA

		t = "";
		state = State.Start;

		if ( Character.isWhitespace((char) a) )
			a = getChar(); // get the next non-whitespace character
		if ( a == -1 ) // end-of-stream is reached
			return -1;

		while ( a != -1 ) // do the body if "a" is not end-of-stream
		{
			c = (char) a;
			nextSt = nextState[state.ordinal()][a];
			if ( nextSt == State.UNDEF ) // The FA will halt.
			{
				if ( isFinal(state) )
					return 1; // valid token extracted
				else // "c" is an unexpected character
				{
					t = t+c;
					a = getNextChar();
					return 0; // invalid token found
				}
			}
			else // The FA will go on.
			{
				state = nextSt;
				t = t+c;
				a = getNextChar();
			}
		}

		// end-of-stream is reached while a token is being extracted

		if ( isFinal(state) )
			return 1; // valid token extracted
		else
			return 0; // invalid token found
	} // end driver

	private static void setNextState()
	{
		for (int s = 0; s <= 19; s++ )
			for (int c = 0; c <= 127; c++ )
				nextState[s][c] = State.UNDEF;

		for (char c = 'A'; c <= 'Z'; c++)
		{
			nextState[State.Start          .ordinal()][c] = State.Id;
			nextState[State.Id             .ordinal()][c] = State.Id;
			nextState[State.UnderscoreMinus.ordinal()][c] = State.Id;
		}

		for (char c = 'a'; c <= 'z'; c++)
		{
			nextState[State.Start          .ordinal()][c] = State.Id;
			nextState[State.Id             .ordinal()][c] = State.Id;
			nextState[State.UnderscoreMinus.ordinal()][c] = State.Id;
		}

		for (char d = '0'; d <= '9'; d++)
		{
			nextState[State.Start          .ordinal()][d] = State.Int;
			nextState[State.Id             .ordinal()][d] = State.Id;
			nextState[State.Int            .ordinal()][d] = State.Int;
			nextState[State.Add            .ordinal()][d] = State.Int;
			nextState[State.Sub            .ordinal()][d] = State.Int;
			nextState[State.Period         .ordinal()][d] = State.Float;
			nextState[State.Float          .ordinal()][d] = State.Float;
			nextState[State.E              .ordinal()][d] = State.FloatE;
			nextState[State.EPlusMinus     .ordinal()][d] = State.FloatE;
			nextState[State.FloatE         .ordinal()][d] = State.FloatE;
			nextState[State.UnderscoreMinus.ordinal()][d] = State.Id;
		}

		nextState[State.Start.ordinal()]['+'] = State.Add;
		nextState[State.Start.ordinal()]['-'] = State.Sub;
		nextState[State.Start.ordinal()]['*'] = State.Mul;
		nextState[State.Start.ordinal()]['/'] = State.Div;
		nextState[State.Start.ordinal()]['<'] = State.Lt;
		nextState[State.Start.ordinal()]['>'] = State.Gt;
		nextState[State.Start.ordinal()]['='] = State.Eq;
		nextState[State.Start.ordinal()]['('] = State.LParen;
		nextState[State.Start.ordinal()][')'] = State.RParen;
		nextState[State.Start.ordinal()]['.'] = State.Period;

		nextState[State.Lt.ordinal()]['='] = State.Le;
		nextState[State.Gt.ordinal()]['='] = State.Ge;
		
		nextState[State.Add.ordinal()]['.'] = State.Period;
		nextState[State.Sub.ordinal()]['.'] = State.Period;
		nextState[State.Int.ordinal()]['.'] = State.Float;
			
		nextState[State.Float.ordinal()]['e'] = State.E;
		nextState[State.Float.ordinal()]['E'] = State.E;
		
		nextState[State.E.ordinal()]['+'] = State.EPlusMinus;
		nextState[State.E.ordinal()]['-'] = State.EPlusMinus;
		
		nextState[State.Id.ordinal()]['_'] = State.UnderscoreMinus;
		nextState[State.Id.ordinal()]['-'] = State.UnderscoreMinus;

	} // end setNextState

	private static boolean isFinal(State state)
	{
		return ( state.compareTo(State.RParen) <= 0 );  
	}
	
	private static void keywordCheck()
	{
		if ( t.equals(define_) )
			state = State.Keyword_define;
		else if ( t.equals(if_) )
			state = State.Keyword_if;
		else if ( t.equals(cond_) )
			state = State.Keyword_cond;
		else if ( t.equals(else_) )
			state = State.Keyword_else;
		else if ( t.equals(and_) )
			state = State.Keyword_and;
		else if ( t.equals(or_) )
			state = State.Keyword_or;
		else if ( t.equals(not_) )
			state = State.Keyword_not;
		else if ( t.equals(false_) )
			state = State.Keyword_false;
		else if ( t.equals(true_) )
			state = State.Keyword_true;
	} // end keywordCheck

	public static void getToken()

	// Extract the next token using the driver of the FA.
	// If an invalid token is found, issue an error message.

	{
		int i = driver();
		if ( state == State.Id )
			keywordCheck();
		else if ( i == 0 )
			displayln(t + "  -- Invalid Token");
	} // end getToken

	public static void display(String s)
	{
		outStream.print(s);
	}

	public static void displayln(String s)
	{
		outStream.println(s);
	}

	public static void setLex(String inFile, String outFile)

	// Sets the nextState array.
	// Sets the input and output streams to "inFile" and "outFile", respectively.
	// Sets the current input character "a" to the first character on
	// the input stream.

	{
		setNextState();

		try
		{
			inStream = new BufferedReader( new FileReader(inFile) );
			outStream = new PrintWriter( new FileOutputStream(outFile) );
			a = inStream.read();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	} // end setLex

	public static void closeIO()
	{
		try
		{
			inStream.close();
			outStream.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	} // end closeIO

	public static void main(String argv[])

	// The input/output file names must be passed as argv[0] and argv[1].

	{
		int i;

		setLex( argv[0], argv[1] );

		while ( a != -1 ) // while "a" is not end-of-stream
		{
			i = driver(); // extract the next token
			if ( i == 1 )
			{
				if ( state == State.Id )
					keywordCheck();
				displayln( t+"   : "+state.toString() );
			}
			else if ( i == 0 )
				displayln( t+"  -- Invalid Token");
		} 

		closeIO();
	} // end main
} 
