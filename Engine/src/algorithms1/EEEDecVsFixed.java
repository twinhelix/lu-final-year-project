package algorithms1;

import static common.Settings.NO_OF_ROUNDS;
import static common.Settings.SCORING_SYSTEM;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import Results.GraphingResults;
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

public class EEEDecVsFixed
{
	private static int decWins = 0;
	private static int fixedWins = 0;
	private static final String EEEdec = "EEE - Decreasing Prob";
	private static final String EEEfix = "EEE - Fixed Prob";
	private static final int rounds = 10000;

	public static void main(String[] args)
	{
		String[] strats = { new GrudgerExpert(0).getName(),
				new PavlovExpert(0).getName(), new CooperateExpert(0).getName() };

		IExpert[] experts = { new TitForTatExpert(0),
				new EEEDecProb(0, strats), new EEEFixedProb(0, strats, 0.05),
				new RandomExpert(0), new DefectExpert(0), new PavlovExpert(0),
				new RemorsefulProberExpert(0, 0.2), new SoftGrudgerExpert(0),
				new GAExpert(0, false) };

		for (int i = 0; i < rounds; i++)
		{
			RoundRobinEngine engine = new RoundRobinEngine(experts,
					NO_OF_ROUNDS, SCORING_SYSTEM);
			engine.run();

			double decScore = engine.getScore(EEEdec);
			double fixedScore = engine.getScore(EEEfix);
			if (decScore > fixedScore)
			{
				decWins++;
			}
			else if (fixedScore > decScore)
			{
				fixedWins++;
			}
		}
		plotHistogram();
	}

	private static void plotHistogram()
	{
		// Create Histogram of all the most commonly appeared experts
		Map<String, Integer> histo = new TreeMap<String, Integer>();
		histo.put(EEEdec, new Integer(decWins));
		histo.put(EEEfix, new Integer(fixedWins));

		Map<String, Integer> sorted_histo = new TreeMap<String, Integer>(
				new ValueComparator(histo));
		sorted_histo.putAll(histo);

		GraphingResults gr = new GraphingResults(
				"Decreasing Prob. vs Fixed Prob.");
		gr.plotInteger(histo);
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
