package com.higginsthomas.expressionevaluator.values;

import com.higginsthomas.expressionevaluator.PropertyValue;
import com.higginsthomas.expressionevaluator.PropertyValueType;


public interface CollectionValue { 
    public PropertyValueType getType();
    public boolean contains(PropertyValue v);
}
