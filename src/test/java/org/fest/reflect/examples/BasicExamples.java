package org.fest.reflect.examples;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.reflect.core.Reflection.constructor;
import static org.fest.reflect.core.Reflection.method;

import java.util.Date;

import org.junit.Test;

import org.fest.assertions.examples.data.Movie;

public class BasicExamples {

  @Test
  public void constructor_and_method_call_example() throws Exception {
    Date date = new Date();
    Movie movie = constructor().withParameterTypes(String.class, Date.class).in(Movie.class)
        .newInstance("Pulp Fiction", date);
    assertThat(movie).isNotNull();
    assertThat(movie.getTitle()).isEqualTo("Pulp Fiction");
    assertThat(movie.getReleaseDate()).isEqualTo(date);

    method("setTitle").withParameterTypes(String.class).in(movie).invoke("Kill Bill");
    assertThat(movie.getTitle()).isEqualTo("Kill Bill");
  }
}
