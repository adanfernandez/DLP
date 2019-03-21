/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	identType:tipo -> ident:ident

public class IdentType extends AbstractTipo {

	public IdentType(String ident) {
		this.ident = ident;

		searchForPositions(ident);	// Obtener linea/columna a partir de los hijos
	}

	public IdentType(Object ident) {
		this.ident = (ident instanceof Token) ? ((Token)ident).getLexeme() : (String) ident;


		searchForPositions(ident);	// Obtener linea/columna a partir de los hijos
	}

	public String getIdent() {
		return ident;
	}
	public void setIdent(String ident) {
		this.ident = ident;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}
	
	private String ident;
	
	private Definicion definicion;
	
	public Definicion getDefinicion()
	{
		return definicion;
	}
	
	public void setDefinicion(Definicion definicion)
	{
		this.definicion = definicion;
	}

	@Override
	public int getBytes() {
		int num = 0;
		DefinicionEstructura def = (DefinicionEstructura)definicion;
		for(VariableEstructura var : def.getVariablesEstructura())
		{
			num += var.getTipo().getBytes();
		}
		return num;
	}
}

