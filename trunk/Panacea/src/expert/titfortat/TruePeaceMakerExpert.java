package expert.titfortat;

import environment.GameHistory;
import expert.AbstractExpert;

public class TruePeaceMakerExpert extends AbstractExpert {
	/***
	 * Co-operate unless opponent defects twice in a row, then defect once, but
	 * sometimes make peace by co-operating in lieu of defecting.
	 * 
	 * @param playerNo
	 */
	public TruePeaceMakerExpert(int playerNo) {
		super(playerNo);

	}

	@Override
	public boolean move(GameHistory history) {
		
		return false;
	}

}
