package com.higginsthomas.expressionevaluator.executer.operations;

import com.higginsthomas.expressionevaluator.PropertyValue;


public class LoadIdentifier extends Value {
    private final int identifier;
    
    private LoadIdentifier(int identifier) { this.identifier = identifier; }
    
    public static final Operation op(int identifier) { return new LoadIdentifier(identifier); }
    
    public PropertyValue getValue() { /* TODO */ return null; }
}
