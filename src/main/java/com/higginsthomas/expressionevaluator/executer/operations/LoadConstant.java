package com.higginsthomas.expressionevaluator.executer.operations;

import com.higginsthomas.expressionevaluator.PropertyValue;
import com.higginsthomas.expressionevaluator.PropertyValueType;


public class LoadConstant extends Operation implements PropertyValue {
    private final PropertyValue constant;
    
    private LoadConstant(PropertyValue constant) { this.constant = constant; }
    
    public static final Operation op(PropertyValue constant) { return new LoadConstant(constant); }
    
    public Object getValue() { return constant.getValue(); }

    public PropertyValueType getType() { return constant.getType(); }
}
