package com.higginsthomas.expressionevaluator.api;

import com.higginsthomas.expressionevaluator.compiler.Compiler;
import com.higginsthomas.expressionevaluator.properties.PropertyMap;
import com.higginsthomas.expressionevaluator.properties.PropertySet;


public abstract class ExpressionCompiler {
    /**
     * Factory method to retrieve an instance of an <code>ExpressionCompiler</code>
     * 
     * @return <code>ExpressionCompiler</code>
     */
    public static ExpressionCompiler compiler() {
        return new Compiler();
    }

    /**
     * Compile query expression.
     * 
     * @param queryExpression   the query expression
     * @param map   a function which validates the identifiers in the expression.
     * @return an <code>ExpressionEvaluator</code> representing the compiled
     *         expression.
     * @throws CompileException 
     */
    public abstract ExpressionEvaluator compile(String queryExpression,
            PropertyMap map) throws CompileException;

    /**
     * Compile query expression.
     * <p>
     * This variation defers the validation of identifiers to evaluation time.
     * For compilation, all identifiers are presumed valid.
     * 
     * @param queryExpression
     *            the query expression
     * @return an <code>ExpressionEvaluator</code> representing the compiled
     *         expression.
     * @throws CompileException 
     */
    public abstract ExpressionEvaluator compile(String queryExpression)
            throws CompileException;

    /**
     * Compile and execute a query expression.
     * <p>
     * Compile the expression and immediately evaluate it against the provided
     * properties. This method is equivalent to
     * <code>compile(queryExpression, properties).evaluate(properties)</code>
     * 
     * @param queryExpression
     *            the query expression
     * @param properties
     *            the property set over which to evaluate the expression.
     * @return result of the evaluation
     * @throws CompileException     if unable to compile the expression
     */
    public abstract boolean evaluate(String queryExpression,
            PropertySet properties) throws CompileException;
}