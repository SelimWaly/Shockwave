package com.voltra.shockwave.java;

import com.voltra.shockwave.java.model.Depth;
import com.voltra.shockwave.java.model.Value;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static com.voltra.shockwave.java.MoveList.MoveEntry;
import static com.voltra.shockwave.java.MoveList.MoveVariation;
import static com.voltra.shockwave.java.MoveList.RootEntry;
import static com.voltra.shockwave.java.model.Color.WHITE;
import static com.voltra.shockwave.java.model.Color.opposite;
import static com.voltra.shockwave.java.model.Move.NOMOVE;
import static java.lang.Math.abs;
import static java.lang.Runtime.getRuntime;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.SECONDS;

final class Search {

	private final ExecutorService threadPool = newFixedThreadPool(getRuntime().availableProcessors());
	private Optional<Future<?>> future = Optional.empty();
	private volatile boolean abort;

	private final Protocol protocol;

	private Position position;
	private final Evaluation evaluation = new Evaluation();

	// We will store a MoveGenerator for each ply so we don't have to create them
	// in search. (which is expensive)
	private final MoveGenerator[] moveGenerators = new MoveGenerator[Depth.MAX_PLY];

	// Depth search
	private int searchDepth;

	// Nodes search
	private long searchNodes;

	// Time & Clock & Ponder search
	private long searchTime;
	private Timer timer;
	private boolean timerStopped;
	private boolean doTimeManagement;

	// Search parameters
	private final MoveList<RootEntry> rootMoves = new MoveList<>(RootEntry.class);
	private long totalNodes;
	private final int initialDepth = 1;
	private int currentDepth;
	private int currentMaxDepth;
	private int currentMove;
	private int currentMoveNumber;
	private final MoveVariation[] pv = new MoveVariation[Depth.MAX_PLY + 1];

	void newDepthSearch(Position position, int searchDepth) {
		reset();

		this.position = position;
		this.searchDepth = searchDepth;
	}

	void newNodesSearch(Position position, long searchNodes) {
		reset();

		this.position = position;
		this.searchNodes = searchNodes;
	}

	void newTimeSearch(Position position, long searchTime) {
		reset();

		this.position = position;
		this.searchTime = searchTime;
		this.timer = new Timer(true);
	}

	void newInfiniteSearch(Position position) {
		reset();

		this.position = position;
	}

	void newClockSearch(Position position,
						long whiteTimeLeft, long whiteTimeIncrement, long blackTimeLeft, long blackTimeIncrement, int movesToGo) {
		newPonderSearch(position,
			whiteTimeLeft, whiteTimeIncrement, blackTimeLeft, blackTimeIncrement, movesToGo
		);

		this.timer = new Timer(true);
	}

	void newPonderSearch(Position position,
						 long whiteTimeLeft, long whiteTimeIncrement, long blackTimeLeft, long blackTimeIncrement, int movesToGo) {
		reset();

		this.position = position;

		long timeLeft;
		long timeIncrement;
		if (position.activeColor == WHITE) {
			timeLeft = whiteTimeLeft;
			timeIncrement = whiteTimeIncrement;
		} else {
			timeLeft = blackTimeLeft;
			timeIncrement = blackTimeIncrement;
		}

		// Don't use all of our time. Search only for 95%. Always leave 1 second as
		// buffer time.
		long maxSearchTime = (long) (timeLeft * 0.95) - 1000L;
		if (maxSearchTime < 1) {
			// We don't have enough time left. Search only for 1 millisecond, meaning
			// get a result as fast as we can.
			maxSearchTime = 1;
		}

		// Assume that we still have to do movesToGo number of moves. For every next
		// move (movesToGo - 1) we will receive a time increment.
		this.searchTime = (maxSearchTime + (movesToGo - 1) * timeIncrement) / movesToGo;
		if (this.searchTime > maxSearchTime) {
			this.searchTime = maxSearchTime;
		}

		this.doTimeManagement = true;
	}

	Search(Protocol protocol) {
		this.protocol = protocol;

		for (int i = 0; i < Depth.MAX_PLY; i++) {
			moveGenerators[i] = new MoveGenerator();
		}

		for (int i = 0; i < pv.length; i++) {
			pv[i] = new MoveVariation();
		}

		reset();
	}

	private void reset() {
		searchDepth = Depth.MAX_DEPTH;
		searchNodes = Long.MAX_VALUE;
		searchTime = 0;
		timer = null;
		timerStopped = false;
		doTimeManagement = false;
		rootMoves.size = 0;
		abort = false;
		totalNodes = 0;
		currentDepth = initialDepth;
		currentMaxDepth = 0;
		currentMove = NOMOVE;
		currentMoveNumber = 0;
	}

	void start() {
		if (future.isEmpty()) {
			future = Optional.of(threadPool.submit(new Worker()));
		}
	}

