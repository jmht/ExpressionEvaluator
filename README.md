ExpressionEvaluator
===================

Exploratory project demonstrating custom query parser in Java.
This is a port of QueryParser (https://github.com/jmht/QueryParser)
from C# to Java, replacing the originally hand-crafted parser with 
Antlr (http://www.antlr.org/) and the .NET Expression Tree 
evaluation with a Java implementation.

The basic motivation remains the same - to explore the option
of a dynamic query capability in a service.

Example:
<pre>
  List<Person> selected = QueryPeople(people, "LastName like '.*son' and age < 40");
  
  ...
  
  List<Person> QueryPeople(final Iterable<Person> people, final String queryString) {
      try {
          ExpressionEvaluator eval = ExpressionCompiler.compiler().compile(queryString, new PersonMap());
          List<Person> result = new LinkedList<Person>();
          long start = System.currentTimeMillis();
          for ( Person p : people ) {
              if ( eval.evaluate(new PersonProperties(p)) ) {
                  result.add(p);
              }
          }
          return result;
      } catch (CompileException e) {
          System.out.println(e.getMessage());
      }
  }

  ...
  
  class PersonMap implements PropertyMap {
      public boolean exists(final String propertyName) {
          switch ( propertyName.toLowerCase() ) {
          case "lastname": return true;
          case "firstname": return true;
          case "sex": return true;
          case "age": return true;
          case "tenure": return true;
          default: return false;
          }
      }
      public PropertyValueType getType(final String propertyName) {
          switch ( propertyName.toLowerCase() ) {
          case "lastname": return PropertyValueType.TEXT;
          case "firstname": return PropertyValueType.TEXT;
          case "sex": return PropertyValueType.TEXT;
          case "age": return PropertyValueType.INTEGER;
          case "tenure": return PropertyValueType.INTEGER;
          case "birthdate": return PropertyValueType.DATE;
          default: throw new RuntimeException("Internal error: Undefined property");
          }
      }
  }

  class PersonProperties extends PersonMap implements PropertySet {
      private final Person p;
      PersonProperties(final Person p) {
          this.p = p;
      }
      public PropertyValue get(String propertyName) {
          switch ( propertyName.toLowerCase() ) {
          case "lastname": return new TextPropertyValue(p.getLastName());
          case "firstname": return new TextPropertyValue(p.getFirstName());
          case "sex": return new TextPropertyValue(p.getSex());
          case "age": { // Computed property
              return new IntegerPropertyValue(Years.yearsBetween(p.birthDate, LocalDate.now()).getYears());
          }
          case "tenure": return new IntegerPropertyValue(p.getTenure());
          case "birthdate": return new DatePropertyValue(p.getDate());
          default: throw new RuntimeException("Internal error: Undefined property");
          }
      }
  }

  interface Person {
      String getLastName();
      String getFirstName(); 
      char   getSex();
      int    getAge();
      int    getTenure();
      LocalDate getBirthDate();
  }
  </pre>
