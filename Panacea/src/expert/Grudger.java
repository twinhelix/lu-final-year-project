package expert;

import environment.GameHistory;

public class Grudger extends AbstractExpert {
	boolean grudge;

	public Grudger(int playerNo) {
		super(playerNo);
		grudge = false;
	}

	@Override
	public boolean move(GameHistory history) {

		// Co-operate until the opponent defects. Then always defect forever
		if (grudge)
			return false;

		if (history.getNumberOfMoves() == 0) {
			return true;
		}

		boolean[] lastMove = history.getLastMove();

		if (!lastMove[playerNo % 2]) {
			grudge = true;
			return false;
		} else
			return true;

	}

}
