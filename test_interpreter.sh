#!/bin/bash

# Compile the interpreter
./makeit.sh

# Check if compilation was successful
if [ $? -ne 0 ]; then
    echo "Compilation failed. Aborting tests."
    exit 1
fi

echo "=== Running arithmetic test ==="
java L0int tests/test_arithmetic.l0 > tests/test_arithmetic.out

echo -e "=== Running boolean test ==="
java L0int tests/test_boolean.l0 > tests/test_boolean.out

# echo -e "\n=== Running nested let test ==="
# java L0int tests/test_nested.l0 > tests/test_nested.out

echo -e "=== Running step 1 lab example ==="
java L0int tests/test_example_lab_step_1.l0 > tests/test_example_lab_step_1.out