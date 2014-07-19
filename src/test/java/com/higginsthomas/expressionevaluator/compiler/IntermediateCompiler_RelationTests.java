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
        
        IntermediateCode result = sut.visit(tree).getIntermediateCode();
        
        assertThat(result.getOperationAt(-1), instanceOf(IsLT.class));
        assertThat(result.getOperationAt(-2), instanceOf(LoadConstant.class));
        assertThat(result.getOperationAt(-3), instanceOf(LoadIdentifier.class));
    }

    @Test
    public void testRelation_Compare_GT() {
        ParseTree tree = parser("x > 5").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        IntermediateCode result = sut.visit(tree).getIntermediateCode();
        
        assertThat(result.getOperationAt(-1), instanceOf(IsLT.class));
        assertThat(result.getOperationAt(-2), instanceOf(LoadIdentifier.class));
        assertThat(result.getOperationAt(-3), instanceOf(LoadConstant.class));
    }

    @Test
    public void testRelation_Compare_GE() {
        ParseTree tree = parser("x >= 5").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        IntermediateCode result = sut.visit(tree).getIntermediateCode();
        
        assertThat(result.getOperationAt(-1), instanceOf(Not.class));
        assertThat(result.getOperationAt(-2), instanceOf(IsLT.class));
        assertThat(result.getOperationAt(-3), instanceOf(LoadConstant.class));
        assertThat(result.getOperationAt(-4), instanceOf(LoadIdentifier.class));
    }

    @Test
    public void testRelation_Compare_LE() {
        ParseTree tree = parser("x <= 5").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        IntermediateCode result = sut.visit(tree).getIntermediateCode();
        
        assertThat(result.getOperationAt(-1), instanceOf(Not.class));
        assertThat(result.getOperationAt(-2), instanceOf(IsLT.class));
        assertThat(result.getOperationAt(-3), instanceOf(LoadIdentifier.class));
        assertThat(result.getOperationAt(-4), instanceOf(LoadConstant.class));
    }

    @Test
    public void testRelation_Compare_EQ() {
        ParseTree tree = parser("x = 5").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        IntermediateCode result = sut.visit(tree).getIntermediateCode();
        
        assertThat(result.getOperationAt(-1), instanceOf(IsEQ.class));
        assertThat(result.getOperationAt(-2), instanceOf(LoadConstant.class));
        assertThat(result.getOperationAt(-3), instanceOf(LoadIdentifier.class));
    }

    @Test
    public void testRelation_Compare_NE() {
        ParseTree tree = parser("x != 5").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        IntermediateCode result = sut.visit(tree).getIntermediateCode();
        
        assertThat(result.getOperationAt(-1), instanceOf(Not.class));
        assertThat(result.getOperationAt(-2), instanceOf(IsEQ.class));
        assertThat(result.getOperationAt(-3), instanceOf(LoadConstant.class));
        assertThat(result.getOperationAt(-4), instanceOf(LoadIdentifier.class));
    }
}
