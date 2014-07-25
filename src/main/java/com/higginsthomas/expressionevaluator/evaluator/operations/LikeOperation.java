package com.higginsthomas.expressionevaluator.evaluator.operations;

import java.util.regex.Pattern;

import com.higginsthomas.expressionevaluator.PropertySet;
import com.higginsthomas.expressionevaluator.PropertyValue;
import com.higginsthomas.expressionevaluator.TextPropertyValue;


public class LikeOperation extends Operation {
    private final PropertyValue operand;
    private final TextPropertyValue patternString;
    private final Pattern pattern;
    
    public LikeOperation(PropertyValue operand, TextPropertyValue text, boolean negate) {
        super(negate);
        this.operand = operand;
        this.patternString = text;
        this.pattern = Pattern.compile(patternString.getValue(), Pattern.UNICODE_CASE);
    }

    @Override
    public Operation negate() { return new LikeOperation(operand, patternString, !isNegated()); }
    
    public PropertyValue getOperand() { return operand; }
    public TextPropertyValue getPattern() { return patternString; }

    public boolean evaluate(PropertySet properties) {
        // TODO: Implement
        return false;
    }
}
