package agent;

import environment.GameHistory;

public interface IExpert {
	public boolean move(GameHistory history);

	public int playerNumber();
}
