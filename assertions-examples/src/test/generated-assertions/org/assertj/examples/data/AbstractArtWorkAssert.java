package org.assertj.examples.data;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.util.Objects;

/**
 * Abstract base class for {@link ArtWork} specific assertions - Generated by CustomAssertionGenerator.
 */
public abstract class AbstractArtWorkAssert<S extends AbstractArtWorkAssert<S, A>, A extends ArtWork> extends AbstractAssert<S, A> {

  /**
   * Creates a new <code>{@link AbstractArtWorkAssert}</code> to make assertions on actual ArtWork.
   * @param actual the ArtWork we want to make assertions on.
   */
  protected AbstractArtWorkAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }

  /**
   * Verifies that the actual ArtWork's creator is equal to the given one.
   * @param creator the given creator to compare the actual ArtWork's creator to.
   * @return this assertion object.
   * @throws AssertionError - if the actual ArtWork's creator is not equal to the given one.
   */
  public S hasCreator(String creator) {
    // check that actual ArtWork we want to make assertions on is not null.
    isNotNull();


    // overrides the default error message with a more explicit one
    String assertjErrorMessage = "\nExpecting creator of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";
    
    // check
    String actualCreator = actual.getCreator();
    if (!Objects.areEqual(actualCreator, creator)) {
      failWithMessage(assertjErrorMessage, actual, creator, actualCreator);
    }

    // return the current assertion for method chaining
    return myself;
  }

}
