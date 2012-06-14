package experiments;

import static common.Settings.NO_OF_ROUNDS;
import static common.Settings.SCORING_SYSTEM;
import agent.IExpert;
import engine.IEngine;
import engine.ImperfectRoundRobinEngine;
import engine.RoundRobinEngine;
import expert.AdaptiveExpert;
import expert.AlternateCCDExpert;
import expert.AlternateDDCExpert;
import expert.AlternateExpert;
import expert.CooperateExpert;
import expert.DefectExpert;
import expert.GAExpert;
import expert.GAExpertWatcher;
import expert.GradualExpert;
import expert.HardMajorityExpert;
import expert.PavlovExpert;
import expert.PavlovRandomExpert;
import expert.ProbableExpert;
import expert.RandomExpert;
import expert.SoftGrudgerExpert;
import expert.SoftMajorityExpert;
import expert.eee.EEEDecProb;
import expert.eee.EEEFixedProb;
import expert.titfortat.HardTitforTatExpert;
import expert.titfortat.NaivePeaceMakerExpert;
import expert.titfortat.NaiveProberExpert;
import expert.titfortat.RemorsefulProberExpert;
import expert.titfortat.SuspiciousTitForTatExpert;
import expert.titfortat.TitForTatExpert;
import expert.titfortat.TitForTwoTatExpert;
import expert.titfortat.TruePeaceMakerExpert;

public class GAEngine
{
	private static boolean PLOT_GRAPHS = false;
	private static boolean IMPERFECT = false;

	public static void main(String[] args)
	{
		String[] strats = { new TitForTatExpert(0).getName(),
				new TitForTwoTatExpert(0).getName(),
				new SoftMajorityExpert(0).getName() };
		// 1010000011110111110011111111111000100010100010100011111110001000111111
		GAExpertWatcher expert = new GAExpertWatcher(
				0,
				"1010000011110111110011111111111000100010100010100011111110001000111111",
				3);
		expert.setUniqueName(expert.getName() + " Imperfect");

		IExpert[] experts = {
				// new TitForTatExpert(0),
				// new EEEDecProb(0, strats), new EEEFixedProb(0, strats, 0.3),
				// new RandomExpert(0), new DefectExpert(0), new
				// PavlovExpert(0),
				// new RemorsefulProberExpert(0, 0.2), new SoftGrudgerExpert(0),
				// new CooperateExpert(0), new TitForTwoTatExpert(0),
				// new SuspiciousTitForTatExpert(0), new SoftMajorityExpert(0),
				// new HardMajorityExpert(0), new AlternateCCDExpert(0),
				// new HardTitforTatExpert(0), new ProbableExpert(0),
				// OTHERS
				new NaivePeaceMakerExpert(0, 0.2),
				new NaiveProberExpert(0, 0.2),
				new TruePeaceMakerExpert(0, 0.2), new AdaptiveExpert(0),
				new AlternateDDCExpert(0),
				new AlternateExpert(0),
				new GradualExpert(0),
				new PavlovRandomExpert(0, 0.02),
				// --- END ---
				// expert,
				new GAExpert(
						0,
						"1011011010001010011010000111001001101000100100110100001010000000111111",
						3),
		// new GAExpertModified(
		// 0,
		// "10010000011010100101100000110000011010001001001000100010110110001101010101101111111111")
		};

		IEngine engine;
		if (!IMPERFECT)
		{
			engine = new RoundRobinEngine(experts, NO_OF_ROUNDS,
					SCORING_SYSTEM, 100000);
		} else
		{
			engine = new ImperfectRoundRobinEngine(experts, NO_OF_ROUNDS,
					SCORING_SYSTEM, 100, 0.2);
		}

		engine.run();
		System.out.println();
		engine.showTally();
		System.out.println();
		int[] hist = expert.getBitFrequency();
		int total = 0;
		for (int i = 0; i < hist.length; i++)
		{
			total += hist[i];
			System.out.print(hist[i] + " ");
		}
		// System.out.println(total);
		if (PLOT_GRAPHS)
		{
			engine.plotResults();
			engine.plotPerformance();
		}
	}
}
