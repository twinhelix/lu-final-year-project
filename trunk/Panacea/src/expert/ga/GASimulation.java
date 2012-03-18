package expert.ga;

import java.io.BufferedWriter;
import java.io.FileWriter;

import utils.Encoding;
import de.uka.aifb.com.jnsga2.FitnessFunction;
import de.uka.aifb.com.jnsga2.NSGA2;
import de.uka.aifb.com.jnsga2.NSGA2Configuration;

public class GASimulation
{
	private final int populationSize = 40;
	private final int numberOfGenerations = 20000;
	private final double mutationProbability = 1.0 / 70;
	private final double crossoverProbability = 0.9;
	private FitnessFunction[] fitnessFunctions;
	private NSGA2Configuration nsgaConfig;
	private NSGA2 nsga;

	public GASimulation()
	{
		initialize();
	}

	public void initialize()
	{
		fitnessFunctions = new FitnessFunction[1];
		fitnessFunctions[0] = new GAFitness();

		nsgaConfig = new NSGA2Configuration(fitnessFunctions,
				mutationProbability, crossoverProbability, populationSize,
				numberOfGenerations);

		nsga = new NSGA2(nsgaConfig);
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
