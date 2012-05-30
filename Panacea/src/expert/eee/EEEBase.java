package expert.eee;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import utils.ExpertsDictionary;
import agent.IExpert;
import environment.GameHistory;
import expert.AbstractExpert;
import expert.titfortat.TitForTatExpert;

/***
 * The Exploration-Exploitation Experts method (EEE)
 * 
 * @author HassassiN
 * 
 */
public abstract class EEEBase extends AbstractExpert
{

	private final boolean DEBUG = false;

	// Array of experts that the EEE algorithm can employ
	protected Advisor[] advisors;
	private Map<String, List<Double>> performance;

	// Values needed
	protected double prob;
	protected int i;
	private double currentTotalScore;
	private Phase phase;
	private int agentChoice, maxStages, currentStage;
	private IExpert[] experts;

	public EEEBase(int playerNo, String[] strategies)
	{
		this(playerNo, strategies, 1);
	}

	public EEEBase(int playerNo, int poolSize)
	{
		super(playerNo);
		this.prob = 1;
		populateRandomExpertArray(poolSize);
		initialize();
	}

	public EEEBase(int playerNo, String[] strategies, double prob)
	{
		super(playerNo);
		this.prob = prob;
		populateExpertArray(strategies);
		initialize();
	}

	@Override
	public void initialize()
	{
		phase = Phase.IDLE;
		agentChoice = 0;
		i = 1;
		currentTotalScore = 0;
		maxStages = 0;
		currentStage = 0;
		initlizePerformanceMap();
	}

	private void initlizePerformanceMap()
	{
		performance = new HashMap<String, List<Double>>();
		for (Advisor advisor : advisors)
		{
			performance.put(advisor.expert.getName(), new LinkedList<Double>());
		}
	}

	public Map<String, List<Double>> getPerformance()
	{
		return performance;
	}

	private void populateRandomExpertArray(int poolSize)
	{
		ExpertsDictionary dict = new ExpertsDictionary(playerNo, 0.2);
		IExpert[] experts = dict.getRandomExperts(poolSize);
		reassignPool(experts);
	}

	public IExpert[] getExperts()
	{
		return experts;
	}

	public void reassignPool(IExpert[] experts)
	{
		this.experts = experts;

		advisors = new Advisor[experts.length];

		for (int i = 0; i < experts.length; i++)
		{
			advisors[i] = new Advisor(experts[i]);
		}
		initialize();
	}

	/***
	 * need to override set player number as all player numbers need to be reset
	 */
	@Override
	public void setPlayerNumber(int playerNo)
	{
		this.playerNo = playerNo;
		// populateExpertArray(strategies);
		for (int i = 0; i < advisors.length; i++)
		{
			advisors[i].expert.setPlayerNumber(playerNo);
		}
	}

	/***
	 * Populates advisers array and initializes values
	 * 
	 */
	protected void populateExpertArray(String[] strategies)
	{
		ExpertsDictionary dict = new ExpertsDictionary(playerNo, 0.2);
		experts = new IExpert[strategies.length];
		advisors = new Advisor[strategies.length];

		for (int i = 0; i < advisors.length; i++)
		{
			IExpert e = dict.getExpert(strategies[i]);
			if (e == null)
			{
				e = new TitForTatExpert(playerNo);
			}
			experts[i] = e;
			advisors[i] = new Advisor(e);
		}
	}

