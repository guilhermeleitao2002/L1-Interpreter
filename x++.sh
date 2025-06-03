rm *.class 2> /dev/null
javacc ParserL0.jj && \
javac *.java && \

# If an argument exists, run the L0int program with that argument
# otherwise, run it without arguments
if [ "$1" != "" ]; then
  java L0int < $1
else
  java L0int
fi