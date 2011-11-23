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
	public int getPlayerNumber() {
		return expert.getPlayerNumber();
	}

	@Override
	public void setPlayerNumber(int playerNo) {
		expert.setPlayerNumber(playerNo);

	}

}