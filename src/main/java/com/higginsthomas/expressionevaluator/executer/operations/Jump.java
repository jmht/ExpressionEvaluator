package com.higginsthomas.expressionevaluator.executer.operations;

public abstract class Jump extends Operation {
    protected final int offset;

    public Jump(final int offset) {
        this.offset = offset;
    }
    
    public int getOffset() { return offset; }
}