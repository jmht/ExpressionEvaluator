package com.higginsthomas.expressionevaluator.values;

import com.higginsthomas.expressionevaluator.PropertyValue;
import com.higginsthomas.expressionevaluator.PropertyValueType;
import com.higginsthomas.expressionevaluator.compiler.IdentifierCache;


public class IdentifierValue implements PropertyValue {
    private final IdentifierCache cache;
    private final int identifierIndex;
    private final String name;
    
    public IdentifierValue(final IdentifierCache cache, final int identifier, final String name) {
        this.cache = cache;
        this.identifierIndex = identifier;
        this.name = name;
    }

    public PropertyValueType getType() {
        return cache.getIdentifierTypeAt(identifierIndex);
    }
    
    public Object getValue() {
        throw new UnsupportedOperationException();
    }
    
    public String getName() {
        return name;
    }

    public int compareTo(PropertyValue that) {
        throw new UnsupportedOperationException();
    }
}
