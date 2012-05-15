package expert.ga;

import static utils.Encoding.P;
import static utils.Encoding.R;
import static utils.Encoding.S;
import static utils.Encoding.T;

import java.util.ArrayList;
import java.util.Collection;

import utils.Encoding;
import environment.GameHistory;
import expert.AbstractExpert;

public class GAExpert extends AbstractExpert
{
	private static boolean PRINT_RESULTS = true;
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

	private int code_length = 64, premises_length = 6;
	private String codebit, premises;
	private GameHistory history;

	public GAExpert(int playerNo, boolean learning)
	{
		super(playerNo);
		if (learning)
			generateRandomStrategy();
		else
		{
			// codebit =
			// "0000001110010000001000010110010000101110010101010011000010000011101100";
			codebit = "0011000010000000001000010110010001101110010101000011000010000011101110";
		}
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

	private void generateRandomStrategy()
	{
		premises = generateRandomBitString(premises_length);
		codebit = generateRandomBitString(code_length) + premises;
	}

	private String generateRandomBitString(int length)
	{
		String code = "";
		for (int i = length; i > 0; i -= 8)
		{
			int current_len = 8;
			if (i < 8)
			{
				current_len = i;
			}

			int bit_length = (int) Math.pow(2, current_len);

			int bit_in_int = (int) (Math.random() * bit_length);

			String current_code = Integer.toBinaryString(bit_in_int);
			current_code = pad(current_code, current_len);

			code = code + current_code;
		}
		return code;
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
		}
		else if (move[playerNo - 1] && !move[playerNo % 2])
		{
			return S;
		}
		else if (!move[playerNo - 1] && move[playerNo % 2])
		{
			return T;
		}
		else
		{
			return P;
		}
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

	private boolean lookupMove(int bit)
	{
		if (codebit.charAt(bit) == '0')
			return false;
		return true;
	}

	@Override
	public boolean move(GameHistory history)
	{
		this.history = history;

		if (history.getNumberOfMoves() >= 3)
		{
			Collection<boolean[]> historyArray = history.getHistory();

			// Get last 3 moves
			boolean[][] last3Moves = new boolean[3][2];

			for (int i = 0; i < 3; i++)
				last3Moves[i] = ((ArrayList<boolean[]>) historyArray)
						.get(historyArray.size() - 1 - i);

			// Encode them in terms of R T P S
			Encoding[] last3Encodings = new Encoding[3];
			for (int i = 0; i < 3; i++)
			{
				last3Encodings[i] = getEncoding(last3Moves[i]);
			}
			if (PRINT_RESULTS)
				System.out.println(last3Encodings[0].toString()
						+ last3Encodings[1].toString()
						+ last3Encodings[2].toString() + " "
						+ encodeToInt(last3Encodings) + " "
						+ lookupMove(encodeToInt(last3Encodings)));
			
			
			return lookupMove(encodeToInt(last3Encodings));
		}

		// First 3 moves just randomize using given premises
		else
		{
			int selection = 64 + ((int) (Math.random() * 2)) + 2
					* history.getNumberOfMoves();
			return lookupMove(selection);
		}
	}
}
