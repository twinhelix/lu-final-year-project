package eee_nsga;

import static common.Settings.NO_OF_ROUNDS;
import static common.Settings.SCORING_SYSTEM;
import static eee_nsga.EEESettings.RUNS_PER_EVALUATION;
import agent.IExpert;
import de.uka.aifb.com.jnsga2.FitnessFunction;
import de.uka.aifb.com.jnsga2.Individual;
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
import expert.titfortat.HardTitforTatExpert;
import expert.titfortat.RemorsefulProberExpert;
import expert.titfortat.SuspiciousTitForTatExpert;
import expert.titfortat.TitForTatExpert;
import expert.titfortat.TitForTwoTatExpert;

public class EEEOwnScore implements FitnessFunction
{
	@Override
	public double evaluate(Individual paramIndividual)
	{
		if (paramIndividual == null)
		{
			throw new IllegalArgumentException(
					"'paramIndividual' must not be null.");
		}
		if (!(paramIndividual instanceof EEEIndividual))
		{
			throw new IllegalArgumentException(
					"'paramIndividual' must be of type 'EEEIndividual'.");
		}

		EEEIndividual individual = (EEEIndividual) paramIndividual;
		EEEDecProb expert = individual.getExpert();

		IExpert[] experts = {
				new TitForTatExpert(0),
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
						3), expert };

		double totalScore = 0d;
		for (int i = 0; i < RUNS_PER_EVALUATION; i++)
		{
			RoundRobinEngine engine = new RoundRobinEngine(experts,
					NO_OF_ROUNDS, SCORING_SYSTEM);
			engine.run();
			totalScore += engine.getScore(expert.getName());
		}

		double averageScore = totalScore / RUNS_PER_EVALUATION;
		// if (averageScore > 95)
		// {
		// System.err.println(expert.getCodebit());
		// }

		return -averageScore;
	}

}
