package com.higginsthomas.expressionevaluator.api;


public class CompileException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public CompileException(String msg, Throwable t) {
        super(msg, t);
    }
}
