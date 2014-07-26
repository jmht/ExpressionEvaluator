package com.higginsthomas.expressionevaluator.evaluator;

import com.higginsthomas.expressionevaluator.ExpressionEvaluator;
import com.higginsthomas.expressionevaluator.PropertySet;
import com.higginsthomas.expressionevaluator.evaluator.operations.Operation;
import com.higginsthomas.expressionevaluator.values.IdentifierTable;

public class Evaluator implements ExpressionEvaluator {
    private final Operation code;
    private final IdentifierTable idTable;
    
    public Evaluator(final Operation code, final IdentifierTable idTable) {
        this.code = code;
        this.idTable = idTable;
    }
    
    public boolean evaluate(final PropertySet properties) {
        idTable.setCache(new RuntimeIdentifierTable(idTable.getIdentifierTable(), properties));
        return code.evaluate(idTable);
    }
}
