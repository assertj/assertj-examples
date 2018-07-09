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
import static org.assertj.core.api.Assertions.contentOf;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.security.MessageDigest;

import org.junit.Test;

/**
 * File and stream usage examples.
 *
 * @author Joel Costigliola
 */
public class FileAndStreamAssertionsExamples extends AbstractAssertionsExamples {

  @Test
  public void file_assertions_examples() throws Exception {
    // file assertion
    File xFile = writeFile("xFile", "The Truth Is Out There");
    assertThat(xFile).exists().isFile().isRelative();
    assertThat(xFile).canRead().canWrite();
    assertThat(contentOf(xFile)).startsWith("The Truth").contains("Is Out").endsWith("There");

    // compare content with another file
    File xFileClone = writeFile("xFileClone", "The Truth Is Out There");
    assertThat(xFile).hasSameContentAs(xFileClone);

    // compare content with a string
    assertThat(xFile).hasContent("The Truth Is Out There");

    // compare content with a string, specifying a character set
    Charset turkishCharset = Charset.forName("windows-1254");
    File xFileWithTurkishCharset = writeFile("xFileWithTurkishCharset", "La Vérité Est Ailleurs", turkishCharset);
    assertThat(xFileWithTurkishCharset).usingCharset(turkishCharset).hasContent("La Vérité Est Ailleurs");

    // compare content with a binary array
    byte[] binaryContent = "The Truth Is Out There".getBytes();
    assertThat(xFile).hasBinaryContent(binaryContent);

    // compare content with a binary array
    binaryContent = "La Vérité Est Ailleurs".getBytes(turkishCharset.name());
    assertThat(xFileWithTurkishCharset).hasBinaryContent(binaryContent);

    File xFileWithExtension = writeFile("xFile.secret", "The Truth Is Out There");
    assertThat(xFileWithExtension)
                                  .hasParent("target")
                                  .hasParent(new File("target"))
                                  .hasExtension("secret")
                                  .hasName("xFile.secret");

    assertThat(new File("/")).hasNoParent();
  }

  @Test
  public void file_assertions_error_examples() throws Exception {
    File xFile = writeFile("xFile", "The Truth Is Out There");
    File xFileWithExtension = writeFile("xFile.secret", "The Truth Is Out There");

    try {
      assertThat(xFileWithExtension).hasParent("xDirectory");
    } catch (AssertionError e) {
      logAssertionErrorMessage("file with unexpected parent.", e);
    }

    try {
      assertThat(xFileWithExtension).hasExtension("png");
    } catch (AssertionError e) {
      logAssertionErrorMessage("file with unexpected extension.", e);
    }

    try {
      assertThat(xFile).hasExtension("secret");
    } catch (AssertionError e) {
      logAssertionErrorMessage("file without extension.", e);
    }

    try {
      assertThat(xFileWithExtension).hasName("xFile");
    } catch (AssertionError e) {
      logAssertionErrorMessage("file with unexpected name.", e);
    }
  }

  @Test
  public void stream_assertions_examples() throws Exception {
    assertThat(streamFrom("string")).hasSameContentAs(streamFrom("string"));
  }

  @Test
  public void improved_diff_assertions_examples() throws Exception {
    File actual = writeFile("actual", "aaaaaa\n" +
                                      "bbbbbb\n" +
                                      "cccccc\n" +
                                      "dddddd\n");
    File expected = writeFile("expected", "------\n" +
                                          "aaaaaa\n" +
                                          "bbbbbb\n" +
                                          "CCCCCC\n" +
                                          "dddddd\n" +
                                          "eeeeee\n");
    try {
      assertThat(actual).hasSameContentAs(expected);
    } catch (AssertionError e) {
      logAssertionErrorMessage("file diff", e);
    }
  }

  @Test
  public void should_check_digests() throws Exception {
    // GIVEN
    File tested = new File("src/test/resources/assertj-core-2.9.0.jar");
    byte[] md5Bytes = new byte[] { -36, -77, 1, 92, -46, -124, 71, 100, 76, -127, 10, -13, 82, -125, 44, 25 };
    byte[] sha1Bytes = new byte[] { 92, 90, -28, 91, 88, -15, 32, 35, -127, 122, -66, 73, 36, 71, -51, -57, -111, 44,
        26, 44 };
    // THEN
    assertThat(tested).hasDigest("SHA1", "5c5ae45b58f12023817abe492447cdc7912c1a2c")
                      .hasDigest(MessageDigest.getInstance("SHA1"), "5c5ae45b58f12023817abe492447cdc7912c1a2c")
                      .hasDigest("SHA1", sha1Bytes)
                      .hasDigest(MessageDigest.getInstance("SHA1"), sha1Bytes)
                      .hasDigest("MD5", "dcb3015cd28447644c810af352832c19")
                      .hasDigest(MessageDigest.getInstance("MD5"), "dcb3015cd28447644c810af352832c19")
                      .hasDigest("MD5", md5Bytes)
                      .hasDigest(MessageDigest.getInstance("MD5"), md5Bytes);

  }

  // helper methods

  private static ByteArrayInputStream streamFrom(String string) {
    return new ByteArrayInputStream(string.getBytes());
  }

  private File writeFile(String fileName, String fileContent) throws Exception {
    return writeFile(fileName, fileContent, Charset.defaultCharset());
  }

  private File writeFile(String fileName, String fileContent, Charset charset) throws Exception {
    File file = new File("target/" + fileName);
    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
    out.write(fileContent);
    out.close();
    return file;
  }

}
