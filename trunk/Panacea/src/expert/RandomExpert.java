package expert;

import environment.GameHistory;

public class RandomExpert extends AbstractExpert {

	public RandomExpert(int playerNo) {
		super(playerNo);
		// TODO Auto-generated constructor stub
	}

	/***
	 * Random Expert generates a random move regardless of history
	 */
	public boolean move(GameHistory history) {
		double choice = Math.random();
		if (choice < 0.5)
			return true;
		else
			return false;
	}
}
