package com.higginsthomas.expressionevaluator.errors;


public class CompileException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public CompileException(String msg, int location) {
        super(makeMessage(msg, location));
    }
    
    public CompileException(String msg, int location, Exception e) {
        super(makeMessage(msg, location), e);
    }
    
    private static String makeMessage(String msg, int location) {
        return String.format("Syntax error @ %d: %s", location + 1, msg);
    }
}
