package expert;

import java.util.Collection;

import environment.GameHistory;

public class GradualExpert extends AbstractExpert {
	boolean grudge;
	int phase;

	/**
	 * Gradual - Cooperates until the opponent defects, in such case defects the
	 * total number of times the opponent has defected during the game. Followed
	 * up by two cooperations.
	 * 
	 * @param playerNo
	 */
	public GradualExpert(int playerNo) {
		super(playerNo);
		grudge = false;
		phase = 0;
	}

	@Override
	public String getName() {
		return "Gradual Expert";
	}

	@Override
	public boolean move(GameHistory history) {
		if (history.getNumberOfMoves() == 0) {
			return true;
		}

		// in punishment phase:
		if (grudge) {
			if (phase > 2) {
				phase--;
				return false;
			}
			if (phase == 2) {
				phase--;
				return true;
			}
			if (phase == 1) {
				phase = 6;
				return true;
			}
		}

		// Checks if opponent defected
		boolean[] lastMove = history.getLastMove();

		if (!lastMove[playerNo % 2]) {
			phase = getDefected(history);
			phase--;
			return false;
		}

		return true;
	}

	/**
	 * Finds the total number of defects from the opponent
	 * 
	 * @param history
	 * @return
	 */
	private int getDefected(GameHistory history) {
		int totalDefects = 0;
		Collection<boolean[]> moves = history.getHistory();

		for (boolean[] move : moves) {
			if (!move[playerNo % 2]) {
				totalDefects++;
			}
		}
		return totalDefects;
	}
}
