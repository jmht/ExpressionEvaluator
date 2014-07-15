package com.higginsthomas.expressionevaluator.compiler;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import com.higginsthomas.expressionevaluator.executer.operations.*;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarLexer;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarParser;

public class IntermediateCompilerTests {

    @Test
    public void testRelation_Compare_LT() {
        ParseTree tree = parser("x < 5").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        IntermediateCode result = sut.visit(tree).getIntermediateCode();
        
        assertThat(result.getOperationAt(0), instanceOf(IsLT.class));
        assertThat(result.getOperationAt(1), instanceOf(LoadConstant.class));
        assertThat(result.getOperationAt(2), instanceOf(LoadIdentifier.class));
    }

    @Test
    public void testRelation_Compare_GT() {
        ParseTree tree = parser("x > 5").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        IntermediateCode result = sut.visit(tree).getIntermediateCode();
        
        assertThat(result.getOperationAt(0), instanceOf(IsLT.class));
        assertThat(result.getOperationAt(1), instanceOf(LoadIdentifier.class));
        assertThat(result.getOperationAt(2), instanceOf(LoadConstant.class));
    }

    @Test
    public void testRelation_Compare_GE() {
        ParseTree tree = parser("x >= 5").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        IntermediateCode result = sut.visit(tree).getIntermediateCode();
        
        assertThat(result.getOperationAt(0), instanceOf(Not.class));
        assertThat(result.getOperationAt(1), instanceOf(IsLT.class));
        assertThat(result.getOperationAt(2), instanceOf(LoadConstant.class));
        assertThat(result.getOperationAt(3), instanceOf(LoadIdentifier.class));
    }

    @Test
    public void testRelation_Compare_LE() {
        ParseTree tree = parser("x <= 5").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        IntermediateCode result = sut.visit(tree).getIntermediateCode();
        
        assertThat(result.getOperationAt(0), instanceOf(Not.class));
        assertThat(result.getOperationAt(1), instanceOf(IsLT.class));
        assertThat(result.getOperationAt(2), instanceOf(LoadIdentifier.class));
        assertThat(result.getOperationAt(3), instanceOf(LoadConstant.class));
    }

    @Test
    public void testRelation_Compare_EQ() {
        ParseTree tree = parser("x = 5").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        IntermediateCode result = sut.visit(tree).getIntermediateCode();
        
        assertThat(result.getOperationAt(0), instanceOf(IsEQ.class));
        assertThat(result.getOperationAt(1), instanceOf(LoadConstant.class));
        assertThat(result.getOperationAt(2), instanceOf(LoadIdentifier.class));
    }

    @Test
    public void testRelation_Compare_NE() {
        ParseTree tree = parser("x != 5").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        IntermediateCode result = sut.visit(tree).getIntermediateCode();
        
        assertThat(result.getOperationAt(0), instanceOf(Not.class));
        assertThat(result.getOperationAt(1), instanceOf(IsEQ.class));
        assertThat(result.getOperationAt(2), instanceOf(LoadConstant.class));
        assertThat(result.getOperationAt(3), instanceOf(LoadIdentifier.class));
    }
    
    @Test
    public void testRelation_InCollection_Range() {
        ParseTree tree = parser("x in [1:10]").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        IntermediateCode result = sut.visit(tree).getIntermediateCode();
        
        assertThat(result.getOperationAt(0), instanceOf(Not.class));
        assertThat(result.getOperationAt(1), instanceOf(IsLT.class));
        assertThat(result.getOperationAt(2), instanceOf(LoadIdentifier.class));
        assertThat(result.getOperationAt(3), instanceOf(LoadConstant.class));

        assertThat(result.getOperationAt(4), instanceOf(JumpIfFalse.class));
        assertThat(((Jump)result.getOperationAt(4)).getOffset(), is(equalTo(4)));
        
        assertThat(result.getOperationAt(5), instanceOf(Not.class));
        assertThat(result.getOperationAt(6), instanceOf(IsLT.class));
        assertThat(result.getOperationAt(7), instanceOf(LoadConstant.class));
        assertThat(result.getOperationAt(8), instanceOf(LoadIdentifier.class));
    }
    
    
    private ExpressionGrammarParser parser(final String query) {
        ANTLRInputStream istream = new ANTLRInputStream(query);
        ExpressionGrammarLexer lexer = new ExpressionGrammarLexer(istream);
        CommonTokenStream tstream = new CommonTokenStream(lexer);
        return new ExpressionGrammarParser(tstream);
    }
}
