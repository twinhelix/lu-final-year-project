package eee_nsga;

import static nsga_expert.GASettings.CROSSOVER_PROBABILITY;
import static nsga_expert.GASettings.MUTATION_PROBABILITY;
import static nsga_expert.GASettings.NUMBER_OF_GENERATIONS;
import static nsga_expert.GASettings.POPULATION_SIZE;

import java.util.LinkedList;

import de.uka.aifb.com.jnsga2.FitnessFunction;
import de.uka.aifb.com.jnsga2.Individual;
import de.uka.aifb.com.jnsga2.NSGA2;
import de.uka.aifb.com.jnsga2.NSGA2Configuration;

public class EEESimulation
{
	private int poolSize = 4;
	private FitnessFunction[] fitnessFunctions;
	private NSGA2Configuration nsgaConfig;
	private NSGA2 nsga;

	public static void main(String[] args)
	{
		EEESimulation sim = new EEESimulation();
		sim.initialize();
	}

	public EEESimulation()
	{

	}

	public void initialize()
	{
		// Set up NSGA object
		fitnessFunctions = new FitnessFunction[2];
		fitnessFunctions[0] = new EEEOwnScore();
		fitnessFunctions[1] = new EEEOpponentScore();

		nsgaConfig = new NSGA2Configuration(fitnessFunctions,
				MUTATION_PROBABILITY, CROSSOVER_PROBABILITY, POPULATION_SIZE,
				NUMBER_OF_GENERATIONS);

		nsga = new NSGA2(nsgaConfig);
		nsga.addNSGA2Listener(new EEEEventListner());

		// Create starting Pop
		LinkedList<Individual> startPopulation = new LinkedList<Individual>();
		for (int i = 0; i < POPULATION_SIZE; i++)
		{
			startPopulation.add(new EEEIndividual(nsga, poolSize));
		}
		nsga.evolve(startPopulation);
	}
}
