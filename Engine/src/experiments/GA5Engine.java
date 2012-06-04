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
				"10010101110100101001100001011010100011000100100100100000100101001111110100101010100101100110100011101001001000000111001100110110010100011101111011100101100100111110000011010111000100000101000111110011010111101111101111001100000101010010001110101110110000010011010111100011011111100100001101000101110101110110000011111010011001100110101111110011001000101001101001111111011010111001000000000010100010111111010000111100000000111101000000100010101110100100101010111101111111110101110110101001110011111111000010101101110010100001000100010100001101110101101110011000111111001101101111101111011000001100000000000100000001110001000100000010011010001100000101100100010011100110110010100000000011100110000101011110010100110101001100110011010011000000000010100000011101001000001110010010111000001110111001001111101010001000101010100101110100010111011110111111111110101001110101101111001101001110100011100100110100100001011110001010111101011000001101111110011110101111110000100010011110000001111011111111010011000101000100100000010011101111110100",
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
					SCORING_SYSTEM, 1);
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
