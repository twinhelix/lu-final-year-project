package engine;

public interface IEngine
{
	/***
	 * Runs the tournament once engine has been intialised.
	 */
	public void run();

	/***
	 * Prints out the tally of how well each expert has performed. This tally
	 * includes the benchmark score, the variance, and the 99 percent confidence
	 * intervals
	 */
	public void showTally();

	/***
	 * Graphs the results of the tournament.
	 */
	public void plotResults();

	public void plotPerformance();

	/***
	 * Gets the mean benchmark score achieved of given expert.
	 * 
	 * @param name
	 * @return
	 */
	public double getScore(String name);

	/***
	 * Gets the average of the mean benchmark scores achieved by the opponents
	 * of given expert.
	 * 
	 * @param name
	 * @return
	 */
	public double getAverageOpponentScore(String name);

}
