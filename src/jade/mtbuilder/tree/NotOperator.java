package jade.mtbuilder.tree;

import jade.lang.acl.*;
import jade.mtbuilder.visitor.*;

/**
 * Operator that represents the logical NOT of a {@link MessageTemplate}.
 * 
 * @author Davide Canton
 */
public class NotOperator extends Operator
{
	/**
	 * Builds a {@link NotOperator}.
	 */
	public NotOperator()
	{
		super("!");
	}

	@Override
	/**
	 * Evaluates to the logical NOT of the left child. 
	 */
	public MessageTemplate evaluate()
	{
		return MessageTemplate.not(getLeft().evaluate());
	}

	@Override
	public String toString()
	{
		return "! " + getLeft();
	}
	
	@Override
	public void accept(MTVisitor visitor)
	{
		visitor.visit(this);
	}
}
