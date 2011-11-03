package agent;

import environment.GameHistory;

public interface IExpert {
	/***
	 * Use expert strategy to make move
	 * @param history
	 * @return
	 */
	public boolean move(GameHistory history);

	/***
	 * Returns player number
	 * @return
	 */
	public int playerNumber();
}
