package com.higginsthomas.expressionevaluator.compiler;

import java.util.ArrayList;

import com.higginsthomas.expressionevaluator.evaluator.operations.Operation;


public abstract class OperationBuilder<T> {
    private final ArrayList<T> value = new ArrayList<T>(2);
    
    public OperationBuilder<T> setOperand(int n, T v) {
        value.add(n, v);
        return this;
    }
    public T getOperand(int n) {
        return value.get(n);
    }
    
    public abstract Operation make();
}
