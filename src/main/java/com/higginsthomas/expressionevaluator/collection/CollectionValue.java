package com.higginsthomas.expressionevaluator.collection;

import com.higginsthomas.expressionevaluator.properties.PropertyValue;
import com.higginsthomas.expressionevaluator.properties.PropertyValueType;


public interface CollectionValue { 
    PropertyValueType getType();
    boolean contains(PropertyValue v);
}
