package expert;

public class GAExpertWatcher extends GAExpert
{
	int[] bit_histogram;

	public GAExpertWatcher(int playerNo, String code, int history_depth)
	{
		super(playerNo, code, history_depth);
		bit_histogram = new int[full_length];
	}

	@Override
	protected boolean lookupMove(int bit)
	{
		bit_histogram[bit]++;
		return super.lookupMove(bit);
	}

	public int[] getBitFrequency()
	{
		return bit_histogram;
	}
}
