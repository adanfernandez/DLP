/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	vacio:expresion -> 

public class Vacio extends AbstractExpresion {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

}

