package ast;

import java.util.List;

import visitor.Visitor;

public class Case extends AbstractSentencia{

	private Expresion resultado;
	private List<Sentencia> sentencias;
	private DefinicionFuncion defFunc;
	
	
	public Case (Expresion resultado, List<Sentencia> sentencias)
	{
		this.resultado = resultado;
		this.sentencias = sentencias;
		
		searchForPositions(resultado, sentencias);	// Obtener linea/columna a partir de los hijos
	}
	
	@SuppressWarnings("unchecked")
	public Case (Object resultado, Object sentencias)
	{
		this.resultado = (Expresion)resultado;
		this.sentencias = (List<Sentencia>)sentencias;
		
		searchForPositions(resultado, sentencias);	// Obtener linea/columna a partir de los hijos
	}
	
	@Override
	public void setFuncion(DefinicionFuncion node) {
		this.defFunc = node;		
	}

	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}

	public Expresion getResultado() {
		return resultado;
	}

	public void setResultado(Expresion resultado) {
		this.resultado = resultado;
	}

	public List<Sentencia> getSentencias() {
		return sentencias;
	}

	public void setSentencias(List<Sentencia> sentencias) {
		this.sentencias = sentencias;
	}

	public DefinicionFuncion getDefFunc() {
		return defFunc;
	}

}
