package expert.eee;

import utils.ExpertsDictionary;
import agent.IExpert;
import expert.GAExpert;
import expert.titfortat.TitForTatExpert;

public class EEEGADecProb extends EEEBase
{
	String name;

	public EEEGADecProb(int playerNo, String[] strategies)
	{
		super(playerNo, strategies);
		this.name = "EEE w/ GA - Dec Prob";
	}

	@Override
	public String getName()
	{
		return name;
	}

	public void setUniqueName(String name)
	{
		this.name = name;
	}

	@Override
	protected void populateExpertArray(String[] strategies)
	{
		ExpertsDictionary dict = new ExpertsDictionary(playerNo, 0.2);

		advisors = new Advisor[strategies.length + 3];

		for (int i = 0; i < strategies.length; i++)
		{
			IExpert e = dict.getExpert(strategies[i]);
			if (e == null)
			{
				e = new TitForTatExpert(playerNo);
			}
			advisors[i] = new Advisor(e);
		}
		GAExpert ga_perfect = new GAExpert(
				playerNo,
				"1011011010001010011010000111001001101000100100110100001010000000111111",
				3);
		ga_perfect.setUniqueName(ga_perfect.getName() + " perfect");
		advisors[strategies.length] = new Advisor(ga_perfect);

		GAExpert ga_imperfect = new GAExpert(
				playerNo,
				"1010000011110111110011111111111000100010100010100011111110001000111111",
				3);
		ga_imperfect.setUniqueName(ga_imperfect.getName() + " imperfect");
		advisors[strategies.length + 1] = new Advisor(ga_imperfect);

		GAExpert ga_other = new GAExpert(
				playerNo,
				"1011100001101100001111000110001011101010110110110010010011000000110011",
				3);
		ga_other.setUniqueName(ga_other.getName() + " other");
		advisors[strategies.length + 2] = new Advisor(ga_other);

	}

	@Override
	protected double getProb()
	{
		return 1 / (i + 0.0);
	}

}
