//### This file created by BYACC 1.8(/Java extension  1.14)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 5 "sintac.y"
package sintactico;

import java.io.*;
import java.util.*;
import ast.*;
import main.*;
//#line 24 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:Object
String   yytext;//user variable to return contextual strings
Object yyval; //used to return semantic vals from action routines
Object yylval;//the 'lval' (result) I got from yylex()
Object valstk[] = new Object[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new Object();
  yylval=new Object();
  valptr=-1;
}
final void val_push(Object val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    Object[] newstack = new Object[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final Object val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final Object val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short AND=257;
public final static short OR=258;
public final static short MASMAS=259;
public final static short VAR=260;
public final static short IDENT=261;
public final static short STRUCT=262;
public final static short PRINT=263;
public final static short PRINTSP=264;
public final static short READ=265;
public final static short IF=266;
public final static short ELSE=267;
public final static short WHILE=268;
public final static short RETURN=269;
public final static short BREAK=270;
public final static short SWITCH=271;
public final static short LITENT=272;
public final static short LITREAL=273;
public final static short CONSTANTECHAR=274;
public final static short CAST=275;
public final static short VACIO=276;
public final static short INT=277;
public final static short CHAR=278;
public final static short FLOAT=279;
public final static short CASE=280;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    2,    3,    5,    8,    7,
    7,    4,    9,    9,   13,   13,   14,   10,   10,   11,
   11,   12,   12,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   15,   15,   15,   16,   16,   16,   16,
   16,   16,   16,   16,   16,   16,   16,   16,   16,   16,
   16,   16,   16,   16,   16,   16,   16,   16,   16,   16,
   16,   16,    6,    6,    6,    6,    6,   19,   19,   17,
   20,   20,   18,   18,   21,
};
final static short yylen[] = {                            2,
    1,    2,    0,    1,    1,    1,    5,    6,    4,    2,
    0,    9,    1,    0,    1,    3,    3,    2,    0,    0,
    2,    0,    2,    3,    3,    3,    2,    4,    7,   11,
    7,    3,    2,    3,    2,    7,    1,    1,    1,    1,
    3,    3,    3,    3,    4,    4,    3,    3,    3,    4,
    4,    3,    3,    3,    3,    4,    1,    7,    1,    7,
    7,    5,    1,    1,    1,    1,    4,    1,    0,    4,
    1,    3,    1,    2,    6,
};
final static short yydefred[] = {                         3,
    0,    0,    0,    0,    0,    2,    4,    5,    6,    0,
    0,    0,    0,    0,    0,    0,   15,   11,    0,   66,
   63,   64,   65,    0,    0,    0,    0,    0,    0,    7,
   17,    0,    0,   16,    0,    0,   10,    0,   18,   20,
    8,    0,   67,    0,    0,   21,    0,    9,    0,   12,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   37,
   38,   40,    0,   59,   23,    0,    0,    0,   57,    0,
    0,    0,    0,    0,    0,   33,    0,   35,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   27,    0,   52,    0,    0,    0,
   24,   25,   26,    0,    0,   32,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   34,    0,   55,    0,   70,    0,
    0,    0,    0,    0,    0,    0,    0,   28,    0,    0,
    0,    0,   56,    0,   22,   22,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   73,    0,    0,    0,    0,
   31,    0,   36,   74,   58,    0,    0,    0,   22,   22,
    0,    0,    0,   30,    0,
};
final static short yydgoto[] = {                          1,
    2,    6,    7,    8,    9,   24,   28,   37,   15,   33,
   44,   47,   16,   17,   65,   66,   69,  155,   99,  100,
  156,
};
final static short yysindex[] = {                         0,
    0, -225, -252,  -29, -247,    0,    0,    0,    0,  -43,
 -233,  -93,  -87,  -25,   -2,    1,    0,    0, -226,    0,
    0,    0,    0,  -10,  -87,  -11, -233, -122,  -42,    0,
    0,  -87,  -71,    0,    3,   -1,    0,  -87,    0,    0,
    0,  -87,    0, -197,   11,    0,  -22,    0,  135,    0,
   38,  135,  135,  135,   47,   48,  119,   15,   49,    0,
    0,    0,   30,    0,    0,  483,   32,  -20,    0,  135,
  783,  794,  830,  135,  135,    0,  836,    0,  135,  -87,
  135,  135,  135,   31,  128,   80,   96,  135,  135,  135,
  135,   36,  135, -168,    0,  128,    0, 1029,   56,   55,
    0,    0,    0,  872,  895,    0,  919,   39,  942,   34,
   34,  135,  135,   13,  135,  135,   22,  135,  135,   22,
  -30,  -30,  -38,  -38,    0,  966,    0,   34,    0,  135,
  -23,  -21,  -18,   66,  135,   34,   34,    0,   34, 1053,
   34, 1064,    0, 1029,    0,    0, -171,  135, 1089,  102,
  112,   -6,   10,  135, -124,    0,  974,  135,  135, -157,
    0, 1021,    0,    0,    0,   22,   22,   -8,    0,    0,
   74,   26,   57,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,  117,    0,    0,    0,    0,    0,    0,    0,    0,
   77,    0,    0,    0,    0,   81,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    4,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   42,    0,    0,    0,    0,    0,    0,
  372,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  505,    0,    0,   82,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -34,    0,   83,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  553,
  607,    0,    0,  318,    0,    0,  520,    0,    0,  529,
  438,  461,  379,  415,    0,    0,    0,  643,    0,    0,
    0,    0,    0,    0,    0,  666,  672,    0,  710,    0,
  739,    0,    0,  -12,    0,    0,    0,    0,  -39,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   58,
    0,    0,    0,    0,    0,  565,  601,    0,    0,    0,
    0,    0,    0,    0,  -40,
};
final static short yygindex[] = {                         0,
    0,    0,   84,    0,    0,    6,    0,    0,    0,    0,
    0,  -62,    0,   94,    0, 1155,  -41,    0,    0,    0,
  -26,
};
final static int YYTABLESIZE=1347;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         35,
  163,   62,   35,   19,   62,   67,   71,   94,   10,   71,
   11,   90,   84,   12,   13,   94,   91,   49,   62,   62,
   97,   90,   88,   62,   89,   94,   91,   14,   72,   18,
   31,   72,   25,   49,    3,    4,    5,   39,   26,   86,
   96,   87,   81,   43,   27,   29,   32,   45,   30,   49,
   38,   40,   93,   62,   90,   88,   42,   89,   94,   91,
   93,   41,    3,   90,   88,   49,   89,   94,   91,   48,
   93,  138,   86,   78,   87,   90,   88,   70,   89,   94,
   91,   22,  152,  153,   75,  108,   74,   75,   79,   80,
   95,  112,  127,   86,  125,   87,  129,   29,  130,  145,
  134,  146,   50,   93,  147,  148,  171,  172,  154,  168,
   67,   67,   93,   49,  170,  175,    1,   14,  160,   49,
   34,   13,   69,   68,   93,    0,   19,   46,  164,   67,
   67,    0,    0,    0,  161,   49,    0,    0,   36,  116,
  115,   49,    0,    0,    0,    0,    0,    0,    0,    0,
  174,   49,    0,    0,    0,  154,  118,  119,   49,    0,
    0,    0,  118,  158,    0,    0,   22,   49,    0,    0,
    0,  159,  115,   20,   49,    0,    0,   76,    0,    0,
    0,    0,   29,    0,    0,    0,    0,    0,  113,   21,
   22,   23,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   62,
   35,    0,   35,   35,   35,   35,    0,   35,   35,   35,
   35,   35,   35,   35,   35,   35,   82,   83,   51,   75,
   52,   53,   54,   55,    0,   56,   57,   58,   59,   60,
   61,   62,   63,   64,   51,    0,   52,   53,   54,   55,
    0,   56,   57,   58,   59,   60,   61,   62,   63,   64,
   51,    0,   52,   53,   54,   55,    0,   56,   57,   58,
   59,   60,   61,   62,   63,   64,   51,    0,   52,   53,
   54,   55,    0,   56,   57,   58,   59,   60,   61,   62,
   63,   64,   22,    0,   22,   22,   22,   22,    0,   22,
   22,   22,   22,   22,   22,   22,   22,   22,   29,    0,
   29,   29,   29,   29,    0,   29,   29,   29,   29,   29,
   29,   29,   29,   29,   51,    0,   52,   53,   54,   55,
   51,   56,   57,  173,   59,   60,   61,   62,   63,   64,
   49,   60,   61,   62,   63,   64,   51,    0,    0,    0,
    0,    0,   51,    0,    0,    0,    0,   60,   61,   62,
   63,   64,   51,   60,   61,   62,   63,   64,   49,   51,
   49,    0,    0,   60,   61,   62,   63,   64,   51,    0,
   60,   61,   62,   63,   64,   51,    0,    0,    0,   60,
   61,   62,   63,   64,   39,    0,   60,   61,   62,   63,
   64,   53,   39,   39,   39,   39,   39,   39,   39,   53,
   53,   53,   53,   53,    0,   53,    0,    0,    0,   39,
   39,   39,   39,   39,   39,    0,   53,   53,   53,   53,
   53,   53,    0,    0,    0,    0,    0,   54,    0,    0,
    0,    0,    0,    0,    0,   54,   54,   54,   54,   54,
    0,   54,   39,    0,   39,    0,    0,    0,    0,    0,
   41,   53,   54,   54,   54,   54,   54,   54,   41,    0,
   41,   41,   41,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   42,    0,   41,   41,   41,   41,   41,
   41,   42,    0,   42,   42,   42,    0,   54,    0,    0,
    0,    0,    0,    0,    0,   84,    0,    0,   42,   42,
   42,   42,   42,   42,   90,   88,    0,   89,   94,   91,
   41,    0,    0,    0,    0,    0,    0,   57,    0,    0,
    0,    0,   86,   85,   87,   81,   57,   57,    0,   57,
   57,   57,   43,   42,    0,    0,    0,    0,    0,    0,
   43,   44,    0,   43,   57,   57,   57,   57,    0,   44,
    0,    0,   44,   93,   49,   49,   49,   43,   43,   43,
   43,   43,   43,    0,    0,   47,   44,   44,   44,   44,
   44,   44,    0,   47,    0,   57,   47,   60,    0,    0,
    0,    0,    0,    0,    0,   60,    0,    0,   60,    0,
   47,   47,   43,   47,    0,   47,    0,    0,    0,    0,
    0,   44,   60,   60,   60,   60,   60,   60,   39,   39,
   39,    0,    0,   61,    0,   53,   53,   53,    0,   48,
    0,   61,    0,    0,   61,   47,    0,   48,    0,    0,
   48,    0,    0,    0,    0,    0,    0,   60,   61,   61,
   61,   61,   61,   61,   48,   48,    0,   48,    0,   48,
    0,   54,   54,   54,    0,   49,    0,    0,    0,    0,
    0,    0,    0,   49,    0,    0,   49,    0,    0,    0,
    0,    0,    0,   61,   41,   41,   41,    0,   51,   48,
   49,   49,    0,   49,   50,   49,   51,    0,    0,   51,
    0,    0,   50,    0,    0,   50,    0,   42,   42,   42,
    0,    0,    0,   51,   51,    0,   51,    0,   51,   50,
   50,    0,   50,    0,   50,   49,    0,    0,    0,   82,
   83,   92,   46,    0,    0,    0,    0,    0,    0,    0,
   46,    0,    0,   46,    0,    0,    0,    0,   51,    0,
    0,   57,   57,   57,   50,    0,    0,   46,   46,    0,
   46,   45,   46,    0,    0,    0,   43,   43,   43,   45,
    0,    0,   45,    0,    0,   44,   44,   44,    0,    0,
    0,    0,    0,    0,    0,    0,   45,   45,    0,   45,
    0,   45,   46,    0,    0,    0,    0,    0,    0,   47,
   47,   47,    0,    0,    0,   84,    0,    0,    0,    0,
    0,   60,   60,   60,   90,   88,   84,   89,   94,   91,
    0,   45,    0,    0,    0,   90,   88,    0,   89,   94,
   91,  101,   86,   96,   87,   81,    0,    0,    0,    0,
    0,    0,  102,   86,   96,   87,   81,   61,   61,   61,
    0,    0,   84,   48,   48,   48,    0,    0,   84,    0,
    0,   90,   88,   93,   89,   94,   91,   90,   88,    0,
   89,   94,   91,    0,   93,    0,    0,    0,  103,   86,
   96,   87,   81,    0,  106,   86,   96,   87,   81,   49,
   49,   49,    0,    0,   84,    0,    0,    0,    0,    0,
    0,    0,  131,   90,   88,    0,   89,   94,   91,    0,
   93,    0,   51,   51,   51,    0,   93,   84,   50,   50,
   50,   86,   96,   87,   81,  132,   90,   88,    0,   89,
   94,   91,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   84,    0,    0,   86,   96,   87,   81,    0,  133,
   90,   88,   93,   89,   94,   91,   46,   46,   46,    0,
    0,    0,    0,    0,   84,    0,    0,    0,   86,   96,
   87,   81,    0,   90,   88,   93,   89,   94,   91,    0,
    0,    0,    0,    0,    0,   45,   45,   45,   84,  135,
    0,   86,   96,   87,   81,    0,   84,   90,   88,   93,
   89,   94,   91,    0,  165,   90,   88,    0,   89,   94,
   91,    0,    0,    0,    0,   86,   96,   87,   81,    0,
    0,    0,   93,   86,   96,   87,   81,    0,    0,   82,
   83,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   82,   83,    0,   84,    0,    0,   93,    0,  143,    0,
    0,   84,   90,   88,   93,   89,   94,   91,    0,    0,
   90,   88,    0,   89,   94,   91,    0,    0,  169,    0,
   86,   96,   87,   81,    0,   84,   82,   83,   86,   96,
   87,   81,   82,   83,   90,   88,   84,   89,   94,   91,
    0,    0,    0,    0,    0,   90,   88,    0,   89,   94,
   91,   93,   86,   96,  150,   81,    0,    0,    0,   93,
    0,   84,    0,  151,   96,   87,   81,    0,   82,   83,
   90,   88,    0,   89,   94,   91,    0,    0,    0,    0,
    0,    0,    0,   93,    0,    0,    0,    0,   86,   96,
   87,   82,   83,    0,   93,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   82,   83,    0,    0,   93,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   82,   83,
    0,    0,    0,   68,    0,    0,   71,   72,   73,    0,
    0,   77,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   82,   83,   98,    0,    0,    0,  104,  105,
   82,   83,    0,  107,    0,  109,  110,  111,    0,  114,
  117,  120,  121,  122,  123,  124,    0,  126,    0,    0,
  128,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  136,  137,    0,  139,
  140,    0,  141,  142,    0,    0,    0,   82,   83,    0,
    0,    0,    0,    0,  144,   82,   83,    0,    0,  149,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  157,    0,  120,  117,    0,    0,  162,   82,
   83,    0,  166,  167,    0,    0,    0,    0,    0,    0,
   82,   83,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   82,   83,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
  125,   41,  125,   91,   44,   47,   41,   46,  261,   44,
   40,   42,   33,  261,   58,   46,   47,   40,   58,   59,
   41,   42,   43,   63,   45,   46,   47,  261,   41,  123,
   25,   44,   58,   40,  260,  261,  262,   32,   41,   60,
   61,   62,   63,   38,   44,  272,   58,   42,   59,   40,
   93,  123,   91,   93,   42,   43,   58,   45,   46,   47,
   91,   59,  260,   42,   43,   40,   45,   46,   47,   59,
   91,   59,   60,   59,   62,   42,   43,   40,   45,   46,
   47,   40,  145,  146,  125,   80,   40,   40,   40,   60,
   59,   61,  261,   60,   59,   62,   41,   40,   44,  123,
   62,  123,  125,   91,  123,   40,  169,  170,  280,  267,
  152,  153,   91,   40,  123,   59,    0,   41,  125,   40,
   27,   41,   41,   41,   91,   -1,  123,   44,  155,  171,
  172,   -1,   -1,   -1,  125,   40,   -1,   -1,  261,   60,
   61,   40,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  125,   40,   -1,   -1,   -1,  280,   61,   62,   40,   -1,
   -1,   -1,   61,   62,   -1,   -1,  125,   40,   -1,   -1,
   -1,   60,   61,  261,   40,   -1,   -1,   59,   -1,   -1,
   -1,   -1,  125,   -1,   -1,   -1,   -1,   -1,   61,  277,
  278,  279,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  259,
  261,   -1,  263,  264,  265,  266,   -1,  268,  269,  270,
  271,  272,  273,  274,  275,  276,  257,  258,  261,  280,
  263,  264,  265,  266,   -1,  268,  269,  270,  271,  272,
  273,  274,  275,  276,  261,   -1,  263,  264,  265,  266,
   -1,  268,  269,  270,  271,  272,  273,  274,  275,  276,
  261,   -1,  263,  264,  265,  266,   -1,  268,  269,  270,
  271,  272,  273,  274,  275,  276,  261,   -1,  263,  264,
  265,  266,   -1,  268,  269,  270,  271,  272,  273,  274,
  275,  276,  261,   -1,  263,  264,  265,  266,   -1,  268,
  269,  270,  271,  272,  273,  274,  275,  276,  261,   -1,
  263,  264,  265,  266,   -1,  268,  269,  270,  271,  272,
  273,  274,  275,  276,  261,   -1,  263,  264,  265,  266,
  261,  268,  269,  270,  271,  272,  273,  274,  275,  276,
   33,  272,  273,  274,  275,  276,  261,   -1,   -1,   -1,
   -1,   -1,  261,   -1,   -1,   -1,   -1,  272,  273,  274,
  275,  276,  261,  272,  273,  274,  275,  276,   61,  261,
   63,   -1,   -1,  272,  273,  274,  275,  276,  261,   -1,
  272,  273,  274,  275,  276,  261,   -1,   -1,   -1,  272,
  273,  274,  275,  276,   33,   -1,  272,  273,  274,  275,
  276,   33,   41,   42,   43,   44,   45,   46,   47,   41,
   42,   43,   44,   45,   -1,   47,   -1,   -1,   -1,   58,
   59,   60,   61,   62,   63,   -1,   58,   59,   60,   61,
   62,   63,   -1,   -1,   -1,   -1,   -1,   33,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   41,   42,   43,   44,   45,
   -1,   47,   91,   -1,   93,   -1,   -1,   -1,   -1,   -1,
   33,   93,   58,   59,   60,   61,   62,   63,   41,   -1,
   43,   44,   45,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   33,   -1,   58,   59,   60,   61,   62,
   63,   41,   -1,   43,   44,   45,   -1,   93,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   33,   -1,   -1,   58,   59,
   60,   61,   62,   63,   42,   43,   -1,   45,   46,   47,
   93,   -1,   -1,   -1,   -1,   -1,   -1,   33,   -1,   -1,
   -1,   -1,   60,   61,   62,   63,   42,   43,   -1,   45,
   46,   47,   33,   93,   -1,   -1,   -1,   -1,   -1,   -1,
   41,   33,   -1,   44,   60,   61,   62,   63,   -1,   41,
   -1,   -1,   44,   91,  257,  258,  259,   58,   59,   60,
   61,   62,   63,   -1,   -1,   33,   58,   59,   60,   61,
   62,   63,   -1,   41,   -1,   91,   44,   33,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   41,   -1,   -1,   44,   -1,
   58,   59,   93,   61,   -1,   63,   -1,   -1,   -1,   -1,
   -1,   93,   58,   59,   60,   61,   62,   63,  257,  258,
  259,   -1,   -1,   33,   -1,  257,  258,  259,   -1,   33,
   -1,   41,   -1,   -1,   44,   93,   -1,   41,   -1,   -1,
   44,   -1,   -1,   -1,   -1,   -1,   -1,   93,   58,   59,
   60,   61,   62,   63,   58,   59,   -1,   61,   -1,   63,
   -1,  257,  258,  259,   -1,   33,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   41,   -1,   -1,   44,   -1,   -1,   -1,
   -1,   -1,   -1,   93,  257,  258,  259,   -1,   33,   93,
   58,   59,   -1,   61,   33,   63,   41,   -1,   -1,   44,
   -1,   -1,   41,   -1,   -1,   44,   -1,  257,  258,  259,
   -1,   -1,   -1,   58,   59,   -1,   61,   -1,   63,   58,
   59,   -1,   61,   -1,   63,   93,   -1,   -1,   -1,  257,
  258,  259,   33,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   41,   -1,   -1,   44,   -1,   -1,   -1,   -1,   93,   -1,
   -1,  257,  258,  259,   93,   -1,   -1,   58,   59,   -1,
   61,   33,   63,   -1,   -1,   -1,  257,  258,  259,   41,
   -1,   -1,   44,   -1,   -1,  257,  258,  259,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   58,   59,   -1,   61,
   -1,   63,   93,   -1,   -1,   -1,   -1,   -1,   -1,  257,
  258,  259,   -1,   -1,   -1,   33,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,   42,   43,   33,   45,   46,   47,
   -1,   93,   -1,   -1,   -1,   42,   43,   -1,   45,   46,
   47,   59,   60,   61,   62,   63,   -1,   -1,   -1,   -1,
   -1,   -1,   59,   60,   61,   62,   63,  257,  258,  259,
   -1,   -1,   33,  257,  258,  259,   -1,   -1,   33,   -1,
   -1,   42,   43,   91,   45,   46,   47,   42,   43,   -1,
   45,   46,   47,   -1,   91,   -1,   -1,   -1,   59,   60,
   61,   62,   63,   -1,   59,   60,   61,   62,   63,  257,
  258,  259,   -1,   -1,   33,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   41,   42,   43,   -1,   45,   46,   47,   -1,
   91,   -1,  257,  258,  259,   -1,   91,   33,  257,  258,
  259,   60,   61,   62,   63,   41,   42,   43,   -1,   45,
   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   33,   -1,   -1,   60,   61,   62,   63,   -1,   41,
   42,   43,   91,   45,   46,   47,  257,  258,  259,   -1,
   -1,   -1,   -1,   -1,   33,   -1,   -1,   -1,   60,   61,
   62,   63,   -1,   42,   43,   91,   45,   46,   47,   -1,
   -1,   -1,   -1,   -1,   -1,  257,  258,  259,   33,   58,
   -1,   60,   61,   62,   63,   -1,   33,   42,   43,   91,
   45,   46,   47,   -1,   41,   42,   43,   -1,   45,   46,
   47,   -1,   -1,   -1,   -1,   60,   61,   62,   63,   -1,
   -1,   -1,   91,   60,   61,   62,   63,   -1,   -1,  257,
  258,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  257,  258,   -1,   33,   -1,   -1,   91,   -1,   93,   -1,
   -1,   33,   42,   43,   91,   45,   46,   47,   -1,   -1,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   58,   -1,
   60,   61,   62,   63,   -1,   33,  257,  258,   60,   61,
   62,   63,  257,  258,   42,   43,   33,   45,   46,   47,
   -1,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,   46,
   47,   91,   60,   61,   62,   63,   -1,   -1,   -1,   91,
   -1,   33,   -1,   60,   61,   62,   63,   -1,  257,  258,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   91,   -1,   -1,   -1,   -1,   60,   61,
   62,  257,  258,   -1,   91,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,  258,   -1,   -1,   91,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
   -1,   -1,   -1,   49,   -1,   -1,   52,   53,   54,   -1,
   -1,   57,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  257,  258,   70,   -1,   -1,   -1,   74,   75,
  257,  258,   -1,   79,   -1,   81,   82,   83,   -1,   85,
   86,   87,   88,   89,   90,   91,   -1,   93,   -1,   -1,
   96,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  112,  113,   -1,  115,
  116,   -1,  118,  119,   -1,   -1,   -1,  257,  258,   -1,
   -1,   -1,   -1,   -1,  130,  257,  258,   -1,   -1,  135,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  148,   -1,  150,  151,   -1,   -1,  154,  257,
  258,   -1,  158,  159,   -1,   -1,   -1,   -1,   -1,   -1,
  257,  258,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,  258,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=280;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,null,null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'","'?'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"\"AND\"","\"OR\"","\"MASMAS\"",
"\"VAR\"","\"IDENT\"","\"STRUCT\"","\"PRINT\"","\"PRINTSP\"","\"READ\"",
"\"IF\"","\"ELSE\"","\"WHILE\"","\"RETURN\"","\"BREAK\"","\"SWITCH\"",
"\"LITENT\"","\"LITREAL\"","\"CONSTANTECHAR\"","\"CAST\"","\"VACIO\"","\"INT\"",
"\"CHAR\"","\"FLOAT\"","\"CASE\"",
};
final static String yyrule[] = {
"$accept : program",
"program : definiciones",
"definiciones : definiciones definicion",
"definiciones :",
"definicion : definicionVariable",
"definicion : definicionFuncion",
"definicion : definicionEstructura",
"definicionVariable : \"VAR\" \"IDENT\" ':' tipo ';'",
"definicionEstructura : \"STRUCT\" \"IDENT\" '{' definicionesVariablesEstructura '}' ';'",
"definicionVariablesEstructura : \"IDENT\" ':' tipo ';'",
"definicionesVariablesEstructura : definicionesVariablesEstructura definicionVariablesEstructura",
"definicionesVariablesEstructura :",
"definicionFuncion : \"IDENT\" '(' listaFunciones ')' retorno '{' definicionesVariables sentencias '}'",
"listaFunciones : parametros",
"listaFunciones :",
"parametros : parametro",
"parametros : parametros ',' parametro",
"parametro : \"IDENT\" ':' tipo",
"retorno : ':' tipo",
"retorno :",
"definicionesVariables :",
"definicionesVariables : definicionesVariables definicionVariable",
"sentencias :",
"sentencias : sentencias sentencia",
"sentencia : \"PRINT\" expr ';'",
"sentencia : \"PRINTSP\" expr ';'",
"sentencia : \"READ\" expr ';'",
"sentencia : llamarFuncion ';'",
"sentencia : expr '=' expr ';'",
"sentencia : \"IF\" '(' expr ')' '{' sentencias '}'",
"sentencia : \"IF\" '(' expr ')' '{' sentencias '}' \"ELSE\" '{' sentencias '}'",
"sentencia : \"WHILE\" '(' expr ')' '{' sentencias '}'",
"sentencia : \"RETURN\" expr ';'",
"sentencia : \"RETURN\" ';'",
"sentencia : expr \"MASMAS\" ';'",
"sentencia : \"BREAK\" ';'",
"sentencia : \"SWITCH\" '(' expr ')' '{' cases '}'",
"expr : \"LITENT\"",
"expr : \"LITREAL\"",
"expr : \"IDENT\"",
"expr : \"CONSTANTECHAR\"",
"expr : expr '+' expr",
"expr : expr '-' expr",
"expr : expr '<' expr",
"expr : expr '>' expr",
"expr : expr '>' '=' expr",
"expr : expr '<' '=' expr",
"expr : expr \"AND\" expr",
"expr : expr \"OR\" expr",
"expr : expr '=' expr",
"expr : expr '=' '=' expr",
"expr : expr '!' '=' expr",
"expr : '(' expr ')'",
"expr : expr '*' expr",
"expr : expr '/' expr",
"expr : expr '.' \"IDENT\"",
"expr : expr '[' expr ']'",
"expr : llamarFuncion",
"expr : \"CAST\" '<' tipo '>' '(' expr ')'",
"expr : \"VACIO\"",
"expr : expr '<' '<' expr '>' '>' expr",
"expr : expr '>' '>' expr '<' '<' expr",
"expr : expr '?' expr ':' expr",
"tipo : \"INT\"",
"tipo : \"CHAR\"",
"tipo : \"FLOAT\"",
"tipo : \"IDENT\"",
"tipo : '[' \"LITENT\" ']' tipo",
"llamarParametros : parametrosLlamada",
"llamarParametros :",
"llamarFuncion : \"IDENT\" '(' llamarParametros ')'",
"parametrosLlamada : expr",
"parametrosLlamada : parametrosLlamada ',' expr",
"cases : case",
"cases : cases case",
"case : \"CASE\" expr ':' sentencias \"BREAK\" ';'",
};

