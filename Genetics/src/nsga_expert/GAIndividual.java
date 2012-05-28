package nsga_expert;

import de.uka.aifb.com.jnsga2.FitnessFunction;
import de.uka.aifb.com.jnsga2.Individual;
import de.uka.aifb.com.jnsga2.NSGA2;
import environment.GameHistory;
import expert.GAExpert;

public class GAIndividual extends Individual
{

	private final int CODEBIT_LENGTH = 70;
	private GAExpert expert;
	private double[] fitnessValues;

	private int sigma_share;

	public GAIndividual(NSGA2 nsga2, int playerNo)
	{
		this(nsga2, playerNo, true);
	}

	public GAIndividual(NSGA2 nsga2, int playerNo, boolean learning)
	{
		super(nsga2);
		expert = new GAExpert(playerNo, learning);
		sigma_share = CODEBIT_LENGTH / 2;

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
			// crossover in front of 'randomIndex'
			String other_codebit = otherGAIndividual.getExpert().getCodebit();
			String own_codebit = expert.getCodebit();

			// Single Point Crossover

			own_codebit = own_codebit.substring(0, sigma_share)
					+ other_codebit.substring(sigma_share,
							other_codebit.length());

			expert.setCodebit(own_codebit);

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
		boolean mutated = false;
		// Mutate random bit of the 70 bits
		if (Math.random() < nsga2.getNSGA2Configuration()
				.getMutationProbability())
		{
			String codebit = expert.getCodebit();
			int mutatebit = (int) (Math.random() * 70);
			codebit = negateBit(codebit, mutatebit);
			expert.setCodebit(codebit);
			mutated = true;
		}

		if (mutated)
		{
			// update fitness values
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
