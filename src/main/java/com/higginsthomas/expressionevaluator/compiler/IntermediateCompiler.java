package com.higginsthomas.expressionevaluator.compiler;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;

import org.joda.time.format.DateTimeFormat;

import com.higginsthomas.expressionevaluator.*;
import com.higginsthomas.expressionevaluator.evaluator.operations.*;
import com.higginsthomas.expressionevaluator.values.CollectionValue;
import com.higginsthomas.expressionevaluator.values.IdentifierTable;
import com.higginsthomas.expressionevaluator.values.IdentifierValue;
import com.higginsthomas.expressionevaluator.values.RangeValue;
import com.higginsthomas.expressionevaluator.values.SetValue;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarBaseVisitor;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarParser;


public class IntermediateCompiler extends ExpressionGrammarBaseVisitor<Object> {
    private final IdentifierTable idTable;
    private final CompilerIdentifierTable id_cache;
    
    public IntermediateCompiler(IdentifierTable idTable, PropertyMap identifierMap) {
        this.idTable = idTable;
        this.id_cache = new CompilerIdentifierTable(identifierMap);
        idTable.setCache(id_cache);
    }

    @Override
    public Object visitNotExpr(final ExpressionGrammarParser.NotExprContext ctx) {
        return ((Operation)visit(ctx.expr())).negate();
    }

    @Override
    public Object visitOrExpr(final ExpressionGrammarParser.OrExprContext ctx) {
        Operation left = (Operation)visit(ctx.expr(0));
        Operation right = (Operation)visit(ctx.expr(1));
        return new OrOperation(left, right, false);
    }
    
    @Override
    public Object visitAndExpr(final ExpressionGrammarParser.AndExprContext ctx) {
        Operation left = ((Operation)visit(ctx.expr(0))).negate();
        Operation right = ((Operation)visit(ctx.expr(1))).negate();
        return new OrOperation(left, right, true);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Object visitCompare(final ExpressionGrammarParser.CompareContext ctx) {
        final int left = 0;
        final int right = 1;
        return ((OperationBuilder<PropertyValue>)visit(ctx.operator()))
                .setOperand(left, ((PropertyValue)visit(ctx.simpleValue(left))))
                .setOperand(right, ((PropertyValue)visit(ctx.simpleValue(right)))).
                make();
    }
    
    @Override
    public Object visitInCollection(final ExpressionGrammarParser.InCollectionContext ctx) {
        PropertyValue operand = (PropertyValue)visit(ctx.simpleValue());
        CollectionValue collection = (CollectionValue)visit(ctx.collection());
        return new InOperation(operand, collection, false);
    }
    
    @Override
    public Object visitRegexCompare(final ExpressionGrammarParser.RegexCompareContext ctx) {
        return new LikeOperation((PropertyValue)visit(ctx.simpleValue()), 
                new TextPropertyValue(unencodeText(ctx.STRING().getText())),
                false);
    }

    @Override
    public Object visitRange(final ExpressionGrammarParser.RangeContext ctx) {
        return new RangeValue((PropertyValue)visit(ctx.constant(0)), 
                (PropertyValue)visit(ctx.constant(1)), 
                (ctx.lexcl == null), (ctx.rexcl == null));
    }

    @Override
    public Object visitSet(final ExpressionGrammarParser.SetContext ctx) {
        HashSet<PropertyValue> s = new HashSet<PropertyValue>();
        for ( ExpressionGrammarParser.ConstantContext x : ctx.constant()) {
            PropertyValue member = (PropertyValue)visit(x);
            s.add(member);
        }
        return new SetValue(s);
    }
    
    @Override
    public Object visitEq(final ExpressionGrammarParser.EqContext ctx) {
        return new OperationBuilder<PropertyValue>() {
            public Operation make() {
                return new EqOperation(getOperand(0), getOperand(1), false);
            }
        };
    }
    
    @Override
    public Object visitNe(final ExpressionGrammarParser.NeContext ctx) {
        return new OperationBuilder<PropertyValue>() {
            public Operation make() {
                return new EqOperation(getOperand(0), getOperand(1), true);
            }
        };
    }
    
    @Override
    public Object visitLt(final ExpressionGrammarParser.LtContext ctx) {
        return new OperationBuilder<PropertyValue>() {
            public Operation make() {
                return new LtOperation(getOperand(0), getOperand(1), false);
            }
        };
    }
    
    @Override
    public Object visitGt(final ExpressionGrammarParser.GtContext ctx) {
        return new OperationBuilder<PropertyValue>() {
            public Operation make() {
                return new LtOperation(getOperand(1), getOperand(0), false);
            }
        };
    }
    
    @Override
    public Object visitGe(final ExpressionGrammarParser.GeContext ctx) {
        return new OperationBuilder<PropertyValue>() {
            public Operation make() {
                return new LtOperation(getOperand(0), getOperand(1), true);
            }
        };
    }
    
    @Override
    public Object visitLe(final ExpressionGrammarParser.LeContext ctx) {
        return new OperationBuilder<PropertyValue>() {
            public Operation make() {
                return new LtOperation(getOperand(1), getOperand(0), true);
            }
        };
    }
    
    @Override
    public Object visitIdentifier(final ExpressionGrammarParser.IdentifierContext ctx) {
        return new IdentifierValue(idTable, id_cache.addIdentifier(ctx.getText()));
    }

    @Override
    public Object visitIntConstant(final ExpressionGrammarParser.IntConstantContext ctx) {
        return new IntegerPropertyValue(new BigInteger(ctx.getText()));
    }

    @Override
    public Object visitDecConstant(final ExpressionGrammarParser.DecConstantContext ctx) {
        return new DecimalPropertyValue(new BigDecimal(ctx.getText()));
    }

    @Override
    public Object visitFloatConstant(final ExpressionGrammarParser.FloatConstantContext ctx) {
        return new FloatPropertyValue(Double.parseDouble(ctx.getText()));
    }

    @Override
    public Object visitStrConstant(final ExpressionGrammarParser.StrConstantContext ctx) {
        return new TextPropertyValue(unencodeText(ctx.getText()));
    }
    
    @Override
    public Object visitDateConstant(final ExpressionGrammarParser.DateConstantContext ctx) {
        String dateString = ctx.getText();
        if ( dateString.contains("-") ) {
            return new DatePropertyValue(DateTimeFormat.forPattern("mm-dd-yyyy").parseLocalDate(ctx.getText()));
        } else {
            return new DatePropertyValue(DateTimeFormat.forPattern("mm/dd/yyyy").parseLocalDate(ctx.getText()));
        }
    }
    
    @Override
    protected Object aggregateResult(Object aggregate, Object nextResult) {
        return (nextResult != null) ? nextResult : aggregate;
    }
    
    private String unencodeText(String encodedText) {
        return encodedText.substring(1, encodedText.length() - 1);
    }
}
