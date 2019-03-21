/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	ident:expresion -> valor:String

public class Ident extends AbstractExpresion {

	public Ident(String valor) {
		this.valor = valor;
	}

	public Ident(Object valor) {
		this.valor = (valor instanceof Token) ? ((Token)valor).getLexeme() : (String) valor;

		searchForPositions(valor);	// Obtener linea/columna a partir de los hijos
	}

	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String valor;
	
	public Definicion getDefinicion() {
		return definicion;
	}

	public void setDefinicion(Definicion definicion) {
		this.definicion = definicion;
	}

	private Definicion definicion;
	
}

