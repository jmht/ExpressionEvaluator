package com.higginsthomas.expressionevaluator.evaluator.operations;

import com.higginsthomas.expressionevaluator.common.PropertyTypeConversion;
import com.higginsthomas.expressionevaluator.common.PropertyTypeConversion.TypeConversionException;
import com.higginsthomas.expressionevaluator.properties.PropertyValue;
import com.higginsthomas.expressionevaluator.properties.PropertyValueType;


public abstract class Operation {
    private boolean negated;
    
    protected Operation(boolean negate) { this.negated = negate; }
    
    public abstract Operation negate();
    public boolean isNegated() { return negated; }
    
    protected PropertyValueType computeResultType(PropertyValueType a,
            PropertyValueType b) throws TypeConversionException 
    {
        return PropertyTypeConversion.computeResultType(a, b);
    }
    protected PropertyValue convert(PropertyValue v, PropertyValueType t) throws TypeConversionException {
        return PropertyTypeConversion.convert(v, t);
    }

    public boolean evaluate() {
        try {
            boolean result = getResult();
            return isNegated() ? !result : result;
        } catch ( TypeConversionException e ) {
            return false;
        }
    }
    protected abstract boolean getResult() throws TypeConversionException;
}
