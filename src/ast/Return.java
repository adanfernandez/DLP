/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	return:sentencia -> devolucion:expresion

public class Return extends AbstractSentencia {

	public Return(Expresion devolucion) {
		this.devolucion = devolucion;

		searchForPositions(devolucion);	// Obtener linea/columna a partir de los hijos
	}

	public Return(Object devolucion) {
		this.devolucion = (Expresion) devolucion;

		searchForPositions(devolucion);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getDevolucion() {
		return devolucion;
	}
	public void setDevolucion(Expresion devolucion) {
		this.devolucion = devolucion;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion devolucion;
	
	
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

