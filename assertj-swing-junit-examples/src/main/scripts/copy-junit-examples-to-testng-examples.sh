#!/bin/bash

rsync -av --delete ../../test/java/org/assertj/swing/junit/examples/ ../../../../assertj-swing-testng-examples/src/test/java/org/assertj/swing/testng/examples

for example in `find ../../../../assertj-swing-testng-examples/src/test/java/org/assertj/swing/testng/examples -type f -name *.java`
do
  echo "Adapting ${example}"
  # the packages
  perl -i -p -e 'undef $/; s/org\.assertj\.swing\.junit/org.assertj.swing.testng/g' "${example}"

  # the base test classes
  perl -i -p -e 'undef $/; s/SwingJUnitExamples/SwingTestNGExamples/g' "${example}"
  perl -i -p -e 'undef $/; s/AssertJSwingJUnitTestCase/AssertJSwingTestngTestCase/g' "${example}"

  # the annotations imports
  perl -i -p -e 'undef $/; s/org\.junit\.After;/org.testng.annotations.AfterMethod;/g' "${example}"
  perl -i -p -e 'undef $/; s/org\.junit\.Before;/org.testng.annotations.BeforeMethod;/g' "${example}"
  perl -i -p -e 'undef $/; s/org\.junit\./org.testng.annotations./g' "${example}"

  # the annotations
  perl -i -p -e 'undef $/; s/\@After([^C])/\@AfterMethod\1/g' "${example}"
  perl -i -p -e 'undef $/; s/\@Before([^C])/\@BeforeMethod\1/g' "${example}"
done
