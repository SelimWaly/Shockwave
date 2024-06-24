package com.voltra.shockwave.java.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CastlingTypeTest {

	@Test
	void testValues() {
		for (int castlingtype : CastlingType.values) {
			assertThat(CastlingType.values[castlingtype]).isEqualTo(castlingtype);
		}
	}
}
