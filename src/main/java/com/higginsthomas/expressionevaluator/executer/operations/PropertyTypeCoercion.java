package com.higginsthomas.expressionevaluator.executer.operations;

import com.higginsthomas.expressionevaluator.PropertyValueType;


public class PropertyTypeCoercion {
    public static PropertyValueType coerceType(PropertyValueType a,
            PropertyValueType b)
    {
        PropertyValueType result = coerceDynamicType(a, b);
        if (result != null) {
            return result;
        } else {
            // TODO: throw new EvaluateException(String.format(
            // "Type Mismatch: %1$s is not compatible with %2$s", a, b));
            return null;
        }
    }

    private static PropertyValueType coerceStaticType(
            PropertyValueType a, PropertyValueType b)
    {
        if (a == PropertyValueType.VARIANT) return b;
        if (a == b || b == PropertyValueType.VARIANT)
            return a;
        if ((a == PropertyValueType.INTEGER && b == PropertyValueType.FLOAT)
                || (a == PropertyValueType.FLOAT && b == PropertyValueType.INTEGER))
            return PropertyValueType.FLOAT;
        return null;
    }

    private static PropertyValueType coerceDynamicType(
            PropertyValueType a, PropertyValueType b)
    {
        PropertyValueType result = coerceStaticType(a, b);
        if (result != null) {
            return result;
        }
        if ((a == PropertyValueType.INTEGER && b == PropertyValueType.TEXT)
                || (a == PropertyValueType.TEXT && b == PropertyValueType.INTEGER))
            return PropertyValueType.INTEGER;
        if ((a == PropertyValueType.FLOAT && b == PropertyValueType.TEXT)
                || (a == PropertyValueType.TEXT && b == PropertyValueType.FLOAT))
            return PropertyValueType.FLOAT;
        return null;
    }
}
