package agent;

import environment.GameHistory;

public class BaseAgent implements IAgent {
	private IExpert expert;

	public BaseAgent(IExpert expert) {
		this.expert = expert;
	}

	@Override
	public boolean move(GameHistory history) {
		return expert.move(history);
	}

	@Override
	public int playerNumber() {
		return expert.playerNumber();
	}

}