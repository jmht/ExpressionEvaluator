package com.higginsthomas.expressionevaluator.evaluator.operations;

import com.higginsthomas.expressionevaluator.PropertyValue;


public class LtOperation extends Operation {
    private final PropertyValue left, right;
    
    public LtOperation(PropertyValue left, PropertyValue right, boolean negate) {
        super(negate);
        this.left = left;
        this.right = right;
    }

    @Override
    public Operation negate() { return new LtOperation(left, right, !isNegated()); }
    
    public PropertyValue getLeft() { return left; }
    public PropertyValue getRight() { return right; }

    public boolean getResult() {
        return getLeft().compareTo(getRight()) < 0;
    }
}
