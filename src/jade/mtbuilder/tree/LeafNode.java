package jade.mtbuilder.tree;

import java.text.*;
import java.util.*;
import jade.*;
import jade.core.*;
import jade.lang.acl.*;
import jade.mtbuilder.visitor.*;

/**
 * Node representing a leaf in the tree.
 * 
 * @author Davide Canton
 */
public class LeafNode extends MTTreeNode
{
	// name of method
	private String name;
	// the message template built
	private MessageTemplate mt;
	private String repr;

	/**
	 * Builds a {@link LeafNode} with a {@link MessageTemplate} that matches ALL.
	 */
	public LeafNode()
	{
		mt = create(null);
	}

	/**
	 * Builds a {@link LeafNode} with a {@link MessageTemplate} that matches a method with a
	 * {@link String} parameter.
	 * 
	 * @param name
	 *            The method name.
	 * @param value
	 *            The value passed to the method.
	 */
	public LeafNode(String name, String value)
	{
		this.name = name;
		mt = create(value);
	}

	/**
	 * Builds a {@link LeafNode} with a {@link MessageTemplate} that matches a method with a
	 * {@link Date} parameter.
	 * 
	 * @param name
	 *            The method name.
	 * @param value
	 *            The value passed to the method.
	 */
	public LeafNode(String name, Date value)
	{
		this.name = name;
		mt = create(value);
	}

	/**
	 * Builds a {@link LeafNode} with a {@link MessageTemplate} that matches a method with a
	 * parameter of type {@link AID[]}.
	 * 
	 * @param name
	 *            The method name.
	 * @param value
	 *            The value passed to the method.
	 */
	public LeafNode(String name, AID... aid)
	{
		this.name = name;
		mt = create(aid);
	}

	/**
	 * Builds a {@link LeafNode} with a {@link MessageTemplate} that matches a method with an
	 * {@link int} parameter.
	 * 
	 * @param name
	 *            The method name.
	 * @param value
	 *            The value passed to the method.
	 */
	public LeafNode(String name, int val)
	{
		this.name = name;
		mt = create(val);
	}

	// creates a MessageTemplate
	private MessageTemplate create(Object o)
	{
		repr = getVal(o);
		try
		{
			if (name == null)
				return MessageTemplate.MatchAll();
			else if (MTUtils.needsDate(name))
				return MTUtils.makeTemplateDate(name, (Date) o);
			else if (MTUtils.needsInt(name))
				return MTUtils.makeTemplateInt(name, ((Integer) o).intValue());
			else if (MTUtils.needsAIDArray(name))
				return MTUtils.makeTemplateAIDArray(name, (AID[]) o);
			else if (MTUtils.needsSingleAID(name))
				return MTUtils.makeTemplateSingleAID(name, ((AID[]) o)[0]);
			else
				return MTUtils.makeTemplateString(name, (String) o);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public MessageTemplate evaluate()
	{
		return mt;
	}

	@Override
	public void accept(MTVisitor visitor)
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		if (name != null)
			return name + " = " + repr;
		else
			return "ALL";
	}

	public String getName()
	{
		return name;
	}

	public String getRepr()
	{
		return repr;
	}

	private String getVal(Object o)
	{
		if (o instanceof Date)
			return DateFormat.getDateInstance().format((Date) o);
		if (o instanceof AID[])
			return Arrays.toString((AID[]) o);
		if (o instanceof Integer)
			return MTUtils.getName((Integer) o);
		return o.toString();
	}
}
