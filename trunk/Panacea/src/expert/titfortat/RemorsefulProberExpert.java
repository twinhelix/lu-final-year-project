package expert.titfortat;

import environment.GameHistory;

public class RemorsefulProberExpert extends TitForTatExpert {

	private double prob;
	private boolean remorse;

	/***
	 * Repeat opponent's last choice (ie Tit For Tat), but sometimes probe by
	 * defecting in lieu of cooperating. If the opponent defects in response to
	 * probing, show remorse by cooperating once.
	 * 
	 * @param playerNo
	 * @param prob
	 */
	public RemorsefulProberExpert(int playerNo, double prob) {
		super(playerNo);
		this.prob = prob;
		initialize();
	}
	
	@Override
	public void initialize() {
		remorse = false;
	}

	@Override
	public String getName() {
		return ("Remorseful Prober Expert: " + prob + "%");
	}

	public boolean move(GameHistory history) {
		if (history.getNumberOfMoves() == 0){
			return true;
		}
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
