package generacionDeCodigo;

import java.io.PrintWriter;
import java.io.Writer;

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
import ast.CondIf;
import ast.CteChar;
import ast.Definicion;
import ast.DefinicionEstructura;
import ast.DefinicionFuncion;
import ast.DefinicionVariable;
import ast.Expresion;
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
import ast.Print;
import ast.Printsp;
import ast.Programa;
import ast.Read;
import ast.Return;
import ast.Sentencia;
import ast.Switch;
import ast.Tipo;
import ast.VariableEstructura;

public class SeleccionDeInstrucciones extends DefaultVisitor {

	public SeleccionDeInstrucciones(Writer writer, String sourceFile) {
		this.writer = new PrintWriter(writer);
		this.sourceFile = sourceFile;
		contadorEtiqueta=0;
	}
	
	public final static String VALOR = "valor";
	public final static String DIRECCION = "direccion";
	public int contadorEtiqueta;
	/*
	 * Poner aquí los visit necesarios.
	 * Si se ha usado VGen solo hay que copiarlos de la clase 'visitor/_PlantillaParaVisitors.txt'.
	 */
		public Object visit(Programa node, Object param) {
			genera("#source \"" + sourceFile + "\"\n");
			for (Definicion def : node.getDefiniciones())
				if (!(def instanceof DefinicionFuncion))
					def.accept(this, param);
			
			//CALL
			genera("\n' Invocation to the " + node.getDefFun().getNombre() +" function");
			genera("call " + node.getDefFun().getNombre());
			
			//HALT
			genera("halt");
			
			for (int i=0; i<node.getDefiniciones().size()-1; i++)
				if (node.getDefiniciones().get(i) instanceof DefinicionFuncion)
					node.getDefiniciones().get(i).accept(this, param);
			
			node.getDefFun().accept(this, param);

			return null;
		}
		
	/*
	 ejecutar[[DefinicionVariable: definicion -> nombre: identificador tipo]]
		<offset>(definicion)
	 */
	public Object visit(DefinicionVariable node, Object param) {
		//node.getTipo().accept(this, param);
		
		String type = typeDef(node);

		generaCompl("\t ' * var " + node.getNombre() + " " + type + " (Offset " + String.valueOf(node.getOffset()) + ")");
		return null;
	}
	
	public Object visit(DefinicionFuncion node, Object param) {
		genera(" " + node.getNombre() +":");
		genera(" ' * Parametros");	
		if (node.getParametros() != null) {
			for (Definicion params : node.getParametros())
				params.accept(this, param);
		}
		genera(" ' * Variables Locales");
		if (node.getDefiniciones() != null)
			for (Definicion defs : node.getDefiniciones()) {
				defs.accept(this, param);
			}
		genera("\n\tenter " + node.getBytesLocales());
		int linea = 0;
		if (node.getSentencias() != null)
			for (Sentencia sent : node.getSentencias()) {
				if (sent.getStart().getLine() != linea)
					genera("\n#line\t" + sent.getStart().getLine());
				linea = sent.getStart().getLine();
				sent.accept(this, node);
			}
		if(node.getRetorno().getClass().equals(NoRetorno.class))
			genera("\tret 0, " + node.getBytesLocales() + ", " + node.getBytesParams());
		return null;
	}
	
	public Object visit(Parametro node, Object param)
	{
		//node.getTipo().accept(this, param);
		String type = typeDef(node);
		genera("\t ' * var " + node.getNombre() + " " + type + " (Offset " + String.valueOf(node.getOffset()) + ")");
		return null;
	}
	
	
	public Object visit(Return node, Object param)
	{
		node.getDevolucion().accept(this, VALOR);
		genera("\t' * Return");
		DefinicionFuncion def = (DefinicionFuncion) param;
		genera("\tret " + def.getRetorno().getBytes() + "," + def.getBytesLocales() + "," + def.getBytesParams());
		return null;
	}
	
