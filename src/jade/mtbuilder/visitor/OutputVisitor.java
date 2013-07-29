package jade.mtbuilder.visitor;

import jade.mtbuilder.tree.*;
import java.io.*;

public abstract class OutputVisitor implements MTVisitor
{
	protected PrintWriter out;

	public OutputVisitor()
	{
		out = new PrintWriter(System.out);
	}

	public OutputVisitor(Writer out)
	{
		this.out = new PrintWriter(out);
	}

	public OutputVisitor(OutputStream out)
	{
		this.out = new PrintWriter(out);
	}

	public abstract void print(MTTreeNode e);
}
