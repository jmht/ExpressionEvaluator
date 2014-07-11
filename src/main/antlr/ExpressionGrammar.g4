grammar ExpressionGrammar;
@header { package com.higginsthomas.expressionevaluator.grammar; }

/* Non-terminals */
expr : orExpr EOF;

orExpr : andExpr ( or orExpr )*;

andExpr : relation ( and andExpr )*;

relation : simpleValue operator simpleValue
         | simpleValue 'in' collection
         | simpleValue like STRING
         | nestedExpr;

nestedExpr : ( not )* '(' orExpr ')';

operator : eq | ne | lt | gt | le | ge;

simpleValue : IDENTIFIER
            | constant;
            
constant : NUMBER
         | STRING
         | DATE;

collection : range
           | set;

range : '[' constant '>'? '..' '<'? constant ']'; 

set : '{' (constant (',' constant)*)? '}';


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
// Identifier MUST follow definition of all keywords to avoid masking them
IDENTIFIER : LETTER (LETTER | DIGIT | CONJ)*;

NUMBER : SIGN? DIGIT+ ('.' DIGIT*)? EXPONENT?;

STRING : DQUOTE (CHAR | SQUOTE)* DQUOTE
       | SQUOTE (CHAR | DQUOTE)* SQUOTE;

DATE : DIGIT? DIGIT '-' DIGIT? DIGIT '-' DIGIT DIGIT DIGIT DIGIT
     | DIGIT? DIGIT '/' DIGIT? DIGIT '/' DIGIT DIGIT DIGIT DIGIT;
       
WS : [ \t\n\r]+ -> skip ;

/* Token fragments */
fragment SIGN : ('+' | '-');

fragment DIGIT : [0-9];

fragment EXPONENT : ([eE] SIGN? DIGIT+);

fragment LETTER : [a-zA-Z];

fragment CONJ : [-_];

fragment SQUOTE : '\'';

fragment DQUOTE : '"';

fragment CHAR : ESC
              | ~('\\' | '\'' | '"');
       
fragment ESC : BSLASH .;

fragment BSLASH : '\\';
