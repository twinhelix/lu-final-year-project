package engine;

import static common.Settings.NO_OF_ROUNDS;
import static common.Settings.SCORING_SYSTEM;
import agent.IExpert;
import expert.CooperateExpert;
import expert.DefectExpert;
import expert.GAExpert;
import expert.GAExpertModified;
import expert.GrudgerExpert;
import expert.PavlovExpert;
import expert.ProbableExpert;
import expert.RandomExpert;
import expert.SoftGrudgerExpert;
import expert.eee.EEEDecProb;
import expert.eee.EEEFixedProb;
import expert.titfortat.RemorsefulProberExpert;
import expert.titfortat.TitForTatExpert;

public class Engine
{
	private static boolean PLOT_GRAPHS = false;

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

		String[] strats = { new GrudgerExpert(0).getName(),
				// new TitForTatExpert(0).getName(),
				new PavlovExpert(0).getName(),
				new CooperateExpert(0).getName(), };

		String[] strats1 = { "Tit-for-Tat Expert", "Soft Grudger",
				"Naive Peace Maker Expert: 0.2%", "Always Cooperate Expert",
				"True Peace Maker Expert", "Alternate Expert",
				"Gradual Expert", "Naive Prober Expert: 0.2%" };

		IExpert[] experts = {
				new TitForTatExpert(0),
				new EEEDecProb(0, strats),
				new EEEFixedProb(0, strats, 0.3),
				new RandomExpert(0),
				new DefectExpert(0),
				new PavlovExpert(0),
				new RemorsefulProberExpert(0, 0.2),
				new SoftGrudgerExpert(0),
				new GAExpert(
						0,
						"1111000010110110010100001110001010101000010101010001100001000000111111",
						3),
				new ProbableExpert(0),
				new GAExpertModified(
						0,
						"10110010000010000010000001001010010000011001001000001010000000001011011011101011100011") };

		IExpert[] experts1 = { new TitForTatExpert(0), new PavlovExpert(0) };

		// "1111010001100010101000101101000011001100100010001000101101100000111111"
		// 1111000010110110010100001110001010101000010101010001100001000000111111
		// - REALLY GOOD
		// ImperfectRoundRobinEngine engine = new
		// ImperfectRoundRobinEngine(experts, NO_OF_ROUNDS,
		// SCORING_SYSTEM, 0.2);
		for (int i = 0; i < 5; i++)
		{
			RoundRobinEngine engine = new RoundRobinEngine(experts,
					NO_OF_ROUNDS, SCORING_SYSTEM);

			engine.run();
			System.out.println();
			engine.showTally();

			if (PLOT_GRAPHS)
			{
				engine.plotResults();
				engine.plotPerformance();
			}
		}
		// GAExpert ge = new GAExpert(0);

		// Game g = new Game(new DefectExpert(1), new RandomExpert(2), 200,
		// scoringSystem);
		// double[] x = g.run();
		// System.out.println(x[0] + " " + x[1]);
	}
}
