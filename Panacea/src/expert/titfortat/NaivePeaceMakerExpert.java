package expert.titfortat;

import environment.GameHistory;

public class NaivePeaceMakerExpert extends TitForTatExpert
{

	private double prob;

	/***
	 * Repeat opponent's last choice (ie Tit For Tat), but sometimes make peace
	 * by co-operating in lieu of defecting.
	 * 
	 * @param playerNo
	 * @param prob
	 */
	public NaivePeaceMakerExpert(int playerNo, double prob)
	{
		super(playerNo);
		this.prob = prob;
	}

	@Override
	public String getName()
	{
		return ("Naive Peace Maker Expert: " + prob + "%");
	}

	public boolean move(GameHistory history)
	{

		// Repeat opponent's last choice (ie Tit For Tat), but sometimes probe
		// by cooperate in lieu of defecting.
		boolean result = super.move(history);

		if (!result)
		{
			if (Math.random() < prob)
				return true;
		}
		return result;

	}
}
