package expert;

import environment.GameHistory;

public class PavlovExpert extends AbstractExpert {
	/***
	 * Repeat last choice if good outcome - If 5 or 3 points scored in the last
	 * round then repeat last choice.
	 * 
	 * @param playerNo
	 */
	public PavlovExpert(int playerNo) {
		super(playerNo);
	}

	@Override
	public boolean move(GameHistory history) {
		if (history.getNumberOfMoves()<1){
			return true;
		}
		
		boolean[] lastMove = history.getLastMove();
		
		if (lastMove[0] && lastMove[1]){
			return true;
		}
		
		else if (lastMove[playerNo%2] && !lastMove[playerNo-1]){
			return false;
		}
		
		return false;
	}

}
