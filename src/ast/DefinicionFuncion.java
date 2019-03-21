/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.List;

import visitor.Visitor;

//	definicionFuncion:definicion -> nombre:String  parametros:parametro*  retorno:tipo  definiciones:definicionVariable*  sentencias:sentencia*

public class DefinicionFuncion extends AbstractDefinicion {

	public DefinicionFuncion(String nombre, List<Parametro> parametros, Tipo retorno, List<DefinicionVariable> definiciones, List<Sentencia> sentencias) {
		this.nombre = nombre;
		this.parametros = parametros;
		this.retorno = retorno;
		this.definiciones = definiciones;
		this.sentencias = sentencias;

		searchForPositions(parametros, retorno, definiciones, sentencias);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public DefinicionFuncion(Object nombre, Object parametros, Object retorno, Object definiciones, Object sentencias) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;
		this.parametros = (List<Parametro>) parametros;
		this.retorno = (Tipo) retorno;
		this.definiciones = (List<DefinicionVariable>) definiciones;
		this.sentencias = (List<Sentencia>) sentencias;

		searchForPositions(nombre, parametros, retorno, definiciones, sentencias);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Parametro> getParametros() {
		return parametros;
	}
	public void setParametros(List<Parametro> parametros) {
		this.parametros = parametros;
	}

	public Tipo getRetorno() {
		return retorno;
	}
	public void setRetorno(Tipo retorno) {
		this.retorno = retorno;
	}

	public List<DefinicionVariable> getDefiniciones() {
		return definiciones;
	}
	public void setDefiniciones(List<DefinicionVariable> definiciones) {
		this.definiciones = definiciones;
	}

	public List<Sentencia> getSentencias() {
		return sentencias;
	}
	public void setSentencias(List<Sentencia> sentencias) {
		this.sentencias = sentencias;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;
	private List<Parametro> parametros;
	private Tipo retorno;
	private List<DefinicionVariable> definiciones;
	private List<Sentencia> sentencias;
	
	public Tipo getTipo()
	{
		return retorno;
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
	
	public int getBytesLocales() {
		int valor=0;
		if(definiciones!=null)
			for(Definicion def: definiciones) {
				valor+=def.getTipo().getBytes();
			}
		return valor;
	}
	
	public int getBytesParams() {
		int valor=0;
		if(parametros!=null)
			for(Definicion par: parametros) {
				valor+=par.getTipo().getBytes();
			}
		return valor;
		
	}
}

