## Workshop

For the basic session, the exercises to do are:
- BasicAssertionsExercises
- IterableAssertionsExercises
- ExceptionAssertionsExercises
- SoftAssertionsExercises

You can start with any of these, I recommend you to start with the BasicAssertionsExercises first to get a feel of AssertJ.

At any time, feel free to
- explore other assertions (there are many!) on different types (there are assertions for more than 50 types, like File, Map, Date, ... )
- try to think of a real case where AssertJ would help
- try some AssertJ feature highlights: http://joel-costigliola.github.io/assertj/assertj-core-features-highlight.html


Here's an example of using AssertJ in a typical ivy.xml PB2 test project:

<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<ivy-module version="1.0">
  <info module="com.orchestral.health.allergy.ccda.esp.test" organisation="orchestral" status="integration">
  </info>
  <configurations>
    <conf name="test"/>
  </configurations>
  <publications>
  </publications>
  <dependencies>
    <dependency conf="test->nodist" org="bundle" name="org.junit" rev="4.11.0"/>
    <dependency conf="test->nodist" org="bundle" name="org.mockito" rev="1.9.5"/>
    <dependency conf="test->nodist" name="assertj-core" org="assertj" rev="3.9.1"/>
    <exclude org="bundle" module="com.google.common"/>
    <exclude org="bundle" module="org.objectweb.asm"/>
  </dependencies>
</ivy-module>
