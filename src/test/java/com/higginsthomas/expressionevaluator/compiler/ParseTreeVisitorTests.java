package com.higginsthomas.expressionevaluator.compiler;

import static org.junit.Assert.*;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarLexer;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarParser;

public class ParseTreeVisitorTests {

    @Test
    public void test() {
        String source = "x < 5";
        ANTLRInputStream istream = new ANTLRInputStream(source);
        ExpressionGrammarLexer lexer = new ExpressionGrammarLexer(istream);
        CommonTokenStream tstream = new CommonTokenStream(lexer);
        ExpressionGrammarParser parser = new ExpressionGrammarParser(tstream);
        ParseTree tree = parser.relation();
        ParseTreeVisitor visitor = new ParseTreeVisitor();
        visitor.visit(tree);
    }
}
