package com.higginsthomas.expressionevaluator;


/**
 * Convenience class providing a base implementation for {@link PropertySet}.
 * <p>
 * This base class allows an implementation to provide only the <code>get</code>
 * operation. This implementation will infer that a property exists from the
 * return of a non-null {@link PropertyValue} from <code>get()<code>.
 * 
 * @author James Higgins-Thomas
 */
public abstract class PropertySetBase implements PropertySet {
    public boolean exists(String propertyName) {
        try {
            return get(propertyName) instanceof PropertyValue;
        } catch (Exception e) {
            return false;
        }
    }

    public PropertyValueType getType(String propertyName) {
        if (exists(propertyName)) {
            return get(propertyName).getType();
        } else {
            throw new IllegalArgumentException(String.format(
                                               "Property %s is undefined",
                                               propertyName));
        }
    }

    public abstract PropertyValue get(String propertyName);
}
