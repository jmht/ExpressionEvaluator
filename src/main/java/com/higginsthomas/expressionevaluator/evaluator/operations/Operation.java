package com.higginsthomas.expressionevaluator.evaluator.operations;

import com.higginsthomas.expressionevaluator.PropertySet;


public abstract class Operation {
    private boolean negated;
    
    protected Operation(boolean negate) { this.negated = negate; }
    
    public abstract Operation negate();
    public boolean isNegated() { return negated; }
    
    public abstract boolean evaluate(PropertySet properties);
}
