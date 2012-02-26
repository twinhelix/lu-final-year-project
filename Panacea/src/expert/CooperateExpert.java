package expert;

import environment.GameHistory;

public class CooperateExpert extends AbstractExpert
{
	/***
	 * Cooperate expert always cooperates regardless of history
	 * 
	 * @param playerNo
	 */
	public CooperateExpert(int playerNo)
	{
		super(playerNo);
	}

	public String getName()
	{
		return ("Always Cooperate Expert");
	}

	public boolean move(GameHistory history)
	{
		return true;
	}

}