//#line 132 "sintac.y"
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
//#line 628 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 30 "sintac.y"
{ raiz = new Programa((List)val_peek(0)); }
break;
case 2:
//#line 32 "sintac.y"
{ yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0)); }
break;
case 3:
//#line 33 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 4:
//#line 35 "sintac.y"
{ yyval = val_peek(0); }
break;
case 5:
//#line 36 "sintac.y"
{ yyval = val_peek(0); }
break;
case 6:
//#line 37 "sintac.y"
{ yyval = val_peek(0); }
break;
case 7:
//#line 39 "sintac.y"
{ yyval = new DefinicionVariable(val_peek(3), val_peek(1)); }
break;
case 8:
//#line 41 "sintac.y"
{ yyval = new DefinicionEstructura(val_peek(4), val_peek(2)); }
break;
case 9:
//#line 43 "sintac.y"
{ yyval = new VariableEstructura(val_peek(3), val_peek(1)); }
break;
case 10:
//#line 45 "sintac.y"
{ yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0)); }
break;
case 11:
//#line 46 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 12:
//#line 48 "sintac.y"
{ yyval = new DefinicionFuncion(val_peek(8), val_peek(6), val_peek(4), val_peek(2), val_peek(1)); }
break;
case 13:
//#line 50 "sintac.y"
{ yyval = val_peek(0); }
break;
case 14:
//#line 51 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 15:
//#line 53 "sintac.y"
{ yyval = new ArrayList(); ((List)yyval).add(val_peek(0));}
break;
case 16:
//#line 54 "sintac.y"
{ yyval = val_peek(2); ((List)yyval).add(val_peek(0)); }
break;
case 17:
//#line 56 "sintac.y"
{ yyval = new Parametro(val_peek(2), val_peek(0)); }
break;
case 18:
//#line 58 "sintac.y"
{ yyval = val_peek(0); }
break;
case 19:
//#line 59 "sintac.y"
{ yyval = new NoRetorno().setPositions(val_peek(0)); }
break;
case 20:
//#line 61 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 21:
//#line 62 "sintac.y"
{ yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0)); }
break;
case 22:
//#line 64 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 23:
//#line 65 "sintac.y"
{ yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0)); }
break;
case 24:
//#line 67 "sintac.y"
{ yyval = new Print(val_peek(1)).setPositions(val_peek(1)); }
break;
case 25:
//#line 68 "sintac.y"
{ yyval = new Printsp(val_peek(1)); }
break;
case 26:
//#line 69 "sintac.y"
{ yyval = new Read(val_peek(1)); }
break;
case 27:
//#line 70 "sintac.y"
{ yyval = val_peek(1); }
break;
case 28:
//#line 71 "sintac.y"
{ yyval = new Asignacion(val_peek(3), val_peek(1)); }
break;
case 29:
//#line 72 "sintac.y"
{ yyval = new CondIf(val_peek(4), val_peek(1), null); }
break;
case 30:
//#line 73 "sintac.y"
{ yyval = new CondIf(val_peek(8), val_peek(5), new CondElse(val_peek(1))); }
break;
case 31:
//#line 74 "sintac.y"
{ yyval = new BucleWhile(val_peek(4), val_peek(1)); }
break;
case 32:
//#line 76 "sintac.y"
{ yyval = new Return(val_peek(1)).setPositions(val_peek(2)); }
break;
case 33:
//#line 77 "sintac.y"
{ yyval = new Return(new Vacio()).setPositions(val_peek(1)); }
break;
case 34:
//#line 78 "sintac.y"
{ yyval = new MasMas(val_peek(2)).setPositions(val_peek(2)); }
break;
case 35:
//#line 79 "sintac.y"
{ yyval = new Break().setPositions(val_peek(1)); }
break;
case 36:
//#line 80 "sintac.y"
{ yyval = new Switch(val_peek(4), val_peek(1)); }
break;
case 37:
//#line 82 "sintac.y"
{ yyval = new LiteralInt(val_peek(0)); }
break;
case 38:
//#line 83 "sintac.y"
{ yyval = new LiteralReal(val_peek(0)); }
break;
case 39:
//#line 84 "sintac.y"
{ yyval = new Ident(val_peek(0)); }
break;
case 40:
//#line 85 "sintac.y"
{ yyval = new CteChar(val_peek(0)); }
break;
case 41:
//#line 86 "sintac.y"
{ yyval = new Operador(val_peek(2), "+", val_peek(0));	}
break;
case 42:
//#line 87 "sintac.y"
{ yyval = new Operador(val_peek(2), "-", val_peek(0));	}
break;
case 43:
//#line 88 "sintac.y"
{ yyval = new Comparacion(val_peek(2), "<", val_peek(0));	}
break;
case 44:
//#line 89 "sintac.y"
{ yyval = new Comparacion(val_peek(2), ">", val_peek(0));	}
break;
case 45:
//#line 90 "sintac.y"
{ yyval = new Comparacion(val_peek(3), ">=", val_peek(0));	}
break;
case 46:
//#line 91 "sintac.y"
{ yyval = new Comparacion(val_peek(3), "<=", val_peek(0));	}
break;
case 47:
//#line 92 "sintac.y"
{ yyval = new Operador(val_peek(2), "and", val_peek(0));	}
break;
case 48:
//#line 93 "sintac.y"
{ yyval = new Operador(val_peek(2), "or", val_peek(0));	}
break;
case 49:
//#line 94 "sintac.y"
{ yyval = new Asignacion(val_peek(2), val_peek(0)).setPositions(val_peek(2));}
break;
case 50:
//#line 95 "sintac.y"
{ yyval = new Comparacion(val_peek(3), "==", val_peek(0));	}
break;
case 51:
//#line 96 "sintac.y"
{ yyval = new Comparacion(val_peek(3), "!=", val_peek(0));	}
break;
case 52:
//#line 97 "sintac.y"
{ yyval = val_peek(1);	}
break;
case 53:
//#line 98 "sintac.y"
{ yyval = new Operador(val_peek(2), "*", val_peek(0));	}
break;
case 54:
//#line 99 "sintac.y"
{ yyval = new Operador(val_peek(2), "/", val_peek(0));	}
break;
case 55:
//#line 100 "sintac.y"
{ yyval = new AccesoCampo(val_peek(2), val_peek(0));	}
break;
case 56:
//#line 101 "sintac.y"
{ yyval = new Operador(val_peek(3), "[]" ,val_peek(1));	}
break;
case 57:
//#line 102 "sintac.y"
{ yyval = val_peek(0); }
break;
case 58:
//#line 103 "sintac.y"
{ yyval = new Cast(val_peek(4), val_peek(1)); }
break;
case 59:
//#line 104 "sintac.y"
{ yyval = new Vacio(); }
break;
case 60:
//#line 105 "sintac.y"
{ yyval = new OperadorRango(val_peek(6), val_peek(3), val_peek(0)).setPositions(val_peek(6)); }
break;
case 61:
//#line 106 "sintac.y"
{ yyval = new OperadorRango(val_peek(0), val_peek(3), val_peek(6)).setPositions(val_peek(6)); }
break;
case 62:
//#line 107 "sintac.y"
{ yyval = new OperadorTernario(val_peek(4), val_peek(2), val_peek(0)).setPositions(val_peek(4));}
break;
case 63:
//#line 109 "sintac.y"
{ yyval = new IntType().setPositions(val_peek(0)); }
break;
case 64:
//#line 110 "sintac.y"
{ yyval = new CharType().setPositions(val_peek(0)); }
break;
case 65:
//#line 111 "sintac.y"
{ yyval = new FloatType().setPositions(val_peek(0)); }
break;
case 66:
//#line 112 "sintac.y"
{ yyval = new IdentType(val_peek(0)).setPositions(val_peek(0)); }
break;
case 67:
//#line 113 "sintac.y"
{ yyval = new Array(((Token)val_peek(2)).getLexeme(), val_peek(0)).setPositions(val_peek(3)); }
break;
case 68:
//#line 116 "sintac.y"
{ yyval = val_peek(0); }
break;
case 69:
//#line 117 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 70:
//#line 119 "sintac.y"
{ yyval = new InvocarFuncion(new Ident(((Token) val_peek(3)).getLexeme()), val_peek(1)).setPositions(val_peek(3)); }
break;
case 71:
//#line 121 "sintac.y"
{ yyval = new ArrayList(); ((List)yyval).add(val_peek(0));}
break;
case 72:
//#line 122 "sintac.y"
{ yyval = val_peek(2); ((List)yyval).add(val_peek(0)); }
break;
case 73:
//#line 124 "sintac.y"
{ yyval = new ArrayList(); ((List)yyval).add(val_peek(0)); }
break;
case 74:
//#line 125 "sintac.y"
{ yyval = val_peek(1); ((List)yyval).add(val_peek(0)); }
break;
case 75:
//#line 127 "sintac.y"
{ yyval = new Case(val_peek(4), val_peek(2)).setPositions(val_peek(4)); }
break;
//#line 1076 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
