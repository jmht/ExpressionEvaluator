package com.higginsthomas.expressionevaluator.compiler;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import com.higginsthomas.expressionevaluator.executer.operations.*;


public class IntermediateCompiler_RangeTests extends IntermediateCompilerTestBase {
    
    @Test
    public void testRelation_InCollection_Range_Exclusive() {
        ParseTree tree = parser("x in [1>:<10]").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        IntermediateCode result = sut.visit(tree).getIntermediateCode();
        
        assertThat(result.getOperationAt(-1), instanceOf(IsLT.class));
        assertThat(result.getOperationAt(-2), instanceOf(LoadConstant.class));
        assertThat(result.getOperationAt(-3), instanceOf(LoadIdentifier.class));

        assertThat(result.getOperationAt(-4), instanceOf(JumpIfFalse.class));
        assertThat(((Jump)result.getOperationAt(-4)).getOffset(), is(equalTo(4)));
        
        assertThat(result.getOperationAt(-5), instanceOf(IsLT.class));
        assertThat(result.getOperationAt(-6), instanceOf(LoadIdentifier.class));
        assertThat(result.getOperationAt(-7), instanceOf(LoadConstant.class));
    }
    
    @Test
    public void testRelation_InCollection_Range_ExclusiveRight() {
        ParseTree tree = parser("x in [1:<10]").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        IntermediateCode result = sut.visit(tree).getIntermediateCode();
        
        assertThat(result.getOperationAt(-1), instanceOf(IsLT.class));
        assertThat(result.getOperationAt(-2), instanceOf(LoadConstant.class));
        assertThat(result.getOperationAt(-3), instanceOf(LoadIdentifier.class));

        assertThat(result.getOperationAt(-4), instanceOf(JumpIfFalse.class));
        assertThat(((Jump)result.getOperationAt(-4)).getOffset(), is(equalTo(4)));
        
        assertThat(result.getOperationAt(-5), instanceOf(Not.class));
        assertThat(result.getOperationAt(-6), instanceOf(IsLT.class));
        assertThat(result.getOperationAt(-7), instanceOf(LoadConstant.class));
        assertThat(result.getOperationAt(-8), instanceOf(LoadIdentifier.class));
    }
    
    @Test
    public void testRelation_InCollection_Range_ExclusiveLeft() {
        ParseTree tree = parser("x in [1>:10]").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        IntermediateCode result = sut.visit(tree).getIntermediateCode();

        assertThat(result.getOperationAt(-1), instanceOf(Not.class));
        assertThat(result.getOperationAt(-2), instanceOf(IsLT.class));
        assertThat(result.getOperationAt(-3), instanceOf(LoadIdentifier.class));
        assertThat(result.getOperationAt(-4), instanceOf(LoadConstant.class));

        assertThat(result.getOperationAt(-5), instanceOf(JumpIfFalse.class));
        assertThat(((Jump)result.getOperationAt(-5)).getOffset(), is(equalTo(5)));
        
        assertThat(result.getOperationAt(-6), instanceOf(IsLT.class));
        assertThat(result.getOperationAt(-7), instanceOf(LoadIdentifier.class));
        assertThat(result.getOperationAt(-8), instanceOf(LoadConstant.class));
    }
    
    @Test
    public void testRelation_InCollection_Range_Inclusive() {
        ParseTree tree = parser("x in [1:10]").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        IntermediateCode result = sut.visit(tree).getIntermediateCode();

        assertThat(result.getOperationAt(-1), instanceOf(Not.class));
        assertThat(result.getOperationAt(-2), instanceOf(IsLT.class));
        assertThat(result.getOperationAt(-3), instanceOf(LoadIdentifier.class));
        assertThat(result.getOperationAt(-4), instanceOf(LoadConstant.class));

        assertThat(result.getOperationAt(-5), instanceOf(JumpIfFalse.class));
        assertThat(((Jump)result.getOperationAt(-5)).getOffset(), is(equalTo(5)));
        
        assertThat(result.getOperationAt(-6), instanceOf(Not.class));
        assertThat(result.getOperationAt(-7), instanceOf(IsLT.class));
        assertThat(result.getOperationAt(-8), instanceOf(LoadConstant.class));
        assertThat(result.getOperationAt(-9), instanceOf(LoadIdentifier.class));
    }
}
