package jade.mtbuilder.tree;

import jade.lang.acl.*;
import jade.mtbuilder.visitor.*;

/**
 * Represents a generic node of the tree. After the tree is built, invoke <code>evaluate</code> on
 * the root node in order to get the {@link MessageTemplate}.
 * 
 * @author Davide Canton
 */
public abstract class MTTreeNode
{
	private MTTreeNode left, right, parent;

	/**
	 * Gets left child.
	 * 
	 * @return Left child.
	 */
	public MTTreeNode getLeft()
	{
		return left;
	}

	/**
	 * Sets left child to <code>left</code> and parent of left.
	 * 
	 * @param left
	 *            Left child.
	 */
	public void setLeft(MTTreeNode left)
	{
		this.left = left;
		if (this.left != null)
			this.left.setParent(this);
	}

	/**
	 * Gets right child.
	 * 
	 * @return Right child.
	 */
	public MTTreeNode getRight()
	{
		return right;
	}

	/**
	 * Sets right child to <code>right</code> and parent of right.
	 * 
	 * @param right
	 *            Right child.
	 */
	public void setRight(MTTreeNode right)
	{
		this.right = right;
		if (this.right != null)
			this.right.setParent(this);
	}

	/**
	 * Gets parent.
	 * 
	 * @return Parent.
	 */
	public MTTreeNode getParent()
	{
		return parent;
	}

	/**
	 * Sets parent.
	 * 
	 * @param parent
	 *            The parent.
	 */
	public void setParent(MTTreeNode parent)
	{
		this.parent = parent;
	}

	/**
	 * Evaluates the tree and returns {@link MessageTemplate}.
	 * 
	 * @return The {@link MessageTemplate} represented by the tree.
	 */
	public abstract MessageTemplate evaluate();

	/**
	 * Receives a visitor.
	 * 
	 * @param visitor
	 *            The {@link MTVisitor}.
	 */
	public abstract void accept(MTVisitor visitor);
}
