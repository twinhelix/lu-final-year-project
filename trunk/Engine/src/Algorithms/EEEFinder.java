package Algorithms;

import static common.Settings.NO_OF_ROUNDS;
import static common.Settings.SCORING_SYSTEM;

import java.util.HashSet;
import java.util.Set;

import Utils.SetUtils;
import agent.IExpert;
import engine.RoundRobinEngine;
import expert.AdaptiveExpert;
import expert.AlternateExpert;
import expert.CooperateExpert;
import expert.DefectExpert;
import expert.GradualExpert;
import expert.GrudgerExpert;
import expert.PavlovExpert;
import expert.PavlovRandomExpert;
import expert.RandomExpert;
import expert.SoftGrudgerExpert;
import expert.eee.EEEDecProb;
import expert.eee.EEEFixedProb;
import expert.ga.GAExpert;
import expert.titfortat.NaivePeaceMakerExpert;
import expert.titfortat.NaiveProberExpert;
import expert.titfortat.RemorsefulProberExpert;
import expert.titfortat.SuspiciousTitForTatExpert;
import expert.titfortat.TitForTatExpert;
import expert.titfortat.TitForTwoTatExpert;
import expert.titfortat.TruePeaceMakerExpert;

public class EEEFinder
{
	public static void main(String[] args)
	{

		Set<String> experts = populateExperts();

		Set<Set<String>> expertSets = SetUtils.powerSet(experts);

		for (Set<String> s : expertSets)
		{
			if (s.size() >= 2)
			{
				// System.out.println(s);
				run(s);
			}
		}
	}

	public static Set<String> populateExperts()
	{
		Set<String> experts = new HashSet<String>();

		// populate all experts here
		experts.add(new AdaptiveExpert(0).getName());
		experts.add(new AlternateExpert(0).getName());
		experts.add(new CooperateExpert(0).getName());
		experts.add(new DefectExpert(0).getName());
		experts.add(new GradualExpert(0).getName());
		experts.add(new GrudgerExpert(0).getName());
		experts.add(new PavlovExpert(0).getName());
		experts.add(new PavlovRandomExpert(0, 0.2).getName());
		experts.add(new RandomExpert(0).getName());
		experts.add(new SoftGrudgerExpert(0).getName());

		// All Tit-for-Tat variants
		experts.add(new TitForTatExpert(0).getName());
		experts.add(new TitForTwoTatExpert(0).getName());
		experts.add(new SuspiciousTitForTatExpert(0).getName());
		experts.add(new NaivePeaceMakerExpert(0, 0.2).getName());
		experts.add(new NaiveProberExpert(0, 0.2).getName());
		experts.add(new RemorsefulProberExpert(0, 0.2).getName());
		experts.add(new TruePeaceMakerExpert(0, 0.2).getName());

		return experts;
	}

	private static void run(Set<String> strategies)
	{
		System.out.println();
		String[] strats = strategies.toArray(new String[0]);
		for (int i = 0; i < strats.length; i++)
			System.out.print(strats[i] + " ");

		IExpert[] experts = { new TitForTatExpert(0),
				new EEEDecProb(0, strats), new EEEFixedProb(0, strats, 0.3),
				new RandomExpert(0), new DefectExpert(0), new PavlovExpert(0),
				new RemorsefulProberExpert(0, 0.2), new SoftGrudgerExpert(0),
				new GAExpert(0, false) };

		RoundRobinEngine engine = new RoundRobinEngine(experts, NO_OF_ROUNDS,
				SCORING_SYSTEM);
		// engine.run();
		// System.out.println();
		// engine.showTally();
	}

}
