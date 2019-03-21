package ast;

import visitor.Visitor;

public class AccesoCampo extends AbstractTraceable implements Expresion {

	private Expresion left;
	private String campo;
	private boolean isModificable;
	private Tipo tipo;
	
	public AccesoCampo(Expresion left, String campo) {
		this.left = left;
		this.campo = campo;
		isModificable = true;
		searchForPositions(left, campo);	// Obtener linea/columna a partir de los hijos
	}

	public AccesoCampo(Object left, Object campo) {
		this.left = (Expresion) left;
		this.campo = (campo instanceof Token) ? ((Token)campo).getLexeme() : (String) campo;

		searchForPositions(left, campo);	// Obtener linea/columna a partir de los hijos
	}
	
	public Expresion getLeft()
	{
		return left;
	}
	
	public String getCampo()
	{
		return campo;
	}

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

	@Override
	public Tipo getTipo() {
		return tipo;
	}

	@Override
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;		
	}

	@Override
	public boolean isModificable() {
		return isModificable;
	}

	@Override
	public void setModificable(boolean modificable) {
		this.isModificable=modificable;		
	}

}
