/*
 * @(#)Compiler.java
 *
 * Revisions and updates (c) 2022-2025 Sandy Brownlee. alexander.brownlee@stir.ac.uk
 *
 * Original release:
 *
 * Copyright (C) 1999, 2003 D.A. Watt and D.F. Brown
 * Dept. of Computing Science, University of Glasgow, Glasgow G12 8QQ Scotland
 * and School of Computer and Math Sciences, The Robert Gordon University,
 * St. Andrew Street, Aberdeen AB25 1HG, Scotland.
 * All rights reserved.
 *
 * This software is provided free for educational use only. It may
 * not be used for commercial purposes without the prior written permission
 * of the authors.
 */

package triangle;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import triangle.abstractSyntaxTrees.Program;
import triangle.codeGenerator.Emitter;
import triangle.codeGenerator.Encoder;
import triangle.contextualAnalyzer.Checker;
import triangle.optimiser.ConstantFolder;
import triangle.optimiser.StatisticsVisitor;
import triangle.syntacticAnalyzer.Parser;
import triangle.syntacticAnalyzer.Scanner;
import triangle.syntacticAnalyzer.SourceFile;
import triangle.treeDrawer.Drawer;

/**
 * The main driver class for the Triangle compiler.
 */
@Command(name = "tc",
         mixinStandardHelpOptions = true,
         version = "Triangle Compiler 2.1",
         description = "Compiles Triangle source programs to TAM object code")
public class Compiler implements Runnable {

	@Parameters(index = "0", description = "The Triangle source file to compile")
	private String sourceName;

	@Option(names = {"-o", "--objectName"},
	        description = "Output object file name (default: obj.tam)",
	        defaultValue = "obj.tam")
	private String objectName;

	@Option(names = {"--showTree"},
	        description = "Display the AST after contextual analysis")
	private boolean showTree;

	@Option(names = {"--folding"},
	        description = "Enable constant folding optimization")
	private boolean folding;

	@Option(names = {"--showTreeAfter"},
	        description = "Display the AST after folding is complete")
	private boolean showTreeAfter;

	@Option(names = {"--showStats"},
	        description = "Display program statistics (expression counts)")
	private boolean showStats;

	private static Scanner scanner;
	private static Parser parser;
	private static Checker checker;
	private static Encoder encoder;
	private static Emitter emitter;
	private static ErrorReporter reporter;
	private static Drawer drawer;

	/** The AST representing the source program. */
	private static Program theAST;

	/**
	 * Compile the source program to TAM machine code.
	 *
	 * @param sourceName   the name of the file containing the source program.
	 * @param objectName   the name of the file containing the object program.
	 * @param showingAST   true iff the AST is to be displayed after contextual
	 *                     analysis
	 * @param showingTable true iff the object description details are to be
	 *                     displayed during code generation (not currently
	 *                     implemented).
	 * @param enableFolding true iff constant folding optimization should be enabled
	 * @param showingASTAfterFolding true iff the AST is to be displayed after
	 *                     constant folding
	 * @param showingStatistics true iff program statistics should be displayed
	 * @return true iff the source program is free of compile-time errors, otherwise
	 *         false.
	 */
	static boolean compileProgram(String sourceName, String objectName, boolean showingAST,
	                              boolean showingTable, boolean enableFolding, boolean showingASTAfterFolding,
	                              boolean showingStatistics) {

		System.out.println("********** " + "Triangle Compiler (Java Version 2.1)" + " **********");

		System.out.println("Syntactic Analysis ...");
		SourceFile source = SourceFile.ofPath(sourceName);

		if (source == null) {
			System.out.println("Can't access source file " + sourceName);
			System.exit(1);
		}

		scanner = new Scanner(source);
		reporter = new ErrorReporter(false);
		parser = new Parser(scanner, reporter);
		checker = new Checker(reporter);
		emitter = new Emitter(reporter);
		encoder = new Encoder(emitter, reporter);
		drawer = new Drawer();

		// scanner.enableDebugging();
		theAST = parser.parseProgram(); // 1st pass
		if (reporter.getNumErrors() == 0) {
			// if (showingAST) {
			// drawer.draw(theAST);
			// }
			System.out.println("Contextual Analysis ...");
			checker.check(theAST); // 2nd pass
			if (showingAST) {
				drawer.draw(theAST);
			}
			if (showingStatistics) {
				StatisticsVisitor stats = new StatisticsVisitor();
				stats.visitProgram(theAST);
				stats.printStatistics();
			}
			if (enableFolding) {
				theAST.visit(new ConstantFolder());
				if (showingASTAfterFolding) {
					drawer.draw(theAST);
				}
			}

			if (reporter.getNumErrors() == 0) {
				System.out.println("Code Generation ...");
				encoder.encodeRun(theAST, showingTable); // 3rd pass
			}
		}

		boolean successful = (reporter.getNumErrors() == 0);
		if (successful) {
			emitter.saveObjectProgram(objectName);
			System.out.println("Compilation was successful.");
		} else {
			System.out.println("Compilation was unsuccessful.");
		}
		return successful;
	}

	/**
	 * Triangle compiler main program.
	 *
	 * @param args command-line arguments parsed by picocli
	 */
	public static void main(String[] args) {
		int exitCode = new CommandLine(new Compiler()).execute(args);
		System.exit(exitCode);
	}

	@Override
	public void run() {
		var compiledOK = compileProgram(sourceName, objectName, showTree, false, folding, showTreeAfter, showStats);

		if (!compiledOK) {
			throw new RuntimeException("Compilation failed");
		}
	}
}
