package com.higginsthomas.expressionevaluator;

import java.math.BigDecimal;

import com.higginsthomas.expressionevaluator.errors.IncompatibleTypeException;


/**
 * Represents a property value of type DECIMAL.
 * 
 * @author James Higgins-Thomas
 */
public class DecimalPropertyValue implements PropertyValue {
    private BigDecimal value;

    public DecimalPropertyValue(BigDecimal value) {
        this.value = value;
    }

    public PropertyValueType getType() { return PropertyValueType.DECIMAL; }

    public BigDecimal getValue() { return value; }

    public int compareTo(PropertyValue that) {
        if ( !this.getType().equals(that.getType()) ) throw new IncompatibleTypeException(this.getType(), that.getType());
        return ((BigDecimal)this.getValue()).compareTo((BigDecimal)that.getValue());
    }
}
