package com.higginsthomas.expressionevaluator.common;

import com.higginsthomas.expressionevaluator.properties.PropertyValueType;


public class IncompatibleTypeException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public IncompatibleTypeException(PropertyValueType a, PropertyValueType b) {
        super(String.format("Internal error: Cannot compare values of different types (%s, %s)", a, b));
    }
}
