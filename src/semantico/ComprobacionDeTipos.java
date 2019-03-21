package semantico;

import main.GestorErrores;
import visitor.DefaultVisitor;
import ast.AccesoCampo;
import ast.Array;
import ast.Asignacion;
import ast.Break;
import ast.BucleWhile;
import ast.Case;
import ast.Cast;
import ast.CharType;
import ast.Comparacion;
import ast.CondElse;
import ast.CondIf;
import ast.CteChar;
import ast.Definicion;
import ast.DefinicionEstructura;
import ast.DefinicionFuncion;
import ast.DefinicionVariable;
import ast.FloatType;
import ast.Ident;
import ast.IdentType;
import ast.IntType;
import ast.InvocarFuncion;
import ast.LiteralInt;
import ast.LiteralReal;
import ast.MasMas;
import ast.NoRetorno;
import ast.Operador;
import ast.OperadorRango;
import ast.OperadorTernario;
import ast.Parametro;
import ast.Position;
import ast.Print;
import ast.Printsp;
import ast.Read;
import ast.Return;
import ast.Sentencia;
import ast.Switch;
import ast.Tipo;
import ast.Vacio;

public class ComprobacionDeTipos extends DefaultVisitor {

	public final static String ESWHILE = "while";
	
	
	public ComprobacionDeTipos(GestorErrores gestor) {
		this.gestorErrores = gestor;
	}

	
	//	class DefinicionVariable { String nombre;  Tipo tipo; }
	public Object visit(DefinicionVariable node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, node.getTipo());
		return null;
	}

	//	class DefinicionFuncion { String nombre;  List<Parametro> parametros;  Tipo retorno;  List<DefinicionVariable> definiciones;  List<Sentencia> sentencias; }
	public Object visit(DefinicionFuncion node, Object param) {	
		for(Parametro p : node.getParametros())
			p.accept(this, param);
		if (node.getRetorno() != null)
			node.getRetorno().accept(this, param);
		for(Definicion def:node.getDefiniciones())
			def.accept(this, param);
		for(Sentencia sentencia:node.getSentencias())
			sentencia.accept(this, node.getTipo());

		predicado(esTipoSimple(node.getRetorno()),
				"El tipo de retorno tiene que ser simple",
				node.getStart());

		if(!(node.getRetorno() instanceof NoRetorno))
		{
			predicado(node.getSentencias().size()!=0,
					"Falta una sentencia return",
					node.getStart());
			if(node.getSentencias().size() == 1 && !(node.getSentencias().get(0) instanceof Return))
			{
				predicado(false,
						"Falta sentencia return en la función",
						node.getStart());
			}
			for(int i=0; i<node.getSentencias().size() -1; i++)
			{
				predicado(!(node.getSentencias().get(i) instanceof Return),
						"El return tiene que ser la última instrucción",
						node.getSentencias().get(i).getStart());
				predicado(node.getSentencias().get(node.getSentencias().size()-1) instanceof Return,
						"Falta la sentencia Return en la función",
						node.getStart());
			}
		}
		return null;
	}


	//	class Parametro { String nombre;  Tipo tipo; }
	public Object visit(Parametro node, Object param) {
		super.visit(node, param);
		predicado(esTipoSimple(node.getTipo()),
				"Los parámetros tienen que ser de tipo primitivo",
				node.getStart());		
		return null;
	}

	//	class Print { Expresion expresion; }
	public Object visit(Print node, Object param) {	
		super.visit(node, param);

		if(node.getExpresion().getTipo() != null )
			predicado(esTipoSimple(node.getExpresion().getTipo()), "El tipo de la expresión tiene que ser simple", node.getStart());
		
		if(node.getExpresion() instanceof InvocarFuncion)
		{
			InvocarFuncion invocacion = ((InvocarFuncion)node.getExpresion());
			predicado(!invocacion.getFuncion().getRetorno().getClass().equals(NoRetorno.class),
					"La función tiene que tener algún tipo de retorno",
					node.getStart());
		}
		
		return null;
	}

	//	class Printsp { Expresion expresion; }
	public Object visit(Printsp node, Object param) {
		super.visit(node, param);

		if(node.getExpresion().getTipo() != null )
			predicado(esTipoSimple(node.getExpresion().getTipo()), "El tipo de la expresión tiene que ser simple", node.getStart());
		
		if(node.getExpresion() instanceof InvocarFuncion)
		{
			InvocarFuncion invocacion = ((InvocarFuncion)node.getExpresion());
			predicado(!invocacion.getFuncion().getRetorno().getClass().equals(NoRetorno.class),
					"La función tiene que tener algún tipo de retorno",
					node.getStart());
		}
		
		return null;
	}

	//	class Read { Expresion expresion; }
	public Object visit(Read node, Object param) {
		super.visit(node, param);
		
		predicado(esTipoSimple(node.getExpresion().getTipo()),
				"Tiene que ser tipo simple",
				node.getStart());
		
		predicado(node.getExpresion().isModificable(),
				"Tiene que ser un tipo modificable",
				node.getStart());
		
		return null;
	}
	

	//	class CondIf { Expresion condicion;  List<Sentencia> cuerpo;  CondElse condelse; }
	public Object visit(CondIf node, Object param) {
		super.visit(node, param);
		predicado(node.getCondicion().getTipo().getClass().equals(IntType.class), "La comprobación tiene que ser de tipo entera.", node.getStart());
		return null;
	}

	//	class CondElse { List<Sentencia> instrucciones; }
	public Object visit(CondElse node, Object param) {
		visitChildren(node.getInstrucciones(), param);
		return null;
	}

	//	class BucleWhile { Expresion condicion;  List<Sentencia> cuerpo; }
	public Object visit(BucleWhile node, Object param) {
		super.visit(node, ESWHILE);
		predicado(node.getCondicion().getTipo().getClass().equals(IntType.class), "La comprobación tiene que ser de tipo entera.", node.getStart());
		return null;
	}

	//	class Return { Expresion devolucion; }
	public Object visit(Return node, Object param) {
		super.visit(node, param);
	
		
		if(param.getClass().equals(NoRetorno.class))
		{
			predicado(node.getDevolucion().getClass().equals(Vacio.class),
					"El return no debe tener expresión en funciones void",
					node.getStart());
		}
		else{
			if(!node.getDevolucion().getClass().equals(Vacio.class)){
				predicado(param.getClass().equals(node.getDevolucion().getTipo().getClass()),
						"El tipo de retorno no coincide",
						node.getStart());
				
			}
			else
				predicado(node.getDevolucion() == null,
					"Tiene que haber un valor de retorno",
					node.getStart());
		}	
		return null;
	}


	//	class Array { Expresion dimension;  Tipo retorno; }
	public Object visit(Array node, Object param) {
		super.visit(node, param);		
		return null;
	}

	

	//	class DefinicionEstructura { String nombre;  List<VariableEstructura> variablesEstructura; }
	public Object visit(DefinicionEstructura node, Object param) {
		super.visit(node, node.getNombre());
		node.setTipo(new IdentType(node.getNombre()));
		return null;
	}



	//	class Operador { Expresion left;  String operador;  Expresion right; }
	public Object visit(Operador node, Object param) {
		
		super.visit(node, param);
		node.setModificable(false);
		if(node.getOperador().equals("and")
				|| node.getOperador().equals("or"))
		{
			boolean soniguales = node.getLeft().getTipo().getClass().equals(node.getRight().getTipo().getClass());
			predicado(node.getLeft().getTipo().getClass().equals(IntType.class)
					&& soniguales,
					"Los dos operandos tienen que ser enteros",
					node.getStart());
			node.setTipo(node.getLeft().getTipo());
		}
		if(node.getOperador().equals("[]"))
		{
			predicado(node.getRight().getTipo().getClass().equals(IntType.class),
	 				"El tipo de la dimensión del array tiene que ser entero",
					node.getStart());
			boolean correcto = node.getLeft().getTipo() instanceof Array;
			predicado(correcto, "La parte de la izquierda no es un array", node.getStart());
			if(correcto)
				node.setTipo(((Array)node.getLeft().getTipo()).getRetorno());
			node.setModificable(true);
		}
		else
		{
			predicado((node.getLeft().getTipo().getClass().equals(IntType.class) || node.getLeft().getTipo().getClass().equals(FloatType.class))
					&& ((node.getRight().getTipo().getClass().equals(IntType.class) || node.getRight().getTipo().getClass().equals(FloatType.class))),
				"Los operandos tienen que ser de tipo int o float",
				node.getStart());
			node.setTipo(node.getLeft().getTipo());
		}
		
		return null;
	}

	//	class Asignacion { Expresion left;  Expresion right; }
	public Object visit(Asignacion node, Object param) {
		super.visit(node, param);
		predicado(node.getLeft().isModificable(),
				"El operador de la izquierda tiene que ser modificable",
				node.getStart());
		predicado(esTipoSimple(node.getLeft().getTipo()),
				"El operando de la izquierda tiene que ser un tipo simple",
				node.getStart());
		predicado(node.getLeft().getTipo().getClass().equals(node.getRight().getTipo().getClass()),
				"En una asignación el tipo de la expresión de la izquierda tiene que ser como el de la expresión de la derecha.",
				node.getStart());
		return null;
	}

	//	class Comparacion { Expresion left;  String operador;  Expresion right; }
	public Object visit(Comparacion node, Object param) {
		super.visit(node, param);
		
		boolean sonIguales = node.getLeft().getTipo().getClass().equals(node.getRight().getTipo().getClass());
		predicado(sonIguales,
				"Los operandos tienen que ser del mismo tipo",
					node.getStart());
		
		if(sonIguales)
			predicado(esTipoSimple(node.getLeft().getTipo()),
					"Los operandos tienen que ser de tipo simple",
						node.getStart());
		
		
		node.setTipo(node.getLeft().getTipo());
		
		return null;
	}

	//	class Vacio {  }
	public Object visit(Vacio node, Object param) {
		super.visit(node, param);
		return null;
	}

	//	class LiteralInt { String valor; }
	public Object visit(LiteralInt node, Object param) {
		super.visit(node, param);
		node.setModificable(false);
		node.setTipo(new IntType());
		return null;
	}

	//	class LiteralReal { String valor; }
	public Object visit(LiteralReal node, Object param) {
		super.visit(node, param);
		node.setModificable(false);
		node.setTipo(new FloatType());
		return null;
	}

	//	class CteChar { String valor; }
	public Object visit(CteChar node, Object param) {
		super.visit(node, param);
		node.setModificable(false);
		node.setTipo(new CharType());
		return null;
	}

	//	class Ident { String valor; }
	public Object visit(Ident node, Object param) {
		Definicion def = node.getDefinicion();
		node.setModificable(true);
		node.setTipo(def.getTipo());
		return null;
	}

	//	class Cast { Tipo tipo;  Expresion expr; }
	public Object visit(Cast node, Object param) {
		
		super.visit(node, param);

		predicado(esTipoSimple(node.getTipo()) && esTipoSimple(node.getExpr().getTipo()),
				"Ambos parámetros tienen que ser un tipo simple",
				node.getStart());
		
		predicado(!node.getTipo().getClass().equals(node.getExpr().getTipo().getClass()),
				"Los tipos tienen que ser diferentes",
				node.getStart());
		
		node.setTipo(node.getTipo());
		return null;
	}

	//	class InvocarFuncion { Ident nombre;  List<Expresion> parametros; }
	public Object visit(InvocarFuncion node, Object param) {
		super.visit(node, param);
		
		DefinicionFuncion defFunc = node.getFuncion();
		
		if(defFunc != null){
			predicado(defFunc.getParametros().size()==node.getParametros().size(),
					"Número de parámetros incorrectos",
					node.getStart());
			if(defFunc.getParametros().size()==node.getParametros().size()){
				for(int i=0; i<defFunc.getParametros().size(); i++)
				{
					predicado(defFunc.getParametros().get(i).getTipo().getClass().equals(node.getParametros().get(i).getTipo().getClass()), 
					"El tipo de los parámetros no coincide",
					node.getStart());
				}
			}
			node.setTipo(defFunc.getTipo());
		}
		
		return null;
	}

	
	public Object visit(AccesoCampo node, Object param)
	{
		super.visit(node, param);
		node.setModificable(true);
		//comprobar que node.izda instance of identtype
		//accesoCampo.izda.tipo.definicion.contieneCampo(accesoCampo.dcha)
		
		if(node.getLeft().getTipo() instanceof IdentType)
		{
			IdentType identType = (IdentType) node.getLeft().getTipo();
			boolean correcto = identType.getDefinicion() != null;
			predicado(correcto,
					"No definido.",
					node.getStart());
			//System.out.println(node.getCampo());
			if(correcto){
				Tipo tipo = ((DefinicionEstructura)identType.getDefinicion()).contiene(node.getCampo());
				predicado(tipo != null,
						"La estructura no contiene el campo " + node.getCampo(),
						node.getStart());
				node.setTipo(tipo);
			}
		}
		else {
			predicado(false, "La parte izquierda no es una estructura", node.getStart());
		}
		return null;
	}
	
	public Object visit(MasMas node, Object param)
	{
		super.visit(node, param);
		
		predicado(node.getExpresion().getTipo().getClass().equals(IntType.class),
				"El operador '++' tiene que utilizarse con tipos enteros",
				node.getStart());
		predicado(node.getExpresion().isModificable(),
				"La variable no es modificable",
				node.getStart());		
		return null;
	}
	
	public Object visit(OperadorRango node, Object param)
	{
		super.visit(node, param);
		predicado(node.getMayor().getTipo().getClass().equals(IntType.class) && node.getMenor().getTipo().getClass().equals(IntType.class),
				"Ambos lados del rango tienen que ser de tipo entero",
				node.getStart());
		predicado(node.getValor().getTipo().getClass().equals(IntType.class),
				"El valor del medio del rango tiene que ser de tipo entero",
				node.getStart());
		node.setTipo(node.getMayor().getTipo());
		return null;
	}
	
	public Object visit(Break node, Object param)
	{
		
		boolean esCorrecto = param.equals(ESWHILE);
		
		predicado(esCorrecto,
				"La sentencia break solamente puede estar en bucles while",
				node.getStart());
		
		return null;
	}
	
	
	public Object visit(OperadorTernario node, Object param)
	{
		super.visit(node, param);
		
		Tipo typeSi = node.getSi().getTipo();
		Tipo typeNo = node.getNo().getTipo();
		Tipo typeComparacion= node.getComparacion().getTipo();
		
		predicado(typeSi.getClass().equals(typeNo.getClass()),
				"Los retornos tienen que ser del mismo tipo",
				node.getStart());
		
		predicado(esTipoSimple(typeSi),
				"Los retornos tienen que ser un tipo primitivo",
				node.getStart());
		
		predicado(typeComparacion.getClass().equals(IntType.class),
				"El tipo de la comparación tiene que ser entero",
				node.getStart());
		
		node.setTipo(node.getSi().getTipo());
		node.setModificable(false);
		
		return null;
	}
	
	public Object visit(Switch node, Object param)
	{
		node.getCondicion().accept(this, param);
		for(Case ca : node.getCases())
			ca.accept(this, node.getCondicion().getTipo());
		return param;
	}

	public Object visit(Case node, Object param)
	{
		super.visit(node, null);
		Tipo tipo = (Tipo) param;
		predicado(node.getResultado().getTipo().getClass().equals(tipo.getClass()),
				"El tipo de la condición del switch tiene que ser del mismo el del resultado del case.",
				node.getStart());
		
		return null;
	}
	

	/**
	 * Comprobar si un tipo es de tipo primitivo o no.
	 * @param node
	 * @return
	 */
	private boolean esTipoSimple(Tipo node)
	{
		return 
			(node.getClass().equals(IntType.class)
					|| node.getClass().equals(CharType.class)
					|| node.getClass().equals(FloatType.class)
					|| node.getClass().equals(NoRetorno.class)
					);
	}
	
	
	/**
	 * Método auxiliar opcional para ayudar a implementar los predicados de la Gramática Atribuida.
	 * 
	 * Ejemplo de uso (suponiendo implementado el método "esPrimitivo"):
	 * 	predicado(esPrimitivo(expr.tipo), "La expresión debe ser de un tipo primitivo", expr.getStart());
	 * 	predicado(esPrimitivo(expr.tipo), "La expresión debe ser de un tipo primitivo", null);
	 * 
	 * NOTA: El método getStart() indica la linea/columna del fichero fuente de donde se leyó el nodo.
	 * Si se usa VGen dicho método será generado en todos los nodos AST. Si no se quiere usar getStart() se puede pasar null.
	 * 
	 * @param condicion Debe cumplirse para que no se produzca un error
	 * @param mensajeError Se imprime si no se cumple la condición
	 * @param posicionError Fila y columna del fichero donde se ha producido el error. Es opcional (acepta null)
	 */
	private void predicado(boolean condicion, String mensajeError, Position posicionError) {
		if (!condicion)
			gestorErrores.error("Comprobación de tipos", mensajeError, posicionError);
	}
	
	private GestorErrores gestorErrores;
}