	/**
	 * -Exploration. An exploration phase consists of picking a random expert e
	 * (i.e., from the uniform distribution over {1,...,r}), and following e�s
	 * recommendations for a certain number of stages depending on the variant
	 * of the method.
	 * 
	 * -Exploitation. An exploitation phase consists of picking an expert e with
	 * maximum Me, breaking ties at random, and following e�s recommendations
	 * for a certain number of stages depending on the variant of the method.
	 */
	@Override
	public boolean move(GameHistory history)
	{

		if (advisors == null || advisors.length == 0)
		{
			return false;
		}

		currentStage++;

		if (history.getNumberOfMoves() == 0)
		{
			findExploreAgent();
			return explore(history);

		}
		else
		{
			// first always update previous round scores
			updateScores(history);

			if (currentStage > maxStages)
			{

				// End of a phase
				// if (DEBUG)
				// System.out.println("Phase: " + i);
				i++;
				updateLastPhase(maxStages);
				if (DEBUG)
				{
					showExpertList();
					System.out.println("Current total Score: "
							+ currentTotalScore);
				}
				currentTotalScore = 0;
				phase = Phase.IDLE;
				currentStage = 1;
			}

			if (phase == Phase.IDLE)
			{
				// A phase has just ended, update last phase and check
				// whether to explore or exploit

				double exploreProb = Math.random();

				if (exploreProb < getProb())
				{
					// Perform Exploration phase --> update scores from last
					// phase first
					findExploreAgent();
					return explore(history);
				}
				else
				{
					phase = Phase.EXPLOIT;
					return exploit(history);
				}
				// Update last phase

			}
			else if (phase == Phase.EXPLORE)
			{
				return explore(history);
			}
			else if (phase == Phase.EXPLOIT)
			{
				return exploit(history);
			}
		}
		return false;
	}

	protected abstract double getProb();

	private void findExploreAgent()
	{
		phase = Phase.EXPLORE;
		agentChoice = (int) (Math.random() * (advisors.length));
		maxStages = advisors[agentChoice].phase;
		if (DEBUG)
			System.out.println("PICKING TO EXPLORE: "
					+ advisors[agentChoice].expert.getName() + " for "
					+ maxStages + " stages");
	}

	private boolean explore(GameHistory history)
	{
		// Use current experts advice
		return advisors[agentChoice].expert.move(history);
	}

	private boolean exploit(GameHistory history)
	{

		if (currentStage == 1)
		{
			findBestExpert();
			maxStages = advisors[agentChoice].phase;
			if (DEBUG)
			{
				System.out.println("PICKING TO EXPLOIT: "
						+ advisors[agentChoice].expert.getName() + " for "
						+ maxStages + " stages");
			}
		}
		// Use current experts advice
		return advisors[agentChoice].expert.move(history);
	}

	private void showExpertList()
	{
		for (int i = 0; i < advisors.length; i++)
		{
			System.out.println(advisors[i].expert.getName() + " "
					+ advisors[i].aveReward);
		}
		System.out.println();
	}

	private void findBestExpert()
	{
		double bestScore = advisors[0].aveReward;
		List<Integer> indices = new LinkedList<Integer>();
		indices.add(0);

		for (int i = 0; i < advisors.length; i++)
		{
			if (advisors[i].aveReward == bestScore)
			{
				indices.add(i);
			}
			else if (advisors[i].aveReward > bestScore)
			{
				bestScore = advisors[i].aveReward;
				indices.clear();
				indices.add(i);
			}
		}
		int choice = (int) (Math.random() * indices.size());
		// System.out.println(choice);
		agentChoice = indices.get(choice);
	}

	private void updateScores(GameHistory history)
	{
		currentTotalScore += history.getPlayerLastScore(playerNo);
	}

	private void updateLastPhase(int stages)
	{
		// Find Average:
		double averageReward = currentTotalScore / stages;
		// Update Number of phases
		advisors[agentChoice].phase++;
		// Update Number of stages
		advisors[agentChoice].stage += stages;
		// Update Average reward of phases followed
		advisors[agentChoice].aveReward = advisors[agentChoice].aveReward
				+ (((double) stages) / advisors[agentChoice].stage)
				* (averageReward - advisors[agentChoice].aveReward);
		updatePerformance();
	}

	private void updatePerformance()
	{
		for (Advisor advisor : advisors)
		{
			performance.get(advisor.expert.getName()).add(advisor.aveReward);
		}
	}
}
