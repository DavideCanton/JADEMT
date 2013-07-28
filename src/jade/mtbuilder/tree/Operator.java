package jade.mtbuilder.tree;

/**
 * Abstract class for an operator of the tree.
 * 
 * @author Davide Canton
 */
public abstract class Operator extends MTTreeNode
{
	// symbol of the operator
	private String symbol;

	/**
	 * Builds an {@link Operator} with symbol <code>s</code>.
	 * 
	 * @param s
	 *            The symbol.
	 */
	protected Operator(String s)
	{
		this.symbol = s;
	}

	@Override
	public String toString()
	{
		return getLeft() + " " + symbol + " " + getRight();
	}
}
