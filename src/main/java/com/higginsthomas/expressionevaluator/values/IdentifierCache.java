package com.higginsthomas.expressionevaluator.values;

import com.higginsthomas.expressionevaluator.PropertyValue;


public interface IdentifierCache {
    /**
     * Return the identifier at the specified identifier index.
     * 
     * @param index the identifier's index
     * @return the identifier.
     */
    public PropertyValue getIdentifier(final int index);
    
    /**
     * Return the identifier names.
     * <p>
     * The index of each identifier in the array represents the identifier identity
     * as allocated by the compile pass.
     */
    public String[] getIdentifierTable();
}
