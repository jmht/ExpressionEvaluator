grammar ExpressionGrammar;
@header { package com.higginsthomas.expressionevaluator.grammar; }

/* Non-terminals */
start : expr? EOF;

expr : ( not )* '(' expr ')'                    # notExpr
     | relation                                 # relExpr
     | expr and expr                            # andExpr
     | expr or expr                             # orExpr
     ;
     
relation : simpleValue operator simpleValue     # compare
         | simpleValue 'in' collection          # inCollection
         | simpleValue like STRING              # regexCompare
         ;

collection : range
           | set
           ;

range : '[' constant '>'? ':' '<'? constant ']'; 

set : '{' (constant (',' constant)*)? '}';

operator : eq | ne | lt | gt | le | ge;

simpleValue : IDENTIFIER                        #identifier
            | constant                          #constValue
            ;
            
constant : INTEGER                              # intConstant
         | DECIMAL                              # decConstant
         | FLOAT                                # floatConstant
         | STRING                               # strConstant
         | DATE                                 # dateConstant
         ;

/* Psuedo-terminals */
// These are defined as rules to allow for the fact that these symbols have other
// lexical meanings in the grammar.
or : 'or' | '|' | '||';

and : 'and' | '&' | '&&';

not : 'not' | '~';

eq : 'eq' | '=' | '==';

ne : 'ne' | '!=' | '<>';

lt : 'lt' | '<';

gt : 'gt' | '>';

le : 'le' | '<=';

ge : 'ge' | '>=';

like : 'like' | '~' | '~=';


/* Terminals */
IDENTIFIER : ID_NAME (ID_CONJ ID_NAME)*;

INTEGER : SIGN? DIGIT+;

DECIMAL : INTEGER '.' DIGIT*
        | SIGN? '.' DIGIT+;

FLOAT : (INTEGER | DECIMAL ) ([eE] SIGN? DIGIT+);

STRING : DQUOTE (CHAR | SQUOTE)*? DQUOTE
       | SQUOTE (CHAR | DQUOTE)*? SQUOTE;

DATE : DIGIT? DIGIT '-' DIGIT? DIGIT '-' DIGIT DIGIT DIGIT DIGIT
     | DIGIT? DIGIT '/' DIGIT? DIGIT '/' DIGIT DIGIT DIGIT DIGIT;
       
WS : [ \t\n\r]+ -> skip ;

/* Token fragments */
fragment SIGN : ('+' | '-');

fragment DIGIT : [0-9];

fragment LETTER : [a-zA-Z];

// Identifiers consist of simple or compound "names". Names must begin with a letter, but may consist
// of letters and digits. Compound names are simple names conjoined with either a period or hyphen.
fragment ID_LETTER : (LETTER | '_');                    // Underscore is considered a "letter" in an identifier

fragment ID_NAME : ID_LETTER (ID_LETTER | DIGIT)*; 

fragment ID_CONJ : [-\.]; 

// Strings consist of character sequences within a matched pair of single or double quotes. The backslash character
// is used as an escape the following character.
fragment CHAR : ESC  
              | ~[\'\"];
       
fragment ESC : BSLASH .;                                // Backslash escapes any following character - interpretation
                                                        // of the escape sequence is deferred to the semantic layer. 

fragment SQUOTE : '\'';

fragment DQUOTE : '"';

fragment BSLASH : '\\';
