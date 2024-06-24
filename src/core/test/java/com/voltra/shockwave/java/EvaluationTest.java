package com.voltra.shockwave.java;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EvaluationTest {

	@Test
	void testEvaluate() {
		Position position = Notation.toPosition(Notation.STANDARDPOSITION);
		Evaluation evaluation = new Evaluation();

		assertThat(evaluation.evaluate(position)).isEqualTo(Evaluation.TEMPO);
	}
}
