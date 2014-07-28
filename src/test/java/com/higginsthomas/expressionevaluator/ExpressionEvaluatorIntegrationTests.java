package com.higginsthomas.expressionevaluator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ExpressionEvaluatorIntegrationTests {
    private final TestHelper helper = new TestHelper();
    private ExpressionCompiler compiler;
    private PropertyMap propertyMap;
    private PropertySet propertySet;
    
    @Before
    public void before() {
        this.compiler = new ExpressionCompiler();
        this.propertyMap = helper.getTestPropertyMap();
        this.propertySet = helper.getTestPropertySet();
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
}
