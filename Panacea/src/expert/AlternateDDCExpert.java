package expert;

import environment.GameHistory;

public class AlternateDDCExpert extends AbstractExpert
{

	/**
	 * Alternate strategy - always play alternating defect/defect/co-operate.
	 * 
	 * @param playerNo
	 */
	public AlternateDDCExpert(int playerNo)
	{
		super(playerNo);
		initialize();
	}

	@Override
	public void initialize()
	{
	}

	@Override
	public String getName()
	{
		return "Alternate DDC Expert";
	}

	@Override
	public boolean move(GameHistory history)
	{
		if (history.getNumberOfMoves() % 3 == 0
				|| history.getNumberOfMoves() % 3 == 1)
			return false;
		else
			return true;
	}
}