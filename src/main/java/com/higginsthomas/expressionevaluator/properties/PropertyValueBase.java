package com.higginsthomas.expressionevaluator.properties;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class PropertyValueBase implements PropertyValue {
    
    @Override
    public int hashCode(){
        return new HashCodeBuilder()
            .append(getType())
            .append(getValue())
            .toHashCode();
    }

    @Override
    public boolean equals(final Object obj){
        if ( !(obj instanceof PropertyValue) ) return false;
        final PropertyValue other = (PropertyValue) obj;
        return new EqualsBuilder()
            .append(getType(), other.getType())
            .append(getValue(), other.getValue())
            .isEquals();
    }
}
