package com.higginsthomas.expressionevaluator.grammar;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Before;
import org.junit.Test;

import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarParser.*;


public class ExpressionGrammarTests {
    private ExpressionGrammarLexer lexer;
    private CommonTokenStream tstream;
    private ExpressionGrammarParser parser;
    
    @Before
    public void before() {
        this.lexer = new ExpressionGrammarLexer(null);
        this.tstream = new CommonTokenStream(lexer);
        this.parser = new ExpressionGrammarParser(tstream);
        lexer.removeErrorListeners();
        parser.removeErrorListeners();
        parser.setErrorHandler(new AbortErrorStrategy());
    }

    @Test
    public void testConstant_Integer() {
        String source = "123";
        lexer.setInputStream(new ANTLRInputStream(source));
        
        ParseTree result = parser.constant();
        
        assertThat(result, instanceOf(ConstantContext.class));
    }

    @Test
    public void testConstant_PositiveInteger() {
        String source = "+123";
        lexer.setInputStream(new ANTLRInputStream(source));
        
        ParseTree result = parser.constant();
        
        assertThat(result, instanceOf(ConstantContext.class));
    }

    @Test
    public void testConstant_NegativeInteger() {
        String source = "-123";
        lexer.setInputStream(new ANTLRInputStream(source));
        
        ParseTree result = parser.constant();
        
        assertThat(result, instanceOf(ConstantContext.class));
    }

    @Test
    public void testConstant_Decimal() {
        String source = "123.45";
        lexer.setInputStream(new ANTLRInputStream(source));
        
        ParseTree result = parser.constant();
        
        assertThat(result, instanceOf(ConstantContext.class));
    }

    @Test
    public void testConstant_DecimalFraction() {
        String source = ".45";
        lexer.setInputStream(new ANTLRInputStream(source));
        
        ParseTree result = parser.constant();
        
        assertThat(result, instanceOf(ConstantContext.class));
    }

    @Test
    public void testConstant_DecimalIntegral() {
        String source = "1.";
        lexer.setInputStream(new ANTLRInputStream(source));
        
        ParseTree result = parser.constant();
        
        assertThat(result, instanceOf(ConstantContext.class));
    }

    @Test
    public void testConstant_DecimalFractionPositive() {
        String source = "+.45";
        lexer.setInputStream(new ANTLRInputStream(source));
        
        ParseTree result = parser.constant();
        
        assertThat(result, instanceOf(ConstantContext.class));
    }

    @Test
    public void testConstant_DecimalFractionNegative() {
        String source = "-.45";
        lexer.setInputStream(new ANTLRInputStream(source));
        
        ParseTree result = parser.constant();
        
        assertThat(result, instanceOf(ConstantContext.class));
    }

    @Test
    public void testConstant_FloatA() {
        String source = "1e1";
        lexer.setInputStream(new ANTLRInputStream(source));
        
        ParseTree result = parser.constant();
        
        assertThat(result, instanceOf(ConstantContext.class));
    }

    @Test
    public void testConstant_FloatB() {
        String source = "1e-1";
        lexer.setInputStream(new ANTLRInputStream(source));
        
        ParseTree result = parser.constant();
        
        assertThat(result, instanceOf(ConstantContext.class));
    }

    @Test
    public void testConstant_FloatC() {
        String source = "1e+12";
        lexer.setInputStream(new ANTLRInputStream(source));
        
        ParseTree result = parser.constant();
        
        assertThat(result, instanceOf(ConstantContext.class));
    }

    @Test
    public void testConstant_FloatD() {
        String source = ".1e1";
        lexer.setInputStream(new ANTLRInputStream(source));
        
        ParseTree result = parser.constant();
        
        assertThat(result, instanceOf(ConstantContext.class));
    }

    @Test
    public void testConstant_FloatE() {
        String source = "-.1e1";
        lexer.setInputStream(new ANTLRInputStream(source));
        
        ParseTree result = parser.constant();
        
        assertThat(result, instanceOf(ConstantContext.class));
    }

    @Test
    public void testConstant_FloatF() {
        String source = "+1.1e1";
        lexer.setInputStream(new ANTLRInputStream(source));
        
        ParseTree result = parser.constant();
        
        assertThat(result, instanceOf(ConstantContext.class));
    }

    @Test(expected= RuntimeException.class)
    public void testConstant_ParseError_Incomplete() {
        String source = "+";
        lexer.setInputStream(new ANTLRInputStream(source));
        
        parser.constant();
    }

    @Test
    public void testConstant_ParseError_MissingExponent() {
        String source = "+1.1e";
        lexer.setInputStream(new ANTLRInputStream(source));
        
        parser.constant();
    }

    @Test(expected= RuntimeException.class)
    public void testConstant_ParseError_NotInteger() {
        String source = "x";
        lexer.setInputStream(new ANTLRInputStream(source));
        
        parser.constant();
    }
    
    private class AbortErrorStrategy extends DefaultErrorStrategy {
        @Override
        public void recover(Parser recognizer, RecognitionException e) {
            throw new RuntimeException(e);
        }
        
        @Override
        public Token recoverInline(Parser recognizer) throws RecognitionException {
            throw new RuntimeException(new InputMismatchException(recognizer));
        }
        
        @Override
        public void sync(Parser recognizer) { }
    }
}
