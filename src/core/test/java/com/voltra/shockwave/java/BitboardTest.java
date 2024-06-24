package com.voltra.shockwave.java;

import com.voltra.shockwave.java.model.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Random;

import static com.voltra.shockwave.java.model.Square.a6;
import static org.assertj.core.api.Assertions.assertThat;

class BitboardTest {

	private LinkedList<Integer> pool = null;

	@BeforeEach
	void setUp() {
		Random random = new Random();
		pool = new LinkedList<>();

		while (pool.size() < Long.SIZE) {
			int value = random.nextInt(Long.SIZE);
			if (!pool.contains(Square.values[value])) {
				pool.add(Square.values[value]);
			}
		}
	}

	@Test
	void shouldAddAllSquaresCorrectly() {
		long bitboard = 0;

		for (int x88square : pool) {
			bitboard = Bitboard.add(x88square, bitboard);
		}

		assertThat(bitboard).isEqualTo(-1L);
	}

	@Test
	void shouldRemoveAllSquaresCorrectly() {
		long bitboard = -1;

		for (int x88square : pool) {
			bitboard = Bitboard.remove(x88square, bitboard);
		}

		assertThat(bitboard).isEqualTo(0L);
	}

	@Test
	void shouldReturnTheNextSquare() {
		long bitboard = Bitboard.add(a6, 0);

		int square = Bitboard.next(bitboard);

		assertThat(square).isEqualTo(a6);
	}

	@Test
	void shouldReturnCorrectRemainder() {
		long bitboard = 0b1110100;

		long remainder = Bitboard.remainder(bitboard);

		assertThat(remainder).isEqualTo(0b1110000L);
	}

	@Test
	void shouldReturnCorrectSize() {
		long bitboard = 0b111;

		int size = Bitboard.size(bitboard);

		assertThat(size).isEqualTo(3);
	}
}
