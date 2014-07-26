package com.higginsthomas.expressionevaluator.values;

import com.higginsthomas.expressionevaluator.PropertyValue;
import com.higginsthomas.expressionevaluator.PropertyValueType;


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

    public int compareTo(PropertyValue that) {
        return get().compareTo(that);
    }
    
    private PropertyValue get() {
        return table.getIdentifier(index);
    }
}
