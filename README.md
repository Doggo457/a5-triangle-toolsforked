# Triangle Compiler - Extended Implementation

Extended Triangle compiler with new features and optimizations.

## Student Information
- **Student:** Callum Challinor
- **Assignment:** CSCU9A5 Triangle Compiler Extensions

## Quick Start

### Build the Project
```bash
./gradlew build
```

### Run the Compiler
```bash
./gradlew :Triangle.Compiler:run --args="<full-path-to-program>.tri"
```

### View Help
```bash
./gradlew :Triangle.Compiler:run --args="--help"
```

## Implemented Features

### 1. CLI Parser (picocli)
Professional command-line interface with options:
- `-o, --objectName` - Specify output file
- `--showTree` - Display AST after contextual analysis
- `--folding` - Enable constant folding
- `--showTreeAfter` - Display AST after folding
- `--showStats` - Display program statistics
- `-h, --help` - Show help
- `-V, --version` - Show version

### 2. Double Operator (**)
Syntax: `variable**;` - Doubles the value of a variable (equivalent to `variable := variable * 2`)

### 3. Loop-While-Do Control Structure
Syntax: `loop C1 while E do C2`
- Executes C1 at least once
- Then evaluates condition E
- If true, executes C2 and loops back to C1

### 4. Curly Brackets Support
Use `{` and `}` as alternatives to `begin` and `end`

### 5. Statistics Visitor
Counts CharacterExpression and IntegerExpression nodes in the AST.
Use with `--showStats` option.

### 6. Curly Bracket Support
Allows using `{` `}` instead of `begin` `end`

## Test Programs

Located in `programs/` directory:
- `double.tri` - Tests double operator
- `while-curly.tri` - Tests curly brackets
- `loopwhile.tri` - Tests loop-while-do structure

## Documentation

Complete documentation is in the `documentation/` folder:
- `ASSIGNMENT_REPORT.md` - Full assignment report (submit as PDF)
- `IMPLEMENTATION_STATUS.md` - Implementation details and status
- `QUICK_START_GUIDE.md` - Quick reference guide

## Project Structure

```
a5-triangle-tools/
├── documentation/           # All documentation files
├── programs/               # Test programs
├── Triangle.Compiler/      # Compiler source code
├── Triangle.AbstractMachine/  # TAM implementation
├── Triangle.AbstractMachine.Disassembler/
├── Triangle.AbstractMachine.Interpreter/
├── build.gradle           # Build configuration
└── README.md             # This file
```

## Building and Testing

```bash
# Clean build
./gradlew clean build

# Test double operator
./gradlew :Triangle.Compiler:run --args="$(pwd)/programs/double.tri"

# Test with statistics
./gradlew :Triangle.Compiler:run --args="$(pwd)/programs/double.tri --showStats"

# Test loop-while-do
./gradlew :Triangle.Compiler:run --args="$(pwd)/programs/loopwhile.tri"

# Test curly brackets
./gradlew :Triangle.Compiler:run --args="$(pwd)/programs/while-curly.tri"
```

## License

Educational use only. See LICENSE file for details.
