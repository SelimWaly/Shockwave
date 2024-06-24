package com.voltra.shockwave.java;

import static com.voltra.shockwave.java.model.Color.opposite;
import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

public final class Perft {

	private static final int MAX_DEPTH = 6;

	private final MoveGenerator[] moveGenerators = new MoveGenerator[MAX_DEPTH];

	public void run() {
		Position position = Notation.toPosition(Notation.STANDARDPOSITION);
		int depth = MAX_DEPTH;

		for (int i = 0; i < MAX_DEPTH; i++) {
			moveGenerators[i] = new MoveGenerator();
		}

		System.out.format("Testing %s at depth %d%n", Notation.fromPosition(position), depth);

		long startTime = currentTimeMillis();
		long result = miniMax(depth, position, 0);
		long endTime = currentTimeMillis();

		long duration = endTime - startTime;

		System.out.format(
			"Nodes: %d%nDuration: %02d:%02d:%02d.%03d%n",
			result,
			MILLISECONDS.toHours(duration),
			MILLISECONDS.toMinutes(duration) - HOURS.toMinutes(MILLISECONDS.toHours(duration)),
			MILLISECONDS.toSeconds(duration) - MINUTES.toSeconds(MILLISECONDS.toMinutes(duration)),
			duration - SECONDS.toMillis(MILLISECONDS.toSeconds(duration))
		);

		System.out.format("n/ms: %d%n", result / duration);
	}

	private long miniMax(int depth, Position position, int ply) {
		if (depth == 0) {
			return 1;
		}

		int totalNodes = 0;

		boolean isCheck = position.isCheck();
		MoveGenerator moveGenerator = moveGenerators[ply];
		MoveList<MoveList.MoveEntry> moves = moveGenerator.getMoves(position, depth, isCheck);
		for (int i = 0; i < moves.size; i++) {
			int move = moves.entries[i].move;

			position.makeMove(move);
			if (!position.isCheck(opposite(position.activeColor))) {
				totalNodes += miniMax(depth - 1, position, ply + 1);
			}
			position.undoMove(move);
		}

		return totalNodes;
	}
}
