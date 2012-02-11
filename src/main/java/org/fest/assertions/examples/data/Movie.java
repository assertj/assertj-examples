package org.fest.assertions.examples.data;

import java.util.Date;

public class Movie {

  private final String title;
  private final Date releaseDate;

  public Movie(String title, Date releaseDate) {
    super();
    this.title = title;
    this.releaseDate = releaseDate;
  }

  public String getTitle() {
    return title;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((releaseDate == null) ? 0 : releaseDate.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Movie other = (Movie) obj;
    if (releaseDate == null) {
      if (other.releaseDate != null) return false;
    } else if (!releaseDate.equals(other.releaseDate)) return false;
    if (title == null) {
      if (other.title != null) return false;
    } else if (!title.equals(other.title)) return false;
    return true;
  }

}
