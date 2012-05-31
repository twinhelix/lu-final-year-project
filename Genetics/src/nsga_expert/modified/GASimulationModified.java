package nsga_expert.modified;

import static nsga_expert.GASettings.CROSSOVER_PROBABILITY;
import static nsga_expert.GASettings.MUTATION_PROBABILITY;
import static nsga_expert.GASettings.NUMBER_OF_GENERATIONS;
import static nsga_expert.GASettings.POPULATION_SIZE;

import java.util.LinkedList;

import de.uka.aifb.com.jnsga2.FitnessFunction;
import de.uka.aifb.com.jnsga2.Individual;
import de.uka.aifb.com.jnsga2.NSGA2;
import de.uka.aifb.com.jnsga2.NSGA2Configuration;

public class GASimulationModified
{

	private FitnessFunction[] fitnessFunctions;
	private NSGA2Configuration nsgaConfig;
	private NSGA2 nsga;

	public static void main(String[] args)
	{
		GASimulationModified sim = new GASimulationModified();
		sim.initialize();
	}

	public GASimulationModified()
	{

	}

	public void initialize()
	{
		// Set up NSGA object
		fitnessFunctions = new FitnessFunction[2];
		fitnessFunctions[0] = new GAFitnessOwnScoreModified();
		fitnessFunctions[1] = new GAFitnessOpponentScoreModified();

		nsgaConfig = new NSGA2Configuration(fitnessFunctions,
				MUTATION_PROBABILITY, CROSSOVER_PROBABILITY, POPULATION_SIZE,
				NUMBER_OF_GENERATIONS);

		nsga = new NSGA2(nsgaConfig);
		nsga.addNSGA2Listener(new GAExpertModifiedEventListner());

		// Create starting Pop
		LinkedList<Individual> startPopulation = new LinkedList<Individual>();
		for (int i = 0; i < POPULATION_SIZE; i++)
		{
			startPopulation.add(new GAIndividualModified(nsga, 0));
		}
		LinkedList<Individual> pop = nsga.evolve(startPopulation);
		System.out.println(pop.getFirst().getRank());

	}
}
