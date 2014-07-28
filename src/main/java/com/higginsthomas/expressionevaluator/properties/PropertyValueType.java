package com.higginsthomas.expressionevaluator.properties;

/**
 * This enumeration represents the types recognized by the expression evaluator.
 * <p>
 * Supported types are:
 * <table>
 * <th><td>Enum type</td><td>Java type</td><td>comments</td></th>
 * <tr><td>TEXT</td><td>String</td><td>Arbitrary text</td></tr>
 * <tr><td>INTEGER</td>BigInteger<td></td><td>Integer number</td></tr>
 * <tr><td>DECIMAL/td><td>BigDecimal</td><td>Decimal number</td></tr>
 * <tr><td>FLOAT</td><td>Double</td><td>Floating number</td></tr>
 * <tr><td>DATE</td><td>LocalDate</td><td>Date</td></tr>
 * <tr><td>VARIANT</td><td>-</td><td>Indicates a property whose type varies 
 *                     or is unknown at compile time. This should only be returned
 *                     by <code>PropertyMap</code> and not <code>PropertySet</code></td></tr>
 * </table>
 * 
 * @author James Higgins-Thomas
 */
public enum PropertyValueType {
    INTEGER, 
    DECIMAL, 
    FLOAT, 
    TEXT, 
    DATE,
    VARIANT 
}
