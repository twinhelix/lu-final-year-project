package nsga_expert;

import de.uka.aifb.com.jnsga2.FitnessFunction;
import de.uka.aifb.com.jnsga2.Individual;
import de.uka.aifb.com.jnsga2.NSGA2;
import environment.GameHistory;
import expert.GAExpert;

public class GAIndividual extends Individual
{

	private int CODEBIT_LENGTH;
	private GAExpert expert;
	private double[] fitnessValues;
	private boolean useSingleCrossover = false;

	public GAIndividual(NSGA2 nsga2, int playerNo)
	{
		this(nsga2, playerNo, 3);
	}

	public GAIndividual(NSGA2 nsga2, int playerNo, int history_depth)
	{
		super(nsga2);
		expert = new GAExpert(playerNo, null, history_depth);
		CODEBIT_LENGTH = (int) Math.pow(4, history_depth) + 2 * history_depth;
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
		GAIndividual clone = new GAIndividual(nsga2, expert.getPlayerNumber());
		clone.expert.setCodebit(expert.getCodebit());
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

		GAIndividual otherGAIndividual = ((GAIndividual) otherIndividual);

		if (nsga2 != otherGAIndividual.nsga2)
		{
			throw new IllegalArgumentException(
					"Both individuals must belong to the same NSGA-II instance.");
		}

		// execute crossover?
		if (Math.random() < nsga2.getNSGA2Configuration()
				.getCrossoverProbability())
		{
			String other_codebit = otherGAIndividual.getExpert().getCodebit();
			String own_codebit = expert.getCodebit();
			String dummy = own_codebit;
			if (useSingleCrossover)
			{
				// crossover in front of 'randomIndex'
				// Single Point Crossover
				int sigma_share = (int) (Math.random() * CODEBIT_LENGTH);

				own_codebit = own_codebit.substring(0, sigma_share)
						+ other_codebit.substring(sigma_share, other_codebit
								.length());
				expert.setCodebit(own_codebit);

				other_codebit = other_codebit.substring(0, sigma_share)
						+ dummy.substring(sigma_share, dummy.length());
			} else
			{
				int start = (int) (Math.random() * CODEBIT_LENGTH);
				int finish = (int) (Math.random() * CODEBIT_LENGTH);

				if (start > finish)
				{
					int temp = start;
					start = finish;
					finish = temp;
				}

				own_codebit = own_codebit.substring(0, start)
						+ other_codebit.substring(start, finish)
						+ own_codebit.substring(finish, own_codebit.length());
				other_codebit = other_codebit.substring(0, start)
						+ dummy.substring(start, finish)
						+ other_codebit.substring(finish, other_codebit
								.length());
			}
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

	/**
	 * Mutates this individual.
	 * 
	 * After mutation, the fitness values are updated.
	 */
	@Override
	protected void mutate()
	{
		// Mutate a random bit of the 70 bit
		String codebit = expert.getCodebit();
		int mutatebit = (int) (Math.random() * 70);
		codebit = negateBit(codebit, mutatebit);
		expert.setCodebit(codebit);

		// update fitness values
		for (int i = 0; i < fitnessValues.length; i++)
		{
			fitnessValues[i] = nsga2.getNSGA2Configuration()
					.getFitnessFunction(i).evaluate(this);
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

	public GameHistory getHistory()
	{
		return expert.getHistory();
	}

	public int getPlayerNumber()
	{
		return expert.getPlayerNumber();
	}

	public GAExpert getExpert()
	{
		return expert;
	}
}
