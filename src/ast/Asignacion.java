/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	Asignacion:expresion, sentencia -> left:expresion  right:expresion

public class Asignacion extends AbstractTraceable implements Sentencia {

	public Asignacion(Expresion left, Expresion right) {
		this.left = left;
		this.right = right;

		searchForPositions(left, right);	// Obtener linea/columna a partir de los hijos
	}

	public Asignacion(Object left, Object right) {
		this.left = (Expresion) left;
		this.right = (Expresion) right;

		searchForPositions(left, right);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getLeft() {
		return left;
	}
	public void setLeft(Expresion left) {
		this.left = left;
	}

	public Expresion getRight() {
		return right;
	}
	public void setRight(Expresion right) {
		this.right = right;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion left;
	private Expresion right;
	
	private Tipo tipo;
	
	public Tipo getTipo()
	{
		return tipo;
	}
	public void setTipo(Tipo tipo)
	{
		this.tipo = tipo;
	}
	
	private DefinicionFuncion miFunc;
	@Override
	public void setFuncion(DefinicionFuncion node) {
		miFunc = node;
	}
	public DefinicionFuncion getFuncion()
	{
		return miFunc;
	}

}

