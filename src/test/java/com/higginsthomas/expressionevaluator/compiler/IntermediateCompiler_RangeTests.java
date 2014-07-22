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
        
        InOperation result = (InOperation)sut.visit(tree);
    }
    
    @Test
    public void testRelation_InCollection_Range_ExclusiveRight() {
        ParseTree tree = parser("x in [1:<10]").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        InOperation result = (InOperation)sut.visit(tree);
    }
    
    @Test
    public void testRelation_InCollection_Range_ExclusiveLeft() {
        ParseTree tree = parser("x in [1>:10]").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        InOperation result = (InOperation)sut.visit(tree);
    }
    
    @Test
    public void testRelation_InCollection_Range_Inclusive() {
        ParseTree tree = parser("x in [1:10]").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        InOperation result = (InOperation)sut.visit(tree);
    }
}
