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
		return "EEE w/ GA - Dec Prob";
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
				"1000010100000001011100000101101001000000000010110111101001001000111111",
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
