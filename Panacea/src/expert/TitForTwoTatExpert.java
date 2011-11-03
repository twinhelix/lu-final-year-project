package expert;

import java.util.Collection;
import java.util.Stack;

import environment.GameHistory;

public class TitForTwoTatExpert extends AbstractExpert {

	public TitForTwoTatExpert(int playerNo) {
		super(playerNo);
		// TODO Auto-generated constructor stub
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

		// Defect if the other player defected twice in a row
		boolean[] lastMove = moves.pop();
		if (!lastMove[playerNo % 2]) {
			lastMove = moves.pop();
			if (!lastMove[playerNo % 2]) {
				return false;
			}
		}
		return true;
	}
}