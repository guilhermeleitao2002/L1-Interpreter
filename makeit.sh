#!/bin/sh

rm *.class 2> /dev/null
javacc ParserL0.jj && \
javac *.java && \

# Added to run the tests after compilation
./run_tests.sh 

