package expert;

import environment.GameHistory;

public class AlternateExpert extends AbstractExpert {

	private boolean current;

	/**
	 * Alternate strategy - always play alternating defect/co-operate.
	 * 
	 * @param playerNo
	 */
	public AlternateExpert(int playerNo) {
		super(playerNo);
		current = false;
	}

	@Override
	public String getName() {
		return "Alternate Expert";
	}

	@Override
	public boolean move(GameHistory history) {
		current = !current;
		return current;
	}
}
