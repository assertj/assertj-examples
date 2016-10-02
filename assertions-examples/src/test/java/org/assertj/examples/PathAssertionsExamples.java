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

import static java.lang.System.lineSeparator;
import static java.nio.file.Files.createDirectory;
import static java.nio.file.Files.createSymbolicLink;
import static java.nio.file.Files.deleteIfExists;
import static java.nio.file.Files.write;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;

import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Path assertions usage examples.
 * 
 * @author Joel Costigliola
 */
public class PathAssertionsExamples extends AbstractAssertionsExamples {

  private Path xFile;
  private Path xFileTurkish;
  private Path xFileFrench;
  private Path xFileClone;
  private Path rwxFile;

  @Override
  @Before
  public void setup() {
	xFile = Paths.get("target/xfile.txt");
	xFileFrench = Paths.get("target/xfile-french.txt");
	xFileTurkish = Paths.get("target/xfile.turkish");
	xFileClone = Paths.get("target/xfile-clone.txt");
	rwxFile = Paths.get("target/rwxFile");
  }

  @Test
  public void path_content_assertions_examples() throws Exception {

	// check paths content

	write(xFile, "The Truth Is Out There".getBytes());
	// The default charset is used
	assertThat(xFile).hasContent("The Truth Is Out There");

	try {
	  assertThat(xFile).hasContent("La Vérité Est Ailleurs");
	} catch (AssertionError e) {
	  logAssertionErrorMessage("path hasContent", e);
	}

	// check content using a specific charset

	Charset turkishCharset = Charset.forName("windows-1254");
	write(xFileTurkish, singleton("Gerçek Başka bir yerde mi"), turkishCharset);
	assertThat(xFileTurkish).usingCharset("windows-1254").hasContent("Gerçek Başka bir yerde mi" + lineSeparator());
	assertThat(xFileTurkish).usingCharset(turkishCharset).hasContent("Gerçek Başka bir yerde mi" + lineSeparator());

	// compare paths content (uses the default charset)

	write(xFileClone, "The Truth Is Out There".getBytes());
	assertThat(xFile).hasSameContentAs(xFileClone);

	write(xFileFrench, "La Vérité Est Ailleurs".getBytes());
	try {
	  assertThat(xFile).hasSameContentAs(xFileFrench);
	} catch (AssertionError e) {
	  logAssertionErrorMessage("path hasSameContentAs", e);
	}
  }

  @Test
  public void path_assertions_binary_content() throws Exception {
	write(xFile, "The Truth Is Out There".getBytes());
	assertThat(xFile).hasBinaryContent("The Truth Is Out There".getBytes());

	// using a specific charset
	Charset turkishCharset = Charset.forName("windows-1254");
	write(xFileTurkish, singleton("Gerçek Başka bir yerde mi"), turkishCharset);

	// The following assertion succeeds:
	String expectedContent = "Gerçek Başka bir yerde mi" + lineSeparator();
	byte[] binaryContent = expectedContent.getBytes(turkishCharset.name());
	assertThat(xFileTurkish).hasBinaryContent(binaryContent);

	// The following assertion fails ... unless you are in Turkey ;-):
	try {
	  assertThat(xFileTurkish).hasBinaryContent("Gerçek Başka bir yerde mi".getBytes());
	} catch (AssertionError e) {
	  logAssertionErrorMessage("path hasBinaryContent with charset ", e);
	}
  }

  @Test
  public void path_rwx_assertion() throws Exception {
	assumeTrue(SystemUtils.IS_OS_UNIX);

	// Create a file and set permissions to be readable by all.
	write(rwxFile, "rwx file".getBytes());

	// using PosixFilePermission to set file permissions 777
	Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
	// add owners permission
	perms.add(PosixFilePermission.OWNER_READ);
	perms.add(PosixFilePermission.OWNER_WRITE);
	perms.add(PosixFilePermission.OWNER_EXECUTE);
	// add group permissions
	perms.add(PosixFilePermission.GROUP_READ);
	perms.add(PosixFilePermission.GROUP_WRITE);
	perms.add(PosixFilePermission.GROUP_EXECUTE);
	// add others permissions
	perms.add(PosixFilePermission.OTHERS_READ);
	perms.add(PosixFilePermission.OTHERS_WRITE);
	perms.add(PosixFilePermission.OTHERS_EXECUTE);

	Files.setPosixFilePermissions(rwxFile, perms);

	final Path symlinkToRwxFile = FileSystems.getDefault().getPath("symlink-to-rwxFile");
	if (!Files.exists(symlinkToRwxFile)) {
	  createSymbolicLink(symlinkToRwxFile, rwxFile);
	}

	// The following assertions succeed:
	assertThat(rwxFile).isReadable()
	                   .isWritable()
	                   .isExecutable();

	assertThat(symlinkToRwxFile).isReadable()
	                            .isWritable()
	                            .isExecutable();
  }

