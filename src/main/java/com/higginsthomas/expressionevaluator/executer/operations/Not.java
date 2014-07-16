package com.higginsthomas.expressionevaluator.executer.operations;

public class Not extends Operation {
    private static final Not _Not = new Not();
    private Not() { }
    
    public static final Operation op() { return _Not; }
}
