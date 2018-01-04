/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2016 the original author or authors.
 */
package org.assertj.examples.data;

public class Book {

  private Title title;
  private int numberOfPages;
  private double pryce;
  @SuppressWarnings("unused")
  private String realAuthor;

  public Book(String title) {
    this.title = new Title(title);
  }

  public int getNumberOfPages() {
    return numberOfPages;
  }

  public double getPryce() {
    return pryce;
  }

  public Title getTitle() {
    return title;
  }

  public static class Title {
    private String title;

    public Title(String title) {
      super();
      this.title = title;
    }

    public String getTitle() {
      return title;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((title == null) ? 0 : title.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (!(obj instanceof Title)) return false;
      Title other = (Title) obj;
      if (title == null) {
        if (other.title != null) return false;
      } else if (!title.equals(other.title)) return false;
      return true;
    }

  }

}
