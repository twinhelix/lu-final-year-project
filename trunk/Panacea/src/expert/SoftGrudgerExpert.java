package expert;

import environment.GameHistory;

public class SoftGrudgerExpert extends AbstractExpert {
	int phase;

	/**
	 * Soft Grudger - Cooperates until the opponent defects, in such case
	 * opponent is punished with d,d,d,d,c,c.
	 * 
	 * @param playerNo
	 */
	public SoftGrudgerExpert(int playerNo) {
		super(playerNo);
		phase = 6;
	}

	@Override
	public String getName() {
		return "Soft Grudger";
	}

	@Override
	public boolean move(GameHistory history) {
		if (history.getNumberOfMoves() == 0) {
			return true;
		}

		// in punishment phase:
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

		// Checks if opponent defected
		boolean[] lastMove = history.getLastMove();

		if (!lastMove[playerNo % 2]) {
			phase--;
			return false;
		}

		return true;
	}

}
