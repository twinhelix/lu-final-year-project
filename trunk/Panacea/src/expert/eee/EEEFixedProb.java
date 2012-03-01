package expert.eee;

public class EEEFixedProb extends ExploreExploitExpert
{

	public EEEFixedProb(int playerNo, String[] strategies, double prob)
	{
		super(playerNo, strategies, prob);

	}

	@Override
	protected double getProb()
	{
		return prob;
	}
	
	@Override
	public String getName()
	{
		return "EEE - Fixed Prob";
	}

}
