package jade.mtbuilder.tree;

import jade.lang.acl.*;

/**
 * Operator that represents the logical OR of two {@link MessageTemplate}s.
 * 
 * @author Davide Canton
 */
public class OrOperator extends Operator
{
	/**
	 * Builds an {@link OrOperator}.
	 */
	public OrOperator()
	{
		super("|");
	}

	/**
	 * Evaluates to the logical or of left and right child.
	 */
	@Override
	public MessageTemplate evaluate()
	{
		return MessageTemplate.or(getLeft().evaluate(), getRight().evaluate());
	}
}
