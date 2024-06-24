package com.voltra.shockwave.java.model;

import static com.voltra.shockwave.java.model.Color.BLACK;
import static com.voltra.shockwave.java.model.Color.WHITE;

public final class Piece {

	public static final int MASK = 0x1F;

	public static final int WHITE_PAWN = 0;
	public static final int WHITE_KNIGHT = 1;
	public static final int WHITE_BISHOP = 2;
	public static final int WHITE_ROOK = 3;
	public static final int WHITE_QUEEN = 4;
	public static final int WHITE_KING = 5;
	public static final int BLACK_PAWN = 6;
	public static final int BLACK_KNIGHT = 7;
	public static final int BLACK_BISHOP = 8;
	public static final int BLACK_ROOK = 9;
	public static final int BLACK_QUEEN = 10;
	public static final int BLACK_KING = 11;

	public static final int NOPIECE = 12;

	public static final int[] values = {
		WHITE_PAWN, WHITE_KNIGHT, WHITE_BISHOP, WHITE_ROOK, WHITE_QUEEN, WHITE_KING,
		BLACK_PAWN, BLACK_KNIGHT, BLACK_BISHOP, BLACK_ROOK, BLACK_QUEEN, BLACK_KING
	};

	private Piece() {
	}

	public static boolean isValid(int piece) {
		switch (piece) {
			case WHITE_PAWN:
			case WHITE_KNIGHT:
			case WHITE_BISHOP:
			case WHITE_ROOK:
			case WHITE_QUEEN:
			case WHITE_KING:
			case BLACK_PAWN:
			case BLACK_KNIGHT:
			case BLACK_BISHOP:
			case BLACK_ROOK:
			case BLACK_QUEEN:
			case BLACK_KING:
				return true;
			default:
				return false;
		}
	}

	public static int valueOf(int color, int piecetype) {
		switch (color) {
			case WHITE:
				switch (piecetype) {
					case PieceType.PAWN:
						return WHITE_PAWN;
					case PieceType.KNIGHT:
						return WHITE_KNIGHT;
					case PieceType.BISHOP:
						return WHITE_BISHOP;
					case PieceType.ROOK:
						return WHITE_ROOK;
					case PieceType.QUEEN:
						return WHITE_QUEEN;
					case PieceType.KING:
						return WHITE_KING;
					default:
						throw new IllegalArgumentException();
				}
			case BLACK:
				switch (piecetype) {
					case PieceType.PAWN:
						return BLACK_PAWN;
					case PieceType.KNIGHT:
						return BLACK_KNIGHT;
					case PieceType.BISHOP:
						return BLACK_BISHOP;
					case PieceType.ROOK:
						return BLACK_ROOK;
					case PieceType.QUEEN:
						return BLACK_QUEEN;
					case PieceType.KING:
						return BLACK_KING;
					default:
						throw new IllegalArgumentException();
				}
			default:
				throw new IllegalArgumentException();
		}
	}

	public static int getType(int piece) {
		switch (piece) {
			case WHITE_PAWN:
			case BLACK_PAWN:
				return PieceType.PAWN;
			case WHITE_KNIGHT:
			case BLACK_KNIGHT:
				return PieceType.KNIGHT;
			case WHITE_BISHOP:
			case BLACK_BISHOP:
				return PieceType.BISHOP;
			case WHITE_ROOK:
			case BLACK_ROOK:
				return PieceType.ROOK;
			case WHITE_QUEEN:
			case BLACK_QUEEN:
				return PieceType.QUEEN;
			case WHITE_KING:
			case BLACK_KING:
				return PieceType.KING;
			default:
				throw new IllegalArgumentException();
		}
	}

	public static int getColor(int piece) {
		switch (piece) {
			case WHITE_PAWN:
			case WHITE_KNIGHT:
			case WHITE_BISHOP:
			case WHITE_ROOK:
			case WHITE_QUEEN:
			case WHITE_KING:
				return WHITE;
			case BLACK_PAWN:
			case BLACK_KNIGHT:
			case BLACK_BISHOP:
			case BLACK_ROOK:
			case BLACK_QUEEN:
			case BLACK_KING:
				return BLACK;
			default:
				throw new IllegalArgumentException();
		}
	}
}
