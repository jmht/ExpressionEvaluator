package com.higginsthomas.expressionevaluator;

//import static org.hamcrest.CoreMatchers.*;
//import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.higginsthomas.expressionevaluator.errors.CompileException;


public class ExpressionEvaluationCompileExceptionTests {
    private final TestHelper helper = new TestHelper();
    private ExpressionCompiler compiler;
    private PropertyMap propertyMap;
    
    @Before
    public void before() {
        this.compiler = new ExpressionCompiler();
        this.propertyMap = helper.getTestPropertyMap();
    }

    @Test(expected= CompileException.class)
    public void SyntaxErrorTest() {
        compiler.compile("i # 5", propertyMap);
    }

    @Test(expected= CompileException.class)
    public void BadIdentifierErrorTest() {
        compiler.compile("x = 5", propertyMap);
    }
}
