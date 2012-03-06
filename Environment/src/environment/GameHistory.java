package environment;

import java.util.Collection;
import java.util.Stack;

public class GameHistory
{
	private Collection<boolean[]> history;
	private ScoringSystem scoringSystem;
	private double[] result;

	public GameHistory(ScoringSystem scoringSystem)
	{
		this.scoringSystem = scoringSystem;
		history = new Stack<boolean[]>();
		result = new double[2];
	}

	public ScoringSystem getScoringSystem()
	{
		return scoringSystem;
	}

	public int getNumberOfMoves()
	{
		return history.size();
	}

	public void newMove(boolean agent1, boolean agent2)
	{
		boolean[] move = { agent1, agent2 };
		history.add(move);
		double[] res = scoringSystem.getPoints(move);
		result[0] += res[0];
		result[1] += res[1];
	}

	/***
	 * Returns last move made
	 * 
	 * @return
	 */
	public boolean[] getLastMove()
	{
		if (history.isEmpty())
		{
			return null;
		}
		return ((Stack<boolean[]>) history).peek();
	}

	/***
	 * Returns last move's scores
	 * 
	 * @return
	 */
	public double[] getLastMoveScores()
	{
		if (history.isEmpty())
		{
			return null;
		}
		return scoringSystem.getPoints(getLastMove());
	}

	/***
	 * Returns complete history
	 * 
	 * @return
	 */
	public Collection<boolean[]> getHistory()
	{
		return history;
	}

	public boolean getPlayerLastMove(int playerNo)
	{
		return ((Stack<boolean[]>) history).peek()[playerNo - 1];
	}

	public double getPlayerLastScore(int playerNo)
	{
		double[] lastScore = getLastMoveScores();
		if (lastScore != null)
		{
			return lastScore[playerNo - 1];
		} else
			return 0;
	}

	public boolean getOtherPlayerLastMove(int playerNo)
	{
		return ((Stack<boolean[]>) history).peek()[playerNo % 2];
	}

	public double getOtherPlayerLastScore(int playerNo)
	{
		double[] lastScore = getLastMoveScores();
		if (lastScore != null)
		{
			return lastScore[playerNo % 2];
		} else
			return 0;
	}

	public double[] getCurrentResult()
	{
		return result;
	}

}
