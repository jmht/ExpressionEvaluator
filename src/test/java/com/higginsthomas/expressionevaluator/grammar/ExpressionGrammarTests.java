package com.higginsthomas.expressionevaluator.grammar;

import static org.junit.Assert.*;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import org.junit.Test;


public class ExpressionGrammarTests {
    @Test
    public void test() {
        String source = "x";
        ANTLRInputStream istream = new ANTLRInputStream(source);
        ExpressionGrammarLexer lexer = new ExpressionGrammarLexer(istream);
        CommonTokenStream tstream = new CommonTokenStream(lexer);
        ExpressionGrammarParser parser = new ExpressionGrammarParser(tstream);
        parser.constant();
    }
}
