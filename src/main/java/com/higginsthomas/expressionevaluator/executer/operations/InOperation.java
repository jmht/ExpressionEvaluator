package com.higginsthomas.expressionevaluator.executer.operations;

import com.higginsthomas.expressionevaluator.PropertyValue;


public class InOperation extends Operation {
    private final PropertyValue operand;
    private final CollectionValue collection;
    
    public InOperation(PropertyValue operand, CollectionValue collection, boolean negate) {
        super(negate);
        this.operand = operand;
        this.collection = collection;
    }

    @Override
    public Operation negate() { return new InOperation(operand, collection, !isNegated()); }
    
    public PropertyValue getOperand() { return operand; }
    public CollectionValue getCollection() { return collection; }
}
