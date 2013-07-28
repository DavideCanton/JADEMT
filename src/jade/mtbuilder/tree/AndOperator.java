package jade.mtbuilder.tree;

import jade.lang.acl.*;

/**
 * Operator that represents the logical AND of two {@link MessageTemplate}s.
 * 
 * @author Davide Canton
 */
public class AndOperator extends Operator
{
	/**
	 * Builds an {@link AndOperator}.
	 */
	public AndOperator()
	{
		super("&");
	}

	
	/**
	 * Evaluates to the logical and of left and right child.
	 */
	@Override
	public MessageTemplate evaluate()
	{
		return MessageTemplate.and(getLeft().evaluate(), getRight().evaluate());
	}

}
