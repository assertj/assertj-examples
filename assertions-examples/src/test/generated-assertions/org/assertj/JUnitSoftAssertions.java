package org.assertj;

import org.assertj.core.internal.cglib.proxy.Enhancer;

import org.assertj.core.api.ErrorCollector;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

/**
 * Entry point for assertions of different data types. Each method in this class is a static factory for the
 * type-specific assertion objects.
 */
public class JUnitSoftAssertions implements TestRule {

  /** Collects error messages of all AssertionErrors thrown by the proxied method. */
  protected final ErrorCollector collector = new ErrorCollector();

  /** Creates a new </code>{@link JUnitSoftAssertions}</code>. */
  public JUnitSoftAssertions() {
    super();
  }

  /**
   * TestRule implementation that verifies that no proxied assertion methods have failed.
   */
  public Statement apply(final Statement base, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        base.evaluate();
        MultipleFailureException.assertEmpty(collector.errors());
      }
    };
  }
  
  @SuppressWarnings("unchecked")
  protected <T, V> V proxy(Class<V> assertClass, Class<T> actualClass, T actual) {
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(assertClass);
    enhancer.setCallback(collector);
    return (V) enhancer.create(new Class[] { actualClass }, new Object[] { actual });
  }

  /**
   * Creates a new "soft" instance of <code>{@link com.google.common.net.HostAndPortAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public com.google.common.net.HostAndPortAssert assertThat(com.google.common.net.HostAndPort actual) {
    return proxy(com.google.common.net.HostAndPortAssert.class, com.google.common.net.HostAndPort.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link com.google.common.net.InetAddressesAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public com.google.common.net.InetAddressesAssert assertThat(com.google.common.net.InetAddresses actual) {
    return proxy(com.google.common.net.InetAddressesAssert.class, com.google.common.net.InetAddresses.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link com.google.common.net.InternetDomainNameAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public com.google.common.net.InternetDomainNameAssert assertThat(com.google.common.net.InternetDomainName actual) {
    return proxy(com.google.common.net.InternetDomainNameAssert.class, com.google.common.net.InternetDomainName.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.apache.commons.lang3.exception.ContextedRuntimeExceptionAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.apache.commons.lang3.exception.ContextedRuntimeExceptionAssert assertThat(org.apache.commons.lang3.exception.ContextedRuntimeException actual) {
    return proxy(org.apache.commons.lang3.exception.ContextedRuntimeExceptionAssert.class, org.apache.commons.lang3.exception.ContextedRuntimeException.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.AlignmentAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.AlignmentAssert assertThat(org.assertj.examples.data.Alignment actual) {
    return proxy(org.assertj.examples.data.AlignmentAssert.class, org.assertj.examples.data.Alignment.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.ArtWorkAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.ArtWorkAssert assertThat(org.assertj.examples.data.ArtWork actual) {
    return proxy(org.assertj.examples.data.ArtWorkAssert.class, org.assertj.examples.data.ArtWork.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.BasketBallPlayerAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.BasketBallPlayerAssert assertThat(org.assertj.examples.data.BasketBallPlayer actual) {
    return proxy(org.assertj.examples.data.BasketBallPlayerAssert.class, org.assertj.examples.data.BasketBallPlayer.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.BookAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.BookAssert assertThat(org.assertj.examples.data.Book actual) {
    return proxy(org.assertj.examples.data.BookAssert.class, org.assertj.examples.data.Book.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.BookTitleAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.BookTitleAssert assertThat(org.assertj.examples.data.Book.Title actual) {
    return proxy(org.assertj.examples.data.BookTitleAssert.class, org.assertj.examples.data.Book.Title.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.ClassUsingDifferentClassesWithSameNameAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.ClassUsingDifferentClassesWithSameNameAssert assertThat(org.assertj.examples.data.ClassUsingDifferentClassesWithSameName actual) {
    return proxy(org.assertj.examples.data.ClassUsingDifferentClassesWithSameNameAssert.class, org.assertj.examples.data.ClassUsingDifferentClassesWithSameName.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.EmployeeAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.EmployeeAssert assertThat(org.assertj.examples.data.Employee actual) {
    return proxy(org.assertj.examples.data.EmployeeAssert.class, org.assertj.examples.data.Employee.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.MagicalAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.MagicalAssert assertThat(org.assertj.examples.data.Magical actual) {
    return proxy(org.assertj.examples.data.MagicalAssert.class, org.assertj.examples.data.Magical.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.MansionAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.MansionAssert assertThat(org.assertj.examples.data.Mansion actual) {
    return proxy(org.assertj.examples.data.MansionAssert.class, org.assertj.examples.data.Mansion.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.NameAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.NameAssert assertThat(org.assertj.examples.data.Name actual) {
    return proxy(org.assertj.examples.data.NameAssert.class, org.assertj.examples.data.Name.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.PersonAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.PersonAssert assertThat(org.assertj.examples.data.Person actual) {
    return proxy(org.assertj.examples.data.PersonAssert.class, org.assertj.examples.data.Person.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.RaceAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.RaceAssert assertThat(org.assertj.examples.data.Race actual) {
    return proxy(org.assertj.examples.data.RaceAssert.class, org.assertj.examples.data.Race.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.RingAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.RingAssert assertThat(org.assertj.examples.data.Ring actual) {
    return proxy(org.assertj.examples.data.RingAssert.class, org.assertj.examples.data.Ring.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.TeamAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.TeamAssert assertThat(org.assertj.examples.data.Team actual) {
    return proxy(org.assertj.examples.data.TeamAssert.class, org.assertj.examples.data.Team.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.TolkienCharacterAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.TolkienCharacterAssert assertThat(org.assertj.examples.data.TolkienCharacter actual) {
    return proxy(org.assertj.examples.data.TolkienCharacterAssert.class, org.assertj.examples.data.TolkienCharacter.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.bug18.Dollar$Assert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.bug18.Dollar$Assert assertThat(org.assertj.examples.data.bug18.Dollar$ actual) {
    return proxy(org.assertj.examples.data.bug18.Dollar$Assert.class, org.assertj.examples.data.bug18.Dollar$.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.bug26.WithGenericListTypeAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.bug26.WithGenericListTypeAssert assertThat(org.assertj.examples.data.bug26.WithGenericListType actual) {
    return proxy(org.assertj.examples.data.bug26.WithGenericListTypeAssert.class, org.assertj.examples.data.bug26.WithGenericListType.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.bug27.MyIteratorWrapperAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.bug27.MyIteratorWrapperAssert assertThat(org.assertj.examples.data.bug27.MyIteratorWrapper actual) {
    return proxy(org.assertj.examples.data.bug27.MyIteratorWrapperAssert.class, org.assertj.examples.data.bug27.MyIteratorWrapper.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.bug27.MyModelClassAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.bug27.MyModelClassAssert assertThat(org.assertj.examples.data.bug27.MyModelClass actual) {
    return proxy(org.assertj.examples.data.bug27.MyModelClassAssert.class, org.assertj.examples.data.bug27.MyModelClass.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.bug27.MyModelClassMyBeanAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.bug27.MyModelClassMyBeanAssert assertThat(org.assertj.examples.data.bug27.MyModelClass.MyBean actual) {
    return proxy(org.assertj.examples.data.bug27.MyModelClassMyBeanAssert.class, org.assertj.examples.data.bug27.MyModelClass.MyBean.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.movie.MovieAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.movie.MovieAssert assertThat(org.assertj.examples.data.movie.Movie actual) {
    return proxy(org.assertj.examples.data.movie.MovieAssert.class, org.assertj.examples.data.movie.Movie.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.movie.TeamAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.movie.TeamAssert assertThat(org.assertj.examples.data.movie.Team actual) {
    return proxy(org.assertj.examples.data.movie.TeamAssert.class, org.assertj.examples.data.movie.Team.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.neo4j.DragonBallGraphAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.neo4j.DragonBallGraphAssert assertThat(org.assertj.examples.data.neo4j.DragonBallGraph actual) {
    return proxy(org.assertj.examples.data.neo4j.DragonBallGraphAssert.class, org.assertj.examples.data.neo4j.DragonBallGraph.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.service.GameServiceAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.service.GameServiceAssert assertThat(org.assertj.examples.data.service.GameService actual) {
    return proxy(org.assertj.examples.data.service.GameServiceAssert.class, org.assertj.examples.data.service.GameService.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.service.TeamManagerAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  public org.assertj.examples.data.service.TeamManagerAssert assertThat(org.assertj.examples.data.service.TeamManager actual) {
    return proxy(org.assertj.examples.data.service.TeamManagerAssert.class, org.assertj.examples.data.service.TeamManager.class, actual);
  }

}