	/*
	valor[[operacionLogica: expresion -> left:expresion  operador:string right:expresion]]
			valor[[left]]
			valor[[right]]
			si operador es "&&"
			<and>
			si operador es "||"
			<or>
	*/
	public Object visit(Operador op, Object param) {
		
		
		switch (op.getOperador()) {
		case "and":
			op.getLeft().accept(this, VALOR);
			op.getRight().accept(this, VALOR);
			genera("\tand");
			break;
		case "or":
			op.getLeft().accept(this, VALOR);
			op.getRight().accept(this, VALOR);
			genera("\tor");
			break;
		case ("+"):
			op.getLeft().accept(this, VALOR);
			op.getRight().accept(this, VALOR);
			if(op.getLeft().getTipo() instanceof FloatType) 
				genera("\taddf");
			else if(op.getLeft().getTipo() instanceof IntType) 
				genera("\taddi");
			break;
		case("-"):
			op.getLeft().accept(this, VALOR);
			op.getRight().accept(this, VALOR);
			if(op.getLeft().getTipo() instanceof FloatType) 
				genera("\tsubf");
			else if(op.getLeft().getTipo() instanceof IntType) 
				genera("\tsubi");
			break;
		case("*"):
			op.getLeft().accept(this, VALOR);
			op.getRight().accept(this, VALOR);
			if(op.getLeft().getTipo() instanceof FloatType) 
				genera("\tmulf");
			else if(op.getLeft().getTipo() instanceof IntType) 
				genera("\tmuldi");
			break;
		case("/"):
			op.getLeft().accept(this, VALOR);
			op.getRight().accept(this, VALOR);
			if(op.getLeft().getTipo() instanceof FloatType) 
				genera("\tdivf");
			else if(op.getLeft().getTipo() instanceof IntType) 
				genera("\tdivi");
			break;
		case("[]"):
			op.getLeft().accept(this, DIRECCION);
			op.getRight().accept(this, VALOR);
			genera("\tpush " + op.getTipo().getBytes());
			genera("\tmuli");
			genera("\taddi");
		
			break;
		}
		return null;
	}
	
	public Object visit(Comparacion node, Object param)
	{
		node.getLeft().accept(this, VALOR);
		node.getRight().accept(this, VALOR);
		Tipo t = node.getLeft().getTipo();
		switch(node.getOperador())
		{
		case(">"):
			if(t instanceof FloatType) 
				genera("\tgtf");
			else if(t instanceof IntType)
				genera("\tgti");
			break;
		case(">="):
			if(t instanceof FloatType) 
				genera("\tgef");
			else if(t instanceof IntType)
				genera("\tgei");
			break;
		case("<"):
			if(t instanceof FloatType) 
				genera("\tltf");
			else if(t instanceof IntType)
				genera("\tlti");
			break;
		case("<="):
			if(t instanceof FloatType) 
				genera("\tlef");
			else if(t instanceof IntType)
				genera("\tlei");
			break;
		case("=="):
			if(t instanceof FloatType) 
				genera("\teqf");
			else if(t instanceof IntType)
				genera("\teqi");
			break;
		case("!="):
			if(t instanceof FloatType) 
				genera("\tnef");
			else if(t instanceof IntType)
				genera("\tnei");
			break;
		}
		
		return null;
	}

	
	public Object visit(LiteralInt node, Object param)
	{
		genera("\tpushi " + node.getValor());
		return null;
	}
	
	public Object visit(LiteralReal node, Object param)
	{
		genera("\tpushf " + node.getValor());
		return null;
	}
	
	public Object visit(CteChar node, Object param)
	{
		genera("\tpushb " + Integer.valueOf(node.getValor().replace("'", "").charAt(0)));
		return null;
	}
	
	/*@Override
	public Object visit(CharType node, Object param) {
		generaCompl(" char ");
		return null;
	}
	
	@Override
	public Object visit(IntType node, Object param) {
		generaCompl(" int ");	
		return null;
	}
	
	@Override
	public Object visit(FloatType node, Object param) {
		generaCompl(" float ");	
		return null;
	}*/
	
	@Override
	public Object visit(Array node, Object param) {
		generaCompl("[" + node.getDimension() + ", ");
		node.getRetorno().accept(this, param);
		generaCompl("]\n");
		return null;
	}
	
	public Object visit(IdentType node, Object param)
	{
		generaCompl("(");
		DefinicionEstructura def = (DefinicionEstructura)node.getDefinicion();
		for (VariableEstructura variable : def.getVariablesEstructura()) {
			variable.accept(this, param);
			if (!def.getVariablesEstructura().get(def.getVariablesEstructura().size() - 1).equals(variable))
				generaCompl("x");
		}
		generaCompl(") ");
		return null;
	}
	
	@Override
	public Object visit(Asignacion node, Object param) {
		node.getLeft().accept(this, DIRECCION);
		node.getRight().accept(this, VALOR);
		store(node.getLeft().getTipo());
		return null;
	}
	
