package com.higginsthomas.expressionevaluator.executer.operations;

/**
 * No Operation
 * 
 * @author James Higgins-Thomas
 */
public class NOP extends Operation {
    private static final NOP _NOP = new NOP();
    private NOP() { }
    
    public static final Operation op() { return _NOP; }
}
