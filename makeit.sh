#!/bin/sh

rm *.class
javacc ParserL0.jj
javac *.java
