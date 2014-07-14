package com.higginsthomas.expressionevaluator;


/**
 * Represents a property value of type TEXT.
 * 
 * @author James Higgins-Thomas
 */
public class TextPropertyValue implements PropertyValue {
    private String value;

    public TextPropertyValue(String value) {
        this.value = value;
    }

    public PropertyValueType getType() { return PropertyValueType.TEXT; }

    public String getValue() { return value; }
}
