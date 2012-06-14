package experiments;

import static common.Settings.NO_OF_ROUNDS;
import static common.Settings.SCORING_SYSTEM;
import agent.IExpert;
import engine.IEngine;
import engine.ImperfectRoundRobinEngine;
import engine.RoundRobinEngine;
import expert.AdaptiveExpert;
import expert.AlternateDDCExpert;
import expert.AlternateExpert;
import expert.CooperateExpert;
import expert.GAExpert;
import expert.GradualExpert;
import expert.PavlovRandomExpert;
import expert.SoftMajorityExpert;
import expert.eee.EEEDecProb;
import expert.eee.EEEFixedProb;
import expert.eee.EEEGADecProb;
import expert.titfortat.NaivePeaceMakerExpert;
import expert.titfortat.NaiveProberExpert;
import expert.titfortat.TitForTatExpert;
import expert.titfortat.TitForTwoTatExpert;
import expert.titfortat.TruePeaceMakerExpert;

public class EEEGAEngine
{
	private static boolean PLOT_GRAPHS = false;
	private static boolean IMPERFECT = false;

	public static void main(String[] args)
	{

		String[] strats1 = { new SoftMajorityExpert(0).getName(),
				new TitForTwoTatExpert(0).getName(),
				new CooperateExpert(0).getName() };

		String[] stratsTest = {"Soft Majority Expert", "Tit-for-2-Tat Expert", new TitForTatExpert(0).getName()};
		
		IExpert[] experts = {

				new EEEDecProb(0, stratsTest),
				new EEEGADecProb(0, stratsTest),
				new EEEFixedProb(0, stratsTest, 0.3),
				// new TitForTatExpert(0),
				// new RandomExpert(0),
				// new DefectExpert(0),
				// new PavlovExpert(0),
				// new RemorsefulProberExpert(0, 0.2),
				// new SoftGrudgerExpert(0),
				// new ProbableExpert(0),
				// new CooperateExpert(0),
				// new TitForTwoTatExpert(0),
				// new SoftMajorityExpert(0),
				// new SuspiciousTitForTatExpert(0),
				// new HardMajorityExpert(0),
				// new AlternateCCDExpert(0),
				// new HardTitforTatExpert(0),
				new GAExpert(
						0,
						"1111000000001001011100000111101001110000000100010000101010010000111111",
						3), new NaivePeaceMakerExpert(0, 0.2),
				new NaiveProberExpert(0, 0.2),
				new TruePeaceMakerExpert(0, 0.2), new AdaptiveExpert(0),
				new AlternateDDCExpert(0), new AlternateExpert(0),
				new GradualExpert(0), new PavlovRandomExpert(0, 0.02) };

		IEngine engine;
		if (!IMPERFECT)
		{
			engine = new RoundRobinEngine(experts, NO_OF_ROUNDS,
					SCORING_SYSTEM, 1000);
		} else
		{
			engine = new ImperfectRoundRobinEngine(experts, NO_OF_ROUNDS,
					SCORING_SYSTEM, 100000, 0.2);
		}
		engine.run();
		System.out.println();
		engine.showTally();

		if (PLOT_GRAPHS)
		{
			engine.plotResults();
			engine.plotPerformance();
		}
	}
}
