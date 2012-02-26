package expert.titfortat;

import environment.GameHistory;

public class SuspiciousTitForTat extends TitForTatExpert
{

	/***
	 * Suspicious Tit For Tat - As for Tit For Tat except begins by defecting.
	 * 
	 * @param playerNo
	 */
	public SuspiciousTitForTat(int playerNo)
	{
		super(playerNo);
	}

	public String getName()
	{
		return ("Suspicious Tit-for-Tat Expert");
	}

	@Override
	public boolean move(GameHistory history)
	{

		if (history.getNumberOfMoves() == 0)
		{
			return false;
		}
		else
			return super.move(history);
	}

}
