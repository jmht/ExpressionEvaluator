package com.higginsthomas.expressionevaluator.evaluator.operations;

import com.higginsthomas.expressionevaluator.properties.PropertyTypeConversion.TypeConversionException;

public class NopOperation extends Operation {

    public NopOperation(boolean negate) {
        super(negate);
    }

    @Override
    public Operation negate() {
        return new NopOperation(!isNegated());
    }

    @Override
    protected boolean getResult() throws TypeConversionException {
        return true;
    }
}
