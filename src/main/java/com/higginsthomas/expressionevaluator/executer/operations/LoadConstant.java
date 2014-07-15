package com.higginsthomas.expressionevaluator.executer.operations;

import com.higginsthomas.expressionevaluator.PropertyValue;


public class LoadConstant extends Operation {
    private final PropertyValue constant;
    
    public LoadConstant(PropertyValue constant) {
        this.constant = constant;
    }
}
