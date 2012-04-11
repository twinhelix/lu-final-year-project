package engine;

import static common.Settings.NO_OF_ROUNDS;
import static common.Settings.SCORING_SYSTEM;
import agent.IExpert;
import expert.CooperateExpert;
import expert.DefectExpert;
import expert.PavlovExpert;
import expert.RandomExpert;
import expert.SoftGrudgerExpert;
import expert.eee.EEEDecProb;
import expert.eee.EEEFixedProb;
import expert.ga.GAExpert;
import expert.titfortat.RemorsefulProberExpert;
import expert.titfortat.TitForTatExpert;

public class Engine
{

	public static void main(String[] args)
	{
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
		String[] strats = { new SoftGrudgerExpert(0).getName(),
				new TitForTatExpert(0).getName(),
				new PavlovExpert(0).getName(), new CooperateExpert(0).getName() };

		IExpert[] experts = { new TitForTatExpert(0),
				new EEEDecProb(0, strats), new EEEFixedProb(0, strats, 0.3),
				new RandomExpert(0), new DefectExpert(0), new PavlovExpert(0),
				new RemorsefulProberExpert(0, 0.2), new SoftGrudgerExpert(0) };

		IExpert[] experts1 = { new TitForTatExpert(0), new PavlovExpert(0) };

		RoundRobinEngine engine = new RoundRobinEngine(experts, NO_OF_ROUNDS,
				SCORING_SYSTEM);
		engine.run();
		System.out.println();
		engine.showTally();
		engine.plotResults();
		
		
		GAExpert ge = new GAExpert(0);
		
		// Game g = new Game(new DefectExpert(1), new RandomExpert(2), 200,
		// scoringSystem);
		// double[] x = g.run();
		// System.out.println(x[0] + " " + x[1]);
	}
}
