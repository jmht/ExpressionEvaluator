package com.higginsthomas.expressionevaluator.evaluator.operations;

import com.higginsthomas.expressionevaluator.common.PropertyTypeConversion.TypeConversionException;
import com.higginsthomas.expressionevaluator.properties.PropertyValue;
import com.higginsthomas.expressionevaluator.properties.PropertyValueType;


public class EqOperation extends Operation {
    private final PropertyValue left, right;
    
    public EqOperation(PropertyValue left, PropertyValue right, boolean negate) {
        super(negate);
        this.left = left;
        this.right = right;
    }

    @Override
    public Operation negate() { return new EqOperation(left, right, !isNegated()); }
    
    public PropertyValue getLeft() { return left; }
    public PropertyValue getRight() { return right; }

    @Override
    public boolean getResult() throws TypeConversionException {
        PropertyValueType opType = computeResultType(getLeft().getType(), getRight().getType());
        final PropertyValue left = convert(getLeft(), opType);
        final PropertyValue right = convert(getRight(), opType);
        return left.compareTo(right) == 0;
    }
}
