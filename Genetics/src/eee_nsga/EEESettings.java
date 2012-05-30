package eee_nsga;

import java.io.File;

public class EEESettings
{
	public static final int RUNS_PER_EVALUATION = 5;
	public static final int POPULATION_SIZE = 40;
	public static final int NUMBER_OF_GENERATIONS = 500;
	public static final double MUTATION_PROBABILITY = 0.2; 
	public static final double CROSSOVER_PROBABILITY = 0.9;
	public static File WRITEFILE = new File(
			"EEE Pool Findings.txt");
}
