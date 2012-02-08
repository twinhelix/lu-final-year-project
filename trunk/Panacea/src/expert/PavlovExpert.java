package expert;

import environment.GameHistory;

public class PavlovExpert extends AbstractExpert {
	protected boolean repeat;

	/***
	 * AKA Win Stay Lose Shift
	 * Repeat last choice if good outcome - If 5 or 3 points scored in the last
	 * round then repeat last choice.
	 * 
	 * @param playerNo
	 */
	public PavlovExpert(int playerNo) {
		super(playerNo);
	}

	public String getName() {
		return ("Pavlov Expert");
	}

	@Override
	public boolean move(GameHistory history) {
		repeat = false;
		if (history.getNumberOfMoves() < 1) {
			return true;
		}

		boolean[] lastMove = history.getLastMove();

		if (lastMove[0] && lastMove[1]) {
			repeat = true;
			return true;
		}

		else if (lastMove[playerNo % 2] && !lastMove[playerNo - 1]) {
			repeat = true;
			return false;
		}

		return false;
	}

}
