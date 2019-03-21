/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;

import visitor.*;

//	definicionEstructura:definicion -> nombre:String  variablesEstructura:variableEstructura*

public class DefinicionEstructura extends AbstractDefinicion {

	public DefinicionEstructura(String nombre, List<VariableEstructura> variablesEstructura) {
		this.nombre = nombre;
		this.variablesEstructura = variablesEstructura;

		searchForPositions(variablesEstructura);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public DefinicionEstructura(Object nombre, Object variablesEstructura) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;
		this.variablesEstructura = (List<VariableEstructura>) variablesEstructura;

		searchForPositions(nombre, variablesEstructura);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<VariableEstructura> getVariablesEstructura() {
		return variablesEstructura;
	}
	public void setVariablesEstructura(List<VariableEstructura> variablesEstructura) {
		this.variablesEstructura = variablesEstructura;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;
	private List<VariableEstructura> variablesEstructura;
	private Tipo tipo;
	
	public Tipo getTipo()
	{
		return tipo;
	}
	
	public void setTipo(Tipo tipo)
	{
		this.tipo=tipo;
	}
	
	public Tipo contiene(String nombre)
	{
		for(VariableEstructura var : variablesEstructura)
		{
			if(var.getNombre().equals(nombre))
				return var.getTipo();
		}
		return null;
	}

	
	private int offset;
	
	@Override
	public int getOffset() {
		return offset;
	}

	@Override
	public void setOffset(int offset) {
		this.offset = offset;		
	}
	
	
	private int ambito;
	@Override
	public int getAmbito() {
		return ambito;
	}

	@Override
	public void setAmbito(int ambito) {
		this.ambito=ambito;
	}
	
	public int getOffsetCampo(String nombre)
	{
		for(VariableEstructura var : variablesEstructura)
		{
			if(var.getNombre().equals(nombre))
				return var.getOffset();
		}
		return 0;
	}
}

