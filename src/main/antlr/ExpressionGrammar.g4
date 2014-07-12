grammar ExpressionGrammar;
@header { package com.higginsthomas.expressionevaluator.grammar; }

/* Non-terminals */
start : expr? EOF;

expr : relation
     | expr and expr                // 'and' has precedence (tighter binding) than 'or'
     | expr or expr
     | ( not )* '(' expr ')';
     
relation : simpleValue operator simpleValue
         | simpleValue 'in' collection
         | simpleValue like STRING;

collection : range
           | set;

range : '[' constant '>'? ':' '<'? constant ']'; 

set : '{' (constant (',' constant)*)? '}';

operator : eq | ne | lt | gt | le | ge;

simpleValue : IDENTIFIER
            | constant;
            
constant : INTEGER
         | DECIMAL
         | FLOAT
         | STRING
         | DATE;

/* Psuedo-terminals */
// These reflect logical terminals defined by multiple lexical representations
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
IDENTIFIER : LETTER (LETTER | DIGIT | CONJ)*;

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

fragment ID_LETTER : (LETTER | CONJ);

fragment CONJ : [-_];

fragment CHAR : ESC
              | ~[\'\\"];
       
fragment ESC : BSLASH .;

fragment SQUOTE : '\'';

fragment DQUOTE : '"';

fragment BSLASH : '\\';
