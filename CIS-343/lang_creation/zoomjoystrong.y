%{
    #include <stdio.h>
    #include "zoomjoystrong.h"

    void yyerror(const char* s);
    int yywrap();
    int yylex();
%}

%define parse.error verbose

%union {
    int i;
    float f;
}
%start commands

%token END
%token RECTANGLE
%token LINE
%token CIRCLE
%token POINT
%token SET_COLOR
%token END_STATEMENT
%token INT
%token FLOAT

%type<i> INT
%type<f> FLOAT

%%

commands:   expression commands
        |   END
        ;

expression:   rect_exp
          |   line_exp
          |   circle_exp
          |   point_exp
          |   set_color_exp 
          ;


rect_exp:       RECTANGLE INT INT INT INT END_STATEMENT  
        { 
            if ($2 >= 0 && $3 >= 0 && $4 >= 0 && $5 >= 0)
                rectangle($2, $3, $4, $5);
            else
                yyerror("Syntax error: rectangle arg is out of range. x,y: =>0, h,w: >0.");
        }
;

line_exp:       LINE INT INT INT INT END_STATEMENT  
        { 
            if ($2 >= 0 && $3 >= 0 && $4 >= 0 && $5 >= 0)
                line($2, $3, $4, $5);
            else
                yyerror("Syntax error: line arg is out of range. =>0");
        }
;

circle_exp:     CIRCLE INT INT INT END_STATEMENT         
        {
            if ($2 >= 0 && $3 >= 0 && $4 > 0)
                circle($2, $3, $4);
            else
                yyerror("Syntax error: circle arg is out of range. x,y: =>0, r: >0.");
        }
;

point_exp:      POINT INT INT END_STATEMENT            
        {
            if ($2 >= 0 && $3 >= 0)
                point($2, $3);
            else
                yyerror("Syntax error: point arg is out of range. >0");
        }
;

set_color_exp:  SET_COLOR INT INT INT END_STATEMENT        
            {
                if ($2 >= 0 && $2 <= 255 && $3 >= 0 && $3 <= 255 && $4 >= 0 && $4 <= 255)
                    set_color($2, $3, $4); 
                else
                    yyerror("Syntax error: set_color arg is out of range (0->255).");
            }
;

%%

int main(int argc, char** argv) {
    setup();
    yyparse();
    return 0;
}

void yyerror(char const* s) {
    printf("%s\n", s);
}

int yywrap() {
    return 1;
}

