package com.higginsthomas.expressionevaluator.properties;


/**
 * Represents a property value of type FLOAT.
 * 
 * @author James Higgins-Thomas
 */
public class FloatPropertyValue extends ConstantPropertyValueBase {
    public FloatPropertyValue(Double value) {
        super(PropertyValueType.FLOAT, value);
    }

    public FloatPropertyValue(double value) {
        this(Double.valueOf(value));
    }

    @Override
    public Double getValue() { return (Double)super.getValue(); }

    @Override
    public int compareTo(PropertyValue that) {
        if ( !this.getType().equals(that.getType()) ) throw new IncompatibleTypeException(this.getType(), that.getType());
        return ((Double)this.getValue()).compareTo((Double)that.getValue());
    }
}
