package expert.ga;

import de.uka.aifb.com.jnsga2.FitnessFunction;
import de.uka.aifb.com.jnsga2.Individual;

public class GAFitnessOpponentScore implements FitnessFunction
{
	@Override
	public double evaluate(Individual paramIndividual)
	{
		GAIndividual individual = (GAIndividual) paramIndividual;
		
		individual.getExpert();
		
		return 90d;
		// return individual.getHistory().getCurrentResult()[individual
		// .getPlayerNumber() % 2];
	}

}
