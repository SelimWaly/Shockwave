package com.voltra.shockwave.java;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoveListTest {

	@Test
	void test() {
		MoveList<MoveList.MoveEntry> moveList = new MoveList<>(MoveList.MoveEntry.class);

		assertThat(moveList.size).isEqualTo(0);

		moveList.entries[moveList.size++].move = 1;
		assertThat(moveList.size).isEqualTo(1);
	}
}
