package com.higginsthomas.expressionevaluator.properties;

import java.math.BigDecimal;


/**
 * Represents a property value of type DECIMAL.
 * 
 * @author James Higgins-Thomas
 */
public class DecimalPropertyValue extends ConstantPropertyValueBase {
    public DecimalPropertyValue(BigDecimal value) {
        super(PropertyValueType.DECIMAL, value);
    }

    @Override
    public BigDecimal getValue() { return (BigDecimal)super.getValue(); }

    @Override
    public int compareTo(PropertyValue that) {
        if ( !this.getType().equals(that.getType()) ) throw new IncompatibleTypeException(this.getType(), that.getType());
        return ((BigDecimal)this.getValue()).compareTo((BigDecimal)that.getValue());
    }
}
