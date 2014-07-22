package com.higginsthomas.expressionevaluator.compiler;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;

import org.joda.time.format.DateTimeFormat;

import com.higginsthomas.expressionevaluator.*;
import com.higginsthomas.expressionevaluator.executer.operations.*;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarBaseVisitor;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarParser;


class IntermediateCompiler extends ExpressionGrammarBaseVisitor<IntermediateCompiler> {
    private final IntermediateCode il_code = new IntermediateCode();

    public IntermediateCode getIntermediateCode() { return il_code; }

    
    public IntermediateCompiler visitInCollection(final ExpressionGrammarParser.InCollectionContext ctx) {
        visit(ctx.simpleValue());       // push left argument
        visit(ctx.collection());        // process collection
        return this;
    }

    public IntermediateCompiler visitRange(final ExpressionGrammarParser.RangeContext ctx) {
        final int lb = 0;
        final int ub = 1;
        Operation operand = il_code.removeOperation();  // pop the first operand
        if ( ctx.lexcl != null ) {
            visit(ctx.constant(lb));            // push lower bound
            il_code.addOperation(operand);      // push our operand
            il_code.addOperation(IsLT.op());    // LT
        } else {
            il_code.addOperation(operand);      // push our operand
            visit(ctx.constant(lb));            // push lower bound
            il_code.addOperation(IsLT.op());    // ~LT
            il_code.addOperation(Not.op());
        }
        int andIp = il_code.addOperation(NOP.op());    // AND
        if ( ctx.rexcl != null ) {
            il_code.addOperation(operand);      // push our operand again
            visit(ctx.constant(ub));            // push upper bound
            il_code.addOperation(IsLT.op());    // LT
        } else {
            visit(ctx.constant(ub));            // push upper bound
            il_code.addOperation(operand);      // push our operand again
            il_code.addOperation(IsLT.op());    // ~LT
            il_code.addOperation(Not.op());
        }
        il_code.replaceOperation(andIp, JumpIfFalse.op(il_code.currentAddress() - andIp));
        return this;
    }

    public IntermediateCompiler visitSet(final ExpressionGrammarParser.SetContext ctx) {
        HashSet<PropertyValue> s = new HashSet<PropertyValue>();
        for ( ExpressionGrammarParser.ConstantContext x : ctx.constant()) {
            visit(x);
            s.add(((LoadConstant)il_code.removeOperation()).getValue());
        }
        return this;
    }
    
    public IntermediateCompiler visitCompare(final ExpressionGrammarParser.CompareContext ctx) {
        final int left = 0;
        final int right = 1;
        visit(ctx.simpleValue(left));   // push left argument
        visit(ctx.simpleValue(right));  // push right argument
        visit(ctx.operator());          // process comparator
        return this;
    }
    
    public IntermediateCompiler visitEq(final ExpressionGrammarParser.EqContext ctx) {
        il_code.addOperation(IsEQ.op());
        return this;
    }
    
    public IntermediateCompiler visitNe(final ExpressionGrammarParser.NeContext ctx) {
        il_code.addOperation(IsEQ.op());
        il_code.addOperation(Not.op());
        return this;
    }
    
    public IntermediateCompiler visitLt(final ExpressionGrammarParser.LtContext ctx) {
        il_code.addOperation(IsLT.op());
        return this;
    }
    
    public IntermediateCompiler visitGt(final ExpressionGrammarParser.GtContext ctx) {
        il_code.swap();
        il_code.addOperation(IsLT.op());
        return this;
    }
    
    public IntermediateCompiler visitGe(final ExpressionGrammarParser.GeContext ctx) {
        il_code.addOperation(IsLT.op());
        il_code.addOperation(Not.op());
        return this;
    }
    
    public IntermediateCompiler visitLe(final ExpressionGrammarParser.LeContext ctx) {
        il_code.swap();
        il_code.addOperation(IsLT.op());
        il_code.addOperation(Not.op());
        return this;
    }
    
    public IntermediateCompiler visitIdentifier(final ExpressionGrammarParser.IdentifierContext ctx) {
        int id_index = il_code.getIdentifierIndex(ctx.getText());
        il_code.addOperation(LoadIdentifier.op(id_index));
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
        il_code.addOperation(LoadConstant.op(value));
        return this;
    }
}
