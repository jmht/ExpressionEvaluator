package com.higginsthomas.expressionevaluator.executer.operations;

public class IsEQ extends Operation { 
    private static final IsEQ _IsEQ = new IsEQ();
    private IsEQ() { }
    
    public static final Operation op() { return _IsEQ; }
}
