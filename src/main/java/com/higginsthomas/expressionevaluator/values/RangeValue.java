package com.higginsthomas.expressionevaluator.values;

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
        // TODO fix this up
        return min.getType();
    }

    @Override
    public boolean contains(PropertyValue v) {
        if ( min.compareTo(v) < 0 && v.compareTo(max) < 0 ) return true;
        if ( minInclusive && min.compareTo(v) == 0 ) return true;
        if ( maxInclusive && v.compareTo(max) == 0 ) return true;
        return false;
    }

}
