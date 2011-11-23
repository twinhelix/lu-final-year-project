package agent;

import environment.GameHistory;

public interface IExpert {
	/***
	 * Use expert strategy to make move
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

	/***
	 * Returns name of Expert
	 * 
	 * @return
	 */
	public String getName();

}
