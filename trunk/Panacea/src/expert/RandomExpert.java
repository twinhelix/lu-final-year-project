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
		double choice = Math.random();
		if (choice < 0.5)
			return true;
		else
			return false;
	}

}
