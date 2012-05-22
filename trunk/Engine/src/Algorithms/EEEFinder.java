package Algorithms;

import static common.Settings.NO_OF_ROUNDS;
import static common.Settings.SCORING_SYSTEM;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

import utils.ReadWriteTextFile;
import Results.GraphingResults;
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
	private static class ExpertArrayDetails
	{
		private Double score;
		private Set<String> strategies;

		private ExpertArrayDetails()
		{
			score = 0d;
			strategies = new HashSet<String>();
		}
	}

	private static final boolean PRINT_DETAILS = false;
	private static final double prob = 0.2;
	private static final String EEEdec = "EEE - Decreasing Prob";
	private static final String EEEfix = "EEE - Fixed Prob";
	private static Stack<Map<String, ExpertArrayDetails>> bestScores;
	private static final int rounds = 100;

	private static File testFile = new File("EEE Results.txt");

	public static void main(String[] args) throws FileNotFoundException,
			IOException
	{

		ReadWriteTextFile.setContents(testFile, "Results: ");

		Set<String> experts = populateExperts();
		bestScores = new Stack<Map<String, ExpertArrayDetails>>();

		Set<Set<String>> expertSets = SetUtils.powerSet(experts);
		for (int counter = 0; counter < rounds; counter++)
		{
			Map<String, ExpertArrayDetails> currentBests = new HashMap<String, EEEFinder.ExpertArrayDetails>();
			currentBests.put(EEEdec, new ExpertArrayDetails());
			currentBests.put(EEEfix, new ExpertArrayDetails());
			bestScores.add(currentBests);

			for (Set<String> s : expertSets)
			{
				if (s.size() >= 2)
				{
					if (PRINT_DETAILS)
						ReadWriteTextFile.setContents(testFile, s.toString());

					System.out.println(s);
					run(s);

					if (PRINT_DETAILS)
						ReadWriteTextFile.setContents(testFile, "");
				}
			}

			ReadWriteTextFile.setContents(testFile,
					"---------- BEST SCORES ROUND " + (counter + 1)
							+ " ----------");
			ReadWriteTextFile.setContents(testFile, EEEdec + ": "
					+ bestScores.peek().get(EEEdec).score);
			ReadWriteTextFile.setContents(testFile,
					bestScores.peek().get(EEEdec).strategies.toString());
			ReadWriteTextFile.setContents(testFile, "");
			ReadWriteTextFile.setContents(testFile, EEEfix + ": "
					+ bestScores.peek().get(EEEfix).score);
			ReadWriteTextFile.setContents(testFile,
					bestScores.peek().get(EEEfix).strategies.toString());

			System.out.println();
			System.out.println(EEEdec + ": "
					+ bestScores.peek().get(EEEdec).score);
			System.out.println(bestScores.peek().get(EEEdec).strategies);
			System.out.println();
			System.out.println(EEEfix + ": "
					+ bestScores.peek().get(EEEfix).score);
			System.out.println(bestScores.peek().get(EEEfix).strategies);
		}
		createHistogram(EEEdec);
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
		experts.add(new PavlovRandomExpert(0, prob).getName());
		experts.add(new RandomExpert(0).getName());
		experts.add(new SoftGrudgerExpert(0).getName());

		// All Tit-for-Tat variants
		experts.add(new TitForTatExpert(0).getName());
		experts.add(new TitForTwoTatExpert(0).getName());
		experts.add(new SuspiciousTitForTatExpert(0).getName());
		experts.add(new NaivePeaceMakerExpert(0, prob).getName());
		experts.add(new NaiveProberExpert(0, prob).getName());
		experts.add(new RemorsefulProberExpert(0, prob).getName());
		experts.add(new TruePeaceMakerExpert(0, prob).getName());

		return experts;
	}

	private static void run(Set<String> strategies)
			throws FileNotFoundException, IOException
	{
		String[] strats = strategies.toArray(new String[0]);

		IExpert[] experts = { new TitForTatExpert(0),
				new EEEDecProb(0, strats), new EEEFixedProb(0, strats, 0.3),
				new RandomExpert(0), new DefectExpert(0), new PavlovExpert(0),
				new RemorsefulProberExpert(0, 0.2), new SoftGrudgerExpert(0),
				new GAExpert(0, false) };

		RoundRobinEngine engine = new RoundRobinEngine(experts, NO_OF_ROUNDS,
				SCORING_SYSTEM);
		engine.run();

		double currentDecScore = engine.getScore(EEEdec);
		double currentFixScore = engine.getScore(EEEfix);

		if (currentDecScore > bestScores.peek().get(EEEdec).score)
		{
			bestScores.peek().get(EEEdec).score = currentDecScore;
			bestScores.peek().get(EEEdec).strategies = strategies;
		}

		if (currentFixScore > bestScores.peek().get(EEEfix).score)
		{
			bestScores.peek().get(EEEfix).score = currentDecScore;
			bestScores.peek().get(EEEfix).strategies = strategies;
		}

		System.out.println(EEEdec + ": " + currentDecScore + "%");
		System.out.println(EEEfix + ": " + currentFixScore + "%");

		if (PRINT_DETAILS)
		{
			ReadWriteTextFile.setContents(testFile, EEEdec + ": "
					+ currentDecScore + "%");
			ReadWriteTextFile.setContents(testFile, EEEfix + ": "
					+ currentFixScore + "%");
			ReadWriteTextFile.setContents(testFile, "");
		}
		System.out.println();
		// engine.showTally();
	}

	private static void createHistogram(String algo)
	{
		// Create Histogram of all the most commonly appeared experts
		Map<String, Integer> histo = new TreeMap<String, Integer>();
		for (Map<String, ExpertArrayDetails> current : bestScores)
		{
			Set<String> strats = current.get(algo).strategies;
			for (String expert : strats)
			{
				if (!histo.containsKey(expert))
				{
					histo.put(expert, new Integer(0));
				}
				histo.put(expert, new Integer(histo.get(expert).intValue() + 1));
			}
		}

		GraphingResults gr = new GraphingResults("EEE Results");
		gr.plotInteger(histo);

	}
}