	 @Override
	 public Object visit(AccesoCampo node, Object param) {
		 
		 if (param.equals(VALOR) || param.equals(DIRECCION))
		 {
			 //DIRECCIÓN
			super.visit(node, param);
			int desplazamiento = (((DefinicionEstructura)((IdentType) node.getLeft().getTipo()).getDefinicion()).getOffsetCampo(node.getCampo()));
			genera("\tpushi " + String.valueOf(desplazamiento));
			genera("\taddi");
			
			if(param.equals(VALOR)){
				//VALOR
				Tipo t = node.getTipo();
				if(t instanceof CharType)
					genera("\tloadb");
				else if(t instanceof IntType)
					genera("\tloadi");
				else if(t instanceof FloatType)
					genera("\tloadf");
				else
					genera("\tload");
			}
		 }
		return null;
		 
	 }

		@Override
		public Object visit(BucleWhile node, Object param) {
			String etiquetaWhile = generarEtiqueta();
			genera(etiquetaWhile + ":");
			node.getCondicion().accept(this, VALOR);
			String etiquetaNoCumple = generarEtiqueta();
			genera("\tjz " + etiquetaNoCumple);
			
			for(Sentencia e : node.getCuerpo())
				e.accept(this, contadorEtiqueta-1);
			
			genera("\tjmp " + etiquetaWhile);
			genera(etiquetaNoCumple + ":");
			return null;
		}

		@Override
		public Object visit(CondIf node, Object param) {
			String etiquetaElse = generarEtiqueta();
			node.getCondicion().accept(this, VALOR);
			String etiquetaFinElse = generarEtiqueta();
			genera("\tjz " + etiquetaElse);
			for(Sentencia s : node.getCuerpo())
				s.accept(this, param);
			genera("\tjmp " + etiquetaFinElse);
			genera(etiquetaElse + ":");
			if(node.getCondelse() != null)
			{
				for(Sentencia s : node.getCondelse().getInstrucciones())
				s.accept(this, param);			
			}
			genera(etiquetaFinElse + ":");
			return null;
		}
	

		
		@Override
		public Object visit(InvocarFuncion node, Object param) {
			if (node.getParametros() != null)
				for (Expresion params : node.getParametros())
					params.accept(this, VALOR);

			genera("' Invocation to the " + node.getNombre().getValor() +" function");
			genera("\tcall " + node.getNombre().getValor());	
			if (!(node.getTipo() instanceof NoRetorno))
			{
				Tipo t = node.getTipo();
				if(t instanceof CharType)
					genera("\tpopb");
				else if(t instanceof IntType)
					genera("\tpopi");
				else if(t instanceof FloatType)
					genera("\tpopf");
				else
					genera("\tpop");
			}
			return null;
		}
		
		@Override
		public Object visit(Ident node, Object param) {
			if (node.getDefinicion().getAmbito() == 1)
					genera("\tpusha " + node.getDefinicion().getOffset());
			else {
					genera("\tpush " + "bp");
					genera("\tpush " + String.valueOf(node.getDefinicion().getOffset()));
					genera("\taddi");
			}
			if(param != null){
				if(param.equals(VALOR)){
					Tipo t = node.getDefinicion().getTipo();
					
					if(t instanceof CharType)
						genera("\tloadb");
					else if(t instanceof IntType)
						genera("\tloadi");
					else if(t instanceof FloatType)
						genera("\tloadf");
					else
						genera("\tload");
				}
			}
			return null;
		}

	public Object visit(Cast node, Object param)
	{
		node.getExpr().accept(this, param);
		Tipo tipoExp = node.getExpr().getTipo();
		Tipo tipoConvertido = node.getTipo();
		
		if(tipoExp instanceof CharType) {
			if(tipoConvertido instanceof IntType) 
				genera("\tb2i");

			else if(tipoConvertido instanceof FloatType)
				genera("\tb2i\ni2f");
		}
		else if(tipoExp instanceof IntType){
			if(tipoConvertido instanceof FloatType)
				genera("\ti2f");
			
			else if(tipoConvertido instanceof CharType)
				genera("\ti2b");
		}
		else if(tipoExp instanceof FloatType) {
			if(tipoConvertido instanceof IntType)
				genera("\tf2i");
			if(tipoConvertido instanceof CharType)
				genera("\tf2i\ni2b\n");
		}
		
		return null;
	}
	
	public Object visit(Print node, Object param)
	{
		node.getExpresion().accept(this, VALOR);
		Tipo t = node.getExpresion().getTipo();
		
		if(t instanceof CharType)
			genera("\toutb");
		else if(t instanceof IntType)
			genera("\touti");
		else if(t instanceof FloatType)
			genera("\toutf");
		else
			genera("\tout");
		
		return null;
	}
	
	public Object visit(Printsp node, Object param)
	{
		node.getExpresion().accept(this, VALOR);
		Tipo t = node.getExpresion().getTipo();
		
		if(t instanceof CharType)
			genera("\toutb");
		else if(t instanceof IntType)
			genera("\touti");
		else if(t instanceof FloatType)
			genera("\toutf");
		else
			genera("\tout");
		genera("\tpushb" +  (int)'\n');
		genera("\toutb");
		return null;
	}
	
	public Object visit(Read node, Object param)
	{
		genera("\t' * Read\n");
		node.getExpresion().accept(this, DIRECCION);
		
		Tipo t = node.getExpresion().getTipo();
		
		if(t instanceof FloatType)
			genera("\tinf");
		else if(t instanceof CharType)
			genera("\tinb");
		else 
			genera("\tin");
		
		if(t instanceof FloatType)
			genera("\tinf\n");
		else if(t instanceof CharType)
			genera("\tinb\n");
		else 
			genera("\tin\n");
		store(node.getExpresion().getTipo());
		return null;
	}
	
	
	public Object visit(MasMas node, Object param)
	{
		node.getExpresion().accept(this, DIRECCION);
		node.getExpresion().accept(this, VALOR);
		genera("\tpushi " + 1);
		genera("\taddi");
		store(new IntType());
		return null;
	}
	
	
	public Object visit(OperadorRango node, Object param)
	{
		node.getValor().accept(this, VALOR);
		node.getMenor().accept(this, VALOR);
		genera("\tlti");
		node.getValor().accept(this, VALOR);
		node.getMayor().accept(this, VALOR);
		genera("\tgti");
		genera("\tand");
		return null;
	}
	
	public Object visit(Break node, Object param)
	{
		genera("#linea\t" + node.getStart().getLine());
		genera("\t ' * " + node.toString());
		genera("\tjmp etiqueta_" + (int)param);
		return null;
	}
	
	public Object visit(OperadorTernario node, Object param)
	{
		if(param.equals(VALOR)){
			node.getComparacion().accept(this, VALOR);
			genera("#linea\t" + node.getStart().getLine() + " OT");
			
			String etiquetaNoCumpleCondicion = generarEtiqueta();
			String etiquetaFin = generarEtiqueta();
	
			
			genera("\tjz " + etiquetaNoCumpleCondicion);
			
			node.getSi().accept(this, VALOR);
			genera("\tjmp " + etiquetaFin);
			
			genera(etiquetaNoCumpleCondicion + ":");
			node.getNo().accept(this, VALOR);
			
			genera(etiquetaFin + ":");
		}
		return null;
	}
	
	public Object visit(Switch node, Object param)
	{
		String etiquetaCase = generarEtiqueta();
		String etiquetaFinSwitch = generarEtiqueta();
		for(Case ca : node.getCases())
		{
			node.getCondicion().accept(this, VALOR);
			ca.getResultado().accept(this, VALOR);
			genera("\tjz " + etiquetaCase);
			for(Sentencia s : ca.getSentencias())
				s.accept(this, param);
			genera("\tjmp " + etiquetaFinSwitch);
			genera(etiquetaCase + " :");
		}
		genera(etiquetaFinSwitch + " :");
		return null;
	}
	
	
	
	private void store(Tipo t) {
		if(t instanceof CharType)
			genera("\tstoreb");
		else if(t instanceof IntType)
			genera("\tstorei");
		else if(t instanceof FloatType)
			genera("\tstoref");
		else
			genera("\tstore");
	}
	
	
	private String typeDef(Definicion node)
	{
		String type = "";
		if(node.getTipo().getClass().equals(FloatType.class))
			type = "Float";
		else if(node.getTipo().getClass().equals(IntType.class))
			type = "Int";
		else if(node.getTipo().getClass().equals(CharType.class))
			type = "Char";
		else if(node.getTipo().getClass().equals(Array.class))
			type = "Array";
		else if(node.getTipo().getClass().equals(IdentType.class))
			type = ((IdentType)node.getTipo()).getIdent();
		
		return type;
	}
	
	public String generarEtiqueta(){
		String etiqueta = "etiqueta_" + contadorEtiqueta;
		contadorEtiqueta++;
		return etiqueta;
	}
	
	// Método auxiliar recomendado -------------
	private void genera(String instruccion) {
		writer.println(instruccion);
	}
	
	/**
	 * Sin salto de linea
	 */
	private void generaCompl(String instruccion){
		writer.print(instruccion);;
	}

	private PrintWriter writer;
	private String sourceFile;
}
