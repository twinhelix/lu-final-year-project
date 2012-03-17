package expert.ga;

import environment.GameHistory;
import expert.AbstractExpert;

public class GAExpert extends AbstractExpert
{
	/*
	 * Code the particular behavioral sequence as a 3-letter string. – e.g RRR
	 * represents the sequence where both parties cooperated over the first
	 * three moves– SSP : The first player was played for sucker twice and
	 * defected.
	 */
	// - CC or R for Reward
	// - CD or S for Sucker
	// - DC or T for Temptation
	// - DD or P for Penalty

	/*
	 * Since solutions are represented using a bit string, we have used the
	 * usual binary tournament selection operator [25], a single-point crossover
	 * operator, and a bitwise mutation operator [2]. In all simulations, we
	 * have used a crossover probability of 0.9 and mutation probability of
	 * 1/70, so that, on an average, only one bit in a string of 70 bits gets
	 * mutated at a time.
	 */

	private GameHistory history = null;

	public GAExpert(int playerNo)
	{
		super(playerNo);

	}

	@Override
	public String getName()
	{
		return "GA Expert";
	}

	@Override
	public boolean move(GameHistory history)
	{
		this.history = history;

		return false;
	}

	public GameHistory getHistory()
	{
		return history;
	}

}
