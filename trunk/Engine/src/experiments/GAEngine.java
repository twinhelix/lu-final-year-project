package experiments;

import static common.Settings.NO_OF_ROUNDS;
import static common.Settings.SCORING_SYSTEM;
import agent.IExpert;
import engine.IEngine;
import engine.ImperfectRoundRobinEngine;
import engine.RoundRobinEngine;
import expert.AlternateCCDExpert;
import expert.CooperateExpert;
import expert.DefectExpert;
import expert.GAExpert;
import expert.GAExpertModified;
import expert.GAExpertWatcher;
import expert.HardMajorityExpert;
import expert.PavlovExpert;
import expert.ProbableExpert;
import expert.RandomExpert;
import expert.SoftGrudgerExpert;
import expert.SoftMajorityExpert;
import expert.eee.EEEDecProb;
import expert.eee.EEEFixedProb;
import expert.titfortat.HardTitforTatExpert;
import expert.titfortat.RemorsefulProberExpert;
import expert.titfortat.SuspiciousTitForTatExpert;
import expert.titfortat.TitForTatExpert;
import expert.titfortat.TitForTwoTatExpert;

public class GAEngine
{
	private static boolean PLOT_GRAPHS = false;
	private static boolean IMPERFECT = false;

	public static void main(String[] args)
	{
		String[] strats = { new TitForTatExpert(0).getName(),
				new TitForTwoTatExpert(0).getName(),
				new SoftMajorityExpert(0).getName() };
		GAExpertWatcher expert = new GAExpertWatcher(
				0,
				"1000010100000001011100000101101001000000000010110111101001001000111111",
				3);
		expert.setUniqueName(expert.getName() + "a");

		IExpert[] experts = {
				new TitForTatExpert(0),
				new EEEDecProb(0, strats),
				new EEEFixedProb(0, strats, 0.3),
				new RandomExpert(0),
				new DefectExpert(0),
				new PavlovExpert(0),
				new RemorsefulProberExpert(0, 0.2),
				new SoftGrudgerExpert(0),
				new CooperateExpert(0),
				new TitForTwoTatExpert(0),
				new SuspiciousTitForTatExpert(0),
				new SoftMajorityExpert(0),
				new HardMajorityExpert(0),
				new AlternateCCDExpert(0),
				new HardTitforTatExpert(0),
				new ProbableExpert(0),
				// OTHERS
				// new NaivePeaceMakerExpert(0, 0.2),
				// new NaiveProberExpert(0, 0.2),
				// new TruePeaceMakerExpert(0, 0.2),
				// new AdaptiveExpert(0),
				// new AlternateDDCExpert(0),
				// new AlternateExpert(0),
				// new GradualExpert(0),
				// new PavlovRandomExpert(0, 0.02),
				// --- END ---
				expert,
				new GAExpert(
						0,
						"1100101100110000111001110101110011100110010000000111110010110111111111",
						3),
				new GAExpertModified(
						0,
						"10010000011010100101100000110000011010001001001000100010110110001101010101101111111111") };

		IEngine engine;
		if (!IMPERFECT)
		{
			engine = new RoundRobinEngine(experts, NO_OF_ROUNDS,
					SCORING_SYSTEM, 100);
		} else
		{
			engine = new ImperfectRoundRobinEngine(experts, NO_OF_ROUNDS,
					SCORING_SYSTEM, 100, 0.2);
		}

		engine.run();
		System.out.println();
		engine.showTally();
		System.out.println();
		// int[] hist = expert.getBitFrequency();
		// int total = 0;
		// for (int i = 0; i < hist.length; i++)
		// {
		// total += hist[i];
		// System.out.println(i + " - " + hist[i]);
		// }
		// System.out.println(total);
		if (PLOT_GRAPHS)
		{
			engine.plotResults();
			engine.plotPerformance();
		}
	}
}
