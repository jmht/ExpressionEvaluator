package com.higginsthomas.expressionevaluator;

/**
 * This class is responsible for evaluating a compiled query expression
 * over a provided set of properties.
 * 
 * @author James Higgins-Thomas
 */
public interface ExpressionEvaluator {
    /**
     * Evaluates the encapsulated expression over the given properties.
     * 
     * @return the result of the expression.
     */
    public boolean evaluate(PropertySet properties);
}
