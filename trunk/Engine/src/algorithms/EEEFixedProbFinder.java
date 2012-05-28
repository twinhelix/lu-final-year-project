package algorithms;

import static common.Settings.NO_OF_ROUNDS;
import static common.Settings.SCORING_SYSTEM;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import utils.ReadWriteTextFile;
import Results.GraphingResults;
import Utils.DoubleValueComparator;
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
	private static final boolean PRINT_DETAILS = false;
	private static final String EEEfix = "EEE - Fixed Prob";
	private static Map<String, Double> bestScores;
	private static final int ROUNDS = 10000;

	private static File testFile = new File("EEE - fix prob results.txt");
	private static DecimalFormat df = new DecimalFormat("#.##");

	private static String[] strats = { new GrudgerExpert(0).getName(),
			new PavlovExpert(0).getName(), new CooperateExpert(0).getName() };

	public static void main(String[] args) throws FileNotFoundException,
			IOException
	{
		ReadWriteTextFile.setContents(testFile,
				"---------- Fixed Prob Results - Average Scores: ----------");
		ReadWriteTextFile.setContents(testFile, arrayToString(strats));

		bestScores = new HashMap<String, Double>();

		for (double prob = 0; prob <= 1; prob = prob + 0.01)
		{
			double probScore = run(prob, ROUNDS);
			bestScores.put(df.format(prob), probScore);

			ReadWriteTextFile.setContents(testFile, "Prob: " + df.format(prob)
					+ " \t\t" + "Score: " + probScore);
			ReadWriteTextFile.setContents(testFile, "");

			if (PRINT_DETAILS)
			{
				System.out.println("Prob: " + df.format(prob) + " \t\t"
						+ "Score: " + probScore);
			}
		}
		createHistogram();
	}

	private static String arrayToString(String[] strats)
	{
		String line = "";
		for (int i = 0; i < strats.length; i++)
		{
			line = line + strats[i] + ", ";
		}
		return line;
	}

	/***
	 * Returns the average score of running with given probability for x number
	 * of rounds
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static double run(double prob, int rounds)
			throws FileNotFoundException, IOException
	{
		IExpert[] experts = { new TitForTatExpert(0),
				new EEEDecProb(0, strats), new EEEFixedProb(0, strats, prob),
				new RandomExpert(0), new DefectExpert(0), new PavlovExpert(0),
				new RemorsefulProberExpert(0, 0.2), new SoftGrudgerExpert(0),
				new GAExpert(0, false) };

		double totalScore = 0d;

		for (int counter = 0; counter < rounds; counter++)
		{
			RoundRobinEngine engine = new RoundRobinEngine(experts,
					NO_OF_ROUNDS, SCORING_SYSTEM);
			engine.run();

			totalScore += engine.getScore(EEEfix);
		}
		return (totalScore / rounds);
	}

	private static void createHistogram()
	{
		// Create Histogram of all the best probabilities

		SortedMap<String, Double> sorted_performance = new TreeMap<String, Double>(
				new DoubleValueComparator(bestScores));
		sorted_performance.putAll(bestScores);

		GraphingResults gr = new GraphingResults(
				"EEE Best Fixed Probability Finding Results");
		gr.plotDouble(bestScores);

		GraphingResults gr15 = new GraphingResults(
				"EEE Best Fixed Probability Finding Results - TOP 15");
		// gr.plotDouble(sorted_performance);
		sorted_performance = MapUtils.putFirstEntries(15, sorted_performance);

		SortedMap<String, Double> sorted_top15 = new TreeMap<String, Double>(
				new DoubleValueComparator(sorted_performance));
		sorted_top15.putAll(sorted_performance);

		gr15.plotDouble(sorted_top15);
	}

}
