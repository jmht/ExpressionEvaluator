package com.higginsthomas.expressionevaluator.executer.operations;

import java.util.Set;

import com.higginsthomas.expressionevaluator.PropertyValue;
import com.higginsthomas.expressionevaluator.PropertyValueType;


public class SetValue implements CollectionValue {
    private final Set<PropertyValue> s;
    
    public SetValue(Set<PropertyValue> s) {
        this.s = s;
    }

    public PropertyValueType getType() {
        // TODO Auto-generated method stub
        return null;
    }
}
