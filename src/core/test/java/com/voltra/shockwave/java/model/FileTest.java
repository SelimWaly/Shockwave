package com.voltra.shockwave.java.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FileTest {

	@Test
	void testValues() {
		for (int file : File.values) {
			assertThat(File.values[file]).isEqualTo(file);
		}
	}
}
