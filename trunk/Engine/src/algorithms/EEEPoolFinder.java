package algorithms;

import static common.Settings.NO_OF_ROUNDS;
import static common.Settings.SCORING_SYSTEM;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import utils.ReadWriteTextFile;
import Results.GraphingResults;
import Utils.DoubleValueComparator;
import Utils.MapUtils;
import Utils.SetUtils;
import agent.IExpert;
import engine.RoundRobinEngine;
import expert.AdaptiveExpert;
import expert.AlternateExpert;
import expert.CooperateExpert;
import expert.DefectExpert;
import expert.GAExpert;
import expert.GradualExpert;
import expert.GrudgerExpert;
import expert.PavlovExpert;
import expert.PavlovRandomExpert;
import expert.ProbableExpert;
import expert.RandomExpert;
import expert.SoftGrudgerExpert;
import expert.eee.EEEDecProb;
import expert.eee.EEEFixedProb;
import expert.titfortat.NaivePeaceMakerExpert;
import expert.titfortat.NaiveProberExpert;
import expert.titfortat.RemorsefulProberExpert;
import expert.titfortat.SuspiciousTitForTatExpert;
import expert.titfortat.TitForTatExpert;
import expert.titfortat.TitForTwoTatExpert;
import expert.titfortat.TruePeaceMakerExpert;

public class EEEPoolFinder
{
	private static final boolean PRINT_DETAILS = false;
	private static final double prob = 0.2;
	private static final String EEEdec = "EEE - Decreasing Prob";
	private static final String EEEfix = "EEE - Fixed Prob";

	private static Map<String, Double> eeeDecResults;
	private static Map<String, Double> eeeFixResults;

	private static final int ROUNDS = 10;
	private static DecimalFormat df = new DecimalFormat("#.##");
	private static File testFile = new File("EEE Pool Finding Results.txt");
	private static File bestFile = new File("EEE Pool Finding Best Scoring.txt");

	public static void main(String[] args) throws FileNotFoundException,
			IOException
	{
		eeeDecResults = new HashMap<String, Double>();
		eeeFixResults = new HashMap<String, Double>();

		Set<String> experts = populateExperts();
		Set<Set<String>> expertSets = SetUtils.powerSet(experts);

		ReadWriteTextFile.setContents(testFile,
				"---------- EEE Pool Finding Results: ----------");
		ReadWriteTextFile.setContents(testFile, "");

		for (Set<String> s : expertSets)
		{
			if (s.size() >= 2)
			{
				if (PRINT_DETAILS)
					System.out.println(s);

				double[] result = run(s, ROUNDS);
//				eeeDecResults.put(s.toString(), new Double(result[0]));
//				eeeFixResults.put(s.toString(), new Double(result[1]));

				// Write results to file

				ReadWriteTextFile.setContents(testFile, s.toString());
				ReadWriteTextFile.setContents(
						testFile,
						"Dec: " + df.format(result[0]) + "\t\t Fixed: "
								+ df.format(result[1]));
				ReadWriteTextFile.setContents(testFile, "");

				if (PRINT_DETAILS)
				{
					System.out.println(s.toString());
					System.out.println("Dec: " + df.format(result[0])
							+ "\t\t Fixed: " + df.format(result[1]));
					System.out.println();
				}
			}
		}
		//createGraph();
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
		experts.add(new ProbableExpert(0).getName());

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

	/***
	 * Returns the average score of given set of strategies and numberof rounds
	 * ran
	 * 
	 * @param strategies
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static double[] run(Set<String> strategies, int rounds)
			throws FileNotFoundException, IOException
	{

		double[] results = { 0d, 0d };

		String[] strats = strategies.toArray(new String[0]);

		IExpert[] experts = {
				new TitForTatExpert(0),
				new EEEDecProb(0, strats),
				new EEEFixedProb(0, strats, 0.3),
				new RandomExpert(0),
				new DefectExpert(0),
				new PavlovExpert(0),
				new RemorsefulProberExpert(0, 0.2),
				new SoftGrudgerExpert(0),
				new GAExpert(
						0,
						"1111000010110110010100001110001010101000010101010001100001000000111111",
						3) };

		for (int i = 0; i < rounds; i++)
		{
			RoundRobinEngine engine = new RoundRobinEngine(experts,
					NO_OF_ROUNDS, SCORING_SYSTEM);
			engine.run();
			results[0] += engine.getScore(EEEdec);
			results[1] += engine.getScore(EEEfix);
		}

		results[0] = results[0] / rounds;
		results[1] = results[1] / rounds;

		return results;
	}

	private static void createGraph() throws FileNotFoundException, IOException
	{
		// Create Graph of all the best pools

		// Dec
		SortedMap<String, Double> dec_sorted_performance = new TreeMap<String, Double>(
				new DoubleValueComparator(eeeDecResults));
		dec_sorted_performance.putAll(eeeDecResults);
		dec_sorted_performance = MapUtils.putFirstEntries(10,
				dec_sorted_performance);

		GraphingResults gr_dec = new GraphingResults(
				"EEE Dec - Best Pool Performance ");
		gr_dec.plotDouble(dec_sorted_performance);

		ReadWriteTextFile.setContents(bestFile,
				"---------- EEE DEC Best Pool  ----------");
		for (String key : dec_sorted_performance.keySet())
		{
			ReadWriteTextFile.setContents(bestFile, key + ": "
					+ dec_sorted_performance.get(key));
		}
		ReadWriteTextFile.setContents(bestFile, "");

		// Fixed
		SortedMap<String, Double> fixed_sorted_performance = new TreeMap<String, Double>(
				new DoubleValueComparator(eeeFixResults));
		fixed_sorted_performance.putAll(eeeFixResults);
		fixed_sorted_performance = MapUtils.putFirstEntries(10,
				fixed_sorted_performance);

		ReadWriteTextFile.setContents(bestFile,
				"---------- EEE FIX Best Pool ----------");
		for (String key : fixed_sorted_performance.keySet())
		{
			ReadWriteTextFile.setContents(bestFile, key + ": "
					+ fixed_sorted_performance.get(key));
		}

		GraphingResults gr_fix = new GraphingResults(
				"EEE Fixed - Best Pool Performance ");
		gr_fix.plotDouble(fixed_sorted_performance);

	}
}
