package jade.mtbuilder.tree;

import jade.lang.acl.*;
import jade.mtbuilder.visitor.*;

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
	
	@Override
	public void accept(MTVisitor visitor)
	{
		visitor.visit(this);
	}
}
