package utils;

import java.util.HashMap;
import java.util.Map;

import agent.IExpert;
import expert.AdaptiveExpert;
import expert.AlternateCCDExpert;
import expert.AlternateDDCExpert;
import expert.CooperateExpert;
import expert.DefectExpert;
import expert.GradualExpert;
import expert.GrudgerExpert;
import expert.HardMajorityExpert;
import expert.PavlovExpert;
import expert.ProbableExpert;
import expert.RandomExpert;
import expert.SoftGrudgerExpert;
import expert.SoftMajorityExpert;
import expert.titfortat.HardTitforTatExpert;
import expert.titfortat.NaivePeaceMakerExpert;
import expert.titfortat.NaiveProberExpert;
import expert.titfortat.RemorsefulProberExpert;
import expert.titfortat.SuspiciousTitForTatExpert;
import expert.titfortat.TitForTatExpert;
import expert.titfortat.TitForTwoTatExpert;
import expert.titfortat.TruePeaceMakerExpert;

public class ExpertsDictionary
{
	private Map<String, IExpert> dictionary;
	private double prob = 0.2;
	private int playerNo = 0;

	public ExpertsDictionary(int playerNo, double prob)
	{
		dictionary = new HashMap<String, IExpert>();
		this.playerNo = playerNo;
		this.prob = prob;
		initialize();
	}

	private void initialize()
	{
		IExpert random = new RandomExpert(playerNo);
		IExpert grudger = new GrudgerExpert(playerNo);
		IExpert coop = new CooperateExpert(playerNo);
		IExpert defect = new DefectExpert(playerNo);
		IExpert pavlov = new PavlovExpert(playerNo);
		IExpert titfortat = new TitForTatExpert(playerNo);
		IExpert titfor2tat = new TitForTwoTatExpert(playerNo);
		IExpert gradual = new GradualExpert(playerNo);
		IExpert adaptive = new AdaptiveExpert(playerNo);
		IExpert naivepeace = new NaivePeaceMakerExpert(playerNo, prob);
		IExpert naiveprob = new NaiveProberExpert(playerNo, prob);
		IExpert remprob = new RemorsefulProberExpert(playerNo, prob);
		IExpert susptitfortat = new SuspiciousTitForTatExpert(playerNo);
		IExpert truepeace = new TruePeaceMakerExpert(playerNo, prob);
		IExpert softgrudger = new SoftGrudgerExpert(playerNo);
		IExpert probable = new ProbableExpert(playerNo);

		IExpert softMajority = new SoftMajorityExpert(playerNo);
		IExpert hardMajority = new HardMajorityExpert(playerNo);
		IExpert alternateCCD = new AlternateCCDExpert(playerNo);
		IExpert alternateDDC = new AlternateDDCExpert(playerNo);
		IExpert hardTitforTat = new HardTitforTatExpert(playerNo);

		dictionary.put(random.getName(), random);
		dictionary.put(grudger.getName(), grudger);
		dictionary.put(coop.getName(), coop);
		dictionary.put(defect.getName(), defect);
		dictionary.put(pavlov.getName(), pavlov);
		dictionary.put(titfortat.getName(), titfortat);
		dictionary.put(titfor2tat.getName(), titfor2tat);
		dictionary.put(gradual.getName(), gradual);
		dictionary.put(adaptive.getName(), adaptive);
		dictionary.put(naivepeace.getName(), naivepeace);
		dictionary.put(naiveprob.getName(), naiveprob);
		dictionary.put(remprob.getName(), remprob);
		dictionary.put(susptitfortat.getName(), susptitfortat);
		dictionary.put(truepeace.getName(), truepeace);
		dictionary.put(softgrudger.getName(), softgrudger);
		dictionary.put(probable.getName(), probable);

		dictionary.put(softMajority.getName(), softMajority);
		dictionary.put(hardMajority.getName(), hardMajority);
		dictionary.put(alternateCCD.getName(), alternateCCD);
		dictionary.put(alternateDDC.getName(), alternateDDC);
		dictionary.put(hardTitforTat.getName(), hardTitforTat);

	}

	public IExpert getExpert(String name)
	{
		if (dictionary.keySet().contains(name))
		{
			return dictionary.get(name);
		}
		return null;
	}

}
