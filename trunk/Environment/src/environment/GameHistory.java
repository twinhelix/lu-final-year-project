package environment;

import java.util.Collection;
import java.util.Stack;

public class GameHistory {
	Collection<boolean[]> history;

	public GameHistory() {
		history = new Stack<boolean[]>();
	}

	public int getNumberOfMoves() {
		return history.size();
	}

	public void newMove(boolean agent1, boolean agent2) {
		boolean[] move = { agent1, agent2 };
		history.add(move);
	}

	public boolean[] getLastMove() {
		if (history.isEmpty()) {
			return null;
		}
		return ((Stack<boolean[]>) history).peek();
	}

	public Collection<boolean[]> getHistory() {
		return history;
	}
}
