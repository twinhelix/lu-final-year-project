package engine;

import agent.IExpert;
import environment.Game;
import environment.ScoringSystem;
import expert.CooperateExpert;
import expert.DefectExpert;
import expert.GrudgerExpert;
import expert.PavlovExpert;
import expert.RandomExpert;
import expert.titfortat.TitForTatExpert;
import expert.titfortat.TitForTwoTatExpert;

public class Engine {

	public static void main(String[] args) {
		double[] cc = { 3.0, 3.0 };
		double[] cd = { 0, 5 };
		double[] dc = { 5, 0 };
		double[] dd = { 1, 1 };
		ScoringSystem scoringSystem = new ScoringSystem(cc, cd, dc, dd);

		IExpert[] experts = { new RandomExpert(0), new GrudgerExpert(0),
				new CooperateExpert(0), new DefectExpert(0),
				new PavlovExpert(0), new TitForTatExpert(0),
				new TitForTwoTatExpert(0) };
		RoundRobinEngine engine = new RoundRobinEngine(experts, 500,
				scoringSystem);
		engine.run();
		System.out.println();
		engine.showTally();

		// Game g = new Game(new DefectExpert(1), new RandomExpert(2), 200,
		// scoringSystem);
		// double[] x = g.run();
		// System.out.println(x[0] + " " + x[1]);
	}
}