	void stop() {
		future.ifPresent(value -> {
			abort = true;
			try {
				value.get();
			} catch (InterruptedException e) {
				currentThread().interrupt();
			} catch (ExecutionException e) {
				protocol.sendInfo("Search aborted with an error: " + e.getCause().getMessage());
			}
		});
		future = Optional.empty();
	}

	void ponderhit() {
		future.ifPresent(value -> {
			// Enable time management
			timer = new Timer(true);
			timer.schedule(new SearchTimer(), searchTime);

			// If we finished the first iteration, we should have a result.
			// In this case check the stop conditions.
			if (currentDepth > initialDepth) {
				checkStopConditions();
			}
		});
	}

	void quit() {
		stop();
		try {
			threadPool.shutdown();
			if (!threadPool.awaitTermination(3, SECONDS)) {
				threadPool.shutdownNow();
			}
		} catch (InterruptedException e) {
			threadPool.shutdownNow();
			currentThread().interrupt();
		}
	}

	private void checkStopConditions() {
		// We will check the stop conditions only if we are using time management,
		// that is if our timer != null.
		if (timer != null && doTimeManagement) {
			if (timerStopped) {
				abort = true;
			} else {
				// Check if we have only one move to make
				if (rootMoves.size == 1) {
					abort = true;
				} else

					// Check if we have a checkmate
					if (Value.isCheckmate(rootMoves.entries[0].value)
						&& currentDepth >= (Value.CHECKMATE - abs(rootMoves.entries[0].value))) {
						abort = true;
					}
			}
		}
	}

	/**
	 * This is our search timer for time & clock & ponder searches.
	 */
	private final class SearchTimer extends TimerTask {

		@Override
		public void run() {
			timerStopped = true;

			// If we finished the first iteration, we should have a result.
			// In this case abort the search.
			if (!doTimeManagement || currentDepth > initialDepth) {
				abort = true;
			}
		}
	}

	private final class Worker implements Runnable {

		@Override
		public void run() {
			if (timer != null) {
				timer.schedule(new SearchTimer(), searchTime);
			}

			// Populate root move list
			MoveList<MoveEntry> moves = moveGenerators[0].getLegalMoves(position, 1, position.isCheck());
			for (int i = 0; i < moves.size; i++) {
				int move = moves.entries[i].move;
				rootMoves.entries[rootMoves.size].move = move;
				rootMoves.entries[rootMoves.size].pv.moves[0] = move;
				rootMoves.entries[rootMoves.size].pv.size = 1;
				rootMoves.size++;
			}

			//### BEGIN Iterative Deepening
			for (int depth = initialDepth; depth <= searchDepth; depth++) {
				currentDepth = depth;
				currentMaxDepth = 0;
				protocol.sendStatus(false, currentDepth, currentMaxDepth, totalNodes, currentMove, currentMoveNumber);

				searchRoot(currentDepth, -Value.INFINITE, Value.INFINITE);

				// Sort the root move list, so that the next iteration begins with the
				// best move first.
				rootMoves.sort();

				checkStopConditions();

				if (abort) {
					break;
				}
			}
			//### ENDOF Iterative Deepening

			if (timer != null) {
				timer.cancel();
			}

			// Update all stats
			protocol.sendStatus(true, currentDepth, currentMaxDepth, totalNodes, currentMove, currentMoveNumber);

			// Send the best move and ponder move
			int bestMove = NOMOVE;
			int ponderMove = NOMOVE;
			if (rootMoves.size > 0) {
				bestMove = rootMoves.entries[0].move;
				if (rootMoves.entries[0].pv.size >= 2) {
					ponderMove = rootMoves.entries[0].pv.moves[1];
				}
			}

			// Send the best move to the GUI
			protocol.sendBestMove(bestMove, ponderMove);
		}

		private void updateSearch(int ply) {
			totalNodes++;

			if (ply > currentMaxDepth) {
				currentMaxDepth = ply;
			}

			if (searchNodes <= totalNodes) {
				// Hard stop on number of nodes
				abort = true;
			}

			pv[ply].size = 0;

			protocol.sendStatus(currentDepth, currentMaxDepth, totalNodes, currentMove, currentMoveNumber);
		}

