/**
 * @generated VGen 1.3.3
 */

package visitor;

import ast.*;

import java.util.*;

/*
DefaultVisitor. Implementación base del visitor para ser derivada por nuevos visitor.
	No modificar esta clase. Para crear nuevos visitor usar el fichero "_PlantillaParaVisitors.txt".
	DefaultVisitor ofrece una implementación por defecto de cada nodo que se limita a visitar los nodos hijos.
*/
public class DefaultVisitor implements Visitor {

	//	class Programa { List<Definicion> definiciones; }
	public Object visit(Programa node, Object param) {
		visitChildren(node.getDefiniciones(), param);
		return null;
	}

	//	class DefinicionVariable { String nombre;  Tipo tipo; }
	public Object visit(DefinicionVariable node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class DefinicionFuncion { String nombre;  List<Parametro> parametros;  Tipo retorno;  List<DefinicionVariable> definiciones;  List<Sentencia> sentencias; }
	public Object visit(DefinicionFuncion node, Object param) {
		visitChildren(node.getParametros(), param);
		if (node.getRetorno() != null)
			node.getRetorno().accept(this, param);
		visitChildren(node.getDefiniciones(), param);
		visitChildren(node.getSentencias(), param);
		return null;
	}

	//	class Parametro { String nombre;  Tipo tipo; }
	public Object visit(Parametro node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class Print { Expresion expresion; }
	public Object visit(Print node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class Printsp { Expresion expresion; }
	public Object visit(Printsp node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class Read { Expresion expresion; }
	public Object visit(Read node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class Asigna { Expresion left;  Expresion right; }
	public Object visit(Asigna node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		return null;
	}

	//	class CondIf { Expresion condicion;  List<Sentencia> cuerpo;  CondElse condelse; }
	public Object visit(CondIf node, Object param) {
		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		visitChildren(node.getCuerpo(), param);
		if (node.getCondelse() != null)
			node.getCondelse().accept(this, param);
		return null;
	}

	//	class CondElse { List<Sentencia> instrucciones; }
	public Object visit(CondElse node, Object param) {
		visitChildren(node.getInstrucciones(), param);
		return null;
	}

	//	class BucleWhile { Expresion condicion;  List<Sentencia> cuerpo; }
	public Object visit(BucleWhile node, Object param) {
		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		visitChildren(node.getCuerpo(), param);
		return null;
	}

	//	class Return { Expresion devolucion; }
	public Object visit(Return node, Object param) {
		if (node.getDevolucion() != null)
			node.getDevolucion().accept(this, param);
		return null;
	}

	//	class IntType {  }
	public Object visit(IntType node, Object param) {
		return null;
	}

	//	class FloatType {  }
	public Object visit(FloatType node, Object param) {
		return null;
	}

	//	class CharType {  }
	public Object visit(CharType node, Object param) {
		return null;
	}

	//	class IdentType { Ident ident; }
	public Object visit(IdentType node, Object param) {
		return null;
	}

	//	class Array { Expresion dimension;  Tipo retorno; }

	public Object visit(Array node, Object param) {
		if (node.getRetorno() != null)
			node.getRetorno().accept(this, param);
		return null;
	}

	//	class NoRetorno {  }
	public Object visit(NoRetorno node, Object param) {
		return null;
	}

	//	class DefinicionEstructura { String nombre;  List<VariableEstructura> variablesEstructura; }
	public Object visit(DefinicionEstructura node, Object param) {
		visitChildren(node.getVariablesEstructura(), param);
		return null;
	}

	//	class VariableEstructura { String nombre;  Tipo tipo; }
	public Object visit(VariableEstructura node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class Operador { Expresion left;  String operador;  Expresion right; }
	public Object visit(Operador node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		return null;
	}

	//	class Asignacion { Expresion left;  Expresion right; }
	public Object visit(Asignacion node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		return null;
	}

	//	class Comparacion { Expresion left;  String operador;  Expresion right; }
	public Object visit(Comparacion node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		return null;
	}

	//	class Vacio {  }
	public Object visit(Vacio node, Object param) {
		return null;
	}

	//	class LiteralInt { String valor; }
	public Object visit(LiteralInt node, Object param) {
		return null;
	}

	//	class LiteralReal { String valor; }
	public Object visit(LiteralReal node, Object param) {
		return null;
	}

	//	class CteChar { String valor; }
	public Object visit(CteChar node, Object param) {
		return null;
	}

	//	class Ident { String valor; }
	public Object visit(Ident node, Object param) {
		return null;
	}

	//	class Cast { Tipo tipo;  Expresion expr; }
	public Object visit(Cast node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		if (node.getExpr() != null)
			node.getExpr().accept(this, param);
		return null;
	}

	//	class InvocarFuncion { Ident nombre;  List<Expresion> parametros; }
	public Object visit(InvocarFuncion node, Object param) {
		if (node.getNombre() != null)
			node.getNombre().accept(this, param);
		visitChildren(node.getParametros(), param);
		return null;
	}
	
	// Método auxiliar -----------------------------
	protected void visitChildren(List<? extends AST> children, Object param) {
		if (children != null)
			for (AST child : children)
				child.accept(this, param);
	}

	@Override
	public Object visit(AccesoCampo node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		
		return null;
	}

	@Override
	public Object visit(MasMas node, Object param) {
		if(node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		
		return null;
	}

	@Override
	public Object visit(OperadorRango node, Object param) {
		if(node.getMenor() != null)
			node.getMenor().accept(this, param);
		if(node.getMayor() != null)
			node.getMayor().accept(this, param);
		if(node.getValor() != null)
			node.getValor().accept(this, param);
		return null;
	}

	@Override
	public Object visit(Break break1, Object param) {
		return null;
	}

	@Override
	public Object visit(OperadorTernario node, Object param) {
		if(node.getComparacion() != null)
			node.getComparacion().accept(this, null);
		if(node.getSi() != null)
			node.getSi().accept(this, null);
		if(node.getNo() != null)
			node.getNo().accept(this, null);
		return null;
	}

	@Override
	public Object visit(Switch node, Object param) {
		if(node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		visitChildren(node.getCases(), param);
		return null;
	}

	@Override
	public Object visit(Case node, Object param) {
		if(node.getResultado() != null)
			node.getResultado().accept(this, param);
		visitChildren(node.getSentencias(), param);
		return null;
	}
	
}
