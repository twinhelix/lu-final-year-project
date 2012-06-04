package expert;

import environment.GameHistory;

public class PavlovRandomExpert extends PavlovExpert
{
	private double prob;

	/**
	 * Pavlov / Random (repeat last choice if good outcome and Random) - If 5 or
	 * 3 points scored in the last round then repeat last choice, but sometimes
	 * make random choices.*
	 * 
	 * @param playerNo
	 * @param prob
	 */
	public PavlovRandomExpert(int playerNo, double prob)
	{
		super(playerNo);
		this.prob = prob;
	}

	@Override
	public String getName()
	{
		return super.getName() + " Random";
	}

	@Override
	public boolean move(GameHistory history)
	{
		boolean move = super.move(history);

		if (super.repeat)
		{
			if (Math.random() < prob)
			{
				return !move;
			}
		}
		return move;
	}
}
