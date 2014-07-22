package com.higginsthomas.expressionevaluator.executer.operations;

import com.higginsthomas.expressionevaluator.PropertyValue;


public class LtOperation extends Operation {
    private final PropertyValue left, right;
    
    public LtOperation(PropertyValue left, PropertyValue right, boolean negate) {
        super(negate);
        this.left = left;
        this.right = right;
    }
    
    public PropertyValue getLeft() { return left; }
    public PropertyValue getRight() { return right; }
}
