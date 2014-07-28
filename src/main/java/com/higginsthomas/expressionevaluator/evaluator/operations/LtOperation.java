package com.higginsthomas.expressionevaluator.evaluator.operations;

import com.higginsthomas.expressionevaluator.PropertyValue;
import com.higginsthomas.expressionevaluator.PropertyValueType;
import com.higginsthomas.expressionevaluator.values.PropertyTypeConversion.TypeConversionException;


public class LtOperation extends Operation {
    private final PropertyValue left, right;
    
    public LtOperation(final PropertyValue left, final PropertyValue right, final boolean negate) {
        super(negate);
        this.left = left;
        this.right = right;
    }

    @Override
    public Operation negate() { return new LtOperation(left, right, !isNegated()); }
    
    public PropertyValue getLeft() { return left; }
    public PropertyValue getRight() { return right; }

    public boolean getResult() throws TypeConversionException {
        PropertyValueType opType = computeResultType(getLeft().getType(), getRight().getType());
        final PropertyValue left = convert(getLeft(), opType);
        final PropertyValue right = convert(getRight(), opType);
        return left.compareTo(right) < 0;
    }
}
