package com.higginsthomas.expressionevaluator.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.higginsthomas.expressionevaluator.PropertyMap;
import com.higginsthomas.expressionevaluator.PropertyValue;
import com.higginsthomas.expressionevaluator.PropertyValueType;
import com.higginsthomas.expressionevaluator.executer.operations.IdentifierValue;


public class IdentifierCache {
    private final PropertyMap identifierMap;
    private final List<String> identifiers = new ArrayList<String>();
    private final Map<String, Integer> id_map = new HashMap<String, Integer>();
    
    public IdentifierCache(final PropertyMap identifierMap) {
        this.identifierMap = identifierMap;
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
    public int getIdentifierIndex(final String identifier) {
        int index;
        if ( id_map.containsKey(identifier) ) {
            index = id_map.get(identifier);
        } else {
            if ( identifierMap.exists(identifier) ) {
                index = identifiers.size();
                identifiers.add(index, identifier);
                id_map.put(identifier, index);
            } else {
                // TODO: How to handle bad identifier
                return -1;
            }
        }
        return index;
    }

    /**
     * Return the name of the identifier at the specified identifier index.
     * 
     * @param index the identifier's index
     * @return the identifier's name.
     */
    public String getIdentifierNameAt(int index) {
        return identifiers.get(index);
    }

    /**
     * Return the identifier at the specified identifier index.
     * 
     * @param index the identifier's index
     * @return the identifier.
     */
    public PropertyValue getIdentifierAt(final int index) {
        return new IdentifierValue(this, index);
    }

    /**
     * Return the type of the identifier at the specified identifier index.
     * 
     * @param index the identifier's index
     * @return the identifier's type.
     */
    public PropertyValueType getIdentifierTypeAt(int index) {
        return identifierMap.getType(getIdentifierNameAt(index));
    }
}
