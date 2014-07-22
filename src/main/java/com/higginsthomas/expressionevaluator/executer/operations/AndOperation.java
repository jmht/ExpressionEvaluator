package com.higginsthomas.expressionevaluator.executer.operations;


public class AndOperation extends Operation {
    private final Operation left, right;
    
    public AndOperation(Operation left, Operation right, boolean negate) {
        super(negate);
        this.left = left;
        this.right = right;
    }
    
    public Operation getLeft() { return left; }
    public Operation getRight() { return right; }
}
