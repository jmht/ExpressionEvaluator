package com.higginsthomas.expressionevaluator.identifiers;

import com.higginsthomas.expressionevaluator.properties.PropertyValue;
import com.higginsthomas.expressionevaluator.properties.PropertyValueType;


public class IdentifierValue implements PropertyValue {
    private final IdentifierTable table;
    private final int index;
    
    public IdentifierValue(final IdentifierTable table, final int identifier) {
        this.table = table;
        this.index = identifier;
    }

    public PropertyValueType getType() {
        return get().getType();
    }
    
    public Object getValue() {
        return get().getValue();
    }

    public int compareTo(final PropertyValue that) {
        return get().compareTo(that);
    }
    
    private PropertyValue get() {
        return table.getIdentifier(index);
    }
}
