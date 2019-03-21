// @author Raúl Izquierdo

/* -- No es necesario modificar esta parte ----------------------------------------------- */
package sintactico;

import java.io.*;
import main.*;
import ast.Position;

%%
%byaccj
%unicode
%line
%column
%public

%{
	public Yylex(Reader in, GestorErrores gestor) {
		this(in);
		this.gestor = gestor;
	}

	public int line() { return yyline + 1; }
	public int column() { return yycolumn + 1; }
	public String lexeme() { return yytext(); }

	// Traza para probar el léxico de manera independiente al sintáctico
	public static void main(String[] args) throws Exception {
		Yylex lex = new Yylex(new FileReader(Main.programa), new GestorErrores());
		int token;
		while ((token = lex.yylex()) != 0)
			System.out.println("\t[" + lex.line() + ":" + lex.column() + "] Token: " + token + ". Lexema: " + lex.lexeme());
	}

	private GestorErrores gestor;
%}

%%

/* -- Modificar aquí. Añadir reglas en esta sección ----------------------------------------- */




int										{ return Parser.INT; }
float									{ return Parser.FLOAT; }
char 									{ return Parser.CHAR; }
var										{ return Parser.VAR; }
read									{ return Parser.READ; }
print|write 							{ return Parser.PRINT; }
printsp									{ return Parser.PRINTSP; }
if										{ return Parser.IF; }
else									{ return Parser.ELSE; }
while									{ return Parser.WHILE; }
return									{ return Parser.RETURN; }
cast									{ return Parser.CAST; }
struct									{ return Parser.STRUCT; }
&&										{ return Parser.AND; }
break									{ return Parser.BREAK; }
switch									{ return Parser.SWITCH; }
case									{ return Parser.CASE; }
"||"									{ return Parser.OR; }
[0-9]+    								{ return Parser.LITENT; }
[0-9]+\.[0-9]+							{ return Parser.LITREAL; }
[a-zA-ZñÑ][a-zA-ZñÑ0-9_]*				{ return Parser.IDENT; }
'.'	{ return Parser.CONSTANTECHAR; }
''										{ return Parser.VACIO; }
\+\+									{ return Parser.MASMAS; }

[\[\]\'\/!+-<>;:,(){}=*?]  				{ return yytext().charAt(0); }

/* -- Se aconseja dejar estas reglas al final ----------------------------------------------- */

"/*"([^*]|\*+[^*/])*\*+"/"		{ }		/* Comentario de varias lineas como este */
"//".*							{ }		// Comentario de una linea como este

[ \n\r]		{ }
"\t"		{ yycolumn += 3; } // Para que coincida con la info del editor de Eclipse (opcional). En eclipse: \t == 4 caracteres. En Jflex: \t == 1 carácter.

.			{ gestor.error("Léxico", "Cadena \"" + yytext() +"\" no reconocida.", new Position(line(), column())); }
