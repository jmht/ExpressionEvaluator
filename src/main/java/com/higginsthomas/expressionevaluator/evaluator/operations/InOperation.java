package com.higginsthomas.expressionevaluator.evaluator.operations;

import com.higginsthomas.expressionevaluator.collection.CollectionValue;
import com.higginsthomas.expressionevaluator.common.PropertyTypeConversion.TypeConversionException;
import com.higginsthomas.expressionevaluator.properties.PropertyValue;


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

    public boolean getResult() throws TypeConversionException {
        final PropertyValue operand = convert(getOperand(), getCollection().getType());
        return collection.contains(operand);
    }
}
