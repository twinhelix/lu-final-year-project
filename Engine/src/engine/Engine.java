package engine;

import agent.IExpert;
import eee.ExploreExploitExpert;
import environment.ScoringSystem;
import expert.AdaptiveExpert;
import expert.CooperateExpert;
import expert.DefectExpert;
import expert.GradualExpert;
import expert.GrudgerExpert;
import expert.PavlovExpert;
import expert.RandomExpert;
import expert.titfortat.NaivePeaceMakerExpert;
import expert.titfortat.NaiveProberExpert;
import expert.titfortat.RemorsefulProberExpert;
import expert.titfortat.SuspiciousTitForTat;
import expert.titfortat.TitForTatExpert;
import expert.titfortat.TitForTwoTatExpert;
import expert.titfortat.TruePeaceMakerExpert;

public class Engine {

	public static void main(String[] args) {
		double[] cc = { 3.0, 3.0 };
		double[] cd = { 0, 5 };
		double[] dc = { 5, 0 };
		double[] dd = { 1, 1 };
		ScoringSystem scoringSystem = new ScoringSystem(cc, cd, dc, dd);

		//ApplicationContext context = new ClassPathXmlApplicationContext("Configs.xml");
		
		
//		IExpert[] experts = { new RandomExpert(0), new GrudgerExpert(0),
//				new CooperateExpert(0), new DefectExpert(0),
//				new PavlovExpert(0), new TitForTatExpert(0),
//				new TitForTwoTatExpert(0), new GradualExpert(0),
//				new AdaptiveExpert(0), new NaivePeaceMakerExpert(0, 0.2),
//				new NaiveProberExpert(0, 0.2),
//				new RemorsefulProberExpert(0, 0.2), new SuspiciousTitForTat(0),
//				new TruePeaceMakerExpert(0, 0.2) };
		String[] strats = {new CooperateExpert(0).getName(), new TitForTatExpert(0).getName(), new AdaptiveExpert(0).getName(), new PavlovExpert(0).getName()};
		IExpert[] experts = { new TitForTatExpert(0), new ExploreExploitExpert(1, strats),new RandomExpert(0), new GrudgerExpert(0),
				new CooperateExpert(0), new DefectExpert(0),
				new PavlovExpert(0) };
		RoundRobinEngine engine = new RoundRobinEngine(experts, 10000,
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
