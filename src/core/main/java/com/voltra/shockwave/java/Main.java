package com.voltra.shockwave.java;

public final class Main {

	public static void main(String[] args) {
		if (args.length == 0) {
			new Shockwave().run();
		} else if (args.length == 1 && "perft".equalsIgnoreCase(args[0])) {
			new Perft().run();
		} else {
			printUsage();
			System.exit(1);
		}
	}

	private static void printUsage() {
		System.err.println("Usage: src [perft]");
	}
}
