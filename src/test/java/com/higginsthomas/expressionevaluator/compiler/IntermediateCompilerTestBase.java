package com.higginsthomas.expressionevaluator.compiler;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarLexer;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarParser;


public class IntermediateCompilerTestBase {
    protected ExpressionGrammarParser parser(final String query) {
        ANTLRInputStream istream = new ANTLRInputStream(query);
        ExpressionGrammarLexer lexer = new ExpressionGrammarLexer(istream);
        CommonTokenStream tstream = new CommonTokenStream(lexer);
        return new ExpressionGrammarParser(tstream);
    }
}
