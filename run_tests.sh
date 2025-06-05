#!/bin/bash

# Color definitions
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Loop through functionality tests
for test_num in {1..34}; do
    test_file="tests/functionality/test${test_num}.l0"
    expected_file="tests/functionality/output/test${test_num}.out"

    # Run the test and capture all output (stdout and stderr)
    output=$(java L0int < "$test_file" 2>&1)
    
    # Get the exit code
    exit_code=$?
    
    # Remove the first two lines (interpreter header and blank line) if they exist
    # but keep error output intact
    if [[ $exit_code -eq 0 ]]; then
        # Only trim interpreter header for successful runs
        trimmed_output=$(echo "$output" | tail -n +3)
    else
        # For errors, keep the full output
        trimmed_output="$output"
    fi
    
    expected=$(cat "$expected_file")
    
    # Compare output
    if [[ "$trimmed_output" == "$expected" ]]; then
        echo -e "${GREEN}[+] Functionality Test $test_num passed successfully!${NC}"
    else
        echo -e "${RED}[-] Functionality Test $test_num failed!${NC}"
        echo -e "${RED}------ Expected output: ------${NC}"
        echo "$expected"
        echo -e "${RED}------ Actual output: ------${NC}"
        echo "$trimmed_output"
        echo -e "${RED}-----------------------------${NC}"
    fi
done

# Do the same for the syntactic error tests
for test_num in {1..8}; do
    test_file="tests/syntax/test${test_num}.l0"
    expected_file="tests/syntax/output/test${test_num}.out"

    # Run the test and capture all output (stdout and stderr)
    output=$(java L0int < "$test_file" 2>&1)
    
    # Get the exit code
    exit_code=$?
    
    # Remove the first two lines (interpreter header and blank line) if they exist
    # but keep error output intact
    if [[ $exit_code -eq 0 ]]; then
        # Only trim interpreter header for successful runs
        trimmed_output=$(echo "$output" | tail -n +3)
    else
        # For errors, keep the full output
        trimmed_output="$output"
    fi
    
    expected=$(cat "$expected_file")
    
    # Compare only first 3 lines of both outputs
    trimmed_output_first_3=$(echo "$trimmed_output" | head -n 3)
    expected_first_3=$(echo "$expected" | head -n 3)
    if [[ "$trimmed_output_first_3" == "$expected_first_3" ]]; then
        echo -e "${GREEN}[+] Syntactic Test $test_num passed successfully!${NC}"
    else
        echo -e "${RED}[-] Syntactic Test $test_num failed!${NC}"
        echo -e "${RED}------ Expected output: ------${NC}"
        echo "$expected"
        echo -e "${RED}------ Actual output: ------${NC}"
        echo "$trimmed_output"
        echo -e "${RED}-----------------------------${NC}"
    fi
done

# Do the same for the dynamic typechecking tests
for test_num in {1..11}; do
    test_file="tests/typecheck/test${test_num}.l0"
    expected_file="tests/typecheck/output/test${test_num}.out"

    # Run the test and capture all output (stdout and stderr)
    output=$(java L0int < "$test_file" 2>&1)
    
    # Get the exit code
    exit_code=$?
    
    # Remove the first two lines (interpreter header and blank line) if they exist
    # but keep error output intact
    if [[ $exit_code -eq 0 ]]; then
        # Only trim interpreter header for successful runs
        trimmed_output=$(echo "$output" | tail -n +3)
    else
        # For errors, keep the full output
        trimmed_output="$output"
    fi
    
    expected=$(cat "$expected_file")
    
    # Compare only first 3 lines of both outputs
    trimmed_output_first_3=$(echo "$trimmed_output" | head -n 3)
    expected_first_3=$(echo "$expected" | head -n 3)
    if [[ "$trimmed_output_first_3" == "$expected_first_3" ]]; then
        echo -e "${GREEN}[+] Typecheck Test $test_num passed successfully!${NC}"
    else
        echo -e "${RED}[-] Typecheck Test $test_num failed!${NC}"
        echo -e "${RED}------ Expected output: ------${NC}"
        echo "$expected"
        echo -e "${RED}------ Actual output: ------${NC}"
        echo "$trimmed_output"
        echo -e "${RED}-----------------------------${NC}"
    fi
done