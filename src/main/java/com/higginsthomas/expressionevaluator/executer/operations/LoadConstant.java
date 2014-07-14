package com.higginsthomas.expressionevaluator.executer.operations;

import com.higginsthomas.expressionevaluator.PropertyValue;


public class LoadConstant extends Operation {
    private final PropertyValue value;
    
    public LoadConstant(PropertyValue value) {
        this.value = value;
    }
}
