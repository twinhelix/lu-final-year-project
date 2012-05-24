package engine;

import static engine.RoundRobinEngine.PRINT_RESULTS;
import agent.IExpert;
import environment.Game;
import environment.ImperfectGame;
import environment.ScoringSystem;

public class ImperfectRoundRobinEngine extends RoundRobinEngine
{
	private double prob = -1d;

	public ImperfectRoundRobinEngine(IExpert[] experts, int totalGames,
			ScoringSystem scoringSystem)
	{
		super(experts, totalGames, scoringSystem);
	}

	public ImperfectRoundRobinEngine(IExpert[] experts, int totalGames,
			ScoringSystem scoringSystem, double prob)
	{
		super(experts, totalGames, scoringSystem);
		this.prob = prob;
	}

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

				ImperfectGame game;
				if (prob == -1d)
				{
					game = new ImperfectGame(e1, e2, totalGames, scoringSystem);
				}
				else
				{
					game = new ImperfectGame(e1, e2, totalGames, scoringSystem,
							prob);
				}
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
}
