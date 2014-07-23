package com.higginsthomas.expressionevaluator.executer.operations;

public class InOperation extends Operation {
    private final CollectionValue c;
    
    public InOperation(CollectionValue c, boolean negate) {
        super(negate);
        this.c = c;
    }

    @Override
    public Operation negate() { return new InOperation(c, !isNegated()); }
}
