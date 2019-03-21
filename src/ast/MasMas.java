package ast;

import visitor.Visitor;

public class MasMas extends AbstractSentencia{

	private Expresion expresion;
	private DefinicionFuncion defFunc;
	
	public MasMas(Expresion expresion)
	{
		this.expresion = expresion;
	}
	
	public MasMas(Object expresion) {
		this.expresion = (Expresion) expresion;

		searchForPositions(expresion);	// Obtener linea/columna a partir de los hijos
	}
	
	@Override
	public void setFuncion(DefinicionFuncion node) {
		defFunc = node;
	}

	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}
	
	public Expresion getExpresion()
	{
		return expresion;
	}
	
	public DefinicionFuncion getDefFunc()
	{
		return defFunc;
	}



}
