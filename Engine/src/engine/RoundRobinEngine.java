package engine;

import agent.IExpert;
import environment.Game;
import environment.ScoringSystem;

public class RoundRobinEngine {
	private IExpert[] experts;
	private int totalGames;
	private ScoringSystem scoringSystem;

	public RoundRobinEngine(IExpert[] experts, int totalGames,
			ScoringSystem scoringSystem) {
		this.experts = experts;
		this.totalGames = totalGames;
		this.scoringSystem = scoringSystem;
	}

	public void run() {

		for (int i = 0; i < experts.length; i++) {
			for (int j = i + 1; j < experts.length; j++) {
				experts[i].setPlayerNumber(1);
				experts[j].setPlayerNumber(2);

				Game game = new Game(experts[i], experts[j], totalGames,
						scoringSystem);
				double[] result = game.run();

				System.out.println(experts[i].getName() + ": " + result[0]
						+ "\t\t" + experts[j].getName() + ": " + result[1]);
			}
		}
	}
}
