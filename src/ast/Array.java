/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	Array:tipo -> dimension:expresion  retorno:tipo

public class Array extends AbstractTipo {

	public Array(int dimension, Tipo retorno) {
		this.dimension = dimension;
		this.retorno = retorno;

		searchForPositions(dimension, retorno);	// Obtener linea/columna a partir de los hijos
	}

	public Array(Object dimension, Object retorno) {
		this.dimension = Integer.parseInt((String)dimension);
		this.retorno = (Tipo) retorno;

		searchForPositions(dimension, retorno);	// Obtener linea/columna a partir de los hijos
	}

	public int getDimension() {
		return dimension;
	}
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public Tipo getRetorno() {
		return retorno;
	}
	public void setRetorno(Tipo retorno) {
		this.retorno = retorno;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private int dimension;
	private Tipo retorno;
	
	
	@Override
	public int getBytes() {
		return (dimension * retorno.getBytes());
	}
}

