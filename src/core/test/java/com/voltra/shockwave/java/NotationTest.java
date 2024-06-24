package com.voltra.shockwave.java;

import com.voltra.shockwave.java.model.File;
import com.voltra.shockwave.java.model.Piece;
import com.voltra.shockwave.java.model.PieceType;
import com.voltra.shockwave.java.model.Square;
import org.junit.jupiter.api.Test;

import static com.voltra.shockwave.java.model.Castling.BLACK_KINGSIDE;
import static com.voltra.shockwave.java.model.Castling.BLACK_QUEENSIDE;
import static com.voltra.shockwave.java.model.Castling.NOCASTLING;
import static com.voltra.shockwave.java.model.Castling.WHITE_KINGSIDE;
import static com.voltra.shockwave.java.model.Castling.WHITE_QUEENSIDE;
import static com.voltra.shockwave.java.model.Color.BLACK;
import static com.voltra.shockwave.java.model.Color.WHITE;
import static com.voltra.shockwave.java.model.Rank.r2;
import static com.voltra.shockwave.java.model.Rank.r7;
import static com.voltra.shockwave.java.model.Square.NOSQUARE;
import static com.voltra.shockwave.java.model.Square.a1;
import static com.voltra.shockwave.java.model.Square.a8;
import static com.voltra.shockwave.java.model.Square.b1;
import static com.voltra.shockwave.java.model.Square.b8;
import static com.voltra.shockwave.java.model.Square.c1;
import static com.voltra.shockwave.java.model.Square.c8;
import static com.voltra.shockwave.java.model.Square.d1;
import static com.voltra.shockwave.java.model.Square.d8;
import static com.voltra.shockwave.java.model.Square.e1;
import static com.voltra.shockwave.java.model.Square.e8;
import static com.voltra.shockwave.java.model.Square.f1;
import static com.voltra.shockwave.java.model.Square.f8;
import static com.voltra.shockwave.java.model.Square.g1;
import static com.voltra.shockwave.java.model.Square.g8;
import static com.voltra.shockwave.java.model.Square.h1;
import static com.voltra.shockwave.java.model.Square.h8;
import static org.assertj.core.api.Assertions.assertThat;

class NotationTest {

	@Test
	void testStandardPosition() {
		Position position = Notation.toPosition(Notation.STANDARDPOSITION);

		// Test pawns
		for (int file : File.values) {
			assertThat(position.board[Square.valueOf(file, r2)]).isEqualTo(Piece.WHITE_PAWN);
			assertThat(position.board[Square.valueOf(file, r7)]).isEqualTo(Piece.BLACK_PAWN);
		}

		// Test knights
		assertThat(position.board[b1]).isEqualTo(Piece.WHITE_KNIGHT);
		assertThat(position.board[g1]).isEqualTo(Piece.WHITE_KNIGHT);
		assertThat(position.board[b8]).isEqualTo(Piece.BLACK_KNIGHT);
		assertThat(position.board[g8]).isEqualTo(Piece.BLACK_KNIGHT);

		// Test bishops
		assertThat(position.board[c1]).isEqualTo(Piece.WHITE_BISHOP);
		assertThat(position.board[f1]).isEqualTo(Piece.WHITE_BISHOP);
		assertThat(position.board[c8]).isEqualTo(Piece.BLACK_BISHOP);
		assertThat(position.board[f8]).isEqualTo(Piece.BLACK_BISHOP);

		// Test rooks
		assertThat(position.board[a1]).isEqualTo(Piece.WHITE_ROOK);
		assertThat(position.board[h1]).isEqualTo(Piece.WHITE_ROOK);
		assertThat(position.board[a8]).isEqualTo(Piece.BLACK_ROOK);
		assertThat(position.board[h8]).isEqualTo(Piece.BLACK_ROOK);

		// Test queens
		assertThat(position.board[d1]).isEqualTo(Piece.WHITE_QUEEN);
		assertThat(position.board[d8]).isEqualTo(Piece.BLACK_QUEEN);

		// Test kings
		assertThat(position.board[e1]).isEqualTo(Piece.WHITE_KING);
		assertThat(position.board[e8]).isEqualTo(Piece.BLACK_KING);

		assertThat(position.material[WHITE]).isEqualTo((8 * PieceType.PAWN_VALUE)
			+ (2 * PieceType.KNIGHT_VALUE)
			+ (2 * PieceType.BISHOP_VALUE)
			+ (2 * PieceType.ROOK_VALUE)
			+ PieceType.QUEEN_VALUE
			+ PieceType.KING_VALUE);
		assertThat(position.material[BLACK]).isEqualTo((8 * PieceType.PAWN_VALUE)
			+ (2 * PieceType.KNIGHT_VALUE)
			+ (2 * PieceType.BISHOP_VALUE)
			+ (2 * PieceType.ROOK_VALUE)
			+ PieceType.QUEEN_VALUE
			+ PieceType.KING_VALUE);

		// Test castling
		assertThat(position.castlingRights & WHITE_KINGSIDE).isNotEqualTo(NOCASTLING);
		assertThat(position.castlingRights & WHITE_QUEENSIDE).isNotEqualTo(NOCASTLING);
		assertThat(position.castlingRights & BLACK_KINGSIDE).isNotEqualTo(NOCASTLING);
		assertThat(position.castlingRights & BLACK_QUEENSIDE).isNotEqualTo(NOCASTLING);

		// Test en passant
		assertThat(position.enPassantSquare).isEqualTo(NOSQUARE);

		// Test active color
		assertThat(position.activeColor).isEqualTo(WHITE);

		// Test half move clock
		assertThat(position.halfmoveClock).isEqualTo(0);

		// Test full move number
		assertThat(position.getFullmoveNumber()).isEqualTo(1);
	}
}
