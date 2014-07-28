package com.higginsthomas.expressionevaluator.values;

import java.util.Set;

import com.higginsthomas.expressionevaluator.PropertyValue;
import com.higginsthomas.expressionevaluator.PropertyValueType;


public class SetValue implements CollectionValue {
    private final Set<PropertyValue> s;
    
    public SetValue(final Set<PropertyValue> s) {
        this.s = s;
    }

    public PropertyValueType getType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean contains(final PropertyValue v) {
        // TODO: This either requires values to be comparable or a different approach
        return s.contains(v);
    }
}
