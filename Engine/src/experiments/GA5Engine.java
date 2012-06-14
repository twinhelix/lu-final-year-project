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
import expert.GAExpertModified;
import expert.GAExpertWatcher;
import expert.GradualExpert;
import expert.GrudgerExpert;
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

public class GA5Engine
{
	private static boolean PLOT_GRAPHS = false;
	private static boolean IMPERFECT = false;

	public static void main(String[] args)
	{
		IEngine engine;
		GAExpert expert = new GAExpert(
				0,
				"10011110000110010110100010001000110100000101110010101010110010001111110111100011100010001110111010000000110101011101100101110001000010100101001001001000010110101100111010000001010010110110010100101001010100101011101011100001100010101111010001101011001000010100011100001111000111011110011111011010000111101101111001110110001001011010110001110011010000010111010100000111100010001010001101001001101111101010111001111110101001100101011000011101011101011101001011001101011000010011011000011101101110001100100001110101000001100111011101100111001100011100001101100010000100001000100110100110110111100110000001110011010101111111100101101011110010100011101101111110000010111000111010001011100110110011000101110100110110101001101111000101110010111101110011011111110100100011010100011111111000001100011011110101011100101111111010000010010010010101011111100100110110001111001010100111011110001100100100010010001000001010011010100111011000010001011111110001101000000011010011101110001110001110110011101111010011100001100000001100010010101010011001",
				5);
		String[] strats = { new GrudgerExpert(0).getName(),
				new PavlovExpert(0).getName(), new CooperateExpert(0).getName() };

		IExpert[] experts = { new TitForTatExpert(0),
				new EEEDecProb(0, strats), new EEEFixedProb(0, strats, 0.3),
				new RandomExpert(0), new DefectExpert(0), new PavlovExpert(0),
				new RemorsefulProberExpert(0, 0.2), new SoftGrudgerExpert(0),
				new ProbableExpert(0), new CooperateExpert(0),
				new SoftMajorityExpert(0), new HardMajorityExpert(0),
				new AlternateCCDExpert(0), new HardTitforTatExpert(0), expert };

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
