package com.higginsthomas.expressionevaluator.compiler;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.joda.time.format.DateTimeFormat;

import com.higginsthomas.expressionevaluator.*;
import com.higginsthomas.expressionevaluator.executer.operations.*;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarBaseVisitor;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarParser;


class IntermediateCompiler extends ExpressionGrammarBaseVisitor<IntermediateCompiler> {
    private final IntermediateCode il_code = new IntermediateCode();

    public IntermediateCode getIntermediateCode() { return il_code; }

    
    public IntermediateCompiler visitInCollection(final ExpressionGrammarParser.InCollectionContext ctx) {
        // Post-fix traversal
        visit(ctx.children.get(0));
        visit(ctx.children.get(2));
        visit(ctx.children.get(1));
        return this;
    }

    public IntermediateCompiler visitCompare(final ExpressionGrammarParser.CompareContext ctx) {
        // Post-fix traversal
        visit(ctx.children.get(0));
        visit(ctx.children.get(2));
        visit(ctx.children.get(1));
        return this;
    }
    
    public IntermediateCompiler visitEq(final ExpressionGrammarParser.EqContext ctx) {
        il_code.addOperation(new IsEQ());
        return this;
    }
    
    public IntermediateCompiler visitNe(final ExpressionGrammarParser.NeContext ctx) {
        il_code.addOperation(new IsEQ());
        il_code.addOperation(new Not());
        return this;
    }
    
    public IntermediateCompiler visitLt(final ExpressionGrammarParser.LtContext ctx) {
        il_code.addOperation(new IsLT());
        return this;
    }
    
    public IntermediateCompiler visitGt(final ExpressionGrammarParser.GtContext ctx) {
        il_code.swap();
        il_code.addOperation(new IsLT());
        return this;
    }
    
    public IntermediateCompiler visitGe(final ExpressionGrammarParser.GeContext ctx) {
        il_code.addOperation(new IsLT());
        il_code.addOperation(new Not());
        return this;
    }
    
    public IntermediateCompiler visitLe(final ExpressionGrammarParser.LeContext ctx) {
        il_code.swap();
        il_code.addOperation(new IsLT());
        il_code.addOperation(new Not());
        return this;
    }
    
    public IntermediateCompiler visitIdentifier(final ExpressionGrammarParser.IdentifierContext ctx) {
        int id_index = il_code.getIdentifierIndex(ctx.getText());
        il_code.addOperation(new LoadIdentifier(id_index));
        return this;
    }

    public IntermediateCompiler visitIntConstant(final ExpressionGrammarParser.IntConstantContext ctx) {
        return handleConstant(new IntegerPropertyValue(new BigInteger(ctx.getText())));
    }

    public IntermediateCompiler visitDecConstant(final ExpressionGrammarParser.DecConstantContext ctx) {
        return handleConstant(new DecimalPropertyValue(new BigDecimal(ctx.getText())));
    }

    public IntermediateCompiler visitFloatConstant(final ExpressionGrammarParser.FloatConstantContext ctx) {
        return handleConstant(new FloatPropertyValue(Double.parseDouble(ctx.getText())));
    }

    public IntermediateCompiler visitStrConstant(final ExpressionGrammarParser.StrConstantContext ctx) {
        return handleConstant(new TextPropertyValue(ctx.getText()));
    }
    
    public IntermediateCompiler visitDateConstant(final ExpressionGrammarParser.DateConstantContext ctx) {
        return handleConstant(new DatePropertyValue(DateTimeFormat.forPattern("mm-dd-yyyy").parseLocalDate(ctx.getText())));
    }

    
    private IntermediateCompiler handleConstant(PropertyValue value) {
        il_code.addOperation(new LoadConstant(value));
        return this;
    }
}
