package com.higginsthomas.expressionevaluator.executer.operations;

import java.util.Set;

import com.higginsthomas.expressionevaluator.PropertyValueType;


public class SetValue implements CollectionValue {
    private final Set s;
    
    public SetValue(Set s) {
        this.s = s;
    }

    public PropertyValueType getType() {
        // TODO Auto-generated method stub
        return null;
    }
}