		private void searchRoot(int depth, int alpha, int beta) {
			int ply = 0;

			updateSearch(ply);

			// Abort conditions
			if (abort) {
				return;
			}

			// Reset all values, so the best move is pushed to the front
			for (int i = 0; i < rootMoves.size; i++) {
				rootMoves.entries[i].value = -Value.INFINITE;
			}

			for (int i = 0; i < rootMoves.size; i++) {
				int move = rootMoves.entries[i].move;

				currentMove = move;
				currentMoveNumber = i + 1;
				protocol.sendStatus(false, currentDepth, currentMaxDepth, totalNodes, currentMove, currentMoveNumber);

				position.makeMove(move);
				int value = -search(depth - 1, -beta, -alpha, ply + 1);
				position.undoMove(move);

				if (abort) {
					return;
				}

				// Do we have a better value?
				if (value > alpha) {
					alpha = value;

					// We found a new best move
					rootMoves.entries[i].value = value;
					savePV(move, pv[ply + 1], rootMoves.entries[i].pv);

					protocol.sendMove(rootMoves.entries[i], currentDepth, currentMaxDepth, totalNodes);
				}
			}

			if (rootMoves.size == 0) {
				// The root position is a checkmate or stalemate. We cannot search
				// further. Abort!
				abort = true;
			}
		}

		private int search(int depth, int alpha, int beta, int ply) {
			// We are at a leaf/horizon. So calculate that value.
			if (depth <= 0) {
				// Descend into quiescent
				return quiescent(0, alpha, beta, ply);
			}

			updateSearch(ply);

			// Abort conditions
			if (abort || ply == Depth.MAX_PLY) {
				return evaluation.evaluate(position);
			}

			// Check insufficient material, repetition and fifty move rule
			if (position.isRepetition() || position.hasInsufficientMaterial() || position.halfmoveClock >= 100) {
				return Value.DRAW;
			}

			// Initialize
			int bestValue = -Value.INFINITE;
			int searchedMoves = 0;
			boolean isCheck = position.isCheck();

			MoveList<MoveEntry> moves = moveGenerators[ply].getMoves(position, depth, isCheck);
			for (int i = 0; i < moves.size; i++) {
				int move = moves.entries[i].move;
				int value = bestValue;

				position.makeMove(move);
				if (!position.isCheck(opposite(position.activeColor))) {
					searchedMoves++;
					value = -search(depth - 1, -beta, -alpha, ply + 1);
				}
				position.undoMove(move);

				if (abort) {
					return bestValue;
				}

				// Pruning
				if (value > bestValue) {
					bestValue = value;

					// Do we have a better value?
					if (value > alpha) {
						alpha = value;
						savePV(move, pv[ply + 1], pv[ply]);

						// Is the value higher than beta?
						if (value >= beta) {
							// Cut-off
							break;
						}
					}
				}
			}

			// If we cannot move, check for checkmate and stalemate.
			if (searchedMoves == 0) {
				if (isCheck) {
					// We have a check mate. This is bad for us, so return a -CHECKMATE.
					return -Value.CHECKMATE + ply;
				} else {
					// We have a stale mate. Return the draw value.
					return Value.DRAW;
				}
			}

			return bestValue;
		}

		private int quiescent(int depth, int alpha, int beta, int ply) {
			updateSearch(ply);

			// Abort conditions
			if (abort || ply == Depth.MAX_PLY) {
				return evaluation.evaluate(position);
			}

			// Check insufficient material, repetition and fifty move rule
			if (position.isRepetition() || position.hasInsufficientMaterial() || position.halfmoveClock >= 100) {
				return Value.DRAW;
			}

			// Initialize
			int bestValue = -Value.INFINITE;
			int searchedMoves = 0;
			boolean isCheck = position.isCheck();

			//### BEGIN Stand pat
			if (!isCheck) {
				bestValue = evaluation.evaluate(position);

				// Do we have a better value?
				if (bestValue > alpha) {
					alpha = bestValue;

					// Is the value higher than beta?
					if (bestValue >= beta) {
						// Cut-off
						return bestValue;
					}
				}
			}
			//### ENDOF Stand pat

			MoveList<MoveEntry> moves = moveGenerators[ply].getMoves(position, depth, isCheck);
			for (int i = 0; i < moves.size; i++) {
				int move = moves.entries[i].move;
				int value = bestValue;

				position.makeMove(move);
				if (!position.isCheck(opposite(position.activeColor))) {
					searchedMoves++;
					value = -quiescent(depth - 1, -beta, -alpha, ply + 1);
				}
				position.undoMove(move);

				if (abort) {
					return bestValue;
				}

				// Pruning
				if (value > bestValue) {
					bestValue = value;

					// Do we have a better value?
					if (value > alpha) {
						alpha = value;
						savePV(move, pv[ply + 1], pv[ply]);

						// Is the value higher than beta?
						if (value >= beta) {
							// Cut-off
							break;
						}
					}
				}
			}

			// If we cannot move, check for checkmate.
			if (searchedMoves == 0 && isCheck) {
				// We have a check mate. This is bad for us, so return a -CHECKMATE.
				return -Value.CHECKMATE + ply;
			}

			return bestValue;
		}

		private void savePV(int move, MoveVariation src, MoveVariation dest) {
			dest.moves[0] = move;
			System.arraycopy(src.moves, 0, dest.moves, 1, src.size);
			dest.size = src.size + 1;
		}
	}
}
