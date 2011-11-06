package expert.titfortat;

import environment.GameHistory;
import expert.AbstractExpert;

public class TitForTatExpert extends AbstractExpert {

	public TitForTatExpert(int playerNo) {
		super(playerNo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean move(GameHistory history) {

		// Cooperate on first move
		if (history.getNumberOfMoves() == 0) {
			return true;
		}

		boolean[] lastMove = history.getLastMove();
		// Copies the last move of the other player
		return lastMove[playerNo % 2];
	}

}
