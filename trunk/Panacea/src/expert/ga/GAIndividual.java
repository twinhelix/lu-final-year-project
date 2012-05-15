package expert.ga;

import agent.IExpert;
import de.uka.aifb.com.jnsga2.FitnessFunction;
import de.uka.aifb.com.jnsga2.Individual;
import de.uka.aifb.com.jnsga2.NSGA2;
import environment.GameHistory;

public class GAIndividual extends Individual
{
	
	private final int CODEBIT_LENGTH = 70;
	private GAExpert expert;

	private int sigma_share;

	public GAIndividual(NSGA2 nsga2, int playerNo)
	{
		super(nsga2);
		expert = new GAExpert(playerNo, true);
		sigma_share = CODEBIT_LENGTH / 2;
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
		GAExpert otherExpert = (GAExpert) ((GAIndividual) paramIndividual)
				.getExpert();
		String other_codebit = otherExpert.getCodebit();
		String own_codebit = expert.getCodebit();

		// Single Point Crossover

		own_codebit = own_codebit.substring(0, sigma_share)
				+ other_codebit.substring(sigma_share, other_codebit.length());

		expert.setCodebit(own_codebit);
	}

	@Override
	protected void mutate()
	{
		// Mutate random bit of the 70 bits
		String codebit = expert.getCodebit();
		int mutatebit = (int) (Math.random() * 70);
		codebit = negateBit(codebit, mutatebit);
		expert.setCodebit(codebit);
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
		FitnessFunction fitnessFunction = nsga2.getNSGA2Configuration()
				.getFitnessFunction(paramInt);
		return fitnessFunction.evaluate(this);
	}

	public GameHistory getHistory()
	{
		return expert.getHistory();
	}

	public int getPlayerNumber()
	{
		return expert.getPlayerNumber();
	}

	public IExpert getExpert()
	{
		return expert;
	}
}
