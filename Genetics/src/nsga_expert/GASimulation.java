package nsga_expert;

import java.util.LinkedList;

import de.uka.aifb.com.jnsga2.FitnessFunction;
import de.uka.aifb.com.jnsga2.Individual;
import de.uka.aifb.com.jnsga2.NSGA2;
import de.uka.aifb.com.jnsga2.NSGA2Configuration;

public class GASimulation
{
	private static final int populationSize = 200;
	private static final int numberOfGenerations = 1000;
	private static final double mutationProbability = 0.14286d; // prob: 1/70
	private static final double crossoverProbability = 0.9;
	private FitnessFunction[] fitnessFunctions;
	private NSGA2Configuration nsgaConfig;
	private NSGA2 nsga;

	public static void main(String[] args)
	{
		GASimulation sim = new GASimulation();
		sim.initialize();
	}

	public GASimulation()
	{
		
	}

	public void initialize()
	{
		// Set up NSGA object
		fitnessFunctions = new FitnessFunction[2];
		fitnessFunctions[0] = new GAFitnessOwnScore();
		fitnessFunctions[1] = new GAFitnessOpponentScore();

		nsgaConfig = new NSGA2Configuration(fitnessFunctions,
				mutationProbability, crossoverProbability, populationSize,
				numberOfGenerations);

		nsga = new NSGA2(nsgaConfig);
		nsga.addNSGA2Listener(new GAExpertEventListner());

		// Create starting Pop
		LinkedList<Individual> startPopulation = new LinkedList<Individual>();
		for (int i = 0; i < populationSize; i++)
		{
			startPopulation.add(new GAIndividual(nsga, 0));
		}
		LinkedList<Individual> pop = nsga.evolve(startPopulation);
		System.out.println(pop.getFirst().getRank());

	}
}
