package nsga_expert;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.LinkedList;

import de.uka.aifb.com.jnsga2.FitnessFunction;
import de.uka.aifb.com.jnsga2.Individual;
import de.uka.aifb.com.jnsga2.NSGA2;
import de.uka.aifb.com.jnsga2.NSGA2Configuration;

public class GASimulation
{
	private static final int populationSize = 4;
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
		fitnessFunctions = new FitnessFunction[1];
		fitnessFunctions[0] = new GAFitnessOwnScore();

		nsgaConfig = new NSGA2Configuration(fitnessFunctions,
				mutationProbability, crossoverProbability, populationSize,
				numberOfGenerations);

		nsga = new NSGA2(nsgaConfig);
		nsga.addNSGA2Listener(new GAExpertEventListner());
			
		// Create starting Pop
		GAIndividual expert = new GAIndividual(nsga, 0);
		LinkedList<Individual> startPopulation = new LinkedList<Individual>();
		startPopulation.add(expert);
		startPopulation.add(new GAIndividual(nsga, 0));
		startPopulation.add(new GAIndividual(nsga, 0));
		startPopulation.add(new GAIndividual(nsga, 0));

		
		System.out.println(expert.getExpert().getCodebit());
		LinkedList<Individual> pop = nsga.evolve(startPopulation);
		GAIndividual e = (GAIndividual) pop.getFirst();
		System.out.println(e.getExpert().getCodebit());

	}

	private void writeToFile(String code)
	{
		{
			try
			{
				// Create file
				FileWriter fstream = new FileWriter("out.txt");
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(code);
				// Close the output stream
				out.close();
			}
			catch (Exception e)
			{// Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
		}
	}
}
