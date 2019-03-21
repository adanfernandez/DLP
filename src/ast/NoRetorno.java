/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	NoRetorno:tipo -> 

public class NoRetorno extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	@Override
	public int getBytes() {
		return 0;
	}
}

