package expert;

import agent.IExpert;
import environment.GameHistory;

public abstract class AbstractExpert implements IExpert {
	protected int playerNo;

	public AbstractExpert(int playerNo) {
		this.playerNo = playerNo;
	}

	public abstract boolean move(GameHistory history);

	public int playerNumber() {
		return playerNo;
	}
}
