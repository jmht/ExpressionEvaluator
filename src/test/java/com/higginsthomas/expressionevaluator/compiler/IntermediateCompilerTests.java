package com.higginsthomas.expressionevaluator.compiler;

import static org.junit.Assert.*;
import static org.hamcrest.core.IsInstanceOf.*;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import com.higginsthomas.expressionevaluator.executer.operations.*;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarLexer;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarParser;

public class IntermediateCompilerTests {

    @Test
    public void testCompareLT() {
        ParseTree tree = parser("x < 5").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        IntermediateCode result = sut.visit(tree).getIntermediateCode();
        
        assertThat(result.getOperationAt(0), instanceOf(IsLT.class));
        assertThat(result.getOperationAt(1), instanceOf(LoadConstant.class));
        assertThat(result.getOperationAt(2), instanceOf(LoadIdentifier.class));
    }

    @Test
    public void testCompareGT() {
        ParseTree tree = parser("x > 5").relation();
        IntermediateCompiler sut = new IntermediateCompiler();
        
        IntermediateCode result = sut.visit(tree).getIntermediateCode();
        
        assertThat(result.getOperationAt(0), instanceOf(IsLT.class));
        assertThat(result.getOperationAt(1), instanceOf(LoadIdentifier.class));
        assertThat(result.getOperationAt(2), instanceOf(LoadConstant.class));
    }
    
    private ExpressionGrammarParser parser(final String query) {
        ANTLRInputStream istream = new ANTLRInputStream(query);
        ExpressionGrammarLexer lexer = new ExpressionGrammarLexer(istream);
        CommonTokenStream tstream = new CommonTokenStream(lexer);
        return new ExpressionGrammarParser(tstream);
    }
}
