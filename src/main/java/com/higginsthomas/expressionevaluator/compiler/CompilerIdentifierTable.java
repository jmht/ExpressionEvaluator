package com.higginsthomas.expressionevaluator.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.higginsthomas.expressionevaluator.PropertyMap;
import com.higginsthomas.expressionevaluator.PropertyValue;
import com.higginsthomas.expressionevaluator.PropertyValueType;
import com.higginsthomas.expressionevaluator.values.IdentifierCache;


public class CompilerIdentifierTable implements IdentifierCache {
    private final PropertyMap identifierMap;
    private final List<Identifier> identifiers = new ArrayList<Identifier>();
    private final Map<String, Integer> id_map = new HashMap<String, Integer>();
    
    public CompilerIdentifierTable(final PropertyMap identifierMap) {
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
    public int addIdentifier(String identifier) {
        int index;
        if ( id_map.containsKey(identifier) ) {
            index = id_map.get(identifier);
        } else {
            if ( identifierMap.exists(identifier) ) {
                index = identifiers.size();
                identifiers.add(index, new Identifier(identifier, identifierMap.getType(identifier)));
                id_map.put(identifier, index);
            } else {
                // TODO: How to handle bad identifier
                throw new RuntimeException("Unrecognized Identifier");
            }
        }
        return index;
    }

    public String[] getIdentifierTable() {
        String table[] = new String[identifiers.size()];
        for ( int i = 0; i < identifiers.size(); ++i ) {
            table[i] = identifiers.get(i).name;
        }
        return table;
    }

    public PropertyValue getIdentifier(final int index) {
        return new PropertyValue() {
            public PropertyValueType getType() {
                return identifiers.get(index).type;
            }
            public Object getValue() {
                throw new UnsupportedOperationException();
            }
            public int compareTo(PropertyValue arg0) {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    
    private class Identifier {
        public final String name;
        public final PropertyValueType type;
        
        public Identifier(String name, PropertyValueType type) {
            this.name = name;
            this.type = type;
        }
    }
}
