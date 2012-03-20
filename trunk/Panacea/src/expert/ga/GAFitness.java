package expert.ga;

import de.uka.aifb.com.jnsga2.FitnessFunction;
import de.uka.aifb.com.jnsga2.Individual;

public class GAFitness implements FitnessFunction
{
	@Override
	public double evaluate(Individual paramIndividual)
	{
		GAIndividual individual = (GAIndividual) paramIndividual;

		return individual.getHistory().getPlayerLastScore(
				individual.getPlayerNumber());
	}

}