package org.fest.assertions.examples;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.contentOf;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

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
    assertThat(xFile).hasContentEqualTo(xFileClone);

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
