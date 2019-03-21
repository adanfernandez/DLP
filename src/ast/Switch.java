package ast;

import java.util.List;

import visitor.Visitor;

public class Switch extends AbstractSentencia{

	private Expresion condicion;
	private List<Case> cases;
	
	public Switch (Expresion condicion, List<Case> cases)
	{
		this.condicion = condicion;
		this.cases = cases;
		
		searchForPositions(condicion, cases);	// Obtener linea/columna a partir de los hijos

	}
	
	@SuppressWarnings("unchecked")
	public Switch (Object condicion, Object cases)
	{
		this.condicion = (Expresion)condicion;
		this.cases = (List<Case>)cases;
		
		searchForPositions(condicion, cases);	// Obtener linea/columna a partir de los hijos
	}
	
	private DefinicionFuncion defFunc;
	
	@Override
	public void setFuncion(DefinicionFuncion node) {
		defFunc = node;
	}
	
	public DefinicionFuncion getDefinicion()
	{
		return defFunc;
	}

	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}

	public Expresion getCondicion() {
		return condicion;
	}

	public void setCondicion(Expresion condicion) {
		this.condicion = condicion;
	}

	public List<Case> getCases() {
		return cases;
	}

	public void setCases(List<Case> cases) {
		this.cases = cases;
	}
}
