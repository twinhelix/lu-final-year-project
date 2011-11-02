package expert;

import environment.GameHistory;

public class TitForTatExpert extends AbstractExpert {

	public TitForTatExpert(int playerNo) {
		super(playerNo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean move(GameHistory history) {
		boolean[] lastMove = history.getLastMove();
		// Cooperate on first move
		if (lastMove == null){
			return true;
		}
		// Copies the last move of the other player
		return lastMove[playerNo % 2];
	}

}
