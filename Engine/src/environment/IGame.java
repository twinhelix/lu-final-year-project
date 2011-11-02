package environment;

import agent.IAgent;

public interface IGame {
	/***
	 * Return first agent.
	 * 
	 * @return
	 */
	public IAgent getAgent1();

	/***
	 * Return second agent.
	 * 
	 * @return
	 */
	public IAgent getAgent2();

	/***
	 * Returns the number of games, if game is infinite game, returns 0
	 * 
	 * @return
	 */
	public int getNumberOfGames();

	/***
	 * Returns the scoring system for game
	 * 
	 * @return
	 */
	public ScoringSystem getScoring();

	/***
	 * Returns the number of wins for player 1
	 * 
	 * @return
	 */
	public double getWins1();

	/***
	 * Returns the number of wins for player 2
	 * 
	 * @return
	 */
	public double getWins2();
	
	/***
	 * Returns the history of all previous moves made by players
	 * @return
	 */
	public GameHistory getHistory();

	/***
	 * Runs the game and returns the result in an array, [0] = first player
	 * wins, [1] = second player wins
	 * 
	 * @return
	 */
	public double[] run();
}
