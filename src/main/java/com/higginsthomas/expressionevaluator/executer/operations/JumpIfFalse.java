package com.higginsthomas.expressionevaluator.executer.operations;

public class JumpIfFalse extends Jump {
    private JumpIfFalse(int offset) { super(offset); }
    
    public static final Operation op(int offset) { return new JumpIfFalse(offset); }
}
