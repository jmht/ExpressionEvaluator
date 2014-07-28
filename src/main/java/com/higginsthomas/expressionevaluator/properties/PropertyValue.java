package com.higginsthomas.expressionevaluator.properties;


/**
 * This represents a property value.
 * 
 * @author James Higgins-Thomas
 */
public interface PropertyValue extends Comparable<PropertyValue> {
    /**
     * Return the logical type of the property.
     * 
     * @return {@link PropertyValueType}
     */
    public PropertyValueType getType();

    /**
     * Return the property value. 
     * <p>
     * This is the generic retrieval method, returning 
     * <code>Object</code>. The actual type of the object 
     * returned should match the property's value type.
     * 
     * @return property value
     */
    public Object getValue();
}
