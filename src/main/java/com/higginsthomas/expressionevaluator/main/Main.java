package com.higginsthomas.expressionevaluator.main;

import com.higginsthomas.expressionevaluator.api.ExpressionCompiler;
import com.higginsthomas.expressionevaluator.compiler.Compiler;


public class Main {
    public static ExpressionCompiler compiler() {
        return new Compiler();
    }
}
