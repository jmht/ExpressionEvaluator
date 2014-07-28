package com.higginsthomas.expressionevaluator;

import java.math.BigDecimal;

import org.joda.time.LocalDate;


public class TestHelper {
    private static final TestData testData[] = {
        new TestData("i", new IntegerPropertyValue(5)),
        new TestData("d", new DecimalPropertyValue(new BigDecimal("3.14"))),
        new TestData("f", new FloatPropertyValue(1E6)),
        new TestData("c", new DatePropertyValue(new LocalDate(2014, 7, 30))),
        new TestData("t", new TextPropertyValue("Test"))
    };

    public PropertyMap getTestPropertyMap() { return new TestPropertyMap(); }
    public PropertySet getTestPropertySet() { return new TestPropertySet(); }

    
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
