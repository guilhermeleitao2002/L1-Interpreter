#!/bin/sh

rm *.class
javacc ParserL0.jj
javac *.java

# Added this to run the tests
./run_tests.sh
rm *.class