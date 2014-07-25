package com.higginsthomas.expressionevaluator;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import com.higginsthomas.expressionevaluator.compiler.IntermediateCompiler;
import com.higginsthomas.expressionevaluator.executer.operations.Operation;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarLexer;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarParser;


/**
 * This class is responsible for compiling a provided query expression into an
 * executable form which can then be evaluated against a set or properties.
 *
 * @author James Higgins-Thomas
 */
public class ExpressionCompiler {
    /**
     * Compile query expression.
     * 
     * @param queryExpression   the query expression
     * @param map   a function which validates the identifiers in the expression.
     * @return an <code>ExpressionEvaluator</code> representing the compiled
     *         expression.
     */
    public ExpressionEvaluator compile(String queryExpression, PropertyMap map) {
        final ANTLRInputStream istream = new ANTLRInputStream(queryExpression);
        final ExpressionGrammarLexer lexer = new ExpressionGrammarLexer(null);
        lexer.setInputStream(istream);
        lexer.removeErrorListeners();
        final CommonTokenStream tstream = new CommonTokenStream(lexer);
        final ExpressionGrammarParser parser = new ExpressionGrammarParser(tstream);
        parser.removeErrorListeners();
        final ParseTree parseTree = parser.start();
        IntermediateCompiler compiler = new IntermediateCompiler(map);
        final Operation expression = (Operation) compiler.visit(parseTree);
        return new ExpressionEvaluator(/* TODO: expression */) {
            public boolean evaluate(PropertySet properties) {
// TODO:                return expression.evaluate(properties);
                return false;
            }
        };
    }

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
     */
    public ExpressionEvaluator compile(String queryExpression) {
        return compile(queryExpression, new PropertyMap() {
            public boolean exists(String propertyName) {
                return true;
            }

            public PropertyValueType getType(String propertyName) {
                return PropertyValueType.VARIANT;
            }
        });
    }

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
     */
    public boolean evaluate(String queryExpression, PropertySet properties) {
        return compile(queryExpression, properties).evaluate(properties);
    }
}
