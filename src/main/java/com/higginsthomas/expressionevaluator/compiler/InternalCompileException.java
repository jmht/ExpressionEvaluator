package com.higginsthomas.expressionevaluator.compiler;


public class InternalCompileException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public InternalCompileException(String msg, int location) {
        super(makeMessage(msg, location));
    }
    
    public InternalCompileException(String msg, int location, Throwable t) {
        super(makeMessage(msg, location), t);
    }
    
    private static String makeMessage(String msg, int location) {
        return String.format("Syntax error @ %d: %s", location + 1, msg);
    }
}
