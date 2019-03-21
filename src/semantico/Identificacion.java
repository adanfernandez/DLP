package semantico;

import main.GestorErrores;
import visitor.DefaultVisitor;
import ast.Definicion;
import ast.DefinicionEstructura;
import ast.DefinicionFuncion;
import ast.DefinicionVariable;
import ast.Expresion;
import ast.Ident;
import ast.IdentType;
import ast.InvocarFuncion;
import ast.Parametro;
import ast.Position;
import ast.Programa;
import ast.Sentencia;
import ast.VariableEstructura;

/**
 * @author Raúl Izquierdo
 */
public class Identificacion extends DefaultVisitor {

	// IF TAMAÑO = 1 ---- GLOBAL
	// IF TAMAÑO = 2 ---- LOCAL

	// PONER UN IF AL FINAL DE CADA VISIT Y ASIGNARLE EL ÁMBITO

	private GestorErrores gestorErrores;

	public Identificacion(GestorErrores gestor) {
		this.gestorErrores = gestor;
	}

	private ContextMap<String, Definicion> contextMap = new ContextMap<String, Definicion>();

	public Object visit(Programa node, Object param)
	{
		super.visit(node, param);
		boolean esMain1 = node.getDefiniciones().get(node.getDefiniciones().size()-1) instanceof DefinicionFuncion;
		if(esMain1)
		{
			predicado(((DefinicionFuncion)node.getDefiniciones().get(node.getDefiniciones().size()-1)).getNombre().equals("main"),
					"La última función tiene que ser una función main",
					node.getStart());
			node.setDefFunc(((DefinicionFuncion)node.getDefiniciones().get(node.getDefiniciones().size()-1)));
		}
		else
		{
			predicado(false,
					"El programa tiene que acabar en una función main",
					node.getStart());
		}
		return null;
	}
	
	public Object visit(DefinicionVariable node, Object param) {
		super.visit(node, param);
		Definicion definicion = contextMap.getFromTop(node.getNombre());
		predicado(definicion == null,
				"Variable ya definida: " + node.getNombre(), node.getStart());
		contextMap.put(node.getNombre(), node);
		asignarAmbito(node);
		return null;
	}

	public Object visit(Ident node, Object param) {
		super.visit(node, param);
		Definicion definicion = contextMap.getFromAny(node.getValor());
		predicado(definicion != null,
				"Variable no definida: " + node.getValor(), node.getStart());
		node.setDefinicion(definicion); // Enlazar referencia con definición
		return null;
	}

	public Object visit(DefinicionFuncion defFunc, Object param) {
		Definicion def = contextMap.getFromAny(defFunc.getNombre());
		predicado(def == null, "Función ya definida: " + defFunc.getNombre(),
				defFunc.getStart());
		contextMap.put(defFunc.getNombre(), defFunc);
		contextMap.set();
		for (Parametro parametro : defFunc.getParametros())
			parametro.accept(this, param);
		for (Definicion definicion : defFunc.getDefiniciones())
			definicion.accept(this, param);
		for (Sentencia sent : defFunc.getSentencias())
			sent.accept(this, param);
		contextMap.reset();
		asignarAmbito(defFunc);
		
		return null;
	}

	public Object visit(Parametro parametro, Object param) {
		Definicion definicion = contextMap.getFromTop(parametro.getNombre());
		predicado(definicion == null,
				"Parámetro ya definido: " + parametro.getNombre(),
				parametro.getStart());

		contextMap.put(parametro.getNombre(), parametro);
		asignarAmbito(parametro);
		return null;
	}

	public Object visit(DefinicionEstructura node, Object param) {
		Definicion definicion = contextMap.getFromTop(node.getNombre());
		predicado(definicion == null,
				"Estructura ya definida: " + node.getNombre(), node.getStart());
		contextMap.put(node.getNombre(), node);

		contextMap.set();
		for (VariableEstructura def : node.getVariablesEstructura()) {
			def.accept(this, param);
			def.setDefinicion(node);
		}
		contextMap.reset();
		asignarAmbito(node);
		return null;
	}

	/*public Object visit(Operador operador, Object param) {
		operador.getLeft().accept(this, param);
		if (!operador.getOperador().equals("."))
			operador.getRight().accept(this, param);
		return null;
	}*/

	public Object visit(InvocarFuncion invocarFuncion, Object param) {
		invocarFuncion.getNombre().accept(this, param);
		for (Expresion expr : invocarFuncion.getParametros())
			expr.accept(this, param);

		DefinicionFuncion def = (DefinicionFuncion) contextMap
				.getFromAny(invocarFuncion.getNombre().getValor());
		invocarFuncion.setFuncion(def);

		return null;
	}

	public Object visit(IdentType node, Object param) {
		super.visit(node, param);

		Definicion definicion = contextMap.getFromAny(node.getIdent());
		predicado(definicion != null,
				"Estructura no definida: " + node.getIdent(), node.getStart());
		node.setDefinicion(definicion);

		return null;
	}
	
	public Object visit(VariableEstructura node, Object param)
	{
		super.visit(node, param);
		
		Definicion definicion = contextMap.getFromTop(node.getNombre());
		predicado(definicion == null,
				"Variable ya definida",
				node.getStart());
		contextMap.put(node.getNombre(), node);
		return null;
	}



	
	/**
	 * Método auxiliar opcional para ayudar a implementar los predicados de la
	 * Gramática Atribuida.
	 * 
	 * Ejemplo de uso: predicado(variables.get(nombre),
	 * "La variable no ha sido definida", expr.getStart());
	 * predicado(variables.get(nombre), "La variable no ha sido definida",
	 * null);
	 * 
	 * NOTA: El método getStart() indica la linea/columna del fichero fuente de
	 * donde se leyó el nodo. Si se usa VGen dicho método será generado en todos
	 * los nodos AST. Si no se quiere usar getStart() se puede pasar null.
	 * 
	 * @param condicion
	 *            Debe cumplirse para que no se produzca un error
	 * @param mensajeError
	 *            Se imprime si no se cumple la condición
	 * @param posicionError
	 *            Fila y columna del fichero donde se ha producido el error. Es
	 *            opcional (acepta null)
	 */
	private void predicado(boolean condicion, String mensajeError,
			Position posicionError) {
		if (!condicion)
			gestorErrores.error("Identificación", mensajeError, posicionError);
	}
	
	/**
	 * Asignar el ámbito (global = 1, local = 2) a todas las definiciones
	 * @param node Definicion
	 */
	private void asignarAmbito(Definicion node)
	{
		node.setAmbito(contextMap.contextos().size());
	}
}