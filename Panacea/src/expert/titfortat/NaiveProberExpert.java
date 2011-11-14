package expert.titfortat;

import environment.GameHistory;

public class NaiveProberExpert extends TitForTatExpert {
	private double prob;

	/***
	 * Repeat opponent's last choice (ie Tit For Tat), but sometimes probe by
	 * defecting in lieu of co-operating.
	 * 
	 * @param playerNo
	 * @param prob
	 */
	public NaiveProberExpert(int playerNo, double prob) {
		super(playerNo);
		this.prob = prob;
	}

	@Override
	public String getName() {
		return ("Naive Prober Expert: " + prob + "%");
	}

	public boolean move(GameHistory history) {

		boolean result = super.move(history);

		if (result) {
			if (Math.random() < prob)
				return false;
		}

		return result;

	}
}
