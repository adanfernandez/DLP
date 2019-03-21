/**
 * @generated VGen 1.3.3
 */

package ast;

public interface Definicion extends AST {
	
	public Tipo getTipo();
	
	public int getOffset();
	public void setOffset(int offset);
	
	public int getAmbito();
	public void setAmbito(int ambito);
}

