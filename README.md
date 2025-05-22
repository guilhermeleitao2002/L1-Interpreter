# L1++ Interpreter

An interpreter for the L1++ functional-imperative programming language, implemented in Java using JavaCC for parsing.

## Language Features

L1++ extends functional programming with imperative features:
- **Core**: integers, booleans, arithmetic/logical operations
- **Functions**: higher-order functions with currying support  
- **Lists**: strict lists (`::`) and lazy lists (`?:`) with pattern matching
- **State**: mutable references via `box`, dereference (`!`), assignment (`:=`)
- **Control**: `if`/`else`, `while` loops, `let` bindings
- **I/O**: `print` and `println` statements

## Building and Running

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
- **20 functionality tests** (`tests/test*.l0`) - core language features
- **16 validation tests** (`validation_tests/test*.l0`) - syntactic errors and dynamic typecheck

Tests compare actual interpreter output against expected results in `.out` files.

This was used to help in the development and debugging of the interpreter.

## Usage

Run the interpreter interactively:
```bash
java L0int
```

Or with a file:
```bash
java L0int < program.l0
```

## Implementation

Built using big-step environment-Store semantics with:
- AST nodes for each language construct
- Environment-based variable binding
- Dynamic type checking with comprehensive error messages