package expert.ga;

import static utils.Encoding.P;
import static utils.Encoding.R;
import static utils.Encoding.S;
import static utils.Encoding.T;

import java.util.ArrayList;

import utils.Encoding;
import environment.GameHistory;
import expert.AbstractExpert;

public class GAExpert extends AbstractExpert
{
	/*
	 * Code the particular behavioral sequence as a 3-letter string. � e.g RRR
	 * represents the sequence where both parties cooperated over the first
	 * three moves SSP : The first player was played for sucker twice and
	 * defected.
	 */
	// - CC or R for Reward
	// - CD or S for Sucker
	// - DC or T for Temptation
	// - DD or P for Penalty

	/*
	 * Since solutions are represented using a bit string, we have used the
	 * usual binary tournament selection operator [25], a single-point crossover
	 * operator, and a bitwise mutation operator [2]. In all simulations, we
	 * have used a crossover probability of 0.9 and mutation probability of
	 * 1/70, so that, on an average, only one bit in a string of 70 bits gets
	 * mutated at a time.
	 */

	private GameHistory history = null;
	private String codebit;
	private String premises;

	public GAExpert(int playerNo)
	{
		super(playerNo);
		assignPremises();
	}

	public String getCodebit()
	{
		return codebit;
	}

	public void setCodebit(String codebit)
	{
		this.codebit = codebit;
	}

	@Override
	public String getName()
	{
		return "GA Expert";
	}

	public GameHistory getHistory()
	{
		return history;
	}

	/***
	 * First 3 steps of the game are randomly assigned
	 * 
	 * @return
	 */
	private void assignPremises()
	{
		int prem = (int) (Math.random() * 64);
		premises = Integer.toBinaryString(prem);
		premises = pad(premises, 6);
	}

	private String pad(String binaryString, int length)
	{
		for (int i = binaryString.length(); i < length; i++)
		{
			binaryString = "0" + binaryString;
		}
		return binaryString;
	}

	private Encoding getEncoding(boolean[] move)
	{
		if (move[playerNo - 1] && move[playerNo % 2])
		{
			return R;
		} else if (move[playerNo - 1] && !move[playerNo % 2])
		{
			return S;
		} else if (!move[playerNo - 1] && move[playerNo % 2])
		{
			return T;
		} else
		{
			return P;
		}
	}

	private String encode(Encoding[] strat)
	{
		int seq = encodeToInt(strat);
		return pad(Integer.toBinaryString(seq), 64);
	}

	private int encodeToInt(Encoding[] strat)
	{
		int seq = 0;
		for (int i = 0; i < strat.length; i++)
		{
			seq = (seq + strat[strat.length - i - 1].ordinal()
					* ((int) Math.pow(4, i)));
		}
		return seq;
	}

	@Override
	public boolean move(GameHistory history)
	{
		this.history = history;

		if (history.getNumberOfMoves() > 3)
		{
			updateCodeString();

		}

		// First 3 moves just randomize
		if (Math.random() >= 0.5)
			return true;
		else
			return false;
	}

	private void updateCodeString()
	{
		ArrayList<boolean[]> hist = (ArrayList<boolean[]>) history.getHistory();
		Encoding[] strat = new Encoding[3];

		for (int i = 1; i <= 3; i++)
		{
			strat[i - 1] = getEncoding((hist.get(hist.size() - i)));
		}

		codebit = encode(strat) + premises;
	}
}
