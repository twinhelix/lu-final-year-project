package engine;

import static common.Settings.SCORING_SYSTEM;
import static common.Settings.NO_OF_ROUNDS;
import agent.IExpert;
import eee.ExploreExploitExpert;
import expert.CooperateExpert;
import expert.DefectExpert;
import expert.GrudgerExpert;
import expert.PavlovExpert;
import expert.RandomExpert;
import expert.titfortat.RemorsefulProberExpert;
import expert.titfortat.TitForTatExpert;
import expert.titfortat.TitForTwoTatExpert;

public class Engine {

	public static void main(String[] args) {
		// 
		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("Configs.xml");

		// IExpert[] experts = { new RandomExpert(0), new GrudgerExpert(0),
		// new CooperateExpert(0), new DefectExpert(0),
		// new PavlovExpert(0), new TitForTatExpert(0),
		// new TitForTwoTatExpert(0), new GradualExpert(0),
		// new AdaptiveExpert(0), new NaivePeaceMakerExpert(0, 0.2),
		// new NaiveProberExpert(0, 0.2),
		// new RemorsefulProberExpert(0, 0.2), new SuspiciousTitForTat(0),
		// new TruePeaceMakerExpert(0, 0.2) };
		String[] strats = {
				new GrudgerExpert(0).getName(),
				new TitForTatExpert(0).getName(), new PavlovExpert(0).getName() };
		IExpert[] experts = { new TitForTatExpert(0),
				new ExploreExploitExpert(0, strats), new RandomExpert(0),
				new GrudgerExpert(0), new DefectExpert(0), new PavlovExpert(0) };
		IExpert[] experts1 = { new ExploreExploitExpert(0, strats),
				new PavlovExpert(0) };

		RoundRobinEngine engine = new RoundRobinEngine(experts, NO_OF_ROUNDS,
				SCORING_SYSTEM);
		engine.run();
		System.out.println();
		engine.showTally();

		// Game g = new Game(new DefectExpert(1), new RandomExpert(2), 200,
		// scoringSystem);
		// double[] x = g.run();
		// System.out.println(x[0] + " " + x[1]);
	}
}
