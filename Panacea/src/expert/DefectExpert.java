package expert;

import environment.GameHistory;

public class DefectExpert extends AbstractExpert
{
	/***
	 * Defect Expert always defects no matter the situation regardless of
	 * history
	 * 
	 * @param playerNo
	 */
	public DefectExpert(int playerNo)
	{
		super(playerNo);
	}

	public String getName()
	{
		return ("Always Defect Expert");
	}

	public boolean move(GameHistory history)
	{
		return false;
	}
}
