package expert.ga;

import de.uka.aifb.com.jnsga2.FitnessFunction;
import de.uka.aifb.com.jnsga2.Individual;
import de.uka.aifb.com.jnsga2.NSGA2;
import environment.GameHistory;

public class GAIndividual extends Individual
{
	GAExpert expert;

	public GAIndividual(NSGA2 nsga2, int playerNo)
	{
		super(nsga2);
		expert = new GAExpert(playerNo);
	}

	@Override
	protected Individual createClonedIndividual()
	{
		Object clone = this.clone();
		return (Individual) clone;
	}

	@Override
	protected void crossover(Individual paramIndividual)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public double getFitnessValue(int paramInt)
			throws IndexOutOfBoundsException
	{
		FitnessFunction fitnessFunction = nsga2.getNSGA2Configuration()
				.getFitnessFunction(paramInt);
		return fitnessFunction.evaluate(this);
	}

	@Override
	protected void mutate()
	{
		// TODO Auto-generated method stub

	}

	protected GameHistory getHistory()
	{
		return expert.getHistory();
	}

	protected int getPlayerNumber()
	{
		return expert.getPlayerNumber();
	}

}
