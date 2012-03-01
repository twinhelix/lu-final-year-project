package expert.eee;

public class EEEDecProb extends ExploreExploitExpert
{

	public EEEDecProb(int playerNo, String[] strategies)
	{
		super(playerNo, strategies);
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
