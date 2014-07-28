package com.higginsthomas.expressionevaluator;

//import static org.hamcrest.CoreMatchers.*;
//import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.higginsthomas.expressionevaluator.api.CompileException;
import com.higginsthomas.expressionevaluator.api.ExpressionCompiler;
import com.higginsthomas.expressionevaluator.compiler.Compiler;
import com.higginsthomas.expressionevaluator.properties.PropertyMap;


public class ExpressionEvaluationCompileExceptionTests {
    private final TestHelper helper = new TestHelper();
    private ExpressionCompiler compiler;
    private PropertyMap propertyMap;
    
    @Before
    public void before() {
        this.compiler = new Compiler();
        this.propertyMap = helper.getTestPropertyMap();
    }

    @Test(expected= CompileException.class)
    public void SyntaxErrorTest() throws CompileException {
        compiler.compile("i # 5", propertyMap);
    }

    @Test(expected= CompileException.class)
    public void BadIdentifierErrorTest() throws CompileException {
        compiler.compile("x = 5", propertyMap);
    }
}
