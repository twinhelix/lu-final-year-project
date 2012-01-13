package expert.titfortat;

import java.util.Collection;
import java.util.Stack;

import environment.GameHistory;
import expert.AbstractExpert;

public class TruePeaceMakerExpert extends TitForTwoTatExpert {

	private double prob;

	/***
	 * Co-operate unless opponent defects twice in a row, then defect once, but
	 * sometimes make peace by co-operating in lieu of defecting.
	 * 
	 * @param playerNo
	 */
	public TruePeaceMakerExpert(int playerNo, double prob) {
		super(playerNo);
		this.prob = prob;
	}

	public String getName() {
		return ("True Peace Maker Expert");
	}

	@Override
	public boolean move(GameHistory history) {

		// Uses Tit For Two Tat, but sometimes probe
		// by cooperate in lieu of defecting.
		boolean result = super.move(history);

		if (!result) {
			if (Math.random() < prob)
				return true;
		}
		return result;
	}
}
