package com.higginsthomas.expressionevaluator.api;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import com.higginsthomas.expressionevaluator.compiler.ErrorListener;
import com.higginsthomas.expressionevaluator.compiler.IntermediateCompiler;
import com.higginsthomas.expressionevaluator.compiler.InternalCompileException;
import com.higginsthomas.expressionevaluator.evaluator.Evaluator;
import com.higginsthomas.expressionevaluator.evaluator.operations.Operation;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarLexer;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarParser;
import com.higginsthomas.expressionevaluator.identifiers.IdentifierTable;
import com.higginsthomas.expressionevaluator.properties.PropertyMap;
import com.higginsthomas.expressionevaluator.properties.PropertySet;
import com.higginsthomas.expressionevaluator.properties.PropertyValueType;


/**
 * This class is responsible for compiling a provided query expression into an
 * executable form which can then be evaluated against a set or properties.
 *
 * @author James Higgins-Thomas
 */
public class ExpressionCompiler {
    final ExpressionGrammarLexer lexer;
    final CommonTokenStream tstream;
    final ExpressionGrammarParser parser;

    public ExpressionCompiler() {
        this(new ErrorListener());
    }

    ExpressionCompiler(ANTLRErrorListener errorListener) {
        this.lexer = new ExpressionGrammarLexer(null);
        this.tstream = new CommonTokenStream(lexer);
        this.parser = new ExpressionGrammarParser(tstream);
        ANTLRErrorListener listener = new ErrorListener(); // TODO: Inject this instead?
        lexer.removeErrorListeners();
        lexer.addErrorListener(listener);
        parser.removeErrorListeners();
        parser.addErrorListener(listener);
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
    public ExpressionEvaluator compile(String queryExpression, PropertyMap map) throws CompileException {
        try {
            lexer.setInputStream(new ANTLRInputStream(queryExpression));
            final ParseTree parseTree = parser.start();
            final IdentifierTable idTable = new IdentifierTable();
            final IntermediateCompiler compiler = new IntermediateCompiler(idTable, map);
            final Operation code = (Operation)compiler.visit(parseTree);
            return new Evaluator(code, idTable);
        } catch ( InternalCompileException e ) {
            throw new CompileException(e.getMessage(), e);
        }
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
     * @throws CompileException 
     */
    public ExpressionEvaluator compile(String queryExpression) throws CompileException {
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
     * @throws CompileException     if unable to compile the expression
     */
    public boolean evaluate(String queryExpression, PropertySet properties) throws CompileException {
        return compile(queryExpression, properties).evaluate(properties);
    }
}
