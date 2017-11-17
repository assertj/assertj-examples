package org.assertj;

/**
 * Entry point for assertions of different data types. Each method in this class is a static factory for the
 * type-specific assertion objects.
 */
public class Assertions {

  /**
   * Creates a new instance of <code>{@link com.google.common.net.HostAndPortAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static com.google.common.net.HostAndPortAssert assertThat(com.google.common.net.HostAndPort actual) {
    return new com.google.common.net.HostAndPortAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link com.google.common.net.InetAddressesAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static com.google.common.net.InetAddressesAssert assertThat(com.google.common.net.InetAddresses actual) {
    return new com.google.common.net.InetAddressesAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link com.google.common.net.InternetDomainNameAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static com.google.common.net.InternetDomainNameAssert assertThat(com.google.common.net.InternetDomainName actual) {
    return new com.google.common.net.InternetDomainNameAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.AlignmentAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.AlignmentAssert assertThat(org.assertj.examples.data.Alignment actual) {
    return new org.assertj.examples.data.AlignmentAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.ArtWorkAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.ArtWorkAssert assertThat(org.assertj.examples.data.ArtWork actual) {
    return new org.assertj.examples.data.ArtWorkAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.BasketBallPlayerAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.BasketBallPlayerAssert assertThat(org.assertj.examples.data.BasketBallPlayer actual) {
    return new org.assertj.examples.data.BasketBallPlayerAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.BookAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.BookAssert assertThat(org.assertj.examples.data.Book actual) {
    return new org.assertj.examples.data.BookAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.ClassUsingDifferentClassesWithSameNameAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.ClassUsingDifferentClassesWithSameNameAssert assertThat(org.assertj.examples.data.ClassUsingDifferentClassesWithSameName actual) {
    return new org.assertj.examples.data.ClassUsingDifferentClassesWithSameNameAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.EmployeeAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.EmployeeAssert assertThat(org.assertj.examples.data.Employee actual) {
    return new org.assertj.examples.data.EmployeeAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.EmployeeTitleAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.EmployeeTitleAssert assertThat(org.assertj.examples.data.Employee.Title actual) {
    return new org.assertj.examples.data.EmployeeTitleAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.MagicalAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.MagicalAssert assertThat(org.assertj.examples.data.Magical actual) {
    return new org.assertj.examples.data.MagicalAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.MansionAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.MansionAssert assertThat(org.assertj.examples.data.Mansion actual) {
    return new org.assertj.examples.data.MansionAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.NameAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.NameAssert assertThat(org.assertj.examples.data.Name actual) {
    return new org.assertj.examples.data.NameAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.PersonAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.PersonAssert assertThat(org.assertj.examples.data.Person actual) {
    return new org.assertj.examples.data.PersonAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.RaceAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.RaceAssert assertThat(org.assertj.examples.data.Race actual) {
    return new org.assertj.examples.data.RaceAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.RingAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.RingAssert assertThat(org.assertj.examples.data.Ring actual) {
    return new org.assertj.examples.data.RingAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.TeamAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.TeamAssert assertThat(org.assertj.examples.data.Team actual) {
    return new org.assertj.examples.data.TeamAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.TolkienCharacterAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.TolkienCharacterAssert assertThat(org.assertj.examples.data.TolkienCharacter actual) {
    return new org.assertj.examples.data.TolkienCharacterAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.bug18.Dollar$Assert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.bug18.Dollar$Assert assertThat(org.assertj.examples.data.bug18.Dollar$ actual) {
    return new org.assertj.examples.data.bug18.Dollar$Assert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.bug26.WithGenericListTypeAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.bug26.WithGenericListTypeAssert assertThat(org.assertj.examples.data.bug26.WithGenericListType actual) {
    return new org.assertj.examples.data.bug26.WithGenericListTypeAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.bug27.MyIteratorWrapperAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.bug27.MyIteratorWrapperAssert assertThat(org.assertj.examples.data.bug27.MyIteratorWrapper actual) {
    return new org.assertj.examples.data.bug27.MyIteratorWrapperAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.bug27.MyModelClassAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.bug27.MyModelClassAssert assertThat(org.assertj.examples.data.bug27.MyModelClass actual) {
    return new org.assertj.examples.data.bug27.MyModelClassAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.bug27.MyModelClassMyBeanAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.bug27.MyModelClassMyBeanAssert assertThat(org.assertj.examples.data.bug27.MyModelClass.MyBean actual) {
    return new org.assertj.examples.data.bug27.MyModelClassMyBeanAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.movie.MovieAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.movie.MovieAssert assertThat(org.assertj.examples.data.movie.Movie actual) {
    return new org.assertj.examples.data.movie.MovieAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.movie.TeamAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.movie.TeamAssert assertThat(org.assertj.examples.data.movie.Team actual) {
    return new org.assertj.examples.data.movie.TeamAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.neo4j.DragonBallGraphRepositoryAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.neo4j.DragonBallGraphRepositoryAssert assertThat(org.assertj.examples.data.neo4j.DragonBallGraphRepository actual) {
    return new org.assertj.examples.data.neo4j.DragonBallGraphRepositoryAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.service.GameServiceAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.service.GameServiceAssert assertThat(org.assertj.examples.data.service.GameService actual) {
    return new org.assertj.examples.data.service.GameServiceAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.assertj.examples.data.service.TeamManagerAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.assertj.examples.data.service.TeamManagerAssert assertThat(org.assertj.examples.data.service.TeamManager actual) {
    return new org.assertj.examples.data.service.TeamManagerAssert(actual);
  }

  /**
   * Creates a new <code>{@link Assertions}</code>.
   */
  protected Assertions() {
    // empty
  }
}
