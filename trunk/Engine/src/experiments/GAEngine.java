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
	private static boolean IMPERFECT = true;

	public static void main(String[] args)
	{
		String[] strats = { new TitForTatExpert(0).getName(),
				new TitForTwoTatExpert(0).getName(),
				new SoftMajorityExpert(0).getName() };
		GAExpertWatcher expert = new GAExpertWatcher(
				0,
				"1000010100000001011100000101101001000000000010110111101001001000111111",
				3);

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
				expert,
				new GAExpert(
						0,
						"10011110101001000001100001110000000101010001000000010010110100001111110011110011001001001010110111001010100101110100100011100110110110011101111010100100001100100101110110101110101101010010101010101010110011000100011010000000111001111010110001100011101011011000101000111010011010101110101000100010010011011010100100010010001001011101011100110111100010010011110011011001101110000010101001100000001001010000111001001110111010010101101100100100011011111000101000111101010010000111111000101110011000101101100000011111100000001000010101000000000010111111000100111100110010011000000100000000100001001111000101110000101000101110110110011010000011111001000100010111100111101111111010010011010101001111111010101011101101101011001101001111101101001100011100111110100011110000001001100001100001000010001000100001010010010101001101100001110010101100101110000011101001101110100101101100001010001001001010001011001100011101100011010010110101100110110111100010100100000000100000011001001011010101000010101001010110110010100011101100100011100100000011",
						5),
				new GAExpertModified(
						0,
						"10010100111100010101101011100010001110000001101110100010000000001001010001100101101011") };

		// "1111010001100010101000101101000011001100100010001000101101100000111111"
		// 1000011001000100000010001111101110100100110101001010000011001001111111

		IEngine engine;
		if (!IMPERFECT)
		{
			engine = new RoundRobinEngine(experts, NO_OF_ROUNDS,
					SCORING_SYSTEM, 100);
		}
		else
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
			System.out.println(i + " - " + hist[i]);
		}
		System.out.println(total);
		if (PLOT_GRAPHS)
		{
			engine.plotResults();
			engine.plotPerformance();
		}
	}
}
