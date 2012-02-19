package expert;

import environment.GameHistory;

public class GrudgerExpert extends AbstractExpert {
	boolean grudge;

	/***
	 * Cooperate until the opponent defects. Then always defect unforgivingly.
	 * 
	 * @param playerNo
	 */
	public GrudgerExpert(int playerNo) {
		super(playerNo);
		initialize();
	}
	
	@Override
	public void initialize() {
		grudge = false;
	}

	public String getName() {
		return ("Grudger Expert");
	}
	
	@Override
	public boolean move(GameHistory history) {

		if (grudge)
			return false;

		if (history.getNumberOfMoves() == 0) {
			return true;
		}

		boolean[] lastMove = history.getLastMove();

		if (!lastMove[playerNo % 2]) {
			grudge = true;
			return false;
		} else {
			return true;
		}
	}
}
