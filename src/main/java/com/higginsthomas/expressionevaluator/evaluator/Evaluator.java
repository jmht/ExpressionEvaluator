package com.higginsthomas.expressionevaluator.evaluator;

import com.higginsthomas.expressionevaluator.ExpressionEvaluator;
import com.higginsthomas.expressionevaluator.PropertySet;
import com.higginsthomas.expressionevaluator.evaluator.operations.Operation;

public class Evaluator implements ExpressionEvaluator {
    private final Operation code;
    
    public Evaluator(final Operation code) {
        this.code = code;
    }
    
    public boolean evaluate(final PropertySet properties) {
        return code.evaluate(new PropertyCache(properties));
    }
}
