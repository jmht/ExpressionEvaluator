package com.higginsthomas.expressionevaluator.compiler;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Before;
import org.junit.Test;

import com.higginsthomas.expressionevaluator.PropertyMap;
import com.higginsthomas.expressionevaluator.PropertyValueType;
import com.higginsthomas.expressionevaluator.executer.operations.*;


public class IntermediateCompiler_RangeTests extends IntermediateCompilerTestBase {
    private IntermediateCompiler sut;
    
    @Before
    public void beforeEachTest() {
        sut = new IntermediateCompiler(new PropertyMap() {
            public boolean exists(String propertyName) { return "x".equalsIgnoreCase(propertyName); }
            public PropertyValueType getType(String propertyName) { return PropertyValueType.INTEGER; }
        });
    }
    
    @Test
    public void testRelation_InCollection_Range_Exclusive() {
        ParseTree tree = parser("x in [1>:<10]").relation();
        
        InOperation result = (InOperation)sut.visit(tree);
    }
    
    @Test
    public void testRelation_InCollection_Range_ExclusiveRight() {
        ParseTree tree = parser("x in [1:<10]").relation();
        
        InOperation result = (InOperation)sut.visit(tree);
    }
    
    @Test
    public void testRelation_InCollection_Range_ExclusiveLeft() {
        ParseTree tree = parser("x in [1>:10]").relation();
        
        InOperation result = (InOperation)sut.visit(tree);
    }
    
    @Test
    public void testRelation_InCollection_Range_Inclusive() {
        ParseTree tree = parser("x in [1:10]").relation();
        
        InOperation result = (InOperation)sut.visit(tree);
    }
}
