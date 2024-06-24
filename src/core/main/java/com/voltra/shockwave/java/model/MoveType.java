package com.voltra.shockwave.java.model;

public final class MoveType {

	public static final int MASK = 0x7;

	public static final int NORMAL = 0;
	public static final int PAWNDOUBLE = 1;
	public static final int PAWNPROMOTION = 2;
	public static final int ENPASSANT = 3;
	public static final int CASTLING = 4;

	public static final int NOMOVETYPE = 5;

	private MoveType() {
	}
}
