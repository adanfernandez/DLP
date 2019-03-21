package ast;

import visitor.Visitor;

public class OperadorTernario extends AbstractTraceable implements Expresion {

	
	private Expresion comparacion,  si,  no;
	
	public OperadorTernario (Expresion comparacion, Expresion si, Expresion no)
	{
		this.comparacion = comparacion;
		this.si = si;
		this.no = no;
	}
	
	public OperadorTernario (Object comparacion, Object si, Object no)
	{
		this.comparacion = (Expresion) comparacion;
		this.si = (Expresion) si;
		this.no = (Expresion) no;
	}

	@Override
	public Object accept(Visitor visitor, Object param) {
		return visitor.visit(this, param);
	}

	
	private Tipo tipo;
	
	@Override
	public Tipo getTipo() {
		return tipo;
	}

	@Override
	public void setTipo(Tipo tipo) {
		this.tipo=tipo;
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

	public Expresion getComparacion() {
		return comparacion;
	}

	public void setComparacion(Expresion comparacion) {
		this.comparacion = comparacion;
	}

	public Expresion getSi() {
		return si;
	}

	public void setSi(Expresion si) {
		this.si = si;
	}

	public Expresion getNo() {
		return no;
	}

	public void setNo(Expresion no) {
		this.no = no;
	}

}
