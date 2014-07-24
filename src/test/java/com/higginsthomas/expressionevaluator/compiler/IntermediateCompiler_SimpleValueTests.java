package com.higginsthomas.expressionevaluator.compiler;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import org.antlr.v4.runtime.tree.ParseTree;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import com.higginsthomas.expressionevaluator.*;
import com.higginsthomas.expressionevaluator.executer.operations.*;


public class IntermediateCompiler_SimpleValueTests extends
        IntermediateCompilerTestBase 
{
    final HashMap<String, PropertyValueType> idMap = new HashMap<String, PropertyValueType>();
    {
        idMap.put("i", PropertyValueType.INTEGER);
        idMap.put("d", PropertyValueType.DECIMAL);
        idMap.put("f", PropertyValueType.FLOAT);
        idMap.put("c", PropertyValueType.DATE);
        idMap.put("s", PropertyValueType.TEXT);
        idMap.put("v", PropertyValueType.VARIANT);
    }
    private IntermediateCompiler sut;
    
    @Before
    public void beforeEachTest() {
        sut = new IntermediateCompiler(new PropertyMap() {
            public boolean exists(String propertyName) { 
                return idMap.containsKey(propertyName.toLowerCase()); 
            }
            public PropertyValueType getType(String propertyName) { 
                return idMap.get(propertyName.toLowerCase()); 
            }
        });
    }
    
    @Test
    public void testValue_Identifier() {
        for ( String id : idMap.keySet() ) {
            ParseTree tree = parser(id).simpleValue();
            
            PropertyValue result = (PropertyValue)sut.visit(tree);
    
            assertThat(result, instanceOf(IdentifierValue.class));
            IdentifierValue identifier = (IdentifierValue)result;
            assertThat(identifier.getName(), equalTo(id));
            assertThat(identifier.getType(), equalTo(idMap.get(id)));
        }
    }
    
    @Test
    public void testValue_Constant() {
        final ConstantTestValue testValues[] = {
            new ConstantTestValue("123", PropertyValueType.INTEGER, new BigInteger("123")),
            new ConstantTestValue("123.45", PropertyValueType.DECIMAL, new BigDecimal("123.45")),
            new ConstantTestValue("12E3", PropertyValueType.FLOAT, Double.valueOf("12E3")),
            new ConstantTestValue("1/12/2014", PropertyValueType.DATE, new LocalDate(2014, 1, 12)),
            new ConstantTestValue("'123'", PropertyValueType.TEXT, "123")
        };
        
        for ( ConstantTestValue test : testValues ) {
            ParseTree tree = parser(test.expr).simpleValue();
            
            PropertyValue result = (PropertyValue)sut.visit(tree);
    
            assertThat(result.getType(), equalTo(test.type));
            assertThat(result.getValue(), equalTo(test.value));
        }
    }

    private class ConstantTestValue {
        public String expr;
        public PropertyValueType type;
        public Object value;
        public ConstantTestValue(String a, PropertyValueType b, Object c) {
            expr = a; type = b; value = c;
        }
    }
}
