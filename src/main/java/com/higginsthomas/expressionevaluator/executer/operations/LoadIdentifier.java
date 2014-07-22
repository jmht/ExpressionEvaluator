package com.higginsthomas.expressionevaluator.executer.operations;

import com.higginsthomas.expressionevaluator.PropertyValue;
import com.higginsthomas.expressionevaluator.PropertyValueType;


public class LoadIdentifier extends Operation implements PropertyValue {
    private final int identifier;
    
    private LoadIdentifier(int identifier) { this.identifier = identifier; }
    
    public static final Operation op(int identifier) { return new LoadIdentifier(identifier); }

    public PropertyValueType getType() {
        // TODO Auto-generated method stub
        return null;
    }

    public Object getValue() {
        // TODO Auto-generated method stub
        return null;
    }
}
