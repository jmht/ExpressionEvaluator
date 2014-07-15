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
        final PropertyValue value = new IntegerPropertyValue(new BigInteger(ctx.getText()));
        final int c_index = il_code.getConstantIndex(value);
        il_code.addOperation(new LoadConstant(c_index));
        return this;
    }

    public IntermediateCompiler visitDecConstant(final ExpressionGrammarParser.DecConstantContext ctx) {
        final PropertyValue value = new DecimalPropertyValue(new BigDecimal(ctx.getText()));
        final int c_index = il_code.getConstantIndex(value);
        il_code.addOperation(new LoadConstant(c_index));
        return this;
    }

    public IntermediateCompiler visitFloatConstant(final ExpressionGrammarParser.FloatConstantContext ctx) {
        final PropertyValue value = new FloatPropertyValue(Double.parseDouble(ctx.getText()));
        final int c_index = il_code.getConstantIndex(value);
        il_code.addOperation(new LoadConstant(c_index));
        return this;
    }

    public IntermediateCompiler visitStrConstant(final ExpressionGrammarParser.StrConstantContext ctx) {
        final PropertyValue value = new TextPropertyValue(ctx.getText());
        final int c_index = il_code.getConstantIndex(value);
        il_code.addOperation(new LoadConstant(c_index));
        return this;
    }
    
    public IntermediateCompiler visitDateConstant(final ExpressionGrammarParser.DateConstantContext ctx) {
        final PropertyValue value = new DatePropertyValue(
                                        DateTimeFormat.forPattern("mm-dd-yyyy")
                                        .parseLocalDate(ctx.getText()));
        final int c_index = il_code.getConstantIndex(value);
        il_code.addOperation(new LoadConstant(c_index));
        return this;
    }
}
