package environment;

import agent.IExpert;

public interface IGame
{
	/***
	 * Return first expert.
	 * 
	 * @return
	 */
	public IExpert getExpert1();

	/***
	 * Return second expert.
	 * 
	 * @return
	 */
	public IExpert getExpert2();

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
	 * Returns the number of points for player 1
	 * 
	 * @return
	 */
	public double getResult1();

	/***
	 * Returns the number of points for player 2
	 * 
	 * @return
	 */
	public double getResult2();

	/***
	 * Returns the history of all previous moves made by players
	 * 
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
