package com.higginsthomas.expressionevaluator.properties;

import java.util.Calendar;

import org.joda.time.LocalDate;


/**
 * Represents a property value of type DATE.
 * 
 * @author James Higgins-Thomas
 */
public class DatePropertyValue extends ConstantPropertyValueBase {
    public DatePropertyValue(LocalDate value) {
        super(PropertyValueType.DATE, value);
    }

    public DatePropertyValue(Calendar value) {
        this(LocalDate.fromCalendarFields(value));
    }

    @Override
    public LocalDate getValue() { return (LocalDate)super.getValue(); }
    
    @Override
    public int compareTo(PropertyValue that) {
        if ( !this.getType().equals(that.getType()) ) throw new IncompatibleTypeException(this.getType(), that.getType());
        return ((LocalDate)this.getValue()).compareTo((LocalDate)that.getValue());
    }
}
