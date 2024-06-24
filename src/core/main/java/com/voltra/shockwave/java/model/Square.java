package com.voltra.shockwave.java.model;

public final class Square {

	public static final int MASK = 0x7F;

	public static final int a1 = 0, a2 = 16;
	public static final int b1 = 1, b2 = 17;
	public static final int c1 = 2, c2 = 18;
	public static final int d1 = 3, d2 = 19;
	public static final int e1 = 4, e2 = 20;
	public static final int f1 = 5, f2 = 21;
	public static final int g1 = 6, g2 = 22;
	public static final int h1 = 7, h2 = 23;

	public static final int a3 = 32, a4 = 48;
	public static final int b3 = 33, b4 = 49;
	public static final int c3 = 34, c4 = 50;
	public static final int d3 = 35, d4 = 51;
	public static final int e3 = 36, e4 = 52;
	public static final int f3 = 37, f4 = 53;
	public static final int g3 = 38, g4 = 54;
	public static final int h3 = 39, h4 = 55;

	public static final int a5 = 64, a6 = 80;
	public static final int b5 = 65, b6 = 81;
	public static final int c5 = 66, c6 = 82;
	public static final int d5 = 67, d6 = 83;
	public static final int e5 = 68, e6 = 84;
	public static final int f5 = 69, f6 = 85;
	public static final int g5 = 70, g6 = 86;
	public static final int h5 = 71, h6 = 87;

	public static final int a7 = 96, a8 = 112;
	public static final int b7 = 97, b8 = 113;
	public static final int c7 = 98, c8 = 114;
	public static final int d7 = 99, d8 = 115;
	public static final int e7 = 100, e8 = 116;
	public static final int f7 = 101, f8 = 117;
	public static final int g7 = 102, g8 = 118;
	public static final int h7 = 103, h8 = 119;

	public static final int NOSQUARE = 127;

	public static final int VALUES_LENGTH = 128;
	public static final int[] values = {
		a1, b1, c1, d1, e1, f1, g1, h1,
		a2, b2, c2, d2, e2, f2, g2, h2,
		a3, b3, c3, d3, e3, f3, g3, h3,
		a4, b4, c4, d4, e4, f4, g4, h4,
		a5, b5, c5, d5, e5, f5, g5, h5,
		a6, b6, c6, d6, e6, f6, g6, h6,
		a7, b7, c7, d7, e7, f7, g7, h7,
		a8, b8, c8, d8, e8, f8, g8, h8
	};

	// These are our move directions
	// N = north, E = east, S = south, W = west
	public static final int N = 16;
	public static final int E = 1;
	public static final int S = -16;
	public static final int W = -1;
	public static final int NE = N + E;
	public static final int SE = S + E;
	public static final int SW = S + W;
	public static final int NW = N + W;

	public static final int[][] pawnDirections = {
		{N, NE, NW}, // Color.WHITE
		{S, SE, SW}  // Color.BLACK
	};
	public static final int[] knightDirections = {
		N + N + E,
		N + N + W,
		N + E + E,
		N + W + W,
		S + S + E,
		S + S + W,
		S + E + E,
		S + W + W
	};
	public static final int[] bishopDirections = {
		NE, NW, SE, SW
	};
	public static final int[] rookDirections = {
		N, E, S, W
	};
	public static final int[] queenDirections = {
		N, E, S, W,
		NE, NW, SE, SW
	};
	public static final int[] kingDirections = {
		N, E, S, W,
		NE, NW, SE, SW
	};

	private Square() {
	}

	public static boolean isValid(int square) {
		return (square & 0x88) == 0;
	}

	public static int valueOf(int file, int rank) {
		return (rank << 4) + file;
	}

	public static int getFile(int square) {
		return square & 0xF;
	}

	public static int getRank(int square) {
		return square >>> 4;
	}
}
