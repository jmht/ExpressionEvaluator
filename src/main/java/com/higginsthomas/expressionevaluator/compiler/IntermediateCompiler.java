package com.higginsthomas.expressionevaluator.compiler;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;

import org.joda.time.format.DateTimeFormat;

import com.higginsthomas.expressionevaluator.*;
import com.higginsthomas.expressionevaluator.executer.operations.*;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarBaseVisitor;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarParser;


class IntermediateCompiler extends ExpressionGrammarBaseVisitor<Object> {
    private final IntermediateCode il_code = new IntermediateCode();

    public IntermediateCode getIntermediateCode() { return il_code; }

    
    public Object visitInCollection(final ExpressionGrammarParser.InCollectionContext ctx) {
//        visit(ctx.simpleValue());       // push left argument
//        visit(ctx.collection());        // process collection
//        return this;
        return null;
    }

    public Object visitRange(final ExpressionGrammarParser.RangeContext ctx) {
//        final int lb = 0;
//        final int ub = 1;
//        Operation operand = il_code.removeOperation();  // pop the first operand
//        if ( ctx.lexcl != null ) {
//            visit(ctx.constant(lb));            // push lower bound
//            il_code.addOperation(operand);      // push our operand
//            il_code.addOperation(IsLT.op());    // LT
//        } else {
//            il_code.addOperation(operand);      // push our operand
//            visit(ctx.constant(lb));            // push lower bound
//            il_code.addOperation(IsLT.op());    // ~LT
//            il_code.addOperation(Not.op());
//        }
//        int andIp = il_code.addOperation(NOP.op());    // AND
//        if ( ctx.rexcl != null ) {
//            il_code.addOperation(operand);      // push our operand again
//            visit(ctx.constant(ub));            // push upper bound
//            il_code.addOperation(IsLT.op());    // LT
//        } else {
//            visit(ctx.constant(ub));            // push upper bound
//            il_code.addOperation(operand);      // push our operand again
//            il_code.addOperation(IsLT.op());    // ~LT
//            il_code.addOperation(Not.op());
//        }
//        il_code.replaceOperation(andIp, JumpIfFalse.op(il_code.currentAddress() - andIp));
//        return this;
        return null;
    }

    public Object visitSet(final ExpressionGrammarParser.SetContext ctx) {
//        HashSet<PropertyValue> s = new HashSet<PropertyValue>();
//        for ( ExpressionGrammarParser.ConstantContext x : ctx.constant()) {
//            visit(x);
//            //TODO: s.add(((LoadConstant)il_code.removeOperation()).getValue());
//        }
//        return this;
        return null;
    }
    
    public Object visitCompare(final ExpressionGrammarParser.CompareContext ctx) {
        final int left = 0;
        final int right = 1;
        return ((OperationBuilder<PropertyValue>)visit(ctx.operator()))
                .setOperand(left, ((PropertyValue)visit(ctx.simpleValue(left))))
                .setOperand(right, ((PropertyValue)visit(ctx.simpleValue(right)))).
                make();
    }
    
    public Object visitEq(final ExpressionGrammarParser.EqContext ctx) {
        return new OperationBuilder<PropertyValue>() {
            public Operation make() {
                return new EqOperation(getOperand(0), getOperand(1), false);
            }
        };
    }
    
    public Object visitNe(final ExpressionGrammarParser.NeContext ctx) {
        return new OperationBuilder<PropertyValue>() {
            public Operation make() {
                return new EqOperation(getOperand(0), getOperand(1), true);
            }
        };
    }
    
    public Object visitLt(final ExpressionGrammarParser.LtContext ctx) {
        return new OperationBuilder<PropertyValue>() {
            public Operation make() {
                return new LtOperation(getOperand(0), getOperand(1), false);
            }
        };
    }
    
    public Object visitGt(final ExpressionGrammarParser.GtContext ctx) {
        return new OperationBuilder<PropertyValue>() {
            public Operation make() {
                return new LtOperation(getOperand(1), getOperand(0), false);
            }
        };
    }
    
    public Object visitGe(final ExpressionGrammarParser.GeContext ctx) {
        return new OperationBuilder<PropertyValue>() {
            public Operation make() {
                return new LtOperation(getOperand(0), getOperand(1), true);
            }
        };
    }
    
    public Object visitLe(final ExpressionGrammarParser.LeContext ctx) {
        return new OperationBuilder<PropertyValue>() {
            public Operation make() {
                return new LtOperation(getOperand(1), getOperand(0), true);
            }
        };
    }
    
    public Object visitIdentifier(final ExpressionGrammarParser.IdentifierContext ctx) {
//        int id_index = il_code.getIdentifierIndex(ctx.getText());
//        il_code.addOperation(LoadIdentifier.op(id_index));
//        return this;
        return null;
    }

    public Object visitIntConstant(final ExpressionGrammarParser.IntConstantContext ctx) {
        return new IntegerPropertyValue(new BigInteger(ctx.getText()));
    }

    public Object visitDecConstant(final ExpressionGrammarParser.DecConstantContext ctx) {
        return new DecimalPropertyValue(new BigDecimal(ctx.getText()));
    }

    public Object visitFloatConstant(final ExpressionGrammarParser.FloatConstantContext ctx) {
        return new FloatPropertyValue(Double.parseDouble(ctx.getText()));
    }

    public Object visitStrConstant(final ExpressionGrammarParser.StrConstantContext ctx) {
        return new TextPropertyValue(ctx.getText());
    }
    
    public Object visitDateConstant(final ExpressionGrammarParser.DateConstantContext ctx) {
        return new DatePropertyValue(DateTimeFormat.forPattern("mm-dd-yyyy").parseLocalDate(ctx.getText()));
    }
}
