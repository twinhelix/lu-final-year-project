package expert.titfortat;

import environment.GameHistory;
import expert.AbstractExpert;

public class TitForTatExpert extends AbstractExpert {
	/***
	 * Cooperate on first move. Copies the last move of the other player
	 * 
	 * @param playerNo
	 */
	public TitForTatExpert(int playerNo) {
		super(playerNo);
	}

	@Override
	public boolean move(GameHistory history) {

		if (history.getNumberOfMoves() == 0) {
			return true;
		}

		boolean[] lastMove = history.getLastMove();

		return lastMove[playerNo % 2];
	}

	public String getName() {
		return ("Tit-for-Tat Expert");
	}

}
