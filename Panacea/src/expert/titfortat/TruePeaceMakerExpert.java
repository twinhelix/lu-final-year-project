package expert.titfortat;

import environment.GameHistory;
import expert.AbstractExpert;

public class TruePeaceMakerExpert extends AbstractExpert {
	
	public TruePeaceMakerExpert(int playerNo) {
		super(playerNo);
		
	}

	@Override
	public boolean move(GameHistory history) {
		
		return false;
	}

}
