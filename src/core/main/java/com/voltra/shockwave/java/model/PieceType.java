package com.voltra.shockwave.java.model;

public final class PieceType {

	public static final int MASK = 0x7;

	public static final int PAWN = 0;
	public static final int KNIGHT = 1;
	public static final int BISHOP = 2;
	public static final int ROOK = 3;
	public static final int QUEEN = 4;
	public static final int KING = 5;

	public static final int NOPIECETYPE = 6;

	public static final int[] values = {
		PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING
	};

	// Piece values as defined by Larry Kaufman
	public static final int PAWN_VALUE = 100;
	public static final int KNIGHT_VALUE = 325;
	public static final int BISHOP_VALUE = 325;
	public static final int ROOK_VALUE = 500;
	public static final int QUEEN_VALUE = 975;
	public static final int KING_VALUE = 20000;

	private PieceType() {
	}

	public static boolean isValidPromotion(int piecetype) {
		switch (piecetype) {
			case KNIGHT:
			case BISHOP:
			case ROOK:
			case QUEEN:
				return true;
			default:
				return false;
		}
	}

	public static boolean isSliding(int piecetype) {
		switch (piecetype) {
			case BISHOP:
			case ROOK:
			case QUEEN:
				return true;
			case PAWN:
			case KNIGHT:
			case KING:
				return false;
			default:
				throw new IllegalArgumentException();
		}
	}

	public static int getValue(int piecetype) {
		switch (piecetype) {
			case PAWN:
				return PAWN_VALUE;
			case KNIGHT:
				return KNIGHT_VALUE;
			case BISHOP:
				return BISHOP_VALUE;
			case ROOK:
				return ROOK_VALUE;
			case QUEEN:
				return QUEEN_VALUE;
			case KING:
				return KING_VALUE;
			default:
				throw new IllegalArgumentException();
		}
	}
}
