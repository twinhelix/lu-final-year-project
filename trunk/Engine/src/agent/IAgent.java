package agent;

import environment.GameHistory;

public interface IAgent<T extends IExpert> {
	public boolean move(GameHistory history);

	public int playerNumber();
}
