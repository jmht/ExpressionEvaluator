package com.higginsthomas.expressionevaluator.executer.operations;

import com.higginsthomas.expressionevaluator.PropertyValue;
import com.higginsthomas.expressionevaluator.PropertyValueType;
import com.higginsthomas.expressionevaluator.compiler.IdentifierCache;


public class IdentifierValue implements PropertyValue {
    private final IdentifierCache cache;
    private final int identifierIndex;
    
    public IdentifierValue(final IdentifierCache cache, final int identifier) {
        this.cache = cache;
        this.identifierIndex = identifier;
    }

    public PropertyValueType getType() {
        return cache.getIdentifierTypeAt(identifierIndex);
    }

    public String getName() {
        return cache.getIdentifierNameAt(identifierIndex);
    }
    
    public Object getValue() {
        throw new UnsupportedOperationException();
    }
}
