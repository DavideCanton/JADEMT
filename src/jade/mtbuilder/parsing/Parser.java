package jade.mtbuilder.parsing;

import jade.*;
import jade.core.*;
import jade.mtbuilder.tree.*;
import java.io.*;
import java.util.*;

/**
 * Recursive Descent Parser built on top of the EBNF grammar of Template String.
 * 
 * @author Davide Canton
 */
public class Parser
{
	// current lex
	private Lex lex;
	// dates binding
	private Date[] dates;

	/**
	 * Sets the current lex.
	 * 
	 * @param lex
	 *            The current lex.
	 */
	public void setLex(Lex lex)
	{
		this.lex = lex;
	}

	/**
	 * Parses the input from lex using the array dates.
	 * 
	 * @param dates
	 *            The array of the dates.
	 * @return The {@link MTTreeNode} representing the parsed string.
	 * @throws IOException
	 *             If reading fails.
	 */
	public MTTreeNode parse(Date... dates) throws IOException
	{
		this.dates = dates;
		lex.nextToken();
		MTTreeNode root = program();
		lex.accept(Token.EOF);
		return root;
	}

	private MTTreeNode program() throws IOException
	{
		MTTreeNode root = term();
		while (lex.currentToken() == Token.OR)
		{
			lex.accept(Token.OR);
			OrOperator or = new OrOperator();
			or.setLeft(root);
			or.setRight(term());
			root = or;
		}
		return root;
	}

	private MTTreeNode term() throws IOException
	{
		MTTreeNode root = fact();
		while (lex.currentToken() == Token.AND)
		{
			lex.accept(Token.AND);
			AndOperator and = new AndOperator();
			and.setLeft(root);
			and.setRight(fact());
			root = and;
		}
		return root;
	}

	private MTTreeNode fact() throws IOException
	{
		switch (lex.currentToken())
		{
			case NOT:
			{
				lex.nextToken();
				NotOperator not = new NotOperator();
				not.setLeft(fact());
				return not;
			}
			case ALL:
			{
				lex.nextToken();
				return new LeafNode();
			}
			case OPAR:
			{
				lex.nextToken();
				MTTreeNode node = program();
				lex.accept(Token.CPAR);
				return node;
			}
			case STRING:
			{
				String s = lex.getString();
				lex.nextToken();
				lex.accept(Token.EQ);
				if (lex.currentToken() == Token.STRING)
				{
					String n = lex.getString();
					Integer x = MTUtils.getValue(n);
					lex.nextToken();
					if (x == null)
						return new LeafNode(s, n);
					else
						return new LeafNode(s, x.intValue());
				}
				else if (lex.currentToken() == Token.OBRACE)
				{
					lex.nextToken();
					String x = lex.getString();
					int n = Integer.parseInt(x);
					lex.nextToken();
					lex.accept(Token.CBRACE);
					return new LeafNode(s, dates[n]);
				}
				else if (lex.currentToken() == Token.AID)
				{
					List<AID> lis = new LinkedList<AID>();
					lis.add(lex.getAid());
					lex.nextToken();
					while (lex.currentToken() == Token.COMMA)
					{
						lex.nextToken();
						lis.add(lex.getAid());
						lex.nextToken();
					}
					return new LeafNode(s, lis.toArray(new AID[0]));
				}
			}
			default:
				throw new IllegalStateException("Not valid token: " + lex.currentToken());
		}
	}

	public static void main(String[] args)
	{
		Lex lex = new Lex("Performative = REQUEST | (Performative = INFORM & Ontology = Sguo)");
		Parser p = new Parser();
		p.setLex(lex);
		try
		{
			p.parse();
		}
		catch (IOException exc)
		{
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
	}
}
