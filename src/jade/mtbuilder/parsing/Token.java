package jade.mtbuilder.parsing;

import jade.lang.acl.*;

/**
 * Tokens read by {@link Lex}.
 * 
 * @author Davide Canton
 */
public enum Token
{
	/**
	 * AND of two {@link MessageTemplate}s. (&)
	 */
	AND,
	/**
	 * OR of two {@link MessageTemplate}s. (|)
	 */
	OR,
	/**
	 * NOT of a {@link MessageTemplate}. (!)
	 */
	NOT,
	/**
	 * A date.
	 */
	DATE,
	/**
	 * A generic alphanumeric string.
	 */
	STRING,
	/**
	 * The equals symbol. (=)
	 */
	EQ,
	/**
	 * End Of File
	 */
	EOF,
	/**
	 * Invalid token.
	 */
	INVALID,
	/**
	 * Open bracket. (
	 */
	OPAR,
	/**
	 * Close bracket. )
	 */
	CPAR,
	/**
	 * Comma. (,)
	 */
	COMMA,
	/**
	 * An AID.
	 */
	AID,
	/**
	 * Matching ALL. (*)
	 */
	ALL,
	/**
	 * Open {.
	 */
	OBRACE,
	/**
	 * Open }.
	 */
	CBRACE;
}
