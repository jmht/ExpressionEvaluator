package com.higginsthomas.expressionevaluator.executer.operations;

import com.higginsthomas.expressionevaluator.PropertyValue;


public class LoadConstant extends Value {
    private final PropertyValue constant;
    
    private LoadConstant(PropertyValue constant) { this.constant = constant; }
    
    public static final Operation op(PropertyValue constant) { return new LoadConstant(constant); }
    
    public PropertyValue getValue() { return constant; }
}
