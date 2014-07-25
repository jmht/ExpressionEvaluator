package com.higginsthomas.expressionevaluator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ExpressionEvaluatorIntegrationTests {
    private ExpressionCompiler compiler;
    
    @Before
    public void before() {
        this.compiler = new ExpressionCompiler();
    }
    
    @Test
    public void test_1() {
        ExpressionEvaluator evaluator = compiler.compile("x = 5", 
                new PropertyMap() {
                    public boolean exists(String propertyName) { return ( propertyName.equals("x")); }
                    public PropertyValueType getType(String propertyName) { return PropertyValueType.INTEGER; }
                });
        
        assertThat(evaluator.evaluate(new PropertySet() {
                public boolean exists(String propertyName) { return ( propertyName.equals("x")); }
                public PropertyValueType getType(String propertyName) { return PropertyValueType.INTEGER; }
                public PropertyValue get(String propertyName) { return new IntegerPropertyValue(1); }
            }), 
            equalTo(true));
    }
}
