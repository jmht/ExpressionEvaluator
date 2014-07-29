package com.higginsthomas.expressionevaluator.properties;


/**
 * Represents a property value of type TEXT.
 * 
 * @author James Higgins-Thomas
 */
public class TextPropertyValue extends ConstantPropertyValueBase {
    public TextPropertyValue(String value) {
        super(PropertyValueType.TEXT, value);
    }

    public TextPropertyValue(char ch) {
        this(String.valueOf(ch));
    }

    @Override
    public String getValue() { return (String)super.getValue(); }

    @Override
    public int compareTo(PropertyValue that) {
        if ( !this.getType().equals(that.getType()) ) throw new IncompatibleTypeException(this.getType(), that.getType());
        return ((String)this.getValue()).compareTo((String)that.getValue());
    }
}
