/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;

import visitor.*;

//	invocarFuncion:expresion, sentencia -> nombre:ident  parametros:expresion*

public class InvocarFuncion extends AbstractTraceable implements Expresion, Sentencia {

	public InvocarFuncion(Ident nombre, List<Expresion> parametros) {
		this.nombre = nombre;
		this.parametros = parametros;

		searchForPositions(nombre, parametros);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public InvocarFuncion(Object nombre, Object parametros) {
		this.nombre = (Ident) nombre;
		this.parametros = (List<Expresion>) parametros;

		searchForPositions(nombre, parametros);	// Obtener linea/columna a partir de los hijos
	}

	public Ident getNombre() {
		return nombre;
	}
	public void setNombre(Ident nombre) {
		this.nombre = nombre;
	}

	public List<Expresion> getParametros() {
		return parametros;
	}
	public void setParametros(List<Expresion> parametros) {
		this.parametros = parametros;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Ident nombre;
	private List<Expresion> parametros;
	
	private Tipo tipo;
	
	public Tipo getTipo()
	{
		return tipo;
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
	public void setTipo(Tipo tipo)
	{
		this.tipo = tipo;
	}

	
	private boolean modificable;
	
	@Override
	public boolean isModificable() {
		return modificable;
	}

	@Override
	public void setModificable(boolean modificable) {
		this.modificable = modificable;
	}
}