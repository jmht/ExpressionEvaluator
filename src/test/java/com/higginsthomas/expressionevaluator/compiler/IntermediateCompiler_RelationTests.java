package com.higginsthomas.expressionevaluator.compiler;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.math.BigInteger;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Before;
import org.junit.Test;

import com.higginsthomas.expressionevaluator.*;
import com.higginsthomas.expressionevaluator.executer.operations.*;


public class IntermediateCompiler_RelationTests extends IntermediateCompilerTestBase {
    private IntermediateCompiler sut;
    
    @Before
    public void beforeEachTest() {
        sut = new IntermediateCompiler(new PropertyMap() {
            public boolean exists(String propertyName) { return "x".equalsIgnoreCase(propertyName); }
            public PropertyValueType getType(String propertyName) { return PropertyValueType.INTEGER; }
        });
    }

    @Test
    public void testRelation_Compare_LT() {
        ParseTree tree = parser("x < 5").relation();
        
        LtOperation result = (LtOperation)sut.visit(tree);

        assertThat(result.isNegated(), is(false));
        assertThat(result.getLeft(), instanceOf(IdentifierValue.class));
        assertThat(result.getRight(), instanceOf(IntegerPropertyValue.class));
        assertThat(((IntegerPropertyValue)result.getRight()).getValue().longValue(), equalTo(5L));
    }

    @Test
    public void testRelation_Compare_GT() {
        ParseTree tree = parser("x > 5").relation();
        
        LtOperation result = (LtOperation)sut.visit(tree);

        assertThat(result.isNegated(), is(false));
        assertThat(result.getLeft(), instanceOf(IntegerPropertyValue.class));
        assertThat(((IntegerPropertyValue)result.getLeft()).getValue().longValue(), equalTo(5L));
        assertThat(result.getRight(), instanceOf(IdentifierValue.class));
    }

    @Test
    public void testRelation_Compare_GE() {
        ParseTree tree = parser("x >= 5").relation();
        
        LtOperation result = (LtOperation)sut.visit(tree);

        assertThat(result.isNegated(), is(true));
        assertThat(result.getLeft(), instanceOf(IdentifierValue.class));
        assertThat(result.getRight(), instanceOf(IntegerPropertyValue.class));
        assertThat(((IntegerPropertyValue)result.getRight()).getValue().longValue(), equalTo(5L));
    }

    @Test
    public void testRelation_Compare_LE() {
        ParseTree tree = parser("x <= 5").relation();
        
        LtOperation result = (LtOperation)sut.visit(tree);

        assertThat(result.isNegated(), is(true));
        assertThat(result.getLeft(), instanceOf(IntegerPropertyValue.class));
        assertThat(((IntegerPropertyValue)result.getLeft()).getValue().longValue(), equalTo(5L));
        assertThat(result.getRight(), instanceOf(IdentifierValue.class));
    }

    @Test
    public void testRelation_Compare_EQ() {
        ParseTree tree = parser("x = 5").relation();
        
        EqOperation result = (EqOperation)sut.visit(tree);

        assertThat(result.isNegated(), is(false));
        assertThat(result.getLeft(), instanceOf(IdentifierValue.class));
        assertThat(result.getRight(), instanceOf(IntegerPropertyValue.class));
        assertThat(((IntegerPropertyValue)result.getRight()).getValue().longValue(), equalTo(5L));
    }

    @Test
    public void testRelation_Compare_NE() {
        ParseTree tree = parser("x != 5").relation();
       
        EqOperation result = (EqOperation)sut.visit(tree);

        assertThat(result.isNegated(), is(true));
        assertThat(result.getLeft(), instanceOf(IdentifierValue.class));
        assertThat(result.getRight(), instanceOf(IntegerPropertyValue.class));
        assertThat(((IntegerPropertyValue)result.getRight()).getValue().longValue(), equalTo(5L));
    }
}
