package com.higginsthomas.expressionevaluator.compiler;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.higginsthomas.expressionevaluator.IntegerPropertyValue;
import com.higginsthomas.expressionevaluator.executer.operations.*;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarBaseVisitor;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarParser;


public class ParseTreeVisitor extends ExpressionGrammarBaseVisitor<Void> {
    private List<Operation> il_code = new ArrayList<Operation>();
    private List<String> identifiers = new ArrayList<String>();
    private Map<String, Integer> id_map = new HashMap<String, Integer>();

    public Void visitCompare(final ExpressionGrammarParser.CompareContext ctx) {
        // Post-fix traversal
        visit(ctx.children.get(0));
        visit(ctx.children.get(2));
        visit(ctx.children.get(1));
        return null;
    }
    
    public Void visitLt(final ExpressionGrammarParser.LtContext ctx) {
        il_code.add(new IsLT());
        return null;
    }
    
    public Void visitIdentifier(final ExpressionGrammarParser.IdentifierContext ctx) {
        final String identifier = ctx.getText();
        int id_index;
        if ( id_map.containsKey(identifier) ) {
            id_index = id_map.get(identifier);
        } else {
            id_index = identifiers.size();
            identifiers.add(id_index, identifier);
            id_map.put(identifier, id_index);
        }
        il_code.add(new LoadIdentifier(id_index));
        return null;
    }

    public Void visitIntConstant(final ExpressionGrammarParser.IntConstantContext ctx) {
        final BigInteger value = new BigInteger(ctx.getText());
        il_code.add(new LoadConstant(new IntegerPropertyValue(value)));
        return null;
    }
}
