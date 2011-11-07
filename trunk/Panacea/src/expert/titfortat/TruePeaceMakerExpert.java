package expert.titfortat;

import java.util.Collection;
import java.util.Stack;

import environment.GameHistory;
import expert.AbstractExpert;

public class TruePeaceMakerExpert extends AbstractExpert {

	private double prob;

	/***
	 * Co-operate unless opponent defects twice in a row, then defect once, but
	 * sometimes make peace by co-operating in lieu of defecting.
	 * 
	 * @param playerNo
	 */
	public TruePeaceMakerExpert(int playerNo, double prob) {
		super(playerNo);
		this.prob = prob;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean move(GameHistory history) {
		if (history.getNumberOfMoves() < 2)
			return true;

		// Cooperate on first 2 moves
		if (history.getNumberOfMoves() < 2) {
			return true;
		}

		Collection<boolean[]> hist = (Collection<boolean[]>) ((Stack<boolean[]>) history
				.getHistory()).clone();

		Stack<boolean[]> moves = (Stack<boolean[]>) hist;

		boolean[] lastMove = moves.pop();
		boolean[] last2Move = moves.pop();

		if (!lastMove[playerNo % 2] && !last2Move[playerNo % 2]) {
			// defect, but with a chance of making peace
			if (Math.random() < prob)
				return true;
			return false;
		}
		return true;
	}
}
