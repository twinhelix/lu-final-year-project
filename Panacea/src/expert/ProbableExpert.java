package expert;

import java.util.Stack;

import environment.GameHistory;

/***
 * Expert cooperates to begin with, and then takes into account the last 10
 * moves, counts the outcomes of 3, then uses that as a percentage to cooperate.
 * 
 * @author HassassiN
 * 
 */
public class ProbableExpert extends AbstractExpert
{
	private int historyDepth = 10;

	public ProbableExpert(int playerNo)
	{
		super(playerNo);
	}

	public ProbableExpert(int playerNo, int historyDepth)
	{
		super(playerNo);
		this.historyDepth = historyDepth;
	}

	@Override
	public String getName()
	{
		return "ProbabalExpert";
	}

	@Override
	public boolean move(GameHistory history)
	{
		int moves = history.getNumberOfMoves();
		if (moves == 0)
		{
			return true;
		}
		else if (moves < historyDepth)
		{
			int coops = getCooperates(history, moves);
			double prob = ((double) coops) / moves;
			return (Math.random() < prob);
		}
		else
		{
			int coops = getCooperates(history, historyDepth);
			double prob = ((double) coops) / historyDepth;
			return (Math.random() < prob);
		}
	}

	private int getCooperates(GameHistory history, int depth)
	{
		int coops = 0;
		Stack<boolean[]> moves = new Stack<boolean[]>();
		moves.addAll(history.getHistory());
		boolean[] move;
		for (int i = 0; i < depth; i++)
		{
			move = moves.pop();
			if (move[getPlayerNumber() % 2])
			{
				coops++;
			}
		}
		return coops;
	}
}
