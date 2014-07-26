package com.higginsthomas.expressionevaluator.evaluator.operations;

import com.higginsthomas.expressionevaluator.values.IdentifierTable;


public class OrOperation extends Operation {
    private final Operation left, right;
    
    public OrOperation(Operation left, Operation right, boolean negate) {
        super(negate);
        this.left = left;
        this.right = right;
    }

    @Override
    public Operation negate() { return new OrOperation(left, right, !isNegated()); }
    
    public Operation getLeft() { return left; }
    public Operation getRight() { return right; }

    public boolean evaluate(IdentifierTable properties) {
        return getLeft().evaluate(properties) || getRight().evaluate(properties);
    }
}
