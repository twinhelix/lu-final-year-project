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

		return false;
	}

}
