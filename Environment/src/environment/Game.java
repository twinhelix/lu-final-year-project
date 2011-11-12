package environment;

import org.apache.log4j.Logger;

import agent.IAgent;

public class Game implements IGame {
	private IAgent agent1, agent2;
	private double wins1, wins2;
	private int totalGames;
	private ScoringSystem scoringSystem;
	private GameHistory history;
	private Logger logger = Logger.getLogger(this.getClass());

	public Game(IAgent agent1, IAgent agent2, int totalGames,
			ScoringSystem scoringSystem) {
		this.agent1 = agent1;
		this.agent2 = agent2;
		this.scoringSystem = scoringSystem;
		this.totalGames = totalGames;
		history = new GameHistory();
	}

	@Override
	public double[] run() {
		logger.info("Running game for " + totalGames + " rounds...");
		wins1 = 0;
		wins2 = 0;
		if (totalGames == 0) {
			// Infinite game...
			logger.info("WARNING!!! Infinite game!");
		}
		for (int i = 0; i < totalGames; i++) {
			double[] result = playOneRound();
			wins1 += result[0];
			wins2 += result[1];
		}

		double[] results = { getWins1(), getWins2() };

		logger.info("Scoring - Player 1: " + results[0] + "\t\t\tPlayer2: "
				+ results[1]);
		return results;
	}

	public double[] playOneRound() {
		boolean player1 = agent1.move(history);
		boolean player2 = agent2.move(history);

		// Add in the new move to update the historical data
		history.newMove(player1, player2);

		double[] result;
		if (player1 && player2) {
			result = scoringSystem.getcc();
		}

		else if (player1 && !player2) {
			result = scoringSystem.getcd();
		}

		else if (!player1 && player2) {
			result = scoringSystem.getdc();
		}

		else {
			result = scoringSystem.getdd();
		}

		System.out.println("Player 1: " + player1 + " " + result[0]
				+ "\t\t\tPlayer2: " + player2 + " " + result[1]);
		return result;
	}

	@Override
	public IAgent getAgent1() {
		return agent1;
	}

	@Override
	public IAgent getAgent2() {
		return agent2;
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
