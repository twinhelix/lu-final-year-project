package experiments;

import static common.Settings.NO_OF_ROUNDS;
import static common.Settings.SCORING_SYSTEM;
import agent.IExpert;
import engine.RoundRobinEngine;
import expert.AlternateCCDExpert;
import expert.CooperateExpert;
import expert.DefectExpert;
import expert.GAExpert;
import expert.GAExpertModified;
import expert.GrudgerExpert;
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

	public static void main(String[] args)
	{
		String[] strats = { new GrudgerExpert(0).getName(),
				new TitForTatExpert(0).getName(),
				new PavlovExpert(0).getName(),
				new CooperateExpert(0).getName(), };

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
				new GAExpert(
						0,
						"1111000000001001011100000111101001110000000100010000101010010000111111",
						3),
				new ProbableExpert(0),
				new GAExpertModified(
						0,
						"10110010000010000010000001001010010000011001001000001010000000001011011011101011100011") };

		// "1111010001100010101000101101000011001100100010001000101101100000111111"
		// 1000011001000100000010001111101110100100110101001010000011001001111111
		// - REALLY GOOD
		// ImperfectRoundRobinEngine engine = new
		// ImperfectRoundRobinEngine(experts, NO_OF_ROUNDS,
		// SCORING_SYSTEM, 0.2);

		RoundRobinEngine engine = new RoundRobinEngine(experts, NO_OF_ROUNDS,
				SCORING_SYSTEM, 100);

		engine.run();
		System.out.println();
		engine.showTally();

		if (PLOT_GRAPHS)
		{
			engine.plotResults();
			engine.plotPerformance();
		}

		// GAExpert ge = new GAExpert(0);

		// Game g = new Game(new DefectExpert(1), new RandomExpert(2), 200,
		// scoringSystem);
		// double[] x = g.run();
		// System.out.println(x[0] + " " + x[1]);
	}
}
