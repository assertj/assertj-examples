package examples;

import static org.fest.util.Strings.concat;

class Person {
  private final String name;
  @SuppressWarnings("unused")
  private final int age;

  public Person(String name, int age) {
    this.name = name;
    this.age = age;
  }

  @Override
  public String toString() {
    return concat("Person[name=", name, "]");
  }
}