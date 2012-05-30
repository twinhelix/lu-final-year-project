package experiments;

import static common.Settings.NO_OF_ROUNDS;
import static common.Settings.SCORING_SYSTEM;
import agent.IExpert;
import engine.RoundRobinEngine;
import expert.AlternateCCDExpert;
import expert.CooperateExpert;
import expert.DefectExpert;
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

public class EEEEngine
{
	private static boolean PLOT_GRAPHS = false;

	public static void main(String[] args)
	{

		String[] strats = { new SoftMajorityExpert(0).getName(),
				new TitForTwoTatExpert(0).getName(),
				new CooperateExpert(0).getName(), new PavlovExpert(0).getName() };

		IExpert[] experts = { new TitForTatExpert(0),
				new EEEDecProb(0, strats), new EEEFixedProb(0, strats, 0.3),
				new RandomExpert(0), new DefectExpert(0), new PavlovExpert(0),
				new RemorsefulProberExpert(0, 0.2), new SoftGrudgerExpert(0),
				new ProbableExpert(0), new CooperateExpert(0),
				new TitForTwoTatExpert(0), new SuspiciousTitForTatExpert(0),
				new SoftMajorityExpert(0), new HardMajorityExpert(0),
				new AlternateCCDExpert(0), new HardTitforTatExpert(0) };

		RoundRobinEngine engine = new RoundRobinEngine(experts, NO_OF_ROUNDS,
				SCORING_SYSTEM, 10000);

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
