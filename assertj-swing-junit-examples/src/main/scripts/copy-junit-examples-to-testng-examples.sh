#!/bin/bash

rsync -av ../../test/java/org/assertj/swing/junit/examples/ ../../../../assertj-swing-testng-examples/src/test/java/org/assertj/swing/testng/examples

for example in `find ../../../../assertj-swing-testng-examples/src/test/java/org/assertj/swing/testng/examples -type f -name *.java`
do
  echo "Adapting ${example}"
  perl -i -p -e 'undef $/; s/org\.assertj\.swing\.junit/org.assertj.swing.testng/g' "${example}"
  perl -i -p -e 'undef $/; s/SwingJUnitExamples/SwingTestNGExamples/g' "${example}"
  perl -i -p -e 'undef $/; s/org\.junit\.Test/org.testng.annotations.Test/g' "${example}"

done
