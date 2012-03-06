package expert.ga;

import de.uka.aifb.com.jnsga2.Individual;
import de.uka.aifb.com.jnsga2.NSGA2;

public class GAIndividual extends Individual
{

	public GAIndividual(NSGA2 nsga2)
	{
		super(nsga2);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Individual createClonedIndividual()
	{
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void mutate()
	{
		// TODO Auto-generated method stub
		
	}

}
