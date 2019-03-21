package ast;

import visitor.Visitor;

public class Break extends AbstractSentencia {

	
	private DefinicionFuncion funcion;
	
	@Override
	public void setFuncion(DefinicionFuncion node) {
		funcion = node;
	}
	
	public DefinicionFuncion getFuncion()
	{
		return funcion;
	}

	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}

	public String toString()
	{
		return "break";
	}
}
