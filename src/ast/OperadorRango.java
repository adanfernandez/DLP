package ast;

import visitor.Visitor;

public class OperadorRango extends AbstractExpresion{

	private Expresion menor;
	private Expresion mayor;
	private Expresion valor;
	
	public OperadorRango(Expresion menor, Expresion valor, Expresion mayor)
	{
		this.menor = menor;
		this.valor = valor;
		this.mayor = mayor;
	}
	
	public OperadorRango(Object menor, Object valor,Object mayor) {
		this.menor = (Expresion) menor;
		this.valor = (Expresion)valor;
		this.mayor = (Expresion) mayor;

		searchForPositions(menor, valor, mayor);	// Obtener linea/columna a partir de los hijos
	}
	
	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}

	public Expresion getMenor() {
		return menor;
	}

	public void setMenor(Expresion menor) {
		this.menor = menor;
	}

	public Expresion getMayor() {
		return mayor;
	}

	public void setMayor(Expresion mayor) {
		this.mayor = mayor;
	}

	public Expresion getValor() {
		return valor;
	}

	public void setValor(Expresion valor) {
		this.valor = valor;
	}

}
