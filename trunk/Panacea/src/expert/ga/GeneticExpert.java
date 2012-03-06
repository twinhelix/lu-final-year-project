package expert.ga;

import static utils.Encodings.*;
import environment.GameHistory;
import expert.AbstractExpert;
import utils.Encodings;

public class GeneticExpert extends AbstractExpert
{
	/*
	 * Code the particular behavioral sequence as a 3-letter string. – e.g RRR
	 * represents the sequence where both parties cooperated over the first
	 * three moves– SSP : The first player was played for sucker twice and
	 * defected.
	 */
	// - CC or R for Reward
	// - CD or S for Sucker
	// - DC or T for Temptation
	// - DD or P for Penalty

	private int initialPop;

	public GeneticExpert(int playerNo)
	{
		super(playerNo);
	}

	@Override
	public String getName()
	{
		return "NSGA-II Expert";
	}

	@Override
	public boolean move(GameHistory history)
	{
		Encodings e = R;

		return false;
	}

	private int encode(Encodings[] strat)
	{
		int seq = 0;
		for (int i = 0; i < strat.length; i++)
		{
			seq = (seq + strat[strat.length - i - 1].ordinal()
					* ((int) Math.pow(4, i)));
		}
		return seq;
	}

	private Encodings[] decode(int seq)
	{
		Encodings[] strat = new Encodings[3];

		return strat;
	}
}
