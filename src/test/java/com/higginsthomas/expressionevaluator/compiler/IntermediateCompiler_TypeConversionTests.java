package com.higginsthomas.expressionevaluator.compiler;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Before;
import org.junit.Test;

import com.higginsthomas.expressionevaluator.collection.RangeValue;
import com.higginsthomas.expressionevaluator.collection.SetValue;
import com.higginsthomas.expressionevaluator.evaluator.operations.*;
import com.higginsthomas.expressionevaluator.properties.DatePropertyValue;
import com.higginsthomas.expressionevaluator.properties.DecimalPropertyValue;
import com.higginsthomas.expressionevaluator.properties.FloatPropertyValue;
import com.higginsthomas.expressionevaluator.properties.IntegerPropertyValue;
import com.higginsthomas.expressionevaluator.properties.PropertyMap;
import com.higginsthomas.expressionevaluator.properties.PropertyValueType;
import com.higginsthomas.expressionevaluator.identifiers.*;


public class IntermediateCompiler_TypeConversionTests extends IntermediateCompilerTestBase {
    private IntermediateCompiler sut;
    
    @Before
    public void beforeEachTest() {
        sut = new IntermediateCompiler(new IdentifierTable(), new PropertyMap() {
            public boolean exists(String propertyName) { return "x".equalsIgnoreCase(propertyName); }
            public PropertyValueType getType(String propertyName) { return PropertyValueType.INTEGER; }
        });
    }

    @Test
    public void testRelation_TypeConversion_Integer_Decimal() {
        ParseTree tree = parser("3 eq 3.0").start();
        
        EqOperation result = (EqOperation)sut.visit(tree);

        assertThat(result.isNegated(), is(false));
        assertThat(result.getLeft(), instanceOf(DecimalPropertyValue.class));
        assertThat(result.getRight(), instanceOf(DecimalPropertyValue.class));
    }

    @Test
    public void testRelation_TypeConversion_Integer_Float() {
        ParseTree tree = parser("3 eq 3e0").start();
        
        EqOperation result = (EqOperation)sut.visit(tree);

        assertThat(result.isNegated(), is(false));
        assertThat(result.getLeft(), instanceOf(FloatPropertyValue.class));
        assertThat(result.getRight(), instanceOf(FloatPropertyValue.class));
    }

    @Test(expected= InternalCompileException.class)
    public void testRelation_TypeConversion_Integer_Date() {
        ParseTree tree = parser("3 eq 3/14/2014").start();
        
        sut.visit(tree);
    }

    @Test
    public void testRelation_TypeConversion_Integer_Text() {
        ParseTree tree = parser("3 eq '3'").start();
        
        EqOperation result = (EqOperation)sut.visit(tree);

        assertThat(result.isNegated(), is(false));
        assertThat(result.getLeft(), instanceOf(IntegerPropertyValue.class));
        assertThat(result.getRight(), instanceOf(IntegerPropertyValue.class));
    }

    @Test
    public void testRelation_TypeConversion_Decimal_Float() {
        ParseTree tree = parser("3. eq 3e0").start();
        
        EqOperation result = (EqOperation)sut.visit(tree);

        assertThat(result.isNegated(), is(false));
        assertThat(result.getLeft(), instanceOf(FloatPropertyValue.class));
        assertThat(result.getRight(), instanceOf(FloatPropertyValue.class));
    }

    @Test(expected= InternalCompileException.class)
    public void testRelation_TypeConversion_Decimal_Date() {
        ParseTree tree = parser("3. eq 3/14/2014").start();
        
        sut.visit(tree);
    }

    @Test
    public void testRelation_TypeConversion_Decimal_Text() {
        ParseTree tree = parser("3. eq '3'").start();
        
        EqOperation result = (EqOperation)sut.visit(tree);

        assertThat(result.isNegated(), is(false));
        assertThat(result.getLeft(), instanceOf(DecimalPropertyValue.class));
        assertThat(result.getRight(), instanceOf(DecimalPropertyValue.class));
    }

    @Test(expected= InternalCompileException.class)
    public void testRelation_TypeConversion_Float_Date() {
        ParseTree tree = parser("3e0 eq 3/14/2014").start();
        
        sut.visit(tree);
    }

    @Test
    public void testRelation_TypeConversion_Float_Text() {
        ParseTree tree = parser("3e0 eq '3'").start();
        
        EqOperation result = (EqOperation)sut.visit(tree);

        assertThat(result.isNegated(), is(false));
        assertThat(result.getLeft(), instanceOf(FloatPropertyValue.class));
        assertThat(result.getRight(), instanceOf(FloatPropertyValue.class));
    }

    @Test
    public void testRelation_TypeConversion_Date_Text_Slash() {
        ParseTree tree = parser("3/14/2014 eq '3/14/2014'").start();
        
        EqOperation result = (EqOperation)sut.visit(tree);

        assertThat(result.isNegated(), is(false));
        assertThat(result.getLeft(), instanceOf(DatePropertyValue.class));
        assertThat(result.getRight(), instanceOf(DatePropertyValue.class));
    }

    @Test
    public void testRelation_TypeConversion_Date_Text_Dash() {
        ParseTree tree = parser("3/14/2014 eq '3-14-2014'").start();
        
        EqOperation result = (EqOperation)sut.visit(tree);

        assertThat(result.isNegated(), is(false));
        assertThat(result.getLeft(), instanceOf(DatePropertyValue.class));
        assertThat(result.getRight(), instanceOf(DatePropertyValue.class));
    }

    @Test
    public void testRelation_TypeConversion_Date_Text_Packed() {
        ParseTree tree = parser("3/14/2014 eq '20140314'").start();
        
        EqOperation result = (EqOperation)sut.visit(tree);

        assertThat(result.isNegated(), is(false));
        assertThat(result.getLeft(), instanceOf(DatePropertyValue.class));
        assertThat(result.getRight(), instanceOf(DatePropertyValue.class));
    }

    @Test
    public void testRange_TypeConversion_Integer_Decimal() {
        ParseTree tree = parser("[1:<10.0]").range();
        
        RangeValue result = (RangeValue)sut.visit(tree);

        assertThat(result.getType(), is(PropertyValueType.DECIMAL));
    }

    @Test
    public void testSet_TypeConversion_Mixed() {
        ParseTree tree = parser("{1, 2.0, 3e0, '4'}").set();
        
        SetValue result = (SetValue)sut.visit(tree);

        assertThat(result.getType(), is(PropertyValueType.FLOAT));
    }
}
