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
 * Copyright 2012-2014 the original author or authors.
 */
package org.assertj.examples.data;

public class Book {

  private Title title;

  public Book(String title) {

    this.title = new Title(title);
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
  }

}
