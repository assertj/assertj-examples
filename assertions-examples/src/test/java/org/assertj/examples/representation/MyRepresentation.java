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

import org.assertj.core.presentation.Representation;
import org.assertj.core.presentation.StandardRepresentation;

/**
 * {@link MyRepresentation} is used as it has been registered as the default {@link Representation}.
 * <p> 
 * To register a {@link Representation}, you need to add the file <pre>org.assertj.core.presentation.Representation</pre> in META-INF/services, 
 * it must contain the fully qualified class name of the {@link Representation} to use, in our case: {@code org.assertj.examples.representation.CustomRepresentation}  
 */
public class MyRepresentation extends StandardRepresentation {

  // override needed to specify the format of classes not known by StandardRepresentation.
  @Override
  public String toStringOf(Object o) {
    if (o instanceof Example) return "EXAMPLE";
    // fallback to default formatting.
    return super.toStringOf(o);
  }
}
