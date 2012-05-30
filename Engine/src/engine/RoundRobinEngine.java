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

public class RoundRobinEngine
{
	protected static boolean PRINT_RESULTS = false;

	protected IExpert[] experts;
	protected int totalGames;
	protected ScoringSystem scoringSystem;
	protected Stack<Map<String, Double>> totals;
	protected Map<String, Double> averageScores;
	protected Map<String, Double> variances;
	protected TreeMap<String, Double> sorted_map;
	protected int runs;

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
		initialiseScores();
	}

	private void initialiseTally()
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

		for (IExpert expert : experts)
		{
			averageScores.put(expert.getName(), new Double(0));
			variances.put(expert.getName(), new Double(0));
		}
	}

	/**
	 * Runs a round robin tournament with all given players
	 */
	public void run()
	{
		for (int run = 0; run < runs; run++)
		{
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
						printThreeColumns(e1.getName() + ": " + result[0], e2
								.getName()
								+ ": " + result[1], "");
				}
			}
		}
		calculateAverageScores();
		calculateVariances();
	}

	private void calculateAverageScores()
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

	private void calculateVariances()
	{
		for (Map<String, Double> roundScore : totals)
		{
			for (String key : roundScore.keySet())
			{
				double benchmark = Settings.getBenchMark(averageScores.size());

				double squaredDiff = Math.pow(roundScore.get(key) / benchmark
						- averageScores.get(key) / benchmark, 2);

				variances
						.put(key, new Double(variances.get(key) + squaredDiff));
			}
		}
		for (String key : variances.keySet())
		{
			variances.put(key, new Double(variances.get(key) / runs));
		}

	}

	public double getScore(String key)
	{
		double benchmark = Settings.getBenchMark(averageScores.size());

		return (averageScores.get(key).doubleValue()
				/ ((experts.length + 1) * benchmark) * 100);
	}

	public double getAverageOpponentScore(String key)
	{
		double benchmark = ((experts.length + 1) * Settings
				.getBenchMark(averageScores.size()));

		double totalOpponentsScore = 0d;
		for (String expertName : averageScores.keySet())
		{
			totalOpponentsScore += averageScores.get(expertName);
		}

		totalOpponentsScore -= averageScores.get(key).doubleValue();
		double averageOpponentsScore = totalOpponentsScore
				/ (experts.length - 1);
		return (averageOpponentsScore / benchmark * 100);
	}

	public void showTally()
	{
		sorted_map = new TreeMap<String, Double>(new ValueComparator(
				averageScores));
		sorted_map.putAll(averageScores);
		double benchmark = Settings.getBenchMark(sorted_map.size());

		System.out.println("--- AVERAGE RESULTS OF " + runs + " runs ---");
		int i = 0;
		DecimalFormat df = new DecimalFormat("#.##");

		for (String key : sorted_map.keySet())
		{
			i++;
			printThreeColumns((i + ". " + key + ": " + df.format(sorted_map
					.get(key).doubleValue())), ("Benchmark Score: "
					+ df.format(sorted_map.get(key).doubleValue()
							/ ((experts.length + 1) * benchmark) * 100) + "%"),
					("Variance: " + variances.get(key)));
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

	protected void printThreeColumns(String first, String second, String third)
	{
		System.out.print(first);
		if (first.length() < 45)
		{
			for (int i = first.length(); i < 45; i++)
				System.out.print(" ");
		}
		System.out.print(second);
		if (second.length() < 40)
		{
			for (int i = second.length(); i < 45; i++)
				System.out.print(" ");
		}
		System.out.println(third);
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
