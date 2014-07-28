package com.higginsthomas.expressionevaluator.identifiers;

import com.higginsthomas.expressionevaluator.properties.PropertyValue;


public class IdentifierTable implements IdentifierCache {
    private IdentifierCache activeCache;
    
    public IdentifierTable setCache(IdentifierCache cache) {
        this.activeCache = cache;
        return this;
    }

    public PropertyValue getIdentifier(int index) { return activeCache.getIdentifier(index); }
    public String[] getIdentifierTable() { return activeCache.getIdentifierTable(); }
}
