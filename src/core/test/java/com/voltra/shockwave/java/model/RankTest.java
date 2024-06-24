package com.voltra.shockwave.java.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RankTest {

	@Test
	void testValues() {
		for (int rank : Rank.values) {
			assertThat(Rank.values[rank]).isEqualTo(rank);
		}
	}
}
