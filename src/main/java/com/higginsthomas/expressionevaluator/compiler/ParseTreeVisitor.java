package com.higginsthomas.expressionevaluator.compiler;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.joda.time.format.DateTimeFormat;

import com.higginsthomas.expressionevaluator.*;
import com.higginsthomas.expressionevaluator.executer.operations.*;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarBaseVisitor;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarParser;


class ParseTreeVisitor extends ExpressionGrammarBaseVisitor<IntermediateCode> {
    private final IntermediateCode il_code = new IntermediateCode();

    public IntermediateCode visitCompare(final ExpressionGrammarParser.CompareContext ctx) {
        // Post-fix traversal
        visit(ctx.children.get(0));
        visit(ctx.children.get(2));
        visit(ctx.children.get(1));
        return il_code;
    }
    
    public IntermediateCode visitEq(final ExpressionGrammarParser.LtContext ctx) {
        il_code.addOperation(new IsEQ());
        return il_code;
    }
    
    public IntermediateCode visitNe(final ExpressionGrammarParser.LtContext ctx) {
        il_code.addOperation(new IsEQ());
        il_code.addOperation(new Not());
        return il_code;
    }
    
    public IntermediateCode visitLt(final ExpressionGrammarParser.LtContext ctx) {
        il_code.addOperation(new IsLT());
        return il_code;
    }
    
    public IntermediateCode visitGt(final ExpressionGrammarParser.LtContext ctx) {
        il_code.swap();
        il_code.addOperation(new IsLT());
        return il_code;
    }
    
    public IntermediateCode visitGe(final ExpressionGrammarParser.LtContext ctx) {
        il_code.addOperation(new IsLT());
        il_code.addOperation(new Not());
        return il_code;
    }
    
    public IntermediateCode visitLe(final ExpressionGrammarParser.LtContext ctx) {
        il_code.swap();
        il_code.addOperation(new IsLT());
        il_code.addOperation(new Not());
        return il_code;
    }
    
    public IntermediateCode visitIdentifier(final ExpressionGrammarParser.IdentifierContext ctx) {
        int id_index = il_code.getIdentifierIndex(ctx.getText());
        il_code.addOperation(new LoadIdentifier(id_index));
        return il_code;
    }

    public IntermediateCode visitIntConstant(final ExpressionGrammarParser.IntConstantContext ctx) {
        final PropertyValue value = new IntegerPropertyValue(new BigInteger(ctx.getText()));
        final int c_index = il_code.getConstantIndex(value);
        il_code.addOperation(new LoadConstant(c_index));
        return il_code;
    }

    public IntermediateCode visitDecConstant(final ExpressionGrammarParser.DecConstantContext ctx) {
        final PropertyValue value = new DecimalPropertyValue(new BigDecimal(ctx.getText()));
        final int c_index = il_code.getConstantIndex(value);
        il_code.addOperation(new LoadConstant(c_index));
        return il_code;
    }

    public IntermediateCode visitFloatConstant(final ExpressionGrammarParser.FloatConstantContext ctx) {
        final PropertyValue value = new FloatPropertyValue(Double.parseDouble(ctx.getText()));
        final int c_index = il_code.getConstantIndex(value);
        il_code.addOperation(new LoadConstant(c_index));
        return il_code;
    }

    public IntermediateCode visitStrConstant(final ExpressionGrammarParser.StrConstantContext ctx) {
        final PropertyValue value = new TextPropertyValue(ctx.getText());
        final int c_index = il_code.getConstantIndex(value);
        il_code.addOperation(new LoadConstant(c_index));
        return il_code;
    }
    
    public IntermediateCode visitDateConstant(final ExpressionGrammarParser.DateConstantContext ctx) {
        final PropertyValue value = new DatePropertyValue(
                                        DateTimeFormat.forPattern("mm-dd-yyyy")
                                        .parseLocalDate(ctx.getText()));
        final int c_index = il_code.getConstantIndex(value);
        il_code.addOperation(new LoadConstant(c_index));
        return il_code;
    }
}
