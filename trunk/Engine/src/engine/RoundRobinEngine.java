package engine;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import Performance.GraphingPerformance;
import Results.GraphingResults;
import agent.IExpert;

import common.Settings;

import environment.Game;
import environment.ScoringSystem;

public class RoundRobinEngine
{
	private static boolean PRINT_RESULTS = false;

	private IExpert[] experts;
	private int totalGames;
	private ScoringSystem scoringSystem;
	private Map<String, Double> totals;
	private TreeMap<String, Double> sorted_map;

	public RoundRobinEngine(IExpert[] experts, int totalGames,
			ScoringSystem scoringSystem)
	{
		this.experts = experts;
		this.totalGames = totalGames;
		this.scoringSystem = scoringSystem;
		initialiseTally(experts);
	}

	private void initialiseTally(IExpert[] experts)
	{
		totals = new HashMap<String, Double>();

		for (IExpert expert : experts)
		{
			totals.put(expert.getName(), new Double(0));
		}
	}

	/**
	 * Runs a round robin tournament with all given players
	 */
	public void run()
	{

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

				totals.put(e1.getName(), new Double(totals.get(e1.getName())
						.doubleValue() + result[0]));
				totals.put(e2.getName(), new Double(totals.get(e2.getName())
						.doubleValue() + result[1]));

				if (PRINT_RESULTS)
					printTwoColumns(e1.getName() + ": " + result[0],
							e2.getName() + ": " + result[1]);
			}
		}
	}

	public void showTally()
	{
		sorted_map = new TreeMap<String, Double>(new ValueComparator(totals));
		sorted_map.putAll(totals);
		double benchmark = Settings.getBenchMark(sorted_map.size());

		System.out.println("--- RESULTS ---");
		int i = 0;
		DecimalFormat df = new DecimalFormat("#.##");

		for (String key : sorted_map.keySet())
		{
			i++;
			printTwoColumns(
					(i + ". " + key + ": " + sorted_map.get(key).doubleValue()),
					("Benchmark Score: "
							+ df.format(sorted_map.get(key).doubleValue()
									/ ((experts.length + 1) * benchmark) * 100) + "%"));
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
		graph.plot(sorted_map);
	}

	private void printTwoColumns(String first, String second)
	{
		System.out.print(first);
		if (first.length() < 45)
		{
			for (int i = first.length(); i < 45; i++)
				System.out.print(" ");
		}
		System.out.println(second);
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
			}
			else if ((Double) base.get(a) == (Double) base.get(b))
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
