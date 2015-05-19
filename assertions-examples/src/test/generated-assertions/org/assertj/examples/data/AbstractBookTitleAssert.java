package org.assertj.examples.data;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.util.Objects;

/**
 * Abstract base class for {@link Book.Title} specific assertions - Generated by CustomAssertionGenerator.
 */
public abstract class AbstractBookTitleAssert<S extends AbstractBookTitleAssert<S, A>, A extends Book.Title> extends AbstractAssert<S, A> {

  /**
   * Creates a new <code>{@link AbstractBookTitleAssert}</code> to make assertions on actual Book.Title.
   * @param actual the Book.Title we want to make assertions on.
   */
  protected AbstractBookTitleAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }

  /**
   * Verifies that the actual Book.Title's title is equal to the given one.
   * @param title the given title to compare the actual Book.Title's title to.
   * @return this assertion object.
   * @throws AssertionError - if the actual Book.Title's title is not equal to the given one.
   */
  public S hasTitle(String title) {
    // check that actual Book.Title we want to make assertions on is not null.
    isNotNull();

    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting title of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";
    
    // null safe check
    String actualTitle = actual.getTitle();
    if (!Objects.areEqual(actualTitle, title)) {
      failWithMessage(assertjErrorMessage, actual, title, actualTitle);
    }

    // return the current assertion for method chaining
    return myself;
  }

}