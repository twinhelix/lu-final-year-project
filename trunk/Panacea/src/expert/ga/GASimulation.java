package expert.ga;

import java.io.BufferedWriter;
import java.io.FileWriter;

import utils.Encodings;
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
	private int premises;
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

	private int encode(Encodings[] strat)
	{
		int seq = 0;
		for (int i = 0; i < strat.length; i++)
		{
			seq = (seq + strat[strat.length - i - 1].ordinal()
					* ((int) Math.pow(4, i)));
		}
		return seq;
	}

	private Encodings[] decode(int seq)
	{
		Encodings[] strat = new Encodings[3];

		return strat;
	}

	/***
	 * First 3 steps of the game are randomly assigned
	 * 
	 * @return
	 */
	private void assignPremises()
	{
		premises = (int) Math.random() * 64;
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
