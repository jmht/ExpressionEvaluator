package com.higginsthomas.expressionevaluator.evaluator;

import com.higginsthomas.expressionevaluator.api.ExpressionEvaluator;
import com.higginsthomas.expressionevaluator.evaluator.operations.Operation;
import com.higginsthomas.expressionevaluator.identifiers.IdentifierTable;
import com.higginsthomas.expressionevaluator.properties.PropertySet;


public class Evaluator implements ExpressionEvaluator {
    private final Operation code;
    private final IdentifierTable idTable;
    
    public Evaluator(final Operation code, final IdentifierTable idTable) {
        this.code = code;
        this.idTable = idTable;
    }
    
    public boolean evaluate(final PropertySet properties) {
        idTable.setCache(new RuntimeIdentifierTable(idTable.getIdentifierTable(), properties));
        return code.evaluate();
    }
}
