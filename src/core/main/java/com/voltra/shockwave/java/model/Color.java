package com.voltra.shockwave.java.model;

public final class Color {

	public static final int WHITE = 0;
	public static final int BLACK = 1;

	public static final int NOCOLOR = 2;

	public static final int[] values = {
		WHITE, BLACK
	};

	private Color() {
	}

	public static int opposite(int color) {
		switch (color) {
			case WHITE:
				return BLACK;
			case BLACK:
				return WHITE;
			default:
				throw new IllegalArgumentException();
		}
	}
}
