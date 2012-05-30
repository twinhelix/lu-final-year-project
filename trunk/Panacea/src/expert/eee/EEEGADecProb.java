package expert.eee;

import utils.ExpertsDictionary;
import agent.IExpert;
import expert.GAExpert;
import expert.titfortat.TitForTatExpert;

public class EEEGADecProb extends EEEBase
{

	public EEEGADecProb(int playerNo, String[] strategies)
	{
		super(playerNo, strategies);
	}

	@Override
	public String getName()
	{
		return "EEE with GA - Decreasing Prob";
	}

	@Override
	protected void populateExpertArray(String[] strategies)
	{
		ExpertsDictionary dict = new ExpertsDictionary(playerNo, 0.2);

		advisors = new Advisor[strategies.length + 1];

		for (int i = 0; i < strategies.length; i++)
		{
			IExpert e = dict.getExpert(strategies[i]);
			if (e == null)
			{
				e = new TitForTatExpert(playerNo);
			}
			advisors[i] = new Advisor(e);
		}
		GAExpert ga1 = new GAExpert(
				playerNo,
				"1111000000001001011100000111101001110000000100010000101010010000111111",
				3);
		ga1.setUniqueName(ga1.getName() + " a");

		advisors[strategies.length] = new Advisor(ga1);

	}

	@Override
	protected double getProb()
	{
		return 1 / (i + 0.0);
	}

}
