package expert.titfortat;

import java.util.Collection;
import java.util.Stack;

import environment.GameHistory;
import expert.AbstractExpert;

public class TitForTwoTatExpert extends AbstractExpert {

	/***
	 * Like Tit For Tat except that opponent must make the same choice twice in
	 * row before it is reciprocated.
	 * 
	 * @param playerNo
	 */
	public TitForTwoTatExpert(int playerNo) {
		super(playerNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean move(GameHistory history) {

		// Cooperate on first 2 moves
		if (history.getNumberOfMoves() < 2) {
			return true;
		}

		Collection<boolean[]> hist = (Collection<boolean[]>) ((Stack<boolean[]>) history
				.getHistory()).clone();

		Stack<boolean[]> moves = (Stack<boolean[]>) hist;

		boolean[] lastMove = moves.pop();
		boolean[] last2Move = moves.pop();

		if (lastMove[playerNo % 2] == last2Move[playerNo % 2]) {
			return lastMove[playerNo % 2];
		}

		return lastMove[playerNo - 1];
	}
}