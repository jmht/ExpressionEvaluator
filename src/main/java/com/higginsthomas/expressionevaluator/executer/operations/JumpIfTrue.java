package com.higginsthomas.expressionevaluator.executer.operations;

public class JumpIfTrue extends Jump {
    private JumpIfTrue(int offset) { super(offset); }
    
    public static final Operation op(int offset) { return new JumpIfTrue(offset); }
}