  @Test
  public void path_assertions() throws Exception {
	assumeTrue(SystemUtils.IS_OS_UNIX);

	// Create a regular file, and a symbolic link pointing to it
	final Path existingFile = Paths.get("somefile.txt");
	write(existingFile, "foo".getBytes());
	final Path symlinkToExistingFile = Paths.get("symlink-to-somefile.txt");
	deleteIfExists(symlinkToExistingFile);
	createSymbolicLink(symlinkToExistingFile, existingFile);

	// Create a symbolic link whose target does not exist
	final Path nonExistentPath = Paths.get("nonexistent");
	final Path symlinkToNonExistentPath = Paths.get("symlinkToNonExistentPath");
	deleteIfExists(symlinkToNonExistentPath);
	createSymbolicLink(symlinkToNonExistentPath, nonExistentPath);

	// create directory and symlink to it
	Path dir = Paths.get("target/dir");
	deleteIfExists(dir);
	createDirectory(dir);
	final Path dirSymlink = Paths.get("target", "dirSymlink");
	deleteIfExists(dirSymlink);
	createSymbolicLink(dirSymlink, dir.toAbsolutePath());

	// assertions examples

	assertThat(existingFile).exists();
	assertThat(symlinkToExistingFile).exists();
	assertThat(existingFile).existsNoFollowLinks();
	assertThat(symlinkToNonExistentPath).existsNoFollowLinks();

	assertThat(nonExistentPath).doesNotExist();

	assertThat(existingFile).isRegularFile();
	assertThat(symlinkToExistingFile).isRegularFile();

	assertThat(symlinkToExistingFile).isSymbolicLink();
	assertThat(dirSymlink).isDirectory().isSymbolicLink();
	assertThat(symlinkToNonExistentPath).isSymbolicLink();

	assertThat(dir).isDirectory();
	assertThat(dirSymlink).isDirectory();
	assertThat(dir).hasParent(Paths.get("target"))
	               .hasParent(Paths.get("target/dir/..")) // would fail with hasParentRaw
	               .hasParentRaw(Paths.get("target"));

	assertThat(existingFile.toRealPath()).isCanonical();

	assertThat(existingFile).hasFileName("somefile.txt");
	assertThat(symlinkToExistingFile).hasFileName("symlink-to-somefile.txt");

	assertThat(Paths.get("/foo/bar")).isAbsolute();
	assertThat(Paths.get("foo/bar")).isRelative();
	assertThat(Paths.get("/usr/lib")).isNormalized();
	assertThat(Paths.get("a/b/c")).isNormalized();
	assertThat(Paths.get("../d")).isNormalized();
	assertThat(Paths.get("/")).hasNoParent();
	assertThat(Paths.get("foo")).hasNoParentRaw();
	assertThat(Paths.get("/usr/lib")).startsWith(Paths.get("/usr"))
	                                 .startsWith(Paths.get("/usr/lib/..")) // would fail with startsWithRaw
	                                 .startsWithRaw(Paths.get("/usr"));
	assertThat(Paths.get("/usr/lib")).endsWith(Paths.get("lib"))
	                                 .endsWith(Paths.get("lib/../lib")) // would fail with endsWithRaw
	                                 .endsWithRaw(Paths.get("lib"));

	
	assertThat(Paths.get("abc.txt")).isLessThan(Paths.get("xyz.txt"));
	
	// assertions error examples

	try {
	  assertThat(nonExistentPath).exists();
	} catch (AssertionError e) {
	  logAssertionErrorMessage("path exists", e);
	}
	try {
	  assertThat(nonExistentPath).existsNoFollowLinks();
	} catch (AssertionError e) {
	  logAssertionErrorMessage("path existsNoFollowLinks", e);
	}
	try {
	  assertThat(symlinkToNonExistentPath).exists();
	} catch (AssertionError e) {
	  logAssertionErrorMessage("path exists", e);
	}
	try {
	  assertThat(dir).hasParentRaw(Paths.get("target/dir/.."));
	} catch (AssertionError e) {
	  logAssertionErrorMessage("path hasParentRaw", e);
	}

	try {
	  // fail as symlinkToNonExistentPath exists even if its target does not.
	  assertThat(symlinkToNonExistentPath).doesNotExist();
	} catch (AssertionError e) {
	  logAssertionErrorMessage("path doesNotExist not following links", e);
	}
	try {
	  assertThat(nonExistentPath).exists();
	} catch (AssertionError e) {
	  logAssertionErrorMessage("path exists", e);
	}
  }

}
