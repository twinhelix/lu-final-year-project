package expert.eee;

public class EEEDecProb extends EEEBase
{

	public EEEDecProb(int playerNo, String[] strategies)
	{
		super(playerNo, strategies);
	}

	public EEEDecProb(int playerNo, int poolSize)
	{
		super(playerNo, poolSize);
	}

	@Override
	protected double getProb()
	{
		return 1 / (i + 0.0);
	}

	@Override
	public String getName()
	{
		return "EEE - Decreasing Prob";
	}
}
