package com.higginsthomas.expressionevaluator.properties;




/**
 * Represents a property value of type FLOAT.
 * 
 * @author James Higgins-Thomas
 */
public class FloatPropertyValue implements PropertyValue {
    private Double value;

    public FloatPropertyValue(Double value) {
        this.value = value;
    }

    public FloatPropertyValue(double value) {
        this.value = value;
    }

    public PropertyValueType getType() { return PropertyValueType.FLOAT; }

    public Double getValue() { return value; }

    public int compareTo(PropertyValue that) {
        if ( !this.getType().equals(that.getType()) ) throw new IncompatibleTypeException(this.getType(), that.getType());
        return ((Double)this.getValue()).compareTo((Double)that.getValue());
    }
}
