package com.higginsthomas.expressionevaluator.values;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.joda.time.format.DateTimeFormat;

import com.higginsthomas.expressionevaluator.*;


public class PropertyTypeConversion {
    public static PropertyValueType computeResultType(PropertyValueType a,
            PropertyValueType t) throws TypeConversionException
    {
        switch ( a ) {
        case VARIANT:
            return t;
            
        case TEXT:
            if ( t == PropertyValueType.VARIANT ) return PropertyValueType.TEXT;
            return t;
            
        case DATE:
            if ( t == PropertyValueType.DATE ) return PropertyValueType.DATE;
            break;
        
        case FLOAT:
            if ( t == PropertyValueType.DATE ) break;
            return PropertyValueType.FLOAT;
         
        case DECIMAL:
            if ( t == PropertyValueType.DATE ) break;
            if ( t == PropertyValueType.FLOAT ) return PropertyValueType.FLOAT;
            return PropertyValueType.DECIMAL;
            
        case INTEGER:
            if ( t == PropertyValueType.DATE ) break;
            if ( t == PropertyValueType.FLOAT ) return PropertyValueType.FLOAT;
            if ( t == PropertyValueType.DECIMAL ) return PropertyValueType.DECIMAL;
            return PropertyValueType.INTEGER;
        }
        
        throw new TypeConversionException(String.format("Types %s and %s are incompatible", a, t));
    }
    
    @SuppressWarnings("incomplete-switch")
    public static PropertyValue convert(PropertyValue v, PropertyValueType t) throws TypeConversionException {
        if ( v.getType() == t ) return v;
        if ( t == PropertyValueType.VARIANT ) return v;
        
        switch ( t ) {
        case TEXT:
            return new TextPropertyValue(v.getValue().toString());
            
        case DATE:
            switch ( v.getType() ) {
            case TEXT:
                String s = (String)v.getValue();
                try {
                if ( s.contains("-") ) {
                    return new DatePropertyValue(DateTimeFormat.forPattern("mm-dd-yyyy").parseLocalDate(s));
                } else if ( s.contains("/") ) {
                    return new DatePropertyValue(DateTimeFormat.forPattern("mm/dd/yyyy").parseLocalDate(s));
                } else {
                    return new DatePropertyValue(DateTimeFormat.forPattern("yyyymmdd").parseLocalDate(s));
                }
                } catch ( IllegalArgumentException e ) {
                    throw new TypeConversionException(String.format("Cannot convert \"%s\" to DATE", v.getValue().toString()), e);
                }
            }
            break;
        
        case FLOAT:
            switch ( v.getType() ) {
            case TEXT:
                try {
                    return new FloatPropertyValue(Double.parseDouble((String)v.getValue()));
                } catch ( NumberFormatException e ) {
                    throw new TypeConversionException(String.format("Cannot convert \"%s\" to FLOAT", v.getValue().toString()), e);
                }
            case DECIMAL:
                return new FloatPropertyValue(((BigDecimal)v.getValue()).doubleValue());
            case INTEGER:
                return new FloatPropertyValue(((BigInteger)v.getValue()).doubleValue());
            }
            break;
         
        case DECIMAL:
            switch ( v.getType() ) {
            case TEXT:
                try {
                    return new DecimalPropertyValue(new BigDecimal((String)v.getValue()));
                } catch ( NumberFormatException e ) {
                    throw new TypeConversionException(String.format("Cannot convert \"%s\" to DECIMAL", v.getValue().toString()), e);
                }
            case INTEGER:
                return new DecimalPropertyValue(new BigDecimal((BigInteger)v.getValue()));
            }
            break;
            
        case INTEGER:
            switch ( v.getType() ) {
            case TEXT:
                try {
                    return new IntegerPropertyValue(new BigInteger((String)v.getValue()));
                } catch ( NumberFormatException e ) {
                    throw new TypeConversionException(String.format("Cannot convert \"%s\" to INTEGER", v.getValue().toString()), e);
                }
            }
            break;
        }
        
        throw new TypeConversionException(String.format("Cannot convert value from %s to %s", v.getType(), t));
    }

    
    public static class TypeConversionException extends Exception {
        private static final long serialVersionUID = 1L;
        public TypeConversionException(String msg) {
            super(msg);
        }
        public TypeConversionException(String msg, Throwable t) {
            super(msg, t);
        }
    }
}
