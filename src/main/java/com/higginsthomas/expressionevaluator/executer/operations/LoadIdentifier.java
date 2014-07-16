package com.higginsthomas.expressionevaluator.executer.operations;


public class LoadIdentifier extends Operation {
    private final int identifier;
    
    private LoadIdentifier(int identifier) { this.identifier = identifier; }
    
    public static final Operation op(int identifier) { return new LoadIdentifier(identifier); }
}
