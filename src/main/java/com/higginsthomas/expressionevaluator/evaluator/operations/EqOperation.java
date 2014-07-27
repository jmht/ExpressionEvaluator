package com.higginsthomas.expressionevaluator.evaluator.operations;

import com.higginsthomas.expressionevaluator.PropertyValue;


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
    public boolean getResult() {
        return getLeft().compareTo(getRight()) == 0;
    }
}
