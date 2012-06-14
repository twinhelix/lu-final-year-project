package engine;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import Performance.GraphingPerformance;
import Results.GraphingResults;
import agent.IExpert;

import common.Settings;

import environment.Game;
import environment.ScoringSystem;

public class RoundRobinEngine implements IEngine
{
	protected static boolean PRINT_RESULTS = false;

	protected IExpert[] experts;
	protected int totalGames;
	protected ScoringSystem scoringSystem;
	protected Stack<Map<String, Double>> totals;
	protected Map<String, Double> averageScores;
	protected Map<String, Double> variances;
	protected Map<String, double[]> cis;
	protected TreeMap<String, Double> sorted_map;
	protected DecimalFormat df;
	protected int runs;
	protected double benchmark;

	public RoundRobinEngine(IExpert[] experts, int totalGames,
			ScoringSystem scoringSystem)
	{
		this(experts, totalGames, scoringSystem, 1);
	}

	public RoundRobinEngine(IExpert[] experts, int totalGames,
			ScoringSystem scoringSystem, int runs)
	{
		this.experts = experts;
		this.totalGames = totalGames;
		this.scoringSystem = scoringSystem;
		this.runs = runs;
		totals = new Stack<Map<String, Double>>();
		benchmark = Settings.getBenchMark(experts.length);
		df = new DecimalFormat("#.####");
		initialiseScores();
	}

	protected void initialiseTally()
	{
		Map<String, Double> total = new HashMap<String, Double>();
		for (IExpert expert : experts)
		{
			total.put(expert.getName(), new Double(0));
		}
		totals.push(total);
	}

	private void initialiseScores()
	{
		averageScores = new HashMap<String, Double>();
		variances = new HashMap<String, Double>();
		cis = new HashMap<String, double[]>();
		for (IExpert expert : experts)
		{
			averageScores.put(expert.getName(), new Double(0));
			variances.put(expert.getName(), new Double(0));
			// cis.put(expert.getName(), new double[2]);
		}
	}

	/**
	 * Runs a round robin tournament with all given players
	 */
	public void run()
	{
		for (int run = 0; run < runs; run++)
		{	
			if (run %100 == 0){
				System.out.println("run : " + run);
			}
			initialiseTally();
			for (int i = 0; i < experts.length; i++)
			{
				for (int j = i; j < experts.length; j++)
				{
					IExpert e1 = experts[i];
					IExpert e2 = experts[j];

					if (i == j)
					{
						e2 = (IExpert) experts[j].clone();
					}

					e1.setPlayerNumber(1);
					e2.setPlayerNumber(2);

					e1.initialize();
					e2.initialize();

					Game game = new Game(e1, e2, totalGames, scoringSystem);
					double[] result = game.run();

					totals.peek().put(
							e1.getName(),
							new Double(totals.peek().get(e1.getName())
									.doubleValue()
									+ result[0]));
					totals.peek().put(
							e2.getName(),
							new Double(totals.peek().get(e2.getName())
									.doubleValue()
									+ result[1]));

					if (PRINT_RESULTS)
						printTable(e1.getName() + ": " + result[0], e2
								.getName()
								+ ": " + result[1], "", "");
				}
			}
		}
		calculateAverageScores();
		calculateStats();
	}

	protected void calculateAverageScores()
	{
		for (Map<String, Double> roundScore : totals)
		{
			for (String key : roundScore.keySet())
			{
				averageScores.put(key, new Double(averageScores.get(key)
						+ roundScore.get(key)));
			}
		}
		for (String key : averageScores.keySet())
		{
			averageScores.put(key, new Double(averageScores.get(key) / runs));
		}

	}

	/***
	 * Calculates variances and 99% CI
	 */
	protected void calculateStats()
	{
		for (Map<String, Double> roundScore : totals)
		{

			for (String key : roundScore.keySet())
			{
				double roundBenchmarkScore = roundScore.get(key)
						/ ((experts.length + 1) * benchmark) * 100;
				double aveBenchmarkScore = averageScores.get(key)
						/ ((experts.length + 1) * benchmark) * 100;

				double squaredDiff = Math.pow(roundBenchmarkScore
						- aveBenchmarkScore, 2);

				variances
						.put(key, new Double(variances.get(key) + squaredDiff));
			}
		}
		for (String key : variances.keySet())
		{
			Double var = new Double(variances.get(key) / runs);
			variances.put(key, var);

			double aveBenchMark = (averageScores.get(key)
					/ ((experts.length + 1) * benchmark) * 100);

			cis.put(key, calc99CI(aveBenchMark, var));
		}
	}

	public double getScore(String key)
	{
		return (averageScores.get(key).doubleValue()
				/ ((experts.length + 1) * benchmark) * 100);
	}

	protected double[] calc99CI(double mean, double variance)
	{

		double std = Math.sqrt(variance);

		double[] result = { (mean - 2.58 * std / Math.sqrt(runs)),
				(mean + 2.54 * std / Math.sqrt(runs)) };
		return result;

	}

	public double getAverageOpponentScore(String key)
	{
		double totalOpponentsScore = 0d;
		for (String expertName : averageScores.keySet())
		{
			totalOpponentsScore += averageScores.get(expertName);
		}

		totalOpponentsScore -= averageScores.get(key).doubleValue();
		double averageOpponentsScore = totalOpponentsScore
				/ (experts.length - 1);
		return (averageOpponentsScore / ((experts.length + 1) * benchmark) * 100);
	}

	public void showTally()
	{
		sorted_map = new TreeMap<String, Double>(new ValueComparator(
				averageScores));
		sorted_map.putAll(averageScores);

		System.out.println("--- AVERAGE RESULTS OF " + runs + " runs ---");
		int i = 0;
		printTable("Expert and Average Score", "Benchmark Score", "Variance",
				"99% CI");
		for (String key : sorted_map.keySet())
		{
			i++;
			double[] ci = cis.get(key);
			printTable((i + ". " + key), (df.format(sorted_map.get(key)
					.doubleValue()
					/ ((experts.length + 1) * benchmark) * 100) + "%"), (df
					.format(variances.get(key))), ("[" + df.format(ci[0])
					+ ", " + df.format(ci[1]) + "]"));
		}
	}

	public void plotPerformance()
	{
		GraphingPerformance graph = new GraphingPerformance(
				"Tournament Results");
		graph.plot();
	}

	public void plotResults()
	{
		GraphingResults graph = new GraphingResults("Tournament Results");
		graph.plotDouble(sorted_map);
	}

	protected void printTable(String first, String second, String third,
			String fourth)
	{
		System.out.print(first);
		if (first.length() < 35)
		{
			for (int i = first.length(); i < 35; i++)
				System.out.print(" ");
		}
		System.out.print(second);
		if (second.length() < 20)
		{
			for (int i = second.length(); i < 20; i++)
				System.out.print(" ");
		}
		System.out.print(third);

		if (third.length() < 20)
		{
			for (int i = third.length(); i < 20; i++)
				System.out.print(" ");
		}
		System.out.println(fourth);

	}

	class ValueComparator implements Comparator<Object>
	{

		Map<String, Double> base;

		public ValueComparator(Map<String, Double> base)
		{
			this.base = base;
		}

		public int compare(Object a, Object b)
		{
			if ((Double) base.get(a) < (Double) base.get(b))
			{
				return 1;
			} else if ((Double) base.get(a) == (Double) base.get(b))
			{
				return 0;
			} else
			{
				return -1;
			}
		}
	}
}
