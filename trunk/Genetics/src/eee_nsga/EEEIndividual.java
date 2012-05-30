package eee_nsga;

import java.util.Random;

import utils.ExpertsDictionary;
import agent.IExpert;
import de.uka.aifb.com.jnsga2.FitnessFunction;
import de.uka.aifb.com.jnsga2.Individual;
import de.uka.aifb.com.jnsga2.NSGA2;
import expert.eee.EEEDecProb;

public class EEEIndividual extends Individual
{

	private EEEDecProb expert;
	private double[] fitnessValues;
	private int poolSize;
	private Random random;

	public EEEIndividual(NSGA2 nsga2, int playerNo)
	{
		this(nsga2, playerNo, 4);
	}

	public EEEIndividual(NSGA2 nsga2, int playerNo, int poolSize)
	{
		super(nsga2);
		random = new Random();
		expert = new EEEDecProb(playerNo, poolSize);
		this.poolSize = poolSize;
		fitnessValues = new double[nsga2.getNSGA2Configuration()
				.getNumberOfObjectives()];
		for (int i = 0; i < fitnessValues.length; i++)
		{
			fitnessValues[i] = nsga2.getNSGA2Configuration()
					.getFitnessFunction(i).evaluate(this);
		}
	}

	@Override
	protected Individual createClonedIndividual()
	{
		EEEIndividual clone = new EEEIndividual(nsga2,
				expert.getPlayerNumber(), poolSize);
		clone.expert.reassignPool(expert.getExperts());
		return clone;
	}

	@Override
	protected void crossover(Individual otherIndividual)
	{
		if (otherIndividual == null)
		{
			throw new IllegalArgumentException(
					"'otherIndividual' must not be null.");
		}

		EEEIndividual otherGAIndividual = ((EEEIndividual) otherIndividual);

		if (nsga2 != otherGAIndividual.nsga2)
		{
			throw new IllegalArgumentException(
					"Both individuals must belong to the same NSGA-II instance.");
		}

		// execute crossover?
		if (Math.random() < nsga2.getNSGA2Configuration()
				.getCrossoverProbability())
		{
			// Get the 2 poitns
			IExpert[] ourExperts = expert.getExperts();
			IExpert[] theirExperts = otherGAIndividual.expert.getExperts();

			int len = expert.getExperts().length;
			int start = random.nextInt(len + 1);
			int end = random.nextInt(len + 1);

			// swap
			if (start > end)
			{
				int temp = start;
				start = end;
				end = temp;
			}

			// 2-Point Crossover

			IExpert[][] result = twoPointCrossover(ourExperts, theirExperts,
					start, end);
			expert.reassignPool(result[0]);
			otherGAIndividual.expert.reassignPool(result[1]);

			// update fitness values
			for (int i = 0; i < fitnessValues.length; i++)
			{
				FitnessFunction fitnessFunction = nsga2.getNSGA2Configuration()
						.getFitnessFunction(i);

				fitnessValues[i] = fitnessFunction.evaluate(this);

				otherGAIndividual.fitnessValues[i] = fitnessFunction
						.evaluate(otherGAIndividual);
			}
		}
	}

	private IExpert[][] twoPointCrossover(IExpert[] ourExperts,
			IExpert[] theirExperts, int start, int end)
	{
		IExpert[] first = ourExperts.clone();
		IExpert[] second = ourExperts.clone();
		for (int i = start; i < end; i++)
		{
			ourExperts[i] = second[i];
			theirExperts[i] = first[i];
		}
		IExpert[][] ret = { ourExperts, theirExperts };

		return ret;
	}

	/**
	 * Mutates this individual.
	 * 
	 * After mutation, the fitness values are updated.
	 */
	@Override
	protected void mutate()
	{
		boolean mutate = false;
		// Mutate an expert in the pool to a random one
		if (Math.random() < nsga2.getNSGA2Configuration()
				.getMutationProbability())
		{
			IExpert[] experts = expert.getExperts();
			ExpertsDictionary ed = new ExpertsDictionary(
					expert.getPlayerNumber(), 0.2);
			IExpert[] changeExpert = ed.getRandomExperts(1);
			int bit = (int) (Math.random() * experts.length);

			experts[bit] = changeExpert[0];
			expert.reassignPool(experts);
			mutate = true;
		}

		// update fitness values
		if (mutate)
		{
			for (int i = 0; i < fitnessValues.length; i++)
			{
				fitnessValues[i] = nsga2.getNSGA2Configuration()
						.getFitnessFunction(i).evaluate(this);
			}
		}
	}

	public String negateBit(String str, int index)
	{
		int value = Character.getNumericValue(str.charAt(index));
		value = 1 - value;

		str = str.substring(0, index) + value
				+ str.substring(index + 1, str.length());

		return str;
	}

	@Override
	public double getFitnessValue(int paramInt)
			throws IndexOutOfBoundsException
	{
		return fitnessValues[paramInt];
	}

	public int getPlayerNumber()
	{
		return expert.getPlayerNumber();
	}

	public EEEDecProb getExpert()
	{
		return expert;
	}
}
