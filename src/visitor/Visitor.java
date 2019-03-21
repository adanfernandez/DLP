/**
 * @generated VGen 1.3.3
 */

package visitor;

import ast.*;

public interface Visitor {
	public Object visit(Programa node, Object param);
	public Object visit(DefinicionVariable node, Object param);
	public Object visit(DefinicionFuncion node, Object param);
	public Object visit(Parametro node, Object param);
	public Object visit(Print node, Object param);
	public Object visit(Printsp node, Object param);
	public Object visit(Read node, Object param);
	public Object visit(Asigna node, Object param);
	public Object visit(CondIf node, Object param);
	public Object visit(CondElse node, Object param);
	public Object visit(BucleWhile node, Object param);
	public Object visit(Return node, Object param);
	public Object visit(IntType node, Object param);
	public Object visit(FloatType node, Object param);
	public Object visit(CharType node, Object param);
	public Object visit(IdentType node, Object param);
	public Object visit(Array node, Object param);
	public Object visit(NoRetorno node, Object param);
	public Object visit(DefinicionEstructura node, Object param);
	public Object visit(VariableEstructura node, Object param);
	public Object visit(Operador node, Object param);
	public Object visit(Asignacion node, Object param);
	public Object visit(Comparacion node, Object param);
	public Object visit(Vacio node, Object param);
	public Object visit(LiteralInt node, Object param);
	public Object visit(LiteralReal node, Object param);
	public Object visit(CteChar node, Object param);
	public Object visit(Ident node, Object param);
	public Object visit(Cast node, Object param);
	public Object visit(InvocarFuncion node, Object param);
	public Object visit(AccesoCampo node, Object param);
	public Object visit(MasMas node, Object param);
	public Object visit(OperadorRango node, Object param);
	public Object visit(Break break1, Object param);
	public Object visit(OperadorTernario node, Object param);
	public Object visit(Switch node, Object param);
	public Object visit(Case node, Object param);
}
