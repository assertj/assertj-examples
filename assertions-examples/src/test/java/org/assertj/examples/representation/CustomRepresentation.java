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
package org.assertj.examples.representation;

import org.assertj.core.presentation.StandardRepresentation;

public class CustomRepresentation extends StandardRepresentation {

  // override needed to specify the format of classes not known by StandardRepresentation.
  @Override
  protected String fallbackToStringOf(Object object) {
    if (object instanceof Example) return "EXAMPLE";
    return object.toString();
  }

  // override the String representation defined in StandardRepresentation
  @Override
  protected String toStringOf(String s) {
    return "$" + s + "$";
  }

  public static class Example {
  }
}
