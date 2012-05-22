package common;

import environment.ScoringSystem;

public class Settings
{
	private static double[] cc = { 3, 3 };
	private static double[] cd = { 0, 5 };
	private static double[] dc = { 5, 0 };
	private static double[] dd = { 1, 1 };
	public static final ScoringSystem SCORING_SYSTEM = new ScoringSystem(cc,
			cd, dc, dd);
	public static final int NO_OF_ROUNDS = 200;

	/***
	 * MEASURING PERFORMANCE OF PLAYERS Suppose there are 15 players, each
	 * player plays against each other as well as itself, and each game is of
	 * 200 moves. - Maximum score : 15000 - Minimum score : 0
	 * 
	 * However, neither extreme is achieved in practice
	 * 
	 * Benchmark score : The score a player would have received against an
	 * opponent if both the players always cooperated
	 * 
	 * Divide the score of a player by the number of players, then express it as
	 * a percentage of Benchmark Score.
	 * 
	 * For Example : If a player scores 7500, then he has scored (500/600), i.e.
	 * 83% of the Benchmark Score in this case.
	 */
	public static double getBenchMark(int noOfPlayers)
	{	
		return (cc[0] * NO_OF_ROUNDS);
	}

}