package engine;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import agent.IExpert;

import common.Settings;

import environment.Game;
import environment.ScoringSystem;

public class RoundRobinEngine
{
	private IExpert[] experts;
	private int totalGames;
	private ScoringSystem scoringSystem;
	private Map<String, Double> totals;

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
			for (int j = 0; j < experts.length; j++)
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

				System.out.println(e1.getName() + ": " + result[0] + "\t\t"
						+ e2.getName() + ": " + result[1]);
			}
		}
	}

	public void showTally()
	{
		TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(
				new ValueComparator(totals));
		sorted_map.putAll(totals);
		double benchmark = Settings.getBenchMark(sorted_map.size());

		System.out.println("--- RESULTS ---");
		int i = 0;
		for (String key : sorted_map.keySet())
		{
			i++;
			System.out.println(i + ". " + key + ": "
					+ sorted_map.get(key).doubleValue());
		}
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
