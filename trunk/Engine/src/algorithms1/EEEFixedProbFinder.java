package algorithms1;

import static common.Settings.NO_OF_ROUNDS;
import static common.Settings.SCORING_SYSTEM;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import utils.ReadWriteTextFile;
import Results.GraphingResults;
import Utils.MapUtils;
import agent.IExpert;
import engine.RoundRobinEngine;
import expert.CooperateExpert;
import expert.DefectExpert;
import expert.GAExpert;
import expert.GrudgerExpert;
import expert.PavlovExpert;
import expert.RandomExpert;
import expert.SoftGrudgerExpert;
import expert.eee.EEEDecProb;
import expert.eee.EEEFixedProb;
import expert.titfortat.RemorsefulProberExpert;
import expert.titfortat.TitForTatExpert;

public class EEEFixedProbFinder
{
	private static class FixedProbDetails
	{
		private String prob;
		@SuppressWarnings("unused")
		private double score;

		private FixedProbDetails(String prob, double score)
		{
			this.prob = prob;
			this.score = score;
		}
	}

	private static final boolean PRINT_DETAILS = false;
	private static double prob = 0.2;
	private static final String EEEfix = "EEE - Fixed Prob";
	private static List<FixedProbDetails> bestScores;
	private static final int rounds = 10000;

	private static File testFile = new File("EEE - fix prob results.txt");
	private static DecimalFormat df = new DecimalFormat("#.##");

	public static void main(String[] args) throws FileNotFoundException,
			IOException
	{

		ReadWriteTextFile.setContents(testFile, "Fixed Prob Results: ");

		bestScores = new ArrayList<FixedProbDetails>();

		for (int counter = 0; counter < rounds; counter++)
		{
			double currentBestProb = 0d;
			double currentBestScore = 0d;

			for (prob = 0; prob < 1; prob = prob + 0.01)
			{
				double gameScore = run();
				if (gameScore > currentBestScore)
				{
					currentBestProb = prob;
					currentBestScore = gameScore;
				}
			}
			bestScores.add(new FixedProbDetails(df.format(currentBestProb),
					currentBestScore));

			ReadWriteTextFile.setContents(testFile,
					"---------- BEST SCORES ROUND " + (counter + 1)
							+ " ----------");

			ReadWriteTextFile.setContents(
					testFile,
					EEEfix + " " + df.format(currentBestProb) + ": \t\t"
							+ df.format(currentBestScore) + "%");
			ReadWriteTextFile.setContents(testFile, "");

			if (PRINT_DETAILS)
			{
				System.out.println("---------- BEST SCORES ROUND "
						+ (counter + 1) + " ----------");
				System.out.println(EEEfix + ": " + EEEfix + " "
						+ df.format(currentBestProb) + ": \t"
						+ df.format(currentBestScore));
				System.out.println();
			}
		}
		createHistogram();
	}

	private static double run() throws FileNotFoundException, IOException
	{
		String[] strats = { new GrudgerExpert(0).getName(),
				new PavlovExpert(0).getName(), new CooperateExpert(0).getName() };

		IExpert[] experts = { new TitForTatExpert(0),
				new EEEDecProb(0, strats), new EEEFixedProb(0, strats, prob),
				new RandomExpert(0), new DefectExpert(0), new PavlovExpert(0),
				new RemorsefulProberExpert(0, 0.2), new SoftGrudgerExpert(0),
				new GAExpert(0, false) };

		RoundRobinEngine engine = new RoundRobinEngine(experts, NO_OF_ROUNDS,
				SCORING_SYSTEM);
		engine.run();

		double gameScore = engine.getScore(EEEfix);

		// System.out.println(EEEfix + "- " + prob + ": " + gameScore + "%");
		// System.out.println();
		return gameScore;
	}

	private static void createHistogram()
	{
		// Create Histogram of all the best probabilities
		Map<String, Integer> histo = new TreeMap<String, Integer>();

		for (FixedProbDetails current : bestScores)
		{
			String name = current.prob + "%";
			if (!histo.containsKey(name))
			{
				histo.put(name, new Integer(0));
			}
			histo.put(name, new Integer(histo.get(name).intValue() + 1));

		}
		SortedMap<String, Integer> sorted_histo = new TreeMap<String, Integer>(
				new ValueComparator(histo));
		sorted_histo.putAll(histo);

		GraphingResults gr = new GraphingResults(
				"EEE Best Fixed Probability Finding Results");
		gr.plotInteger(sorted_histo);
		sorted_histo = MapUtils.putFirstEntries(15, sorted_histo);

		SortedMap<String, Integer> sorted_histo2 = new TreeMap<String, Integer>(
				new ValueComparator(sorted_histo));
		sorted_histo2.putAll(sorted_histo);

		gr.plotInteger(sorted_histo2);

	}

	private static class ValueComparator implements Comparator<Object>
	{

		Map<String, Integer> base;

		public ValueComparator(Map<String, Integer> base)
		{
			this.base = base;
		}

		public int compare(Object a, Object b)
		{
			if ((Integer) base.get(a) < (Integer) base.get(b))
			{
				return 1;
			}
			else if ((Integer) base.get(a) == (Integer) base.get(b))
			{
				return 0;
			}
			else
			{
				return -1;
			}
		}
	}
}
