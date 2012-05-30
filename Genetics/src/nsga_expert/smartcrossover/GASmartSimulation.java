package nsga_expert.smartcrossover;

import static nsga_expert.GASettings.CROSSOVER_PROBABILITY;
import static nsga_expert.GASettings.MUTATION_PROBABILITY;
import static nsga_expert.GASettings.NUMBER_OF_GENERATIONS;
import static nsga_expert.GASettings.POPULATION_SIZE;

import java.util.LinkedList;

import de.uka.aifb.com.jnsga2.FitnessFunction;
import de.uka.aifb.com.jnsga2.Individual;
import de.uka.aifb.com.jnsga2.NSGA2;
import de.uka.aifb.com.jnsga2.NSGA2Configuration;

public class GASmartSimulation
{
	private FitnessFunction[] fitnessFunctions;
	private NSGA2Configuration nsgaConfig;
	private NSGA2 nsga;

	public static void main(String[] args)
	{
		GASmartSimulation sim = new GASmartSimulation();
		sim.initialize();
	}

	public GASmartSimulation()
	{

	}

	public void initialize()
	{
		// Set up NSGA object
		fitnessFunctions = new FitnessFunction[2];
		fitnessFunctions[0] = new GASmartFitnessOwnScore();
		fitnessFunctions[1] = new GASmartFitnessOpponentScore();

		nsgaConfig = new NSGA2Configuration(fitnessFunctions,
				MUTATION_PROBABILITY, CROSSOVER_PROBABILITY, POPULATION_SIZE,
				NUMBER_OF_GENERATIONS);

		nsga = new NSGA2(nsgaConfig);
		nsga.addNSGA2Listener(new GAExpertSmartEventListner());

		// Create starting Pop
		LinkedList<Individual> startPopulation = new LinkedList<Individual>();
		for (int i = 0; i < POPULATION_SIZE; i++)
		{
			startPopulation.add(new GAIndividualSmartCrossover(nsga, 0));
		}
		LinkedList<Individual> pop = nsga.evolve(startPopulation);
		System.out.println(pop.getFirst().getRank());

	}
}
