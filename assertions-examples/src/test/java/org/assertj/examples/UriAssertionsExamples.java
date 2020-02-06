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
package org.assertj.examples;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.jupiter.api.Test;

public class UriAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void uri_assertions_examples() throws URISyntaxException {
    assertThat(new URI("http://assertj.org:8080/news.html#print")).hasHost("assertj.org")
                                                                  .hasPort(8080)
                                                                  .hasPath("/news.html")
                                                                  .hasFragment("print");
    assertThat(new URI("http://assertj.org")).hasNoFragment()
                                             .hasNoPort()
                                             .hasPath("")
                                             .hasNoQuery()
                                             .hasNoUserInfo();

    assertThat(new URI("mailto:java-net@java.sun.com")).hasNoPath();

    // These assertions succeed:
    assertThat(new URI("http://test:pass@www.helloworld.org/index.html")).hasUserInfo("test:pass");
    assertThat(new URI("http://test@www.helloworld.org/index.html")).hasUserInfo("test");
    assertThat(new URI("http://:pass@www.helloworld.org/index.html")).hasUserInfo(":pass");

    // These assertions fail:
    // This assertion succeeds:
    assertThat(new URI("http://www.helloworld.org/index.html")).hasNoUserInfo();

    // This assertion fails:
    // assertThat(new URI("http://test:pass@www.helloworld.org/index.html")).hasNoUserInfo();

    assertThat(new URI("http://www.helloworld.org/index.html?happy")).hasParameter("happy");
    assertThat(new URI("http://www.helloworld.org/index.html?happy=very")).hasParameter("happy");
    assertThat(new URI("http://www.helloworld.org/index.html?happy")).hasParameter("happy", null);
    assertThat(new URI("http://www.helloworld.org/index.html?happy=very")).hasParameter("happy", "very");

    assertThat(new URI("http://www.helloworld.org/index.html")).hasNoParameters();
    assertThat(new URI("http://www.helloworld.org/index.html")).hasNoParameter("happy");
    assertThat(new URI("http://www.helloworld.org/index.html")).hasNoParameter("happy", "very");
    assertThat(new URI("http://www.helloworld.org/index.html?happy")).hasNoParameter("happy", "very");
    assertThat(new URI("http://www.helloworld.org/index.html?happy=very")).hasNoParameter("happy", null);
  }

  @Test
  public void url_assertions_examples() throws MalformedURLException {
    assertThat(new URL("http://assertj.org:8080/news.html#print")).hasHost("assertj.org")
                                                                  .hasPort(8080)
                                                                  .hasPath("/news.html")
                                                                  .hasAnchor("print");
    assertThat(new URL("http://assertj.org")).hasNoAnchor()
                                             .hasNoPath()
                                             .hasNoPort()
                                             .hasNoQuery()
                                             .hasNoUserInfo();

    assertThat(new URL("http://helloworld.org")).hasAuthority("helloworld.org");

    assertThat(new URL("http://www.helloworld.org/index.html?happy")).hasParameter("happy");
    assertThat(new URL("http://www.helloworld.org/index.html?happy=very")).hasParameter("happy");
    assertThat(new URL("http://www.helloworld.org/index.html?happy")).hasParameter("happy", null);
    assertThat(new URL("http://www.helloworld.org/index.html?happy=very")).hasParameter("happy", "very");

    assertThat(new URL("http://www.helloworld.org/index.html")).hasNoParameters();
    assertThat(new URL("http://www.helloworld.org/index.html")).hasNoParameter("happy");
    assertThat(new URL("http://www.helloworld.org/index.html")).hasNoParameter("happy", "very");
    assertThat(new URL("http://www.helloworld.org/index.html?happy")).hasNoParameter("happy", "very");
    assertThat(new URL("http://www.helloworld.org/index.html?happy=very")).hasNoParameter("happy", null);
  }

}
