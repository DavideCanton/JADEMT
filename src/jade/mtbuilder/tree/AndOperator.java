package jade.mtbuilder.tree;

import jade.lang.acl.*;
import jade.mtbuilder.visitor.*;

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


	@Override
	public void accept(MTVisitor visitor)
	{
		visitor.visit(this);
	}

}
