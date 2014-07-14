package com.higginsthomas.expressionevaluator;


/**
 * Represents a set of functions over the set of valid properties for the query.
 * <p>
 * This mapping extends that of {@link PropertyMap} with the property's value.
 * 
 * @author James Higgins-Thomas
 */
public interface PropertySet extends PropertyMap {
    /**
    * Returns the value of a property.
    * <p>
    * For a valid property name, this must return the value of the property.
    * 
    * @return {@link PropertyValue}
    */
    public PropertyValue get(String propertyName);
}
