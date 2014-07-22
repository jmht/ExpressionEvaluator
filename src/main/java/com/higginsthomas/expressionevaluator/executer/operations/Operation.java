package com.higginsthomas.expressionevaluator.executer.operations;


public abstract class Operation {
    private boolean negated;
    
    protected Operation(boolean negate) { this.negated = negate; }
    
    public Operation negate() { negated = !negated; return this; }
    public boolean isNegated() { return negated; }
}
