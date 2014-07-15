package com.higginsthomas.expressionevaluator.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.higginsthomas.expressionevaluator.PropertyValue;
import com.higginsthomas.expressionevaluator.executer.operations.Operation;

class IntermediateCode {
    private List<Operation> code = new ArrayList<Operation>();
    private List<String> identifiers = new ArrayList<String>();
    private Map<String, Integer> id_map = new HashMap<String, Integer>();
    private List<PropertyValue> constants = new ArrayList<PropertyValue>();
    private Map<String, Integer> const_map = new HashMap<String, Integer>();

    IntermediateCode addOperation(Operation op) {
        code.add(op);
        return this;
    }

    IntermediateCode swap() {
        final int i = code.size() - 2;
        final Operation t = code.get(i);
        code.set(i, code.get(i + 1));
        code.set(i + 1, t);
        return this;
    }
    
    int getIdentifierIndex(final String identifier) {
        int index;
        if ( id_map.containsKey(identifier) ) {
            index = id_map.get(identifier);
        } else {
            index = identifiers.size();
            identifiers.add(index, identifier);
            id_map.put(identifier, index);
        }
        return index;
    }
    
    int getConstantIndex(final PropertyValue value) {
        final String vs = valueToString(value);
        int index;
        if ( const_map.containsKey(vs) ) {
            index = const_map.get(vs);
        } else {
            index = constants.size();
            constants.add(index, value);
            const_map.put(vs, index);
        }
        return index;
    }
    
    private String valueToString(PropertyValue value) {
        return value.getType().toString() + "." + value.getValue().toString();
    }
}
