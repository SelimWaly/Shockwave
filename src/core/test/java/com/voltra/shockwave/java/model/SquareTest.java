package com.voltra.shockwave.java.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SquareTest {

	@Test
	void testValues() {
		for (int rank : Rank.values) {
			for (int file : File.values) {
				int square = Square.valueOf(file, rank);

				assertThat(Square.getFile(square)).isEqualTo(file);
				assertThat(Square.getRank(square)).isEqualTo(rank);
			}
		}
	}
}
