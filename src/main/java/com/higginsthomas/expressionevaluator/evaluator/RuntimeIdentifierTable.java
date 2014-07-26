package com.higginsthomas.expressionevaluator.evaluator;

import com.higginsthomas.expressionevaluator.PropertySet;
import com.higginsthomas.expressionevaluator.PropertyValue;
import com.higginsthomas.expressionevaluator.values.IdentifierCache;


public class RuntimeIdentifierTable implements IdentifierCache {
    private final PropertySet properties;
    private final PropertyValue[] cache;
    private final String[] identifierTable;
    
    public RuntimeIdentifierTable(final String[] identifierTable, final PropertySet properties) {
        this.properties = properties;
        this.identifierTable = identifierTable;
        this.cache = new PropertyValue[identifierTable.length];
    }
 
    public PropertyValue getIdentifier(int index) {
        if ( cache[index] != null ) return cache[index];
        if ( !properties.exists(identifierTable[index]) ) {
            throw new RuntimeException("No such identifier: " + identifierTable[index]);
        }
        cache[index] = properties.get(identifierTable[index]);
        return cache[index];
    }

    public String[] getIdentifierTable() { return identifierTable; }
}
