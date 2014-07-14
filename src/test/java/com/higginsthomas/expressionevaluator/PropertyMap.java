package com.higginsthomas.expressionevaluator;


/**
 * Represents a set of functions over the set of valid properties for the query.
 * <p>
 * Specifically, this mapping indicates whether a given property is defined and, if 
 * it is, specifies it's type.
 * 
 * @author James Higgins-Thomas
 */
public interface PropertyMap {
    /**
     * Tests whether a property is defined.
     * 
     * @return true if the property is defined, false otherwise.
     */
    public boolean exists(String propertyName);

    /**
     * Returns logical data type of a property.
     * <p>
     * For a valid property name, this must return the type of the property.
     * If the type is unknown at compile time or may vary, return <code>VARIANT</code>.
     * 
     * @return {@link PropertyValueType}
     */
    public PropertyValueType getType(String propertyName);
}
