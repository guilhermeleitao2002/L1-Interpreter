#!/bin/bash

# Color definitions
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Loop through tests 1 to 12
for test_num in {1..12}; do
    test_file="tests/test${test_num}.l0"
    expected_file="tests/test${test_num}.out"
    
    # Run the test and capture output
    output=$(java L0int < "$test_file" 2>&1)
    
    # Remove the first two lines (interpreter header and blank line)
    output=$(echo "$output" | tail -n +3)
    
    expected=$(cat "$expected_file")
    
    # Compare output
    if [ "$output" = "$expected" ]; then
        echo -e "${GREEN}[+] Test $test_num passed successfully!${NC}"
    else
        echo -e "${RED}[-] Test $test_num failed!${NC}"
        echo "Expected:"
        echo "$expected"
        echo "Got:"
        echo "$output"
    fi
done
