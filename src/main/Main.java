package main;

import generacionDeCodigo.*;

import java.io.*;

import semantico.*;
import sintactico.*;
import visitor.*;
import ast.*;


/**
 * Clase que inicia el compilador e invoca a todas sus fases.
 * 
 * No es necesario modificar este fichero. En su lugar hay que modificar:
 * - Para Análisis Sintáctico: 'sintactico/sintac.y' y 'sintactico/lexico.l'
 * - Para Análisis Semántico: 'semantico/Identificacion.java' y 'semantico/ComprobacionDeTipos.java'
 * - Para Generación de Código: 'generacionDeCodigo/GestionDeMemoria.java' y 'generacionDeCodigo/SeleccionDeInstrucciones.java'
 * 
 */
public class Main {
	//public static final String programa = "src/ProgramaVisitorComprobacionTipos.txt";	// Entrada a usar durante el desarrollo
	public static final String programa = "src/switchCase.txt";	// Entrada a usar durante el desarrollo
	//public static final String programa = "src/TiposAdan.txt";	// Entrada a usar durante el desarrollo

	public static void main(String[] args) throws Exception {
		GestorErrores gestor = new GestorErrores();

		AST raiz = compile(programa, gestor); // Poner args[0] en vez de "programa" en la versión final
		if (!gestor.hayErrores())
			System.out.println("El programa se ha compilado correctamente.");

		
		//recorrerArbol(programa, gestor);
		ASTPrinter.toHtml(programa, raiz, "Ejemplo"); // Utilidad generada por VGen (opcional)
	}

	/**
	 * Método que coordina todas las fases del compilador
	 */
	public static AST compile(String sourceName, GestorErrores gestor) throws Exception {

		// 1. Fases de Análisis Léxico y Sintáctico
		Yylex lexico = new Yylex(new FileReader(sourceName), gestor);
		Parser sintáctico = new Parser(lexico, gestor, false);
		sintáctico.parse();

		AST raiz = sintáctico.getAST();
		if (raiz == null) // Hay errores o el AST no se ha implementado aún
			return null;

		// 2. Fase de Análisis Semántico
		AnalisisSemantico semántico = new AnalisisSemantico(gestor);
		semántico.analiza(raiz);
		if (gestor.hayErrores())
			return raiz;

		// 3. Fase de Generación de Código
		File sourceFile = new File(sourceName);
		Writer out = new FileWriter(new File(sourceFile.getParent(), "salida.txt"));

		GeneracionDeCodigo generador = new GeneracionDeCodigo();
		generador.genera(sourceFile.getName(), raiz, out);
		out.close();
	
		return raiz;
	}
	
	
//		public static void recorrerArbol(String sourceName, GestorErrores gestor) throws FileNotFoundException
//		{
//			Yylex lexico = new Yylex(new FileReader(sourceName), gestor);
//			Parser sintáctico = new Parser(lexico, gestor, false);
//			sintáctico.parse();
//	
//			AST raiz = sintáctico.getAST();
//			PrintVisitor p = new PrintVisitor();
//			p.visit((Programa)raiz, null);
//		}
}