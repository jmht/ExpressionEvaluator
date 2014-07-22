package com.higginsthomas.expressionevaluator.executer.operations;

import com.higginsthomas.expressionevaluator.PropertyValue;
import com.higginsthomas.expressionevaluator.PropertyValueType;


public class RangeValue implements CollectionValue {
    private PropertyValue min, max;
    private boolean minInclusive, maxInclusive;
    
    public RangeValue(PropertyValue min, PropertyValue max, boolean minInclusive, boolean maxInclusive) {
        this.min = min;
        this.max = max;
        this.minInclusive = minInclusive;
        this.maxInclusive = maxInclusive;
    }
    
    public PropertyValueType getType() {
        // TODO Auto-generated method stub
        return null;
    }

}
