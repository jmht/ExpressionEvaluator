package com.higginsthomas.expressionevaluator.compiler;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Before;
import org.junit.Test;

import com.higginsthomas.expressionevaluator.PropertyMap;
import com.higginsthomas.expressionevaluator.PropertyValueType;
import com.higginsthomas.expressionevaluator.evaluator.operations.*;
import com.higginsthomas.expressionevaluator.values.IdentifierTable;
import com.higginsthomas.expressionevaluator.values.IdentifierValue;
import com.higginsthomas.expressionevaluator.values.RangeValue;


public class IntermediateCompiler_RangeTests extends IntermediateCompilerTestBase {
    private IntermediateCompiler sut;
    
    @Before
    public void beforeEachTest() {
        sut = new IntermediateCompiler(new IdentifierTable(), new PropertyMap() {
            public boolean exists(String propertyName) { return "x".equalsIgnoreCase(propertyName); }
            public PropertyValueType getType(String propertyName) { return PropertyValueType.INTEGER; }
        });
    }
    
    @Test
    public void testRelation_InCollection_Range_Exclusive() {
        ParseTree tree = parser("x in [1>:<10]").start();
        
        InOperation result = (InOperation)sut.visit(tree);

        assertThat(result.isNegated(), is(false));
        assertThat(result.getOperand(), instanceOf(IdentifierValue.class));
        assertThat(result.getCollection(), instanceOf(RangeValue.class));
    }
    
    @Test
    public void testRelation_InCollection_Range_ExclusiveRight() {
        ParseTree tree = parser("x in [1:<10]").start();

        InOperation result = (InOperation)sut.visit(tree);

        assertThat(result.isNegated(), is(false));
        assertThat(result.getOperand(), instanceOf(IdentifierValue.class));
        assertThat(result.getCollection(), instanceOf(RangeValue.class));
    }
    
    @Test
    public void testRelation_InCollection_Range_ExclusiveLeft() {
        ParseTree tree = parser("x in [1>:10]").start();

        InOperation result = (InOperation)sut.visit(tree);

        assertThat(result.isNegated(), is(false));
        assertThat(result.getOperand(), instanceOf(IdentifierValue.class));
        assertThat(result.getCollection(), instanceOf(RangeValue.class));
    }
    
    @Test
    public void testRelation_InCollection_Range_Inclusive() {
        ParseTree tree = parser("x in [1:10]").start();

        InOperation result = (InOperation)sut.visit(tree);

        assertThat(result.isNegated(), is(false));
        assertThat(result.getOperand(), instanceOf(IdentifierValue.class));
        assertThat(result.getCollection(), instanceOf(RangeValue.class));
    }
}
