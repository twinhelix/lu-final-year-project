package expert;

import environment.GameHistory;

public class AdaptiveExpert extends AbstractExpert {

	/**
	 * Adaptive - Starts with c,c,c,c,c,c,d,d,d,d,d and then takes choices which
	 * have given the best average score re-calculated after every move.
	 * 
	 * @param playerNo
	 */
	public AdaptiveExpert(int playerNo) {
		super(playerNo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "Adaptive Expert";
	}

	@Override
	public boolean move(GameHistory history) {
		// TODO Auto-generated method stub
		return false;
	}

}
