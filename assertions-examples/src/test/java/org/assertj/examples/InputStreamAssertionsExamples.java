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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.MessageDigest;

import org.junit.jupiter.api.Test;

public class InputStreamAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void input_stream_content_can_be_compared_to_string() {
    // GIVEN
    String text = "Real stupidity beats artificial intelligence every time";
    InputStream inputStream = new ByteArrayInputStream(text.getBytes());
    // THEN
    assertThat(inputStream).hasContent(text);
  }

  @Test
  public void input_stream_empty_or_not_assertions() {
    String text = "Real stupidity beats artificial intelligence every time";
    InputStream inputStream = new ByteArrayInputStream(text.getBytes());
    assertThat(inputStream).isNotEmpty();
    assertThat(new ByteArrayInputStream("".getBytes())).isEmpty();
  }

  @Test
  public void should_check_digests() throws Exception {
    // GIVEN
    byte[] md5Bytes = new byte[] { -36, -77, 1, 92, -46, -124, 71, 100, 76, -127, 10, -13, 82, -125, 44, 25 };
    byte[] sha1Bytes = new byte[] { 92, 90, -28, 91, 88, -15, 32, 35, -127, 122, -66, 73, 36, 71, -51, -57, -111, 44,
        26, 44 };
    // THEN
    assertThat(actualInputStream()).hasDigest("SHA1", sha1Bytes);
    assertThat(actualInputStream()).hasDigest("SHA1", "5c5ae45b58f12023817abe492447cdc7912c1a2c");
    assertThat(actualInputStream()).hasDigest("MD5", "dcb3015cd28447644c810af352832c19");
    assertThat(actualInputStream()).hasDigest("MD5", md5Bytes);
    assertThat(actualInputStream()).hasDigest(MessageDigest.getInstance("SHA1"), "5c5ae45b58f12023817abe492447cdc7912c1a2c");
    assertThat(actualInputStream()).hasDigest(MessageDigest.getInstance("SHA1"), sha1Bytes);
    assertThat(actualInputStream()).hasDigest(MessageDigest.getInstance("MD5"), "dcb3015cd28447644c810af352832c19");
    assertThat(actualInputStream()).hasDigest(MessageDigest.getInstance("MD5"), md5Bytes);

  }

  private static FileInputStream actualInputStream() throws FileNotFoundException {
    return new FileInputStream(new File("src/test/resources/assertj-core-2.9.0.jar"));
  }

}
