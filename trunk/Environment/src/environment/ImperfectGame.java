package environment;

//import org.apache.log4j.Logger;

import agent.IExpert;

public class ImperfectGame implements IGame
{
	private IExpert expert1, expert2;
	private double[] results;
	private int totalGames;
	private ScoringSystem scoringSystem;
	private GameHistory history;
	private double imperfect_prob = 0.05;

	// private Logger logger = Logger.getLogger(this.getClass());

	public ImperfectGame(IExpert expert1, IExpert expert2, int totalGames,
			ScoringSystem scoringSystem, double imperfect_prob)
	{
		this.expert1 = expert1;
		this.expert2 = expert2;
		this.scoringSystem = scoringSystem;
		this.totalGames = totalGames;
		this.imperfect_prob = imperfect_prob;
		results = new double[2];
		history = new GameHistory(scoringSystem);
	}

	@Override
	public double[] run()
	{
		// logger.info("Running game for " + totalGames + " rounds...");
		if (totalGames == 0)
		{
			// Infinite game...
			// logger.info("WARNING!!! Infinite game! :| ");
			System.err.println("WARNING!!! Infinite game! :| ");
		}
		for (int i = 0; i < totalGames; i++)
		{
			playOneRound();
		}

		results = history.getCurrentResult();
		// logger.info("Scoring - Player 1: " + results[0] + "\t\t\tPlayer2: " +
		// results[1]);
		return results;
	}

	protected double[] playOneRound()
	{
		boolean player1 = expert1.move(history);
		boolean player2 = expert2.move(history);

		if (Math.random() < imperfect_prob)
		{
			if (Math.random() < 0.5)
			{
				player1 = !player1;
			}
			else
			{
				player2 = !player2;
			}
		}

		// System.out.println("Expert: " + expert1.getName() + " played " +
		// player1);
		// System.out.println("Expert: " + expert2.getName() + " played " +
		// player2);
		boolean move[] = { player1, player2 };
		// Add in the new move to update the historical data
		history.newMove(player1, player2);

		// System.out.println("Player 1: " + player1 + " " + result[0]
		// + "\t\t\tPlayer2: " + player2 + " " + result[1]);
		return scoringSystem.getPoints(move);
	}

	@Override
	public IExpert getExpert1()
	{
		return expert1;
	}

	@Override
	public IExpert getExpert2()
	{
		return expert2;
	}

	@Override
	public int getNumberOfGames()
	{
		return totalGames;
	}

	@Override
	public ScoringSystem getScoring()
	{
		return scoringSystem;
	}

	@Override
	public double getResult1()
	{
		return results[0];
	}

	@Override
	public double getResult2()
	{
		return results[1];
	}

	@Override
	public GameHistory getHistory()
	{
		return history;
	}
}
