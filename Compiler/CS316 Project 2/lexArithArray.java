/**
 
This program uses the Enum type introduced in JDK 1.5.0.

This class is a lexical analyzer for the tokens defined by the grammar:

<plus> --> +
<minus> --> -
<times> --> *
<div> --> /
<LParen> --> "("
<RParen> --> ")"
<assign> --> "="
<semicolon> --> ";"
<int> --> { <digit> }+
<id> --> <letter> { <letter> | <digit> }
<float> --> { <digit> }+ "." { <digit> }+
<floatE> --> <float> (E|e) [+|-] { <digit> }+

This class implements a DFA that will accept the above tokens.
The DFA has 12 final states represented by enum-type literals:

state     token accepted

Id        identifiers
Int       integers
Float     floats without exponentiation part
FloatE    floats with exponentiation part
Plus      +
Minus     -
Times     *
Div       /
LParen    (
RParen    )
Assign    =
Semicolon ;

The DFA also uses 4 non-final states:

state      string recognized

Start      the empty string
Period     float parts ending with "."
E          float parts ending with E or e
EPlusMinus float parts ending with + or - in exponentiation part

The states are represented by an Enum type called "State".
The function "driver" is the driver to operate the DFA. 
The array "nextState" returns the next state given
the current state and the input character.

To modify this lexical analyzer to recognize a different token set,
the array "nextState", the functions "isFinal" and "setNextState", and 
the enum type "State" need to be modified;
the function "driver" and the other utility functions remain the same.

**/

import java.io.*;

public abstract class lexArithArray
{
	public enum State 
       	{ 
	  // non-final states     ordinal number

		Start,             // 0
		Period,            // 1
		E,                 // 2
		EPlusMinus,        // 3

	  // final states

		Id,                // 4
		Int,               // 5
		Float,             // 6
		FloatE,            // 7
		Plus,              // 8
		Minus,             // 9
		Times,             // 10
		Div,               // 11
		LParen,            // 12
		RParen,            // 13
		Assign,            // 14
		Semicolon,         // 15

		UNDEF
	}

	// By enumerating the non-final states first and then the final states,
	// test for a final state can be done by testing if the state's ordinal number
	// is greater than or equal to that of Id.

	public static String t; // holds an extracted token
	public static State state; // the current state of the FA
	private static int a; // the current input character
	private static char c; // used to convert the variable "a" to 
	                       // the char type whenever necessary

	private static State nextState[][] = new State[16][128];
 
          // This array implements the state transition function
          // State x (ASCII char set) --> State.
          // The state argument is converted to its ordinal number used as
          // the first array index from 0 through 13. 

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
		for (int s = 0; s <= 15; s++ )
			for (int c = 0; c <= 127; c++ )
				nextState[s][c] = State.UNDEF;

		for (char c = 'A'; c <= 'Z'; c++)
		{
			nextState[State.Start.ordinal()][c] = State.Id;
			nextState[State.Id   .ordinal()][c] = State.Id;
		}

		for (char c = 'a'; c <= 'z'; c++)
		{
			nextState[State.Start.ordinal()][c] = State.Id;
			nextState[State.Id   .ordinal()][c] = State.Id;
		}

		for (char d = '0'; d <= '9'; d++)
		{
			nextState[State.Start     .ordinal()][d] = State.Int;
			nextState[State.Id        .ordinal()][d] = State.Id;
			nextState[State.Int       .ordinal()][d] = State.Int;
			nextState[State.Period    .ordinal()][d] = State.Float;
			nextState[State.Float     .ordinal()][d] = State.Float;
			nextState[State.E         .ordinal()][d] = State.FloatE;
			nextState[State.EPlusMinus.ordinal()][d] = State.FloatE;
			nextState[State.FloatE    .ordinal()][d] = State.FloatE;
		}

		nextState[State.Start.ordinal()]['+'] = State.Plus;
		nextState[State.Start.ordinal()]['-'] = State.Minus;
		nextState[State.Start.ordinal()]['*'] = State.Times;
		nextState[State.Start.ordinal()]['/'] = State.Div;
		nextState[State.Start.ordinal()]['('] = State.LParen;
		nextState[State.Start.ordinal()][')'] = State.RParen;
		nextState[State.Start.ordinal()]['='] = State.Assign;
		nextState[State.Start.ordinal()][';'] = State.Semicolon;
					
		nextState[State.Int.ordinal()]['.'] = State.Period;
			
		nextState[State.Float.ordinal()]['E'] = state.E;
		nextState[State.Float.ordinal()]['e'] = state.E;
			
		nextState[State.E.ordinal()]['+'] = State.EPlusMinus;
		nextState[State.E.ordinal()]['-'] = State.EPlusMinus;

	} // end setNextState

	private static boolean isFinal(State state)
	{
		return ( state.compareTo(State.Id) >= 0 );  
	}
	
	public static void getToken()

	// Extract the next token using the driver of the FA.
	// If an invalid token is found, issue an error message.

	{
		int i = driver();
		if ( i == 0 )
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
	} // end setIO

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

	{		
		// argv[0]: input file containing source code using tokens defined above
		// argv[1]: output file to display a list of the tokens 

		setLex( argv[0], argv[1] );
		
		int i;

		while ( a != -1 ) // while "a" is not end-of-stream
		{
			i = driver(); // extract the next token
			if ( i == 1 )
				displayln( t+"   : "+state.toString() );
			else if ( i == 0 )
				displayln( t+"  -- Invalid Token");
		} 

		closeIO();
	}
} 
