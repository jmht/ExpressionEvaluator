package com.higginsthomas.expressionevaluator.executer.operations;

public class LoadIdentifier extends Operation {
    private final int identifier;
    
    public LoadIdentifier(int identifier) {
        this.identifier = identifier;
    }
}
