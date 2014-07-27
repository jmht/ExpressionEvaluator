package com.higginsthomas.expressionevaluator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;


public class ExpressionEvaluatorIntegrationTests {
    private static final TestData testData[] = {
        new TestData("i", new IntegerPropertyValue(5)),
        new TestData("d", new DecimalPropertyValue(new BigDecimal("3.14"))),
        new TestData("f", new FloatPropertyValue(1E6)),
        new TestData("c", new DatePropertyValue(new LocalDate(2014, 7, 30))),
        new TestData("t", new TextPropertyValue("Test"))
    };
    private ExpressionCompiler compiler;
    private PropertyMap propertyMap;
    private PropertySet propertySet;
    
    @Before
    public void before() {
        this.compiler = new ExpressionCompiler();
        this.propertyMap = new TestPropertyMap();
        this.propertySet = new TestPropertySet();
    }
    
    @Test
    public void test_iEq5() {
        ExpressionEvaluator evaluator = compiler.compile("i = 5", propertyMap);
        
        assertThat(evaluator.evaluate(propertySet), equalTo(true));
    }
    
    @Test
    public void test_iNe5() {
        ExpressionEvaluator evaluator = compiler.compile("i != 5", propertyMap);
        
        assertThat(evaluator.evaluate(propertySet), equalTo(false));
    }
    
    @Test
    public void test_iLt5() {
        ExpressionEvaluator evaluator = compiler.compile("i < 5", propertyMap); 
        
        assertThat(evaluator.evaluate(propertySet), equalTo(false));
    }
    
    @Test
    public void test_iGe5() {
        ExpressionEvaluator evaluator = compiler.compile("i >= 5", propertyMap); 
        
        assertThat(evaluator.evaluate(propertySet), equalTo(true));
    }
    
    @Test
    public void test_iGt5() {
        ExpressionEvaluator evaluator = compiler.compile("i > 5", propertyMap); 
        
        assertThat(evaluator.evaluate(propertySet), equalTo(false));
    }
    
    @Test
    public void test_iLe5() {
        ExpressionEvaluator evaluator = compiler.compile("i <= 5", propertyMap); 
        
        assertThat(evaluator.evaluate(propertySet), equalTo(true));
    }
    
    @Test
    public void test_iEq5ORdGt3() {
        ExpressionEvaluator evaluator = compiler.compile("i = 5 or d gt 3.", propertyMap);
        
        assertThat(evaluator.evaluate(propertySet), equalTo(true));
    }
    
    @Test
    public void test_iEq5ANDdGt3() {
        ExpressionEvaluator evaluator = compiler.compile("i = 5 and d gt 3.", propertyMap);
        
        assertThat(evaluator.evaluate(propertySet), equalTo(true));
    }
    
    @Test
    public void test_iEq5OrNOTdGt3() {
        ExpressionEvaluator evaluator = compiler.compile("i = 5 or !(d gt 3.)", propertyMap);
        
        assertThat(evaluator.evaluate(propertySet), equalTo(true));
    }
    
    @Test
    public void test_iEq5AndNOTdGt3() {
        ExpressionEvaluator evaluator = compiler.compile("i = 5 and !(d gt 3.)", propertyMap);
        
        assertThat(evaluator.evaluate(propertySet), equalTo(false));
    }
    
    private static class TestData {
        public final String name;
        public final PropertyValue value;
        public TestData(String n, PropertyValue v) {
            name = n; value = v;
        }
    }
    
    private class TestPropertyMap implements PropertyMap {
        @Override
        public boolean exists(String propertyName) { 
            for ( TestData elem : testData ) {
                if ( elem.name.equals(propertyName) ) return true;
            }
            return false;
        }
        @Override
        public PropertyValueType getType(String propertyName) {
            for ( TestData elem : testData ) {
                if ( elem.name.equals(propertyName) ) return elem.value.getType();
            }
            return null;
        }
    }
    
    private class TestPropertySet extends TestPropertyMap implements PropertySet {
        @Override
        public PropertyValue get(String propertyName) {
            for ( TestData elem : testData ) {
                if ( elem.name.equals(propertyName) ) return elem.value;
            }
            return null;
        }
    }
}
