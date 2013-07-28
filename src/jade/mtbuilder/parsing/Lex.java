package jade.mtbuilder.parsing;

import jade.core.*;
import java.io.*;
import java.util.regex.*;

/**
 * Lexical analyzer for parsing Template String.
 * 
 * @author Davide Canton
 */
public class Lex
{
	// the stream tokenizer configured for parsing
	private StreamTokenizer stream;
	// current symbol
	private Token symbol;
	// current string value read
	private String sval;
	// current AID read (if present)
	private AID aid;
	// aid Pattern string
	public static Pattern aidPattern = Pattern.compile("(\\w)+@[\\w\\.]+(:[0-9]+)?(/JADE)?");

	/**
	 * Creates a {@link Lex} from an {@link InputStream} i.
	 * 
	 * @param i
	 *            The {@link InputStream} used for reading.
	 */
	public Lex(InputStream i)
	{
		stream = new StreamTokenizer(new InputStreamReader(i));
		setup();
	}

	/**
	 * Creates a {@link Lex} from an {@link Reader} r.
	 * 
	 * @param r
	 *            The {@link Reader} used for reading.
	 */
	public Lex(Reader r)
	{
		stream = new StreamTokenizer(r);
		setup();
	}

	/**
	 * Creates a {@link Lex} from an {@link String} s.
	 * 
	 * @param s
	 *            The {@link String} used for reading.
	 */
	public Lex(String s)
	{
		stream = new StreamTokenizer(new StringReader(s));
		setup();
	}

	/**
	 * Returns the current string value read.
	 * 
	 * @return The current string value read.
	 */
	public String getString()
	{
		return sval;
	}

	/**
	 * Returns the current AID read.
	 * 
	 * @return The current AID read.
	 */
	public AID getAid()
	{
		return aid;
	}

	/**
	 * Advances the scanner.
	 * 
	 * @throws IOException
	 *             If the reading fails.
	 */
	public void nextToken() throws IOException
	{
		// reads symbol
		int n = stream.nextToken();
		// if EOF set symbol to EOF
		if (n == StreamTokenizer.TT_EOF)
			symbol = Token.EOF;
		// if it's a word, set symbol to word type
		else if (n == StreamTokenizer.TT_WORD)
		{
			sval = stream.sval;
			if (isAID(sval))
			{
				aid = makeAID(sval);
				symbol = Token.AID;
			}
			else
				symbol = Token.STRING;
		}
		// if it's a char, set symbol
		else if (n > 0)
		{
			sval = "" + (char) n;
			switch (n)
			{
				case '&':
					symbol = Token.AND;
					break;
				case '(':
					symbol = Token.OPAR;
					break;
				case ')':
					symbol = Token.CPAR;
					break;
				case '{':
					symbol = Token.OBRACE;
					break;
				case '}':
					symbol = Token.CBRACE;
					break;
				case '|':
					symbol = Token.OR;
					break;
				case '=':
					symbol = Token.EQ;
					break;
				case ',':
					symbol = Token.COMMA;
					break;
				case '*':
					symbol = Token.ALL;
					break;
				default:
					symbol = Token.INVALID;
			}
		}
	}

	// matches if sval is an AID
	private boolean isAID(String sval)
	{
		return aidPattern.matcher(sval).matches();
	}

	// creates an AID from a string
	private AID makeAID(String sval)
	{
		return new AID(sval, true);
	}

	/**
	 * Returns current token read.
	 * 
	 * @return Current token read.
	 */
	public Token currentToken()
	{
		return symbol;
	}

	@Override
	public String toString()
	{
		return stream.toString();
	}

	// setup
	private void setup()
	{
		stream.resetSyntax();
		stream.eolIsSignificant(false);
		stream.whitespaceChars(0, ' '); // char between da 0 a ' '
		stream.wordChars('0', '9');
		stream.wordChars('a', 'z');
		stream.wordChars('A', 'Z');
		stream.wordChars('/', '/');
		stream.wordChars('@', '@');
		stream.wordChars('.', '.');
		stream.wordChars(':', ':');
	}

	/**
	 * Advances if current symbol equals s, otherwise throws {@link IOException}.
	 * 
	 * @param s
	 *            The correct symbol.
	 * @throws IOException
	 *             If current symbol != s.
	 */
	public void accept(Token s) throws IOException
	{
		if (symbol != s)
			throw new IllegalStateException("Found " + symbol + " expected " + s);
		nextToken();
	}
}
