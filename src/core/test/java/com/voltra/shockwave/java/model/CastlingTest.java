package com.voltra.shockwave.java.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CastlingTest {

	@Test
	void testValueOf() {
		assertThat(Castling.valueOf(Color.WHITE, CastlingType.KINGSIDE)).isEqualTo(Castling.WHITE_KINGSIDE);
		assertThat(Castling.valueOf(Color.WHITE, CastlingType.QUEENSIDE)).isEqualTo(Castling.WHITE_QUEENSIDE);
		assertThat(Castling.valueOf(Color.BLACK, CastlingType.KINGSIDE)).isEqualTo(Castling.BLACK_KINGSIDE);
		assertThat(Castling.valueOf(Color.BLACK, CastlingType.QUEENSIDE)).isEqualTo(Castling.BLACK_QUEENSIDE);
	}
}
