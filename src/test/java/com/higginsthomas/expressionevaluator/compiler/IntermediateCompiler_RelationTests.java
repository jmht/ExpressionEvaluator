package com.higginsthomas.expressionevaluator.compiler;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import com.higginsthomas.expressionevaluator.executer.operations.*;


public class IntermediateCompiler_RelationTests extends IntermediateCompilerTestBase {

    @Test
    public void testRelation_Compare_LT() {
        ParseTree tree = parser("x < 5").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        LtOperation result = (LtOperation)sut.visit(tree);

        assert(result.getRight().getValue().equals(5));
        assertThat(result.isNegated(), is(false));
    }

    @Test
    public void testRelation_Compare_GT() {
        ParseTree tree = parser("x > 5").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        LtOperation result = (LtOperation)sut.visit(tree);

        assert(result.getLeft().getValue().equals(5));
        assertThat(result.isNegated(), is(false));
    }

    @Test
    public void testRelation_Compare_GE() {
        ParseTree tree = parser("x >= 5").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        LtOperation result = (LtOperation)sut.visit(tree);

        assert(result.getLeft().getValue().equals(5));
        assertThat(result.isNegated(), is(true));
    }

    @Test
    public void testRelation_Compare_LE() {
        ParseTree tree = parser("x <= 5").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        LtOperation result = (LtOperation)sut.visit(tree);

        assert(result.getRight().getValue().equals(5));
        assertThat(result.isNegated(), is(true));
    }

    @Test
    public void testRelation_Compare_EQ() {
        ParseTree tree = parser("x = 5").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        EqOperation result = (EqOperation)sut.visit(tree);

        assert(result.getRight().getValue().equals(5));
        assertThat(result.isNegated(), is(false));
    }

    @Test
    public void testRelation_Compare_NE() {
        ParseTree tree = parser("x != 5").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        EqOperation result = (EqOperation)sut.visit(tree);

        assert(result.getRight().getValue().equals(5));
        assertThat(result.isNegated(), is(true));
    }
}
