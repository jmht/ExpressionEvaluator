package com.higginsthomas.expressionevaluator.compiler;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Before;
import org.junit.Test;

import com.higginsthomas.expressionevaluator.PropertyMap;
import com.higginsthomas.expressionevaluator.PropertyValueType;
import com.higginsthomas.expressionevaluator.executer.operations.*;


public class IntermediateCompiler_SetTests extends IntermediateCompilerTestBase {
    private IntermediateCompiler sut;
    
    @Before
    public void beforeEachTest() {
        sut = new IntermediateCompiler(new PropertyMap() {
            public boolean exists(String propertyName) { return "x".equalsIgnoreCase(propertyName); }
            public PropertyValueType getType(String propertyName) { return PropertyValueType.INTEGER; }
        });
    }
    
    @Test
    public void testRelation_InCollection_Set() {
        ParseTree tree = parser("x in {1, 2, 3}").start();
        
        InOperation result = (InOperation)sut.visit(tree);

        assertThat(result.isNegated(), is(false));
        assertThat(result.getOperand(), instanceOf(IdentifierValue.class));
        assertThat(result.getCollection(), instanceOf(SetValue.class));
    }
}
