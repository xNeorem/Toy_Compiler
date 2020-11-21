package it.esercitazione4;
import java_cup.runtime.Symbol;
%%
%public
%cup

%unicode

separator = [ \r\n\t\f]
digit = [0-9]
digits = {digit}+
number = {digits}(.{digits})?(E[+-]?{digits})?
letter = [A-Za-z]
string = \"{letter}+\"
id = [{letter}][{letter}|{digit}]*

%%

{separator} {}
"int" { return new Symbol(sym.INT); }
"string" { return new Symbol(sym.STRING); }
"float" { return new Symbol(sym.FLOAT); }
"bool" { return new Symbol(sym.BOOL); }
"true" { return new Symbol(sym.TRUE); }
"false" { return new Symbol(sym.FALSE); }
"null" { return new Symbol(sym.NULL); }
"void" { return new Symbol(sym.VOID); }
"proc" { return new Symbol(sym.PROC); }
"corp" { return new Symbol(sym.CORP); }
"if" { return new Symbol(sym.IF); }
"then" { return new Symbol(sym.THEN); }
"elif" { return new Symbol(sym.ELIF); }
"fi" { return new Symbol(sym.FI); }
"else" { return new Symbol(sym.ELSE); }
"while" { return new Symbol(sym.WHILE); }
"do" { return new Symbol(sym.DO); }
"od" { return new Symbol(sym.OD); }
"readln" { return new Symbol(sym.READ); }
"write" { return new Symbol(sym.WRITE); }
":=" { return new Symbol(sym.ASSIGN); }
"+" { return new Symbol(sym.PLUS); }
"-" { return new Symbol(sym.MINUS); }
"*" { return new Symbol(sym.TIMES); }
"/" { return new Symbol(sym.DIV); }
{id} { return new Symbol(sym.ID, yytext()); }
{digits} { return new Symbol(sym.INT_CONST, yytext()); }
{number} { return new Symbol(sym.FLOAT_CONST, yytext()); }
{string} { return new Symbol(sym.STRING_CONST, yytext()); }
"(" { return new Symbol(sym.LPAR); }
")" { return new Symbol(sym.RPAR); }
":" { return new Symbol(sym.COLON); }
"," { return new Symbol(sym.COMMA); }
";" { return new Symbol(sym.SEMI); }
"=" { return new Symbol(sym.EQ); }
"<>" { return new Symbol(sym.NE); }
"<" { return new Symbol(sym.LT); }
"<=" { return new Symbol(sym.LE); }
">" { return new Symbol(sym.GT); }
">=" { return new Symbol(sym.GE); }
"&&" { return new Symbol(sym.AND); }
"||" { return new Symbol(sym.OR); }
"!" { return new Symbol(sym.NOT); }
<<EOF>> { return new Symbol(sym.EOF); }
[^] { return new Symbol(sym.ERROR, yytext()); }

