package expert;

import environment.GameHistory;

public class CooperateExpert extends AbstractExpert {

	public CooperateExpert(int playerNo) {
		super(playerNo);
		// TODO Auto-generated constructor stub
	}

	/***
	 * Cooperate expert always cooperates regardless of history
	 * 
	 * @return
	 */
	public boolean move(GameHistory history) {
		return true;
	}
}
