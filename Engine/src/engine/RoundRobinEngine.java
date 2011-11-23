package engine;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import agent.IExpert;
import environment.Game;
import environment.ScoringSystem;

public class RoundRobinEngine {
	private IExpert[] experts;
	private int totalGames;
	private ScoringSystem scoringSystem;
	private Map<String, Double> totals;

	public RoundRobinEngine(IExpert[] experts, int totalGames,
			ScoringSystem scoringSystem) {
		this.experts = experts;
		this.totalGames = totalGames;
		this.scoringSystem = scoringSystem;
		initialiseTally(experts);
	}

	private void initialiseTally(IExpert[] experts) {
		totals = new HashMap<String, Double>();

		for (IExpert expert : experts) {
			totals.put(expert.getName(), new Double(0));
		}
	}

	/**
	 * Runs a round robin tournament with all given players
	 */
	public void run() {

		for (int i = 0; i < experts.length; i++) {
			for (int j = 0; j < experts.length; j++) {
				experts[i].setPlayerNumber(1);
				experts[j].setPlayerNumber(2);

				Game game = new Game(experts[i], experts[j], totalGames,
						scoringSystem);
				double[] result = game.run();

				totals.put(experts[i].getName(), new Double(totals.get(
						experts[i].getName()).doubleValue()
						+ result[0]));
				totals.put(experts[j].getName(), new Double(totals.get(
						experts[j].getName()).doubleValue()
						+ result[1]));

				System.out.println(experts[i].getName() + ": " + result[0]
						+ "\t\t" + experts[j].getName() + ": " + result[1]);
			}
		}
	}

	public void showTally() {
		
		
		
		
		Set<String> keys = totals.keySet();
		for (String key : keys) {
			System.out.println(key + ": " + totals.get(key).doubleValue());
		}
	}
}
