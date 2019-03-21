/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;

import visitor.*;

//	bucleWhile:sentencia -> condicion:expresion  cuerpo:sentencia*

public class BucleWhile extends AbstractSentencia {

	public BucleWhile(Expresion condicion, List<Sentencia> cuerpo) {
		this.condicion = condicion;
		this.cuerpo = cuerpo;

		searchForPositions(condicion, cuerpo);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public BucleWhile(Object condicion, Object cuerpo) {
		this.condicion = (Expresion) condicion;
		this.cuerpo = (List<Sentencia>) cuerpo;

		searchForPositions(condicion, cuerpo);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getCondicion() {
		return condicion;
	}
	public void setCondicion(Expresion condicion) {
		this.condicion = condicion;
	}

	public List<Sentencia> getCuerpo() {
		return cuerpo;
	}
	public void setCuerpo(List<Sentencia> cuerpo) {
		this.cuerpo = cuerpo;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion condicion;
	private List<Sentencia> cuerpo;
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

