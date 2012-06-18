package nsga_expert;

import static common.Settings.NO_OF_ROUNDS;
import static common.Settings.SCORING_SYSTEM;
import static nsga_expert.GASettings.RUNS_PER_EVALUATION;
import agent.IExpert;
import de.uka.aifb.com.jnsga2.FitnessFunction;
import de.uka.aifb.com.jnsga2.Individual;
import engine.IEngine;
import engine.RoundRobinEngine;
import expert.AlternateCCDExpert;
import expert.CooperateExpert;
import expert.DefectExpert;
import expert.GAExpert;
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
import expert.titfortat.TitForTatExpert;

public class GAFitnessOwnScore implements FitnessFunction
{
	@Override
	public double evaluate(Individual paramIndividual)
	{
		if (paramIndividual == null)
		{
			throw new IllegalArgumentException(
					"'paramIndividual' must not be null.");
		}
		if (!(paramIndividual instanceof GAIndividual))
		{
			throw new IllegalArgumentException(
					"'paramIndividual' must be of type 'GAIndividual'.");
		}

		GAIndividual individual = (GAIndividual) paramIndividual;
		GAExpert expert = individual.getExpert();

		String[] strats = { new GrudgerExpert(0).getName(),
				new PavlovExpert(0).getName(), new CooperateExpert(0).getName() };

		IExpert[] experts = {

		new EEEDecProb(0, strats), new EEEFixedProb(0, strats, 0.3),
				new TitForTatExpert(0), new RandomExpert(0),
				new DefectExpert(0), new PavlovExpert(0),
				new RemorsefulProberExpert(0, 0.2), new SoftGrudgerExpert(0),
				new ProbableExpert(0), new CooperateExpert(0),
				new SoftMajorityExpert(0), new HardMajorityExpert(0),
				new AlternateCCDExpert(0), new HardTitforTatExpert(0), expert };

		IEngine engine = new RoundRobinEngine(experts, NO_OF_ROUNDS,
				SCORING_SYSTEM, RUNS_PER_EVALUATION);

		/**** IMPERFECT ***/
		// engine = new ImperfectRoundRobinEngine(experts, NO_OF_ROUNDS,
		// SCORING_SYSTEM, 1, 0.2);
		/*******/

		engine.run();
		double averageScore = engine.getScore(expert.getName());

		if (averageScore > 97)
		{
			System.err.println(individual.getExpert().getCodebit());
		}
		return -averageScore;
	}
}
