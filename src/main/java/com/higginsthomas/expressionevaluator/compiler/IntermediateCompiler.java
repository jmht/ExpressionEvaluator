package com.higginsthomas.expressionevaluator.compiler;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;

import org.joda.time.format.DateTimeFormat;

import com.higginsthomas.expressionevaluator.collection.CollectionValue;
import com.higginsthomas.expressionevaluator.collection.RangeValue;
import com.higginsthomas.expressionevaluator.collection.SetValue;
import com.higginsthomas.expressionevaluator.common.PropertyTypeConversion;
import com.higginsthomas.expressionevaluator.common.PropertyTypeConversion.TypeConversionException;
import com.higginsthomas.expressionevaluator.compiler.CompilerIdentifierTable.BadIdentifierException;
import com.higginsthomas.expressionevaluator.evaluator.operations.*;
import com.higginsthomas.expressionevaluator.properties.DatePropertyValue;
import com.higginsthomas.expressionevaluator.properties.DecimalPropertyValue;
import com.higginsthomas.expressionevaluator.properties.FloatPropertyValue;
import com.higginsthomas.expressionevaluator.properties.IntegerPropertyValue;
import com.higginsthomas.expressionevaluator.properties.PropertyMap;
import com.higginsthomas.expressionevaluator.properties.PropertyValue;
import com.higginsthomas.expressionevaluator.properties.PropertyValueType;
import com.higginsthomas.expressionevaluator.properties.TextPropertyValue;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarBaseVisitor;
import com.higginsthomas.expressionevaluator.grammar.ExpressionGrammarParser;
import com.higginsthomas.expressionevaluator.identifiers.IdentifierTable;
import com.higginsthomas.expressionevaluator.identifiers.IdentifierValue;


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
        final Operation left = ((Operation)visit(ctx.expr(0))).negate();
        final Operation right = ((Operation)visit(ctx.expr(1))).negate();
        return new OrOperation(left, right, true);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Object visitCompare(final ExpressionGrammarParser.CompareContext ctx) {
        final int left = 0;
        final int right = 1;
        final PropertyValue leftOperand = ((PropertyValue)visit(ctx.simpleValue(left)));
        final PropertyValue rightOperand = ((PropertyValue)visit(ctx.simpleValue(right)));
        try {
            final PropertyValueType resultType = PropertyTypeConversion.computeResultType(leftOperand.getType(), rightOperand.getType());
            return ((OperationBuilder<PropertyValue>)visit(ctx.operator()))
                    .setOperand(left, PropertyTypeConversion.convert(leftOperand, resultType))
                    .setOperand(right, PropertyTypeConversion.convert(rightOperand, resultType)).
                    make();
        } catch (TypeConversionException e) {
            throw new InternalCompileException(e.getMessage(), ctx.start.getCharPositionInLine(), e);
        }
    }
    
    @Override
    public Object visitInCollection(final ExpressionGrammarParser.InCollectionContext ctx) {
        final PropertyValue operand = (PropertyValue)visit(ctx.simpleValue());
        final CollectionValue collection = (CollectionValue)visit(ctx.collection());
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
        try {
            return new RangeValue((PropertyValue)visit(ctx.constant(0)), 
                    (PropertyValue)visit(ctx.constant(1)), 
                    (ctx.lexcl == null), (ctx.rexcl == null));
        } catch (TypeConversionException e) {
            throw new InternalCompileException(e.getMessage(), ctx.start.getCharPositionInLine(), e);
        }
    }

    @Override
    public Object visitSet(final ExpressionGrammarParser.SetContext ctx) {
        HashSet<PropertyValue> s = new HashSet<PropertyValue>();
        for ( ExpressionGrammarParser.ConstantContext x : ctx.constant()) {
            PropertyValue member = (PropertyValue)visit(x);
            s.add(member);
        }
        try {
            return new SetValue(s);
        } catch (TypeConversionException e) {
            throw new InternalCompileException(e.getMessage(), ctx.start.getCharPositionInLine(), e);
        }
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
        try {
            return new IdentifierValue(idTable, id_cache.addIdentifier(ctx.getText()));
        } catch ( BadIdentifierException e ) {
            throw new InternalCompileException(e.getMessage(), ctx.getStart().getCharPositionInLine(), e);
        }
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
