package expert;

import environment.GameHistory;

public class RandomExpert extends AbstractExpert
{

	/**
	 * Random Expert generates a random move regardless of history
	 * 
	 * @param playerNo
	 */
	public RandomExpert(int playerNo)
	{
		super(playerNo);
	}

	public String getName()
	{
		return ("Random Expert");
	}

	public boolean move(GameHistory history)
	{
		return (Math.random() < 0.5);
	}
}
