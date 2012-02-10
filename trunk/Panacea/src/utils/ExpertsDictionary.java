package utils;

import java.util.HashMap;
import java.util.Map;

import expert.AdaptiveExpert;
import expert.CooperateExpert;
import expert.DefectExpert;
import expert.GradualExpert;
import expert.GrudgerExpert;
import expert.PavlovExpert;
import expert.RandomExpert;
import expert.titfortat.NaivePeaceMakerExpert;
import expert.titfortat.NaiveProberExpert;
import expert.titfortat.RemorsefulProberExpert;
import expert.titfortat.SuspiciousTitForTat;
import expert.titfortat.TitForTatExpert;
import expert.titfortat.TitForTwoTatExpert;
import expert.titfortat.TruePeaceMakerExpert;

import agent.IExpert;

public class ExpertsDictionary {
	private Map<String, IExpert> dictionary;
	private static ExpertsDictionary instance;
	private final double prob = 0.2;

	public static ExpertsDictionary getInstance() {
		if (instance == null) {
			instance = new ExpertsDictionary();
		}
		return instance;
	}

	private ExpertsDictionary() {
		dictionary = new HashMap<String, IExpert>();
		initialize();
	}

	private void initialize() {
		IExpert random = new RandomExpert(0);
		IExpert grudger = new GrudgerExpert(0);
		IExpert coop = new CooperateExpert(0);
		IExpert defect = new DefectExpert(0);
		IExpert pavlov = new PavlovExpert(0);
		IExpert titfortat = new TitForTatExpert(0);
		IExpert titfor2tat = new TitForTwoTatExpert(0);
		IExpert gradual = new GradualExpert(0);
		IExpert adaptive = new AdaptiveExpert(0);
		IExpert naivepeace = new NaivePeaceMakerExpert(0, prob);
		IExpert naiveprob = new NaiveProberExpert(0, prob);
		IExpert remprob = new RemorsefulProberExpert(0, prob);
		IExpert susptitfortat = new SuspiciousTitForTat(0);
		IExpert truepeace = new TruePeaceMakerExpert(0, prob);
		
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
	}

	public IExpert getExpert(String name) {
		if (dictionary.keySet().contains(name)) {
			return dictionary.get(name);
		}
		return null;
	}

}
