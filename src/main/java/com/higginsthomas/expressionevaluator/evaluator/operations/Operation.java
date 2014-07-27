package com.higginsthomas.expressionevaluator.evaluator.operations;


public abstract class Operation {
    private boolean negated;
    
    protected Operation(boolean negate) { this.negated = negate; }
    
    public abstract Operation negate();
    public boolean isNegated() { return negated; }
    
    public boolean evaluate() {
        boolean result = getResult();
        return isNegated() ? !result : result;
    }
    public abstract boolean getResult();
}
