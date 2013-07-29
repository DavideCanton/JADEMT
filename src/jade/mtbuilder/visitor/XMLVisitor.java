package jade.mtbuilder.visitor;

import jade.mtbuilder.parsing.*;
import jade.mtbuilder.tree.*;
import java.io.*;

public class XMLVisitor extends OutputVisitor
{
	private int indent;

	public XMLVisitor()
	{
		super();
	}

	public XMLVisitor(OutputStream out)
	{
		super(out);
	}

	public XMLVisitor(Writer out)
	{
		super(out);
	}

	private void visit0(LeafNode f)
	{
		for (int i = 0; i < indent; i++)
			out.print('\t');
		String s = "<%s>%s</%1$s>";
		out.println(String.format(s, f.getName(), f.getRepr()));
	}

	private void visit0(Operator f)
	{
		for (int i = 0; i < indent; i++)
			out.print('\t');
		indent++;
		String name = f.getClass().getSimpleName();
		int index = name.indexOf("Operator");
		name = name.substring(0, index).toLowerCase();
		out.println("<" + name + ">");
		f.getLeft().accept(this);
		if (f.getRight() != null)
			f.getRight().accept(this);
		indent--;
		for (int i = 0; i < indent; i++)
			out.print('\t');
		out.println("</" + name + ">");
	}

	@Override
	public void print(MTTreeNode e)
	{
		out.println("<mt>");
		indent = 1;
		e.accept(this);
		out.println("</mt>");
		out.flush();
	}

	@Override
	public void visit(MTTreeNode node)
	{
		if (node instanceof LeafNode)
			visit0((LeafNode) node);
		if (node instanceof Operator)
			visit0((Operator) node);
	}

	public static void main(String[] args) throws IOException
	{
		XMLVisitor x = new XMLVisitor();
		Lex lex = new Lex("Performative = REQUEST | (Performative = INFORM & Ontology = Sguo) | Ontology = Gni");
		Parser p = new Parser();
		p.setLex(lex);
		MTTreeNode mtnode = p.parse();
		x.print(mtnode);
		System.out.println(mtnode);
	}

}
