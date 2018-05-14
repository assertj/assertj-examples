package org.assertj;

/**
 * Like {@link SoftAssertions} but as a junit rule that takes care of calling
 * {@link SoftAssertions#assertAll() assertAll()} at the end of each test.
 * <p>
 * Example:
 * <pre><code class='java'> public class SoftlyTest {
 *
 *     &#064;Rule
 *     public final JUnitBDDSoftAssertions softly = new JUnitBDDSoftAssertions();
 *
 *     &#064;Test
 *     public void soft_bdd_assertions() throws Exception {
 *       softly.assertThat(1).isEqualTo(2);
 *       softly.assertThat(Lists.newArrayList(1, 2)).containsOnly(1, 2);
 *       // no need to call assertAll(), this is done automatically.
 *     }
 *  }</code></pre>
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class JUnitSoftAssertions extends org.assertj.core.api.JUnitSoftAssertions {

  /**
   * Creates a new "soft" instance of <code>{@link com.google.common.net.InetAddressesAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public com.google.common.net.InetAddressesAssert assertThat(com.google.common.net.InetAddresses actual) {
    return proxy(com.google.common.net.InetAddressesAssert.class, com.google.common.net.InetAddresses.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link com.google.common.net.InternetDomainNameAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public com.google.common.net.InternetDomainNameAssert assertThat(com.google.common.net.InternetDomainName actual) {
    return proxy(com.google.common.net.InternetDomainNameAssert.class, com.google.common.net.InternetDomainName.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.apache.commons.lang3.exception.ContextedRuntimeExceptionAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.apache.commons.lang3.exception.ContextedRuntimeExceptionAssert assertThat(org.apache.commons.lang3.exception.ContextedRuntimeException actual) {
    return proxy(org.apache.commons.lang3.exception.ContextedRuntimeExceptionAssert.class, org.apache.commons.lang3.exception.ContextedRuntimeException.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.AlignmentAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.AlignmentAssert assertThat(org.assertj.examples.data.Alignment actual) {
    return proxy(org.assertj.examples.data.AlignmentAssert.class, org.assertj.examples.data.Alignment.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.ArtWorkAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.ArtWorkAssert assertThat(org.assertj.examples.data.ArtWork actual) {
    return proxy(org.assertj.examples.data.ArtWorkAssert.class, org.assertj.examples.data.ArtWork.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.BasketBallPlayerAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.BasketBallPlayerAssert assertThat(org.assertj.examples.data.BasketBallPlayer actual) {
    return proxy(org.assertj.examples.data.BasketBallPlayerAssert.class, org.assertj.examples.data.BasketBallPlayer.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.BookAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.BookAssert assertThat(org.assertj.examples.data.Book actual) {
    return proxy(org.assertj.examples.data.BookAssert.class, org.assertj.examples.data.Book.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.BookTitleAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.BookTitleAssert assertThat(org.assertj.examples.data.Book.Title actual) {
    return proxy(org.assertj.examples.data.BookTitleAssert.class, org.assertj.examples.data.Book.Title.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.ClassUsingDifferentClassesWithSameNameAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.ClassUsingDifferentClassesWithSameNameAssert assertThat(org.assertj.examples.data.ClassUsingDifferentClassesWithSameName actual) {
    return proxy(org.assertj.examples.data.ClassUsingDifferentClassesWithSameNameAssert.class, org.assertj.examples.data.ClassUsingDifferentClassesWithSameName.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.EmployeeAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.EmployeeAssert assertThat(org.assertj.examples.data.Employee actual) {
    return proxy(org.assertj.examples.data.EmployeeAssert.class, org.assertj.examples.data.Employee.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.EmployeeTitleAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.EmployeeTitleAssert assertThat(org.assertj.examples.data.Employee.Title actual) {
    return proxy(org.assertj.examples.data.EmployeeTitleAssert.class, org.assertj.examples.data.Employee.Title.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.MagicalAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.MagicalAssert assertThat(org.assertj.examples.data.Magical actual) {
    return proxy(org.assertj.examples.data.MagicalAssert.class, org.assertj.examples.data.Magical.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.MansionAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.MansionAssert assertThat(org.assertj.examples.data.Mansion actual) {
    return proxy(org.assertj.examples.data.MansionAssert.class, org.assertj.examples.data.Mansion.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.NameAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.NameAssert assertThat(org.assertj.examples.data.Name actual) {
    return proxy(org.assertj.examples.data.NameAssert.class, org.assertj.examples.data.Name.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.PersonAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.PersonAssert assertThat(org.assertj.examples.data.Person actual) {
    return proxy(org.assertj.examples.data.PersonAssert.class, org.assertj.examples.data.Person.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.RaceAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.RaceAssert assertThat(org.assertj.examples.data.Race actual) {
    return proxy(org.assertj.examples.data.RaceAssert.class, org.assertj.examples.data.Race.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.RingAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.RingAssert assertThat(org.assertj.examples.data.Ring actual) {
    return proxy(org.assertj.examples.data.RingAssert.class, org.assertj.examples.data.Ring.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.TeamAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.TeamAssert assertThat(org.assertj.examples.data.Team actual) {
    return proxy(org.assertj.examples.data.TeamAssert.class, org.assertj.examples.data.Team.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.TolkienCharacterAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.TolkienCharacterAssert assertThat(org.assertj.examples.data.TolkienCharacter actual) {
    return proxy(org.assertj.examples.data.TolkienCharacterAssert.class, org.assertj.examples.data.TolkienCharacter.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.bug18.Dollar$Assert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.bug18.Dollar$Assert assertThat(org.assertj.examples.data.bug18.Dollar$ actual) {
    return proxy(org.assertj.examples.data.bug18.Dollar$Assert.class, org.assertj.examples.data.bug18.Dollar$.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.bug26.WithGenericListTypeAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.bug26.WithGenericListTypeAssert assertThat(org.assertj.examples.data.bug26.WithGenericListType actual) {
    return proxy(org.assertj.examples.data.bug26.WithGenericListTypeAssert.class, org.assertj.examples.data.bug26.WithGenericListType.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.bug27.MyIteratorWrapperAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.bug27.MyIteratorWrapperAssert assertThat(org.assertj.examples.data.bug27.MyIteratorWrapper actual) {
    return proxy(org.assertj.examples.data.bug27.MyIteratorWrapperAssert.class, org.assertj.examples.data.bug27.MyIteratorWrapper.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.bug27.MyModelClassAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.bug27.MyModelClassAssert assertThat(org.assertj.examples.data.bug27.MyModelClass actual) {
    return proxy(org.assertj.examples.data.bug27.MyModelClassAssert.class, org.assertj.examples.data.bug27.MyModelClass.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.bug27.MyModelClassMyBeanAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.bug27.MyModelClassMyBeanAssert assertThat(org.assertj.examples.data.bug27.MyModelClass.MyBean actual) {
    return proxy(org.assertj.examples.data.bug27.MyModelClassMyBeanAssert.class, org.assertj.examples.data.bug27.MyModelClass.MyBean.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.movie.MovieAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.movie.MovieAssert assertThat(org.assertj.examples.data.movie.Movie actual) {
    return proxy(org.assertj.examples.data.movie.MovieAssert.class, org.assertj.examples.data.movie.Movie.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.movie.TeamAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.movie.TeamAssert assertThat(org.assertj.examples.data.movie.Team actual) {
    return proxy(org.assertj.examples.data.movie.TeamAssert.class, org.assertj.examples.data.movie.Team.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.neo4j.DragonBallGraphRepositoryAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.neo4j.DragonBallGraphRepositoryAssert assertThat(org.assertj.examples.data.neo4j.DragonBallGraphRepository actual) {
    return proxy(org.assertj.examples.data.neo4j.DragonBallGraphRepositoryAssert.class, org.assertj.examples.data.neo4j.DragonBallGraphRepository.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.service.GameServiceAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.service.GameServiceAssert assertThat(org.assertj.examples.data.service.GameService actual) {
    return proxy(org.assertj.examples.data.service.GameServiceAssert.class, org.assertj.examples.data.service.GameService.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link org.assertj.examples.data.service.TeamManagerAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public org.assertj.examples.data.service.TeamManagerAssert assertThat(org.assertj.examples.data.service.TeamManager actual) {
    return proxy(org.assertj.examples.data.service.TeamManagerAssert.class, org.assertj.examples.data.service.TeamManager.class, actual);
  }

}
