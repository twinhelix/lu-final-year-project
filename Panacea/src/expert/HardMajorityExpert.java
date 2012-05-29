package expert;

import environment.GameHistory;

public class HardMajorityExpert extends AbstractExpert
{
	public HardMajorityExpert(int playerNo)
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
		return "Hard Majority Expert";
	}

	@Override
	public boolean move(GameHistory history)
	{
		if (history.getNumberOfMoves() == 0)
		{
			return false;
		}
		int defects = getDefects(history);
		if (defects < history.getNumberOfMoves() / 2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private int getDefects(GameHistory history)
	{
		int defects = 0;
		for (boolean[] move : history.getHistory())
		{
			if (!move[this.getPlayerNumber() % 2])
				defects++;
		}
		return defects;
	}

}
