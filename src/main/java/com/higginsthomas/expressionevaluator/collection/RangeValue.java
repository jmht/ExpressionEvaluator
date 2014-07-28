package com.higginsthomas.expressionevaluator.collection;

import com.higginsthomas.expressionevaluator.common.PropertyTypeConversion;
import com.higginsthomas.expressionevaluator.common.PropertyTypeConversion.TypeConversionException;
import com.higginsthomas.expressionevaluator.properties.PropertyValue;
import com.higginsthomas.expressionevaluator.properties.PropertyValueType;


public class RangeValue implements CollectionValue {
    private final PropertyValue min, max;
    private final boolean minInclusive, maxInclusive;

    public RangeValue(final PropertyValue min, final PropertyValue max,
            final boolean minInclusive, final boolean maxInclusive)
            throws TypeConversionException 
    {
        PropertyValueType resultType = PropertyTypeConversion
                .computeResultType(min.getType(), max.getType());
        this.min = PropertyTypeConversion.convert(min, resultType);
        this.max = PropertyTypeConversion.convert(max, resultType);
        this.minInclusive = minInclusive;
        this.maxInclusive = maxInclusive;
    }

    public PropertyValueType getType() {
        return min.getType();
    }

    @Override
    public boolean contains(PropertyValue v) {
        if (min.compareTo(v) < 0 && v.compareTo(max) < 0) return true;
        if (minInclusive && min.compareTo(v) == 0) return true;
        if (maxInclusive && v.compareTo(max) == 0) return true;
        return false;
    }
}
