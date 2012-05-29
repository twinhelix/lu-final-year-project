package expert;

import environment.GameHistory;

public class SoftMajorityExpert extends AbstractExpert
{
	public SoftMajorityExpert(int playerNo)
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
		return "Soft Majority Expert";
	}

	@Override
	public boolean move(GameHistory history)
	{
		if (history.getNumberOfMoves() == 0)
		{
			return true;
		}
		int coops = getCooperates(history);
		if (coops < history.getNumberOfMoves() / 2)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	private int getCooperates(GameHistory history)
	{
		int coops = 0;
		for (boolean[] move : history.getHistory())
		{
			if (move[this.getPlayerNumber() % 2])
				coops++;
		}
		return coops;
	}

}
