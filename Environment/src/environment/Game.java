package environment;

//import org.apache.log4j.Logger;

import agent.IExpert;

public class Game implements IGame {
	private IExpert expert1, expert2;
	private double wins1, wins2;
	private int totalGames;
	private ScoringSystem scoringSystem;
	private GameHistory history;

	// private Logger logger = Logger.getLogger(this.getClass());

	public Game(IExpert expert1, IExpert expert2, int totalGames,
			ScoringSystem scoringSystem) {
		this.expert1 = expert1;
		this.expert2 = expert2;
		this.scoringSystem = scoringSystem;
		this.totalGames = totalGames;
		history = new GameHistory(scoringSystem);
	}

	@Override
	public double[] run() {
		// logger.info("Running game for " + totalGames + " rounds...");
		wins1 = 0;
		wins2 = 0;
		if (totalGames == 0) {
			// Infinite game...
			// logger.info("WARNING!!! Infinite game! :| ");
		}
		for (int i = 0; i < totalGames; i++) {
			double[] result = playOneRound();
			wins1 += result[0];
			wins2 += result[1];
		}

		double[] results = { getWins1(), getWins2() };

		// logger.info("Scoring - Player 1: " + results[0] + "\t\t\tPlayer2: " +
		// results[1]);
		return results;
	}

	public double[] playOneRound() {
		boolean player1 = expert1.move(history);
		boolean player2 = expert2.move(history);
		boolean move[] = { player1, player2 };
		// Add in the new move to update the historical data
		history.newMove(player1, player2);

		double[] result = scoringSystem.getPoints(move);
		
//		System.out.println("Player 1: " + player1 + " " + result[0]
//				+ "\t\t\tPlayer2: " + player2 + " " + result[1]);
		return result;
	}

	@Override
	public IExpert getExpert1() {
		return expert1;
	}

	@Override
	public IExpert getExpert2() {
		return expert2;
	}

	@Override
	public int getNumberOfGames() {
		return totalGames;
	}

	@Override
	public ScoringSystem getScoring() {
		return scoringSystem;
	}

	@Override
	public double getWins1() {
		return wins1;
	}

	@Override
	public double getWins2() {
		return wins2;
	}

	@Override
	public GameHistory getHistory() {
		return history;
	}
}
