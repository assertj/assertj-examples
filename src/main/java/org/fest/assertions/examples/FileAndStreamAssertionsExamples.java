package org.fest.assertions.examples;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;

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
    
    // compare content with another file
    File xFileClone = writeFile("xFileClone", "The Truth Is Out There");
    assertThat(xFile).hasContentEqualTo(xFileClone);
    
    // compare content with a binary array
    byte[] binaryContent = "The Truth Is Out There".getBytes();
    assertThat(xFile).hasBinaryContent(binaryContent);
  }

  @Test
  // new in FEST 2.0
  public void stream_assertions_examples() throws Exception {
    assertThat(streamFrom("string")).hasContentEqualTo(streamFrom("string"));
  }

  // helper methods

  private static ByteArrayInputStream streamFrom(String string) {
    return new ByteArrayInputStream(string.getBytes());
  }

  private File writeFile(String fileName, String fileContent) throws Exception {
    File file = new File("target/" + fileName);
    BufferedWriter out = new BufferedWriter(new FileWriter(file));
    out.write(fileContent);
    out.close();
    return file;
  }

}
