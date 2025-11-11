# Triangle Compiler Assignment Documentation

This folder contains all documentation for the Triangle Compiler assignment implementation.

## Files in This Folder

1. **ASSIGNMENT_REPORT.md** - Main assignment report covering all 8 tasks
   - Contains comprehensive analysis and implementation details

2. **IMPLEMENTATION_STATUS.md** - Detailed implementation status and testing results
   - Shows what's completed and what remains
   - Includes build commands and test results

## What Was Implemented

### Fully Implemented
- [x] Task 1: Clean Code Analysis
- [x] Task 2: CLI Parser with picocli
- [x] Task 3: Double Operator (**)
- [x] Task 4: Loop-While-Do Control Structure
- [x] Task 5: Curly Brackets Support
- [x] Task 6: Statistics Visitor

### Analysis (2/8 tasks)
- Task 7: Boolean Constant Folding
- Task 8: Hoisting Optimization

## Quick Build & Test

From the project root directory:

```bash
# Build the project
./gradlew build

# Test implementations
./gradlew :Triangle.Compiler:run --args="<full-path>/programs/double.tri"
./gradlew :Triangle.Compiler:run --args="<full-path>/programs/while-curly.tri"
./gradlew :Triangle.Compiler:run --args="<full-path>/programs/loopwhile.tri"

# Show statistics
./gradlew :Triangle.Compiler:run --args="<full-path>/programs/double.tri --showStats"

# Show help
./gradlew :Triangle.Compiler:run --args="--help"
```

