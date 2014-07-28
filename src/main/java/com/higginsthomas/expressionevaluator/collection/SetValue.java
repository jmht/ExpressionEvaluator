package com.higginsthomas.expressionevaluator.collection;

import java.util.HashSet;
import java.util.Set;

import com.higginsthomas.expressionevaluator.common.PropertyTypeConversion;
import com.higginsthomas.expressionevaluator.common.PropertyTypeConversion.TypeConversionException;
import com.higginsthomas.expressionevaluator.properties.PropertyValue;
import com.higginsthomas.expressionevaluator.properties.PropertyValueType;


public class SetValue implements CollectionValue {
    private final Set<PropertyValue> s;
    private final PropertyValueType type;
    
    public SetValue(final Set<PropertyValue> s) throws TypeConversionException {
        this.type = computeSetType(s);
        this.s = constructUniformSet(s, type);
    }

    public PropertyValueType getType() {
        return type;
    }

    @Override
    public boolean contains(final PropertyValue v) {
        return s.contains(v);
    }
    
    private static PropertyValueType computeSetType(Set<PropertyValue> s) throws TypeConversionException {
        PropertyValueType resultType = PropertyValueType.VARIANT;
        for ( PropertyValue e : s ) {
            resultType = PropertyTypeConversion.computeResultType(resultType, e.getType());
        }
        return resultType;
    }
    
    private static Set<PropertyValue> constructUniformSet(Set<PropertyValue> s, PropertyValueType t) throws TypeConversionException {
        final Set<PropertyValue> result = new HashSet<PropertyValue>();
        for ( PropertyValue e : s ) {
            result.add(PropertyTypeConversion.convert(e, t));
        }
        return result;
    }
}
