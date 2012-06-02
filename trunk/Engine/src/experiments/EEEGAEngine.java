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
import expert.HardMajorityExpert;
import expert.PavlovExpert;
import expert.ProbableExpert;
import expert.RandomExpert;
import expert.SoftGrudgerExpert;
import expert.SoftMajorityExpert;
import expert.eee.EEEDecProb;
import expert.eee.EEEFixedProb;
import expert.eee.EEEGADecProb;
import expert.titfortat.HardTitforTatExpert;
import expert.titfortat.RemorsefulProberExpert;
import expert.titfortat.SuspiciousTitForTatExpert;
import expert.titfortat.TitForTatExpert;
import expert.titfortat.TitForTwoTatExpert;

public class EEEGAEngine
{
	private static boolean PLOT_GRAPHS = false;
	private static boolean IMPERFECT = true;

	public static void main(String[] args)
	{

		String[] strats1 = { new SoftMajorityExpert(0).getName(),
				new TitForTwoTatExpert(0).getName(),
				new PavlovExpert(0).getName(), new CooperateExpert(0).getName() };

		String[] strats = { new TitForTatExpert(0).getName(),
				new TitForTwoTatExpert(0).getName(),
				new SoftMajorityExpert(0).getName() };

		IExpert[] experts = {
				new TitForTatExpert(0),
				new EEEDecProb(0, strats),
				new EEEGADecProb(0, strats),
				new EEEFixedProb(0, strats, 0.3),
				new RandomExpert(0),
				new DefectExpert(0),
				new PavlovExpert(0),
				new RemorsefulProberExpert(0, 0.2),
				new SoftGrudgerExpert(0),
				new ProbableExpert(0),
				new CooperateExpert(0),
				new TitForTwoTatExpert(0),
				new SoftMajorityExpert(0),
				new SuspiciousTitForTatExpert(0),
				new HardMajorityExpert(0),
				new AlternateCCDExpert(0),
				new HardTitforTatExpert(0),
				new GAExpert(
						0,
						"1111000000001001011100000111101001110000000100010000101010010000111111",
						3) };

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

		if (PLOT_GRAPHS)
		{
			engine.plotResults();
			engine.plotPerformance();
		}
	}
}
