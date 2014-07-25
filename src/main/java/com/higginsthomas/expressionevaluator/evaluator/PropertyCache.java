package com.higginsthomas.expressionevaluator.evaluator;

import java.util.HashMap;
import java.util.Map;

import com.higginsthomas.expressionevaluator.PropertySet;
import com.higginsthomas.expressionevaluator.PropertyValue;
import com.higginsthomas.expressionevaluator.PropertyValueType;


public class PropertyCache implements PropertySet {
    private final PropertySet properties;
    private final Map<String, PropertyValue> cache;
    
    public PropertyCache(PropertySet properties) {
        this.properties = properties;
        this.cache = new HashMap<String, PropertyValue>();
    }
    
    public boolean exists(String propertyName) {
        return cache.containsKey(propertyName) || properties.exists(propertyName);
    }

    public PropertyValueType getType(String propertyName) {
        return fetchOrAdd(propertyName).getType();
    }

    public PropertyValue get(String propertyName) {
        return fetchOrAdd(propertyName);
    }
    
    private PropertyValue fetchOrAdd(String propertyName) {
        if ( cache.containsKey(propertyName) ) return cache.get(propertyName);
        return cache.put(propertyName, properties.get(propertyName));
    }
}
