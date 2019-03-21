/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	condElse -> instrucciones:sentencia*

public class CondElse extends AbstractTraceable implements AST {

	public CondElse(List<Sentencia> instrucciones) {
		this.instrucciones = instrucciones;

		searchForPositions(instrucciones);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public CondElse(Object instrucciones) {
		this.instrucciones = (List<Sentencia>) instrucciones;

		searchForPositions(instrucciones);	// Obtener linea/columna a partir de los hijos
	}

	public List<Sentencia> getInstrucciones() {
		return instrucciones;
	}
	public void setInstrucciones(List<Sentencia> instrucciones) {
		this.instrucciones = instrucciones;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private List<Sentencia> instrucciones;
}

