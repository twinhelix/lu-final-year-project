package agent;

import environment.GameHistory;

public interface IAgent {

	/***
	 * Agent makes move with history
	 * 
	 * @param history
	 * @return
	 */
	public boolean move(GameHistory history);

	/***
	 * Returns player number
	 * 
	 * @return
	 */
	public int getPlayerNumber();

	/***
	 * Sets player number
	 * 
	 * @return
	 */
	public void setPlayerNumber(int playerNo);
}
