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


/**
 * @author Yvonne Wang
 */
public class Name implements Comparable<Name>{

  private String first;
  private String last;

  public Name() {}

  public Name(String first) {
    setFirst(first);
  }

  public Name(String first, String last) {
    setFirst(first);
    setLast(last);
  }

  public String getFirst() {
    return first;
  }

  public void setFirst(String first) {
    this.first = first;
  }

  public String getLast() {
    return last;
  }

  public void setLast(String last) {
    this.last = last;
  }

  @Override public String toString() {
    return String.format("%s[first=%s, last=%s]", getClass().getSimpleName(), first, last);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Name)) return false;

    Name name = (Name) o;

    if (first != null ? !first.equals(name.first) : name.first != null) return false;
    if (last != null ? !last.equals(name.last) : name.last != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = first != null ? first.hashCode() : 0;
    result = 31 * result + (last != null ? last.hashCode() : 0);
    return result;
  }

  @Override
  public int compareTo(Name other) {
    return last.compareTo(other.last);
  }
}
