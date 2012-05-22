package expert.titfortat;

import java.util.Stack;

import environment.GameHistory;
import expert.AbstractExpert;

public class TitForTwoTatExpert extends AbstractExpert
{

	/***
	 * Like Tit For Tat except that opponent must make the same choice twice in
	 * row before it is reciprocated.
	 * 
	 * @param playerNo
	 */
	public TitForTwoTatExpert(int playerNo)
	{
		super(playerNo);
	}

	public String getName()
	{
		return ("Tit-for-2-Tat Expert");
	}

	@Override
	public boolean move(GameHistory history)
	{

		// Cooperate on first 2 moves
		if (history.getNumberOfMoves() < 2)
		{
			return true;
		}

		// If opponent defects twice in a row, retaliate
		
		Stack<boolean[]> moves = new Stack<boolean[]>();
		moves.addAll(history.getHistory());

		boolean[] lastMove = moves.pop();
		boolean[] last2Move = moves.pop();

		if (!lastMove[playerNo % 2] && !last2Move[playerNo % 2])
		{
			return false;
		}

		return true;
	}
}