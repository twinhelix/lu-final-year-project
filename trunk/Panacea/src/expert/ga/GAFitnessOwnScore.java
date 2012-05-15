package expert.ga;

import de.uka.aifb.com.jnsga2.FitnessFunction;
import de.uka.aifb.com.jnsga2.Individual;

public class GAFitnessOwnScore implements FitnessFunction
{
	@Override
	public double evaluate(Individual paramIndividual)
	{
		GAIndividual individual = (GAIndividual) paramIndividual;
		
		return individual.getHistory().getCurrentResult()[individual.getPlayerNumber()-1];
	}

}
