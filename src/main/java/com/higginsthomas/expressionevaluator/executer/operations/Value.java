package com.higginsthomas.expressionevaluator.executer.operations;

import com.higginsthomas.expressionevaluator.PropertyValue;

public abstract class Value extends Operation {
    public abstract PropertyValue getValue();
}
