// @author Raúl Izquierdo

/* No es necesario modificar esta sección ------------------ */
%{
package sintactico;

import java.io.*;
import java.util.*;
import ast.*;
import main.*;
%}

/* Precedencias aquí --------------------------------------- */
/* MENOS PRIORIDAD */
//%left 'IDENT'
%left '?' ':'			
%left 'AND' 'OR' '!' '='
%left '<' '>'
%left '+' '-'
%left '*' '/'
%left 'MASMAS'
%left '[' ']' '(' ')' '{' '}' '.' ',' 

%%

/* Añadir las reglas en esta sección ----------------------- */



program: definiciones 																				{ raiz = new Programa((List)$1); }
					;
definiciones: definiciones definicion																{ $$ = $1; ((List)$1).add($2); }
					|																				{ $$ = new ArrayList(); }
 					;
definicion: definicionVariable																		{ $$ = $1; }
					| definicionFuncion																{ $$ = $1; }
					| definicionEstructura															{ $$ = $1; }
					;
definicionVariable: 'VAR' 'IDENT' ':' tipo ';'														{ $$ = new DefinicionVariable($2, $4); }
					;
definicionEstructura: 'STRUCT' 'IDENT' '{' definicionesVariablesEstructura '}' ';'					{ $$ = new DefinicionEstructura($2, $4); }
					;
definicionVariablesEstructura : 'IDENT' ':' tipo ';'												{ $$ = new VariableEstructura($1, $3); }
					;
definicionesVariablesEstructura: definicionesVariablesEstructura definicionVariablesEstructura		{ $$ = $1; ((List)$1).add($2); }
					|																				{ $$ = new ArrayList(); }
					;
definicionFuncion: 'IDENT' '(' listaFunciones ')' retorno '{' definicionesVariables sentencias '}'	{ $$ = new DefinicionFuncion($1, $3, $5, $7, $8); }
					;
listaFunciones: parametros																			{ $$ = $1; }
					|																				{ $$ = new ArrayList(); }
					;
parametros: parametro 																				{ $$ = new ArrayList(); ((List)$$).add($1);}
					| parametros ',' parametro														{ $$ = $1; ((List)$$).add($3); }
					;
parametro : 'IDENT' ':' tipo																		{ $$ = new Parametro($1, $3); }
					;
retorno: ':' tipo																					{ $$ = $2; }
					|																				{ $$ = new NoRetorno().setPositions($0); }
					;
definicionesVariables: 																				{ $$ = new ArrayList(); }
					| definicionesVariables definicionVariable										{ $$ = $1; ((List)$1).add($2); }
					;			
sentencias:		    																				{ $$ = new ArrayList(); }			
				    | sentencias sentencia															{ $$ = $1; ((List)$1).add($2); } 
					;
sentencia: 			'PRINT' expr ';'																{ $$ = new Print($2).setPositions($2); }			 					
					| 'PRINTSP' expr ';'															{ $$ = new Printsp($2); }
					| 'READ' expr ';'																{ $$ = new Read($2); }
					| llamarFuncion ';'																{ $$ = $1; }
					| expr '=' expr ';'																{ $$ = new Asignacion($1, $3); }
					| 'IF' '(' expr ')' '{' sentencias '}'											{ $$ = new CondIf($3, $6, null); }
					| 'IF' '(' expr ')' '{' sentencias '}' 'ELSE' '{' sentencias '}'				{ $$ = new CondIf($3, $6, new CondElse($10)); }
					| 'WHILE' '(' expr ')' '{' sentencias '}'										{ $$ = new BucleWhile($3, $6); }
//					| 'IDENT' '=' expr ';'															{ $$ = new Asignacion(new Ident(((Token) $1).getLexeme()), $3).setPositions($1); }																									
					| 'RETURN' expr ';'																{ $$ = new Return($2).setPositions($1); }
					| 'RETURN' ';'																	{ $$ = new Return(new Vacio()).setPositions($1); }
					| expr 'MASMAS' ';'																{ $$ = new MasMas($1).setPositions($1); }
					| 'BREAK' ';'																	{ $$ = new Break().setPositions($1); }
					| 'SWITCH' '(' expr ')' '{' cases  '}' 											{ $$ = new Switch($3, $6); }
					; 
