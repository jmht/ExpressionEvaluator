package com.higginsthomas.expressionevaluator.properties;


public abstract class ConstantPropertyValueBase extends PropertyValueBase {
    private final PropertyValueType type;
    private final Object value;
    
    public ConstantPropertyValueBase(PropertyValueType type, Object value) {
        this.type = type;
        this.value = value;
    }
    
    public PropertyValueType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public abstract int compareTo(PropertyValue that);
}
