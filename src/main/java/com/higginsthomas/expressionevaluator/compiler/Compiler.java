package com.higginsthomas.expressionevaluator.compiler;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import com.higginsthomas.expressionevaluator.api.CompileException;
import com.higginsthomas.expressionevaluator.api.ExpressionCompiler;
import com.higginsthomas.expressionevaluator.api.ExpressionEvaluator;
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
public class Compiler implements ExpressionCompiler {
    final ExpressionGrammarLexer lexer;
    final CommonTokenStream tstream;
    final ExpressionGrammarParser parser;

    public Compiler() {
        this(new ErrorListener());
    }

    Compiler(ANTLRErrorListener errorListener) {
        this.lexer = new ExpressionGrammarLexer(null);
        this.tstream = new CommonTokenStream(lexer);
        this.parser = new ExpressionGrammarParser(tstream);
        ANTLRErrorListener listener = new ErrorListener(); // TODO: Inject this instead?
        lexer.removeErrorListeners();
        lexer.addErrorListener(listener);
        parser.removeErrorListeners();
        parser.addErrorListener(listener);
    }
    
    /* (non-Javadoc)
     * @see com.higginsthomas.expressionevaluator.compiler.ExpressionCompiler#compile(java.lang.String, com.higginsthomas.expressionevaluator.properties.PropertyMap)
     */
    @Override
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

    /* (non-Javadoc)
     * @see com.higginsthomas.expressionevaluator.compiler.ExpressionCompiler#compile(java.lang.String)
     */
    @Override
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

    /* (non-Javadoc)
     * @see com.higginsthomas.expressionevaluator.compiler.ExpressionCompiler#evaluate(java.lang.String, com.higginsthomas.expressionevaluator.properties.PropertySet)
     */
    @Override
    public boolean evaluate(String queryExpression, PropertySet properties) throws CompileException {
        return compile(queryExpression, properties).evaluate(properties);
    }
}
