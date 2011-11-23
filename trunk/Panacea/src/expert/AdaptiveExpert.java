package expert;

import environment.GameHistory;
import environment.ScoringSystem;

public class AdaptiveExpert extends AbstractExpert {
	private double cscore;
	private double dscore;
	private int cmoves;
	private int dmoves;

	/**
	 * Adaptive - Starts with c,c,c,c,c,c,d,d,d,d,d and then takes choices which
	 * have given the best average score re-calculated after every move.
	 * 
	 * @param playerNo
	 */
	public AdaptiveExpert(int playerNo) {
		super(playerNo);
		cscore = 0;
		dscore = 0;
		cmoves = 0;
		dmoves = 0;
	}

	@Override
	public String getName() {
		return "Adaptive Expert";
	}

	@Override
	public boolean move(GameHistory history) {

		if (history.getNumberOfMoves() == 0) {
			return true;
		} else {
			// Update history with last move;
			updateStats(history);

			if (history.getNumberOfMoves() < 5) {
				return true;
			} else if (history.getNumberOfMoves() < 10) {
				return false;
			} else {
				// Checks which choice had the better outcome previously, and
				// make that pick
				if ((cscore / cmoves) >= (dscore / dmoves))
					return true;
				else
					return false;
			}
		}
	}

	/**
	 * Updates the stats from last given move for adaptive expert to use.
	 * 
	 * @param history
	 */
	private void updateStats(GameHistory history) {
		boolean[] move = history.getLastMove();
		ScoringSystem scoringSystem = history.getScoringSystem();

		double[] results = scoringSystem.getPoints(move);

		if (move[playerNo - 1]) {
			cscore += results[playerNo - 1];
			cmoves++;
		} else {
			dscore += results[playerNo - 1];
			dmoves++;
		}

	}

}
