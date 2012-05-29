package expert.titfortat;

import java.util.Stack;

import environment.GameHistory;
import expert.AbstractExpert;

public class HardTitforTatExpert extends AbstractExpert
{

	public HardTitforTatExpert(int playerNo)
	{
		super(playerNo);
	}

	@Override
	public String getName()
	{
		return "Hard Tit-for-Tat";
	}

	@Override
	public boolean move(GameHistory history)
	{
		if (history.getNumberOfMoves() == 0)
		{
			return true;
		}

		Stack<boolean[]> moves = new Stack<boolean[]>();
		moves.addAll(history.getHistory());
		for (int i = 0; i < 3; i++)
		{
			if (moves.size() > 0)
			{
				boolean[] lastMove = moves.pop();
				if (!lastMove[this.getPlayerNumber() % 2])
					return false;
			}
		}

		return true;
	}

}
