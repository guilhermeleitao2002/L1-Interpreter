# LX++ Interpreter

An interpreter for the LX++ functional-imperative programming language, implemented in Java using JavaCC for parsing.

## Building and Running

```bash
./x++.sh <file_path>
```

This script:
1. Cleans previous builds
2. Generates parser from `ParserL0.jj` using JavaCC
3. Compiles the specified Java source files
4. If `file_path` is provided, runs the specified file with the interpreter
5. If no file is specified, runs the interpreter interactively through the standard input

OR

```bash
./makeit.sh
```

This script:
1. Cleans previous builds
2. Generates parser from `ParserL0.jj` using JavaCC
3. Compiles all Java source files
4. Runs the complete test suite

## Testing

The test suite includes:
- **35 functionality tests** (`tests/functionality/test*.l0`) - core language features
- **8 syntactic tests** (`tests/syntax/test*.l0`) - syntactic errors
- **11 typechecking tests** (`tests/typecheck/test*.l0`) - type errors

Tests compare actual interpreter output against expected results in `.out` files.

This was used to help in the development and debugging of the interpreter.