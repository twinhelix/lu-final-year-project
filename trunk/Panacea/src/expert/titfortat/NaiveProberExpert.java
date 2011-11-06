package expert.titfortat;

import environment.GameHistory;

public class NaiveProberExpert extends TitForTatExpert {
	private double prob;

	public NaiveProberExpert(int playerNo, double prob) {
		super(playerNo);
		this.prob = prob;
	}

	public boolean move(GameHistory history) {

		// Repeat opponent's last choice (ie Tit For Tat), but sometimes probe
		// by defecting in lieu of co-operating.
		boolean result = super.move(history);

		if (result) {
			if (Math.random() < prob)
				return false;
		}

		return result;

	}
}
