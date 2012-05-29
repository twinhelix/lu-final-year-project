package nsga_expert;

import java.io.File;

public class GASettings
{
	public static final int RUNS_PER_EVALUATION = 5;
	public static final int POPULATION_SIZE = 200;
	public static final int NUMBER_OF_GENERATIONS = 1000;
	public static final double MUTATION_PROBABILITY = 0.14286d; // prob: 1/70
	public static final double CROSSOVER_PROBABILITY = 0.9;
	public static File WRITEFILE = new File("GA Expert - Frontiers MODIFIED.txt");
}
