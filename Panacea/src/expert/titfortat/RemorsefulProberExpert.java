package expert.titfortat;

import environment.GameHistory;

public class RemorsefulProberExpert extends TitForTatExpert {
	// Repeat opponent's last choice (ie Tit For Tat), but sometimes probe by
	// defecting in lieu of co-operating. If the opponent defects in response to
	// probing, show remorse by co-operating once.
	private double prob;
	private boolean remorse;

	public RemorsefulProberExpert(int playerNo, double prob) {
		super(playerNo);
		this.prob = prob;
		remorse = false;
	}

	public boolean move(GameHistory history) {
		boolean[] lastMove = history.getLastMove();

		// Remorse set to true if last round defected
		if (remorse) {
			remorse = false;
			if (!lastMove[playerNo % 2]) {
				return true;
			}
		}

		// Checks the move of what normal tit for tat would do
		boolean result = super.move(history);

		if (result) {
			if (Math.random() < prob) {
				remorse = true;
				return false;
			}
		}
		return result;
	}
}
