package com.higginsthomas.expressionevaluator.executer.operations;


public class LoadConstant extends Operation {
    private final int constant;
    
    public LoadConstant(int constant) {
        this.constant = constant;
    }
}
