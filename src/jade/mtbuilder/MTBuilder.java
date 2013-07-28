package jade.mtbuilder;

import java.io.*;
import java.util.*;
import jade.lang.acl.*;
import jade.mtbuilder.parsing.*;

/**
 * Main class of the package {@link jade.mtbuilder}. It contains the method <code>build</code>, that
 * builds a {@link MessageTemplate} from a Template String. The EBNF grammar for the Template String
 * is: <br/>
 * <code>
 * &lt;expression&gt; ::= &lt;term&gt; [ OR &lt;term&gt; ] * <br />
 * &lt;term&gt; ::= &lt;fact&gt; [ AND &lt;fact&gt; ] * <br />
 * &lt;fact&gt; ::= NOT &lt;fact&gt; | OPAR &lt;expression&gt; CPAR | STRING EQ ( STRING [ COMMA STRING ] | DATE ) | ALL<br />
 * <br />
 * 
 * ALL ::= * <br />
 * AID ::= STRING AT ( CHAR | DOT ) + [ COLON NUM ] [ "/JADE" ] <br />
 * CHAR ::= \w <br />
 * STRING ::= CHAR+ <br />
 * COLON ::= : <br />
 * NUM ::= 0-9+ <br />
 * DOT ::= . <br />
 * EQ ::= = <br />
 * AND ::= & <br />
 * OR ::= | <br />
 * NOT ::= ! <br />
 * DATE ::= OBRACE NUM CBRACE <br />
 * OPAR ::= ( <br />
 * CPAR ::= ) <br />
 * OBRACE ::= { <br />
 * CBRACE ::= } <br />
 * COMMA ::= , <br />
 * AT ::= @ <br />
 * </code> <br/>
 * N.B. A DATE token is specified as the index of an array, in syntax {0} for example.
 * 
 * @author Davide Canton
 */
public class MTBuilder
{
	/**
	 * Builds a {@link MessageTemplate} from a Template String and an array of dates. Dates can be
	 * empty.
	 * 
	 * @param string
	 *            The Template String.
	 * @param dates
	 *            The dates array.
	 * @return The {@link MessageTemplate} created by parsing the string.
	 */
	public static MessageTemplate build(String string, Date... dates)
	{
		// creates lex and parser		
		Parser p = new Parser();
		p.setLex(new Lex(string));
		try
		{
			// try to parse
			return p.parse(dates).evaluate();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}
