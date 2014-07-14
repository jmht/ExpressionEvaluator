package com.higginsthomas.expressionevaluator;

import java.math.BigInteger;


/**
 * Represents a property value of type INTEGER.
 * 
 * @author James Higgins-Thomas
 */
public class IntegerPropertyValue implements PropertyValue {
    private BigInteger value;

    public IntegerPropertyValue(BigInteger value) {
        this.value = value;
    }

    public IntegerPropertyValue(long value) {
        this.value = BigInteger.valueOf(value);
    }

    public PropertyValueType getType() { return PropertyValueType.INTEGER; }

    public BigInteger getValue() { return value; }
}
