# Triangle Compiler Assignment - Implementation Status

**Date:** 09 November 2025
**Student:** Callum Challinor

---

## Completed Tasks (6 of 8 Fully Implemented)

### Task 1: Clean Code Analysis

**Implementation:**
- Comprehensive analysis in ASSIGNMENT_REPORT.md
- Analyzed "Meaningful Names" (Chapter 2) with examples from Scanner.java
- Analyzed "Functions Should Do One Thing" (Chapter 3) with examples from Parser.java
- Provided concrete improvement suggestions

---

### Task 2: CLI Parser Implementation

**Files Modified:**
- [Triangle.Compiler/build.gradle](Triangle.Compiler/build.gradle) - Added picocli dependency
- [Triangle.Compiler/src/main/java/triangle/Compiler.java](Triangle.Compiler/src/main/java/triangle/Compiler.java) - Implemented picocli annotations

**Features Implemented:**
- `--objectName` / `-o` : Specify output file name
- `--showTree` : Display AST after contextual analysis
- `--folding` : Enable constant folding
- `--showTreeAfter` : Display AST after folding
- `--showStats` : Display program statistics (Task 6 integration)
- `--help` / `-h` : Auto-generated help
- `--version` / `-V` : Version information

**Testing:**
```bash
./gradlew :Triangle.Compiler:run --args="--help"
# Shows professional CLI with all options
```

---

### Task 3: Double Operator (**)

**Files Modified:**
1. [Token.java](Triangle.Compiler/src/main/java/triangle/syntacticAnalyzer/Token.java) - Added `DOUBLESTAR("**")` token
2. [Scanner.java](Triangle.Compiler/src/main/java/triangle/syntacticAnalyzer/Scanner.java) - Special case for `**` before general operator handling
3. [Parser.java](Triangle.Compiler/src/main/java/triangle/syntacticAnalyzer/Parser.java) - Transforms `a**` into `a := a * 2`

**Test Program:**
- [programs/double.tri](programs/double.tri) - Successfully compiles and generates correct TAM code

**Testing:**
```bash
./gradlew :Triangle.Compiler:run --args="programs/double.tri"
# Result: Compilation was successful.
```

---

### Task 4: Loop-While-Do Control Structure

**Files Created:**
- [LoopWhileCommand.java](Triangle.Compiler/src/main/java/triangle/abstractSyntaxTrees/commands/LoopWhileCommand.java) - New AST node

**Files Modified:**
1. [Token.java](Triangle.Compiler/src/main/java/triangle/syntacticAnalyzer/Token.java) - Added `LOOP` keyword
2. [CommandVisitor.java](Triangle.Compiler/src/main/java/triangle/abstractSyntaxTrees/visitors/CommandVisitor.java) - Added `visitLoopWhileCommand` method
3. [Parser.java](Triangle.Compiler/src/main/java/triangle/syntacticAnalyzer/Parser.java) - Added LOOP case to parse syntax
4. [Checker.java](Triangle.Compiler/src/main/java/triangle/contextualAnalyzer/Checker.java) - Contextual analysis visitor
5. [Encoder.java](Triangle.Compiler/src/main/java/triangle/codeGenerator/Encoder.java) - TAM code generation
6. [ConstantFolder.java](Triangle.Compiler/src/main/java/triangle/optimiser/ConstantFolder.java) - Optimization support
7. [StatisticsVisitor.java](Triangle.Compiler/src/main/java/triangle/optimiser/StatisticsVisitor.java) - Statistics support
8. [LayoutVisitor.java](Triangle.Compiler/src/main/java/triangle/treeDrawer/LayoutVisitor.java) - AST drawing support

**Syntax:** `loop C1 while E do C2`

**Test Program:**
- [programs/loopwhile.tri](programs/loopwhile.tri) - Successfully compiles

**Testing:**
```bash
./gradlew :Triangle.Compiler:run --args="programs/loopwhile.tri"
# Result: Compilation was successful.
```

---

### Task 5: Curly Brackets Support

**Files Modified:**
- [Scanner.java](Triangle.Compiler/src/main/java/triangle/syntacticAnalyzer/Scanner.java:235-241)
  - Changed `LCURLY` to return `BEGIN`
  - Changed `RCURLY` to return `END`

**Test Program:**
- [programs/while-curly.tri](programs/while-curly.tri) - Uses `{` and `}` instead of `begin` and `end`

**Testing:**
```bash
./gradlew :Triangle.Compiler:run --args="programs/while-curly.tri"
# Result: Compilation was successful.
```

---

### Task 6: Statistics Visitor

**Files Created:**
- [StatisticsVisitor.java](Triangle.Compiler/src/main/java/triangle/optimiser/StatisticsVisitor.java)

**Files Modified:**
- [Compiler.java](Triangle.Compiler/src/main/java/triangle/Compiler.java) - Added `--showStats` option and integration

**Features:**
- Counts CharacterExpression and IntegerExpression nodes
- Implements all visitor interfaces
- Traverses entire AST recursively
- Displays formatted statistics output

**Testing:**
```bash
./gradlew :Triangle.Compiler:run --args="programs/double.tri --showStats"
# Output:
# === Program Statistics ===
# Character Expressions: 0
# Integer Expressions: 2
# =========================
```

```bash
./gradlew :Triangle.Compiler:run --args="programs/while-curly.tri --showStats"
# Output:
# === Program Statistics ===
# Character Expressions: 0
# Integer Expressions: 3
# =========================
```

---

## Tasks Completed with Design/Analysis

---

## Summary Statistics

| Task | Status | Implementation |
|------|--------|----------------|
| Task 1 | Complete | Full analysis in report |
| Task 2 | Complete | Fully working picocli integration |
| Task 3 | Complete | Double operator working |
| Task 4 | Complete | Loop-while-do working |
| Task 5 | Complete | Curly brackets working |
| Task 6 | Complete | Statistics visitor working |

---

## Test Programs Created

1. [programs/while-curly.tri](programs/while-curly.tri) - Tests curly bracket support
2. [programs/double.tri](programs/double.tri) - Tests double operator `x**;`
3. [programs/loopwhile.tri](programs/loopwhile.tri) - Tests loop-while-do structure

All test programs compile successfully and generate correct TAM code.

---

## Build & Test Commands

### Build Project
```bash
cd c:/Users/callum/Desktop/Randomscripts/a5-triangle-tools
./gradlew build
```

### Run Compiler with Help
```bash
./gradlew :Triangle.Compiler:run --args="--help"
```

### Compile a Program
```bash
./gradlew :Triangle.Compiler:run --args="programs/<program>.tri"
```

### Compile with Options
```bash
./gradlew :Triangle.Compiler:run --args="programs/double.tri --showTree --folding --showStats"
```

---

## Technical Achievements

### Code Quality
- All code follows Clean Code principles from Task 1
- Professional error handling
- Comprehensive visitor pattern implementation
- Proper AST node design
- TAM code generation following existing patterns

### Testing
- All implemented features tested and working
- Multiple test programs created
- Build passes with no errors
- All gradle tests pass

### Documentation
- Comprehensive ASSIGNMENT_REPORT.md
- Detailed implementation status tracking
- Clear code comments
- Professional CLI help output

