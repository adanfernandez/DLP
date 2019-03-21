/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;

import visitor.*;

//	condIf:sentencia -> condicion:expresion  cuerpo:sentencia*  condelse:condElse

public class CondIf extends AbstractSentencia {

	public CondIf(Expresion condicion, List<Sentencia> cuerpo, CondElse condelse) {
		this.condicion = condicion;
		this.cuerpo = cuerpo;
		this.condelse = condelse;

		searchForPositions(condicion, cuerpo, condelse);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public CondIf(Object condicion, Object cuerpo, Object condelse) {
		this.condicion = (Expresion) condicion;
		this.cuerpo = (List<Sentencia>) cuerpo;
		this.condelse = (CondElse) condelse;

		searchForPositions(condicion, cuerpo, condelse);	// Obtener linea/columna a partir de los hijos
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

	public CondElse getCondelse() {
		return condelse;
	}
	public void setCondelse(CondElse condelse) {
		this.condelse = condelse;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion condicion;
	private List<Sentencia> cuerpo;
	private CondElse condelse;
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

