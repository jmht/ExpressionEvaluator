package com.higginsthomas.expressionevaluator;

import java.math.BigDecimal;


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
        if ( !this.getType().equals(that.getType()) ) throw new RuntimeException("Cannot compare types");
        return ((BigDecimal)this.getValue()).compareTo((BigDecimal)that.getValue());
    }
}
