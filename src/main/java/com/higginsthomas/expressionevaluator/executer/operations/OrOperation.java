package com.higginsthomas.expressionevaluator.executer.operations;


public class OrOperation extends Operation {
    private final Operation left, right;
    
    public OrOperation(Operation left, Operation right, boolean negate) {
        super(negate);
        this.left = left;
        this.right = right;
    }
    
    public Operation getLeft() { return left; }
    public Operation getRight() { return right; }
}
