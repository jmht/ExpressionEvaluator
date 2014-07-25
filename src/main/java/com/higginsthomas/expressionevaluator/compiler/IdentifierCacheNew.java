package com.higginsthomas.expressionevaluator.compiler;

import java.util.HashMap;
import java.util.Map;

import com.higginsthomas.expressionevaluator.PropertyMap;
import com.higginsthomas.expressionevaluator.PropertyValueType;


public class IdentifierCacheNew implements PropertyMap {
    private final PropertyMap properties;
    private final Map<String, PropertyValueType> cache;
    
    public IdentifierCacheNew(PropertyMap properties) {
        this.properties = properties;
        this.cache = new HashMap<String, PropertyValueType>();
    }
    
    public boolean exists(String propertyName) {
        return cache.containsKey(propertyName) || properties.exists(propertyName);
    }

    public PropertyValueType getType(String propertyName) {
        return fetchOrAdd(propertyName);
    }
    
    private PropertyValueType fetchOrAdd(String propertyName) {
        if ( cache.containsKey(propertyName) ) return cache.get(propertyName);
        return cache.put(propertyName, properties.getType(propertyName));
    }
}
