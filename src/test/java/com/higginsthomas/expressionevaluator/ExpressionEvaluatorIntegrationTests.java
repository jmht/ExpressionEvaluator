package com.higginsthomas.expressionevaluator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.higginsthomas.expressionevaluator.api.CompileException;
import com.higginsthomas.expressionevaluator.api.ExpressionCompiler;
import com.higginsthomas.expressionevaluator.api.ExpressionEvaluator;
import com.higginsthomas.expressionevaluator.properties.PropertyMap;
import com.higginsthomas.expressionevaluator.properties.PropertySet;


public class ExpressionEvaluatorIntegrationTests {
    private final TestHelper helper = new TestHelper();
    private ExpressionCompiler compiler;
    private PropertyMap propertyMap;
    private PropertySet propertySet;
    
    @Before
    public void before() {
        this.compiler = ExpressionCompiler.compiler();
        this.propertyMap = helper.getTestPropertyMap();
        this.propertySet = helper.getTestPropertySet();
    }
    
    @Test
    public void test_iEq5() throws CompileException {
        ExpressionEvaluator evaluator = compiler.compile("i = 5", propertyMap);
        
        assertThat(evaluator.evaluate(propertySet), equalTo(true));
    }
    
    @Test
    public void test_iNe5() throws CompileException {
        ExpressionEvaluator evaluator = compiler.compile("i != 5", propertyMap);
        
        assertThat(evaluator.evaluate(propertySet), equalTo(false));
    }
    
    @Test
    public void test_iLt5() throws CompileException {
        ExpressionEvaluator evaluator = compiler.compile("i < 5", propertyMap); 
        
        assertThat(evaluator.evaluate(propertySet), equalTo(false));
    }
    
    @Test
    public void test_iGe5() throws CompileException {
        ExpressionEvaluator evaluator = compiler.compile("i >= 5", propertyMap); 
        
        assertThat(evaluator.evaluate(propertySet), equalTo(true));
    }
    
    @Test
    public void test_iGt5() throws CompileException {
        ExpressionEvaluator evaluator = compiler.compile("i > 5", propertyMap); 
        
        assertThat(evaluator.evaluate(propertySet), equalTo(false));
    }
    
    @Test
    public void test_iLe5() throws CompileException {
        ExpressionEvaluator evaluator = compiler.compile("i <= 5", propertyMap); 
        
        assertThat(evaluator.evaluate(propertySet), equalTo(true));
    }
    
    @Test
    public void test_iEq5ORdGt3() throws CompileException {
        ExpressionEvaluator evaluator = compiler.compile("i = 5 or d gt 3.", propertyMap);
        
        assertThat(evaluator.evaluate(propertySet), equalTo(true));
    }
    
    @Test
    public void test_iEq5ANDdGt3() throws CompileException {
        ExpressionEvaluator evaluator = compiler.compile("i = 5 and d gt 3.", propertyMap);
        
        assertThat(evaluator.evaluate(propertySet), equalTo(true));
    }
    
    @Test
    public void test_iEq5OrNOTdGt3() throws CompileException {
        ExpressionEvaluator evaluator = compiler.compile("i = 5 or !(d gt 3.)", propertyMap);
        
        assertThat(evaluator.evaluate(propertySet), equalTo(true));
    }
    
    @Test
    public void test_iEq5AndNOTdGt3() throws CompileException {
        ExpressionEvaluator evaluator = compiler.compile("i = 5 and !(d gt 3.)", propertyMap);
        
        assertThat(evaluator.evaluate(propertySet), equalTo(false));
    }
    
    @Test
    public void test_iInRange() throws CompileException {
        ExpressionEvaluator evaluator = compiler.compile("i in [0:<10]", propertyMap);
        
        assertThat(evaluator.evaluate(propertySet), equalTo(true));
    }
    
    @Test
    public void test_iInSet() throws CompileException {
        ExpressionEvaluator evaluator = compiler.compile("i in {1, 2, 5}", propertyMap);
        
        assertThat(evaluator.evaluate(propertySet), equalTo(true));
    }
}
