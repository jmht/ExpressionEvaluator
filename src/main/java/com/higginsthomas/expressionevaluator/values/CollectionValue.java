package com.higginsthomas.expressionevaluator.values;

import com.higginsthomas.expressionevaluator.PropertyValue;
import com.higginsthomas.expressionevaluator.PropertyValueType;


public interface CollectionValue { 
    PropertyValueType getType();
    boolean contains(PropertyValue v);
}
