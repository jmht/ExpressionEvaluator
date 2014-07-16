package com.higginsthomas.expressionevaluator.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.higginsthomas.expressionevaluator.executer.operations.Operation;


class IntermediateCode {
    private List<Operation> code = new ArrayList<Operation>();
    private List<String> identifiers = new ArrayList<String>();
    private Map<String, Integer> id_map = new HashMap<String, Integer>();

    /**
     * Appends an operation to the end of the code stream.
     * 
     * @param op    the operation to add
     * @return <code>this</code> 
     */
    IntermediateCode addOperation(Operation op) {
        code.add(op);
        return this;
    }

    /**
     * Swap the last two operations of the code stream.
     * 
     * @return <code>this</code> 
     */
    IntermediateCode swap() {
        final int i = code.size() - 2;
        final Operation t = code.get(i);
        code.set(i, code.get(i + 1));
        code.set(i + 1, t);
        return this;
    }
    
    /**
     * Return the index associated with the given identifier.
     * <p>
     * If the identifier is new, assign a new index and return it
     * else return the previously assigned index.
     * 
     * @param identifier the identifier
     * @return the identifier's index
     */
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

    /**
     * Return the operation at the specified code location.
     * 
     * @param offset code address, relative to the end of the stream.
     * @return the operation at the given offset.
     */
    Operation getOperationAt(int offset) {
        return code.get(code.size() - offset - 1);
    }

    /**
     * Return the identifier at the specified identifier index.
     * 
     * @param index the identifier's index
     * @return the identifier.
     */
    String getIdentifierAt(int index) {
        return identifiers.get(index);
    }
}