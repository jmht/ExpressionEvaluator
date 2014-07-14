package com.higginsthomas.expressionevaluator;


/**
 * This class is responsible for compiling a provided query expression into an
 * executable form which can then be evaluated against a set or properties.
 *
 * @author James Higgins-Thomas
 */
public class ExpressionCompiler {
  /**
   * Compile query expression.
   * 
   * @param queryExpression the query expression
   * @param map             a function which validates the identifiers in the expression.
   * @return an <code>ExpressionEvaluator</code> representing the compiled expression.
   */
  public ExpressionEvaluator compile(String queryExpression, PropertyMap map) {
    return new ExpressionEvaluator() {
      public boolean evaluate(PropertySet properties) {
        return false;
      } 
    };
  }
  
  /**
   * Compile query expression.
   * <p>
   * This variation defers the validation of identifiers to evaluation time. For
   * compilation, all identifiers are presumed valid.
   * 
   * @param queryExpression the query expression
   * @return an <code>ExpressionEvaluator</code> representing the compiled expression.
   */
  public ExpressionEvaluator compile(String queryExpression) {
    return compile(queryExpression, new PropertyMap() {
      public boolean exists(String propertyName) { return true ; }
      public PropertyValueType getType(String propertyName) { return PropertyValueType.VARIANT; }
    });
  }

  /**
   * Compile and execute a query expression.
   * <p>
   * Compile the expression and immediately evaluate it against the provided properties.
   * This method is equivalent to 
   * <code>compile(queryExpression, properties).evaluate(properties)</code>
   * 
   * @param queryExpression the query expression
   * @param properties      the property set over which to evaluate the expression.
   * @return result of the evaluation
   */
  public boolean evaluate(String queryExpression, PropertySet properties) {
    return compile(queryExpression, properties).evaluate(properties);
  }
}
