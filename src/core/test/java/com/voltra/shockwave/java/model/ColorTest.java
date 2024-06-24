package com.voltra.shockwave.java.model;

import org.junit.jupiter.api.Test;

import static com.voltra.shockwave.java.model.Color.BLACK;
import static com.voltra.shockwave.java.model.Color.WHITE;
import static com.voltra.shockwave.java.model.Color.opposite;
import static org.assertj.core.api.Assertions.assertThat;

class ColorTest {

	@Test
	void testValues() {
		for (int color : Color.values) {
			assertThat(Color.values[color]).isEqualTo(color);
		}
	}

	@Test
	void testOpposite() {
		assertThat(opposite(BLACK)).isEqualTo(WHITE);
		assertThat(opposite(WHITE)).isEqualTo(BLACK);
	}
}
