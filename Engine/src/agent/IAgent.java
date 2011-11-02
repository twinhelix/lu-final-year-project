package agent;

import environment.GameHistory;

public interface IAgent {
	public boolean move(GameHistory history);

	public int playerNumber();
}
