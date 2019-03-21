/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	cast:expresion -> tipo:tipo  expr:expresion

public class Cast extends AbstractExpresion {

	public Cast(Tipo tipo, Expresion expr) {
		this.tipo = tipo;
		this.expr = expr;

		searchForPositions(tipo, expr);	// Obtener linea/columna a partir de los hijos
	}

	public Cast(Object tipo, Object expr) {
		this.tipo = (Tipo) tipo;
		this.expr = (Expresion) expr;

		searchForPositions(tipo, expr);	// Obtener linea/columna a partir de los hijos
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Expresion getExpr() {
		return expr;
	}
	public void setExpr(Expresion expr) {
		this.expr = expr;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Tipo tipo;
	private Expresion expr;
}

