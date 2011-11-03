package environment;

import agent.IAgent;

public class Game implements IGame {
	private IAgent agent1, agent2;
	private double wins1, wins2;
	private int totalGames;
	private ScoringSystem scoringSystem;
	private GameHistory history;

	public Game(IAgent agent1, IAgent agent2, int totalGames,
			ScoringSystem scoringSystem) {
		this.agent1 = agent1;
		this.agent2 = agent2;
		this.scoringSystem = scoringSystem;
		this.totalGames = totalGames;
		wins1 = 0;
		wins2 = 0;
		history = new GameHistory();
	}

	@Override
	public double[] run() {
		if (totalGames == 0) {
			// RAISE WARNING FOR INFINITE GAME!
			System.err.println("Infinite game!");
		}
		for (int i = 0; i < totalGames; i++) {
			double[] result = playOneRound();
			wins1 += result[0];
			wins2 += result[1];

		}

		double[] results = { getWins1(), getWins2() };
		return results;
	}

	public double[] playOneRound() {
		boolean player1 = agent1.move(history);
		boolean player2 = agent2.move(history);

		// Add in the new move to update the historical data
		history.newMove(player1, player2);

		if (player1 && player2) {
			return scoringSystem.getcc();
		}

		else if (player1 && !player2) {
			return scoringSystem.getcd();
		}

		else if (!player1 && player2) {
			return scoringSystem.getdc();
		}

		else {
			return scoringSystem.getdd();
		}

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
