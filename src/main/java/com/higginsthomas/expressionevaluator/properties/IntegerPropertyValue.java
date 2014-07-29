package com.higginsthomas.expressionevaluator.properties;

import java.math.BigInteger;


/**
 * Represents a property value of type INTEGER.
 * 
 * @author James Higgins-Thomas
 */
public class IntegerPropertyValue extends ConstantPropertyValueBase {
    public IntegerPropertyValue(BigInteger value) {
        super(PropertyValueType.INTEGER, value);
    }

    public IntegerPropertyValue(long value) {
        this(BigInteger.valueOf(value));
    }

    @Override
    public BigInteger getValue() { return (BigInteger)super.getValue(); }

    @Override
    public int compareTo(PropertyValue that) {
        if ( !this.getType().equals(that.getType()) ) throw new IncompatibleTypeException(this.getType(), that.getType());
        return ((BigInteger)this.getValue()).compareTo((BigInteger)that.getValue());
    }
}
