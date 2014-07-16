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
     * Return the current location (address of next code)
     * 
     * @return address
     */
    int currentAddress() {
        return code.size();
    }
    
    /**
     * Appends an operation to the end of the code stream.
     * 
     * @param op    the operation to add
     * @return opcode address 
     */
    int addOperation(Operation op) {
        code.add(op);
        return code.size() - 1;
    }

    /**
     * Replaces the operation at the specified index
     * with another.
     * 
     * @param index the address to replace
     * @param op    the new operation
     * @return opcode address 
     */
    int replaceOperation(int index, Operation op) {
        code.set(index, op);
        return index;
    }

    /**
     * Removes the last operation from the code stream and returns it.
     * 
     * @return the removed operation. 
     */
    Operation removeOperation() {
        return code.remove(code.size()-1);
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
     * @param offset code address. If negative, offset from tail (e.g. 0 is 
     *               first operation, -1 is last).
     * @return the operation at the given offset.
     */
    Operation getOperationAt(int offset) {
        final int index = (offset >= 0) ? offset : code.size() + offset;
        return code.get(index);
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
