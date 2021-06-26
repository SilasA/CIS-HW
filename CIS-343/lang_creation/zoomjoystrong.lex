%{

    #include <stdlib.h>
    #include <stdio.h>
    #include "zoomjoystrong.tab.h"

%}

%%

\-?[0-9]+      { yylval.i = atoi(yytext); return INT; }
\-?([0-9]*\.)?[0-9]+   { printf("Syntax error: floating point numbers not allowed.\n"); return FLOAT; }
rectangle   { return RECTANGLE; }
line        { return LINE; }
circle      { return CIRCLE; }
point       { return POINT; }
set_color   { return SET_COLOR; }
;           { return END_STATEMENT; }
[ \t\s\n\r] ;
.           { printf("Syntax error: unknown token.\n"); }

%%

