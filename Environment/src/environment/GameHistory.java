package environment;

import java.util.Collection;
import java.util.Stack;

public class GameHistory {
	private Collection<boolean[]> history;
	private ScoringSystem scoringSystem;

	public ScoringSystem getScoringSystem() {
		return scoringSystem;
	}

	public GameHistory(ScoringSystem scoringSystem) {
		this.scoringSystem = scoringSystem;
		history = new Stack<boolean[]>();
	}

	public int getNumberOfMoves() {
		return history.size();
	}

	public void newMove(boolean agent1, boolean agent2) {
		boolean[] move = { agent1, agent2 };
		history.add(move);
	}

	/***
	 * Returns last move made
	 * 
	 * @return
	 */
	public boolean[] getLastMove() {
		if (history.isEmpty()) {
			return null;
		}
		return ((Stack<boolean[]>) history).peek();
	}

	/***
	 * Returns complete history
	 * 
	 * @return
	 */
	public Collection<boolean[]> getHistory() {
		return history;
	}

	public boolean getPlayerLastMove(int playerNo) {
		return ((Stack<boolean[]>) history).peek()[playerNo - 1];
	}

	public boolean getOtherPlayerLastMove(int playerNo) {
		return ((Stack<boolean[]>) history).peek()[playerNo % 2];
	}
	
	
}
