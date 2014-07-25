package com.higginsthomas.expressionevaluator;

import java.util.Calendar;

import org.joda.time.LocalDate;


/**
 * Represents a property value of type DATE.
 * 
 * @author James Higgins-Thomas
 */
public class DatePropertyValue implements PropertyValue {
    private LocalDate value;

    public DatePropertyValue(LocalDate value) {
        this.value = value;
    }

    public DatePropertyValue(Calendar value) {
        this.value = LocalDate.fromCalendarFields(value);
    }

    public PropertyValueType getType() { return PropertyValueType.DATE; }

    public LocalDate getValue() { return value; }

    public int compareTo(PropertyValue that) {
        if ( !this.getType().equals(that.getType()) ) throw new RuntimeException("Cannot compare types");
        return ((LocalDate)this.getValue()).compareTo((LocalDate)that.getValue());
    }
}
