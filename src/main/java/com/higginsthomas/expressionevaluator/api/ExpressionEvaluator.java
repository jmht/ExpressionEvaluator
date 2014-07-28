package com.higginsthomas.expressionevaluator.api;

import com.higginsthomas.expressionevaluator.properties.PropertySet;

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
     * @param properties the property settings for the evaluation.
     * @return the result of the expression.
     */
    public boolean evaluate(PropertySet properties);
}
