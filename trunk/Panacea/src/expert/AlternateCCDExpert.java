package expert;

import environment.GameHistory;

public class AlternateCCDExpert extends AbstractExpert
{

	/**
	 * Alternate strategy - always play alternating
	 * co-operate/co-operate/defect.
	 * 
	 * @param playerNo
	 */
	public AlternateCCDExpert(int playerNo)
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
			return true;
		else
			return false;
	}
}