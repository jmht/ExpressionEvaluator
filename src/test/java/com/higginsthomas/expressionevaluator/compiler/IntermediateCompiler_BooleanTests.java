package com.higginsthomas.expressionevaluator.compiler;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Before;
import org.junit.Test;

import com.higginsthomas.expressionevaluator.*;
import com.higginsthomas.expressionevaluator.executer.operations.*;


public class IntermediateCompiler_BooleanTests extends
        IntermediateCompilerTestBase {
    private IntermediateCompiler sut;
    
    @Before
    public void beforeEachTest() {
        sut = new IntermediateCompiler(new PropertyMap() {
            public boolean exists(String propertyName) { return    "x".equalsIgnoreCase(propertyName)
                                                                || "y".equalsIgnoreCase(propertyName); }
            public PropertyValueType getType(String propertyName) { return PropertyValueType.INTEGER; }
        });
    }

    @Test
    public void testBoolean_Or() {
        ParseTree tree = parser("x = 5 or y < 0").start();
        
        OrOperation result = (OrOperation)sut.visit(tree);

        assertThat(result.isNegated(), is(false));
        assertThat(result.getLeft(), instanceOf(EqOperation.class));
        assertThat(result.getLeft().isNegated(), is(false));
        assertThat(result.getRight(), instanceOf(LtOperation.class));
        assertThat(result.getRight().isNegated(), is(false));
    }

    @Test
    public void testBoolean_And() {
        ParseTree tree = parser("x = 5 and y < 0").start();
        
        OrOperation result = (OrOperation)sut.visit(tree);

        assertThat(result.isNegated(), is(true));
        assertThat(result.getLeft(), instanceOf(EqOperation.class));
        assertThat(result.getLeft().isNegated(), is(true));
        assertThat(result.getRight(), instanceOf(LtOperation.class));
        assertThat(result.getRight().isNegated(), is(true));
    }
}
