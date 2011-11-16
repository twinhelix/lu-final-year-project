package engine;

import agent.BaseAgent;
import environment.Game;
import environment.ScoringSystem;
import expert.RandomExpert;

public class Engine {

	public static void main(String[] args) {
		double[] cc = { 3.0, 3.0 };
		double[] cd = { 0, 5 };
		double[] dc = { 5, 0 };
		double[] dd = { 1, 1 };
		ScoringSystem scoringSystem = new ScoringSystem(cc, cd, dc, dd);

		Game g = new Game(new BaseAgent(new RandomExpert(1)), new BaseAgent(
				new RandomExpert(2)), 200, scoringSystem);
		double[] x = g.run();
		System.out.println(x[0] + " " + x[1]);
	}
}