expr: 				'LITENT'																		{ $$ = new LiteralInt($1); }
					| 'LITREAL'																		{ $$ = new LiteralReal($1); }
					| 'IDENT'																		{ $$ = new Ident($1); }
					| 'CONSTANTECHAR'																{ $$ = new CteChar($1); }
					| expr '+' expr 																{ $$ = new Operador($1, "+", $3);	}
					| expr '-' expr																	{ $$ = new Operador($1, "-", $3);	}
					| expr '<' expr 																{ $$ = new Comparacion($1, "<", $3);	}
					| expr '>' expr 																{ $$ = new Comparacion($1, ">", $3);	}
					| expr '>''=' expr 																{ $$ = new Comparacion($1, ">=", $4);	}
					| expr '<''=' expr 																{ $$ = new Comparacion($1, "<=", $4);	}
					| expr 'AND' expr 																{ $$ = new Operador($1, "and", $3);	}
					| expr 'OR' expr 																{ $$ = new Operador($1, "or", $3);	}
					| expr '=' expr																	{ $$ = new Asignacion($1, $3).setPositions($1);}
					| expr '=''=' expr																{ $$ = new Comparacion($1, "==", $4);	}	
					| expr '!''=' expr																{ $$ = new Comparacion($1, "!=", $4);	}
					| '(' expr ')'																	{ $$ = $2;	}
					| expr '*' expr																	{ $$ = new Operador($1, "*", $3);	}
					| expr '/' expr																	{ $$ = new Operador($1, "/", $3);	}
					| expr '.' 'IDENT'																{ $$ = new AccesoCampo($1, $3);	}
					| expr '[' expr ']'																{ $$ = new Operador($1, "[]" ,$3);	}
					| llamarFuncion																	{ $$ = $1; }
					| 'CAST' '<' tipo '>' '(' expr ')'												{ $$ = new Cast($3, $6); };
					| 'VACIO'																		{ $$ = new Vacio(); };
					| expr '<''<' expr '>''>' expr													{ $$ = new OperadorRango($1, $4, $7).setPositions($1); }
					| expr '>''>' expr '<''<' expr 													{ $$ = new OperadorRango($7, $4, $1).setPositions($1); }
					| expr '?' expr ':' expr														{ $$ = new OperadorTernario($1, $3, $5).setPositions($1);}
					;
tipo: 				'INT'																			{ $$ = new IntType().setPositions($1); }															
					| 'CHAR' 																		{ $$ = new CharType().setPositions($1); }
					| 'FLOAT'																		{ $$ = new FloatType().setPositions($1); }
					| 'IDENT'																		{ $$ = new IdentType($1).setPositions($1); }
					| '[' 'LITENT' ']' tipo 														{ $$ = new Array(((Token)$2).getLexeme(), $4).setPositions($1); }
					;

llamarParametros: parametrosLlamada																	{ $$ = $1; }
					|																				{ $$ = new ArrayList(); }
					;
llamarFuncion: 'IDENT' '(' llamarParametros ')'														{ $$ = new InvocarFuncion(new Ident(((Token) $1).getLexeme()), $3).setPositions($1); }						
					;			
parametrosLlamada: expr																				{ $$ = new ArrayList(); ((List)$$).add($1);}
					| parametrosLlamada ',' expr												 	{ $$ = $1; ((List)$$).add($3); }
					;
cases:	case																						{ $$ = new ArrayList(); ((List)$$).add($1); }
		| cases case 																				{ $$ = $1; ((List)$$).add($2); }
		;																						
case:	'CASE' expr ':' sentencias	'BREAK'	';'														{ $$ = new Case($2, $4).setPositions($2); }
		;


%%
/* No es necesario modificar esta sección ------------------ */

public Parser(Yylex lex, GestorErrores gestor, boolean debug) {
	this(debug);
	this.lex = lex;
	this.gestor = gestor;
}

// Métodos de acceso para el main -------------
public int parse() { return yyparse(); }
public AST getAST() { return raiz; }

// Funciones requeridas por Yacc --------------
void yyerror(String msg) {
	Token lastToken = (Token) yylval;
	gestor.error("Sintáctico", "Token = " + lastToken.getToken() + ", lexema = \"" + lastToken.getLexeme() + "\". " + msg, lastToken.getStart());
}

int yylex() {
	try {
		int token = lex.yylex();
		//System.out.println("Token " + token + " lexema " + lex.lexeme());
		yylval = new Token(token, lex.lexeme(), lex.line(), lex.column());
		return token;
	} catch (IOException e) {
		return -1;
	}
}

private Yylex lex;
private GestorErrores gestor;
private AST raiz;
