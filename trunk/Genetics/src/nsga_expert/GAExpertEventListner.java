package nsga_expert;

import static nsga_expert.GASettings.WRITEFILE;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

import utils.ReadWriteTextFile;
import de.uka.aifb.com.jnsga2.Individual;
import de.uka.aifb.com.jnsga2.NSGA2Event;
import de.uka.aifb.com.jnsga2.NSGA2Listener;

/**
 * This class implements an NSGA-II event listener.
 */
public class GAExpertEventListner implements NSGA2Listener
{

	/**
	 * Performs the specified NSGA-II event. Every 100 generations, the best
	 * individuals found so far are printed.
	 * 
	 * @param nsga2event
	 *            NSGA-II event
	 */
	
	// number of solutions to show
	private static final int solutionNumber = 1;
	@Override
	public void performNSGA2Event(NSGA2Event nsga2event)
	{
		if (nsga2event.getNumberGeneration() % 2 == 0)
		{

			System.out.println();
			System.out.println("Generation: "
					+ nsga2event.getNumberGeneration());

			try
			{
				ReadWriteTextFile.setContents(WRITEFILE, "");
				ReadWriteTextFile.setContents(WRITEFILE, "--------- Generation: "
						+ nsga2event.getNumberGeneration() + "---------");
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			LinkedList<Individual> bestIndividuals = nsga2event
					.getBestIndividuals();

			LinkedList<GAIndividual> bestAssignments = new LinkedList<GAIndividual>();
			for (Individual individual : bestIndividuals)
			{
				bestAssignments.add((GAIndividual) individual);
			}

			try
			{
				printBestAssignments(bestAssignments);
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Prints the specified assignment individuals.
	 * 
	 * @param bestAssignments
	 *            assignment individuals
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static void printBestAssignments(
			LinkedList<GAIndividual> bestAssignments)
			throws FileNotFoundException, IOException
	{
		if (bestAssignments == null)
		{
			throw new IllegalArgumentException(
					"'bestAssignments' must not be null.");
		}

		// sort best assignments
		GAIndividual[] array = bestAssignments
				.toArray(new GAIndividual[bestAssignments.size()]);
		Arrays.sort(array, new GAIndividualComparator());

		System.out.println();
		System.out.println("Number of offered solutions: "
				+ bestAssignments.size());

		ReadWriteTextFile.setContents(WRITEFILE, "");
		ReadWriteTextFile.setContents(WRITEFILE,
				"Number of offered solutions: " + bestAssignments.size());
		ReadWriteTextFile.setContents(WRITEFILE, "");

		for (int i = 0; i < array.length && i < solutionNumber; i++)
		{

			System.out.println("GA Fitness: " + (-array[i].getFitnessValue(0)));
			System.out.println("Average Opponents Fitness: "
					+ array[i].getFitnessValue(1));
			System.out.println("Expert Codebit: "
					+ array[i].getExpert().getCodebit());
			System.out.println();

			ReadWriteTextFile.setContents(WRITEFILE,
					"GA Fitness: " + (-array[i].getFitnessValue(0)));
			ReadWriteTextFile
					.setContents(WRITEFILE, "Average Opponents Fitness: "
							+ array[i].getFitnessValue(1));
			ReadWriteTextFile.setContents(WRITEFILE, "Expert Codebit: "
					+ array[i].getExpert().getCodebit());
			ReadWriteTextFile.setContents(WRITEFILE, "");

		}
	}

	/**
	 * This inner class implemens a comparator for two assignment individuals.
	 */
	private static class GAIndividualComparator implements
			Comparator<GAIndividual>
	{

		/**
		 * Compares the two specified assignment individuals. First criterion is
		 * small number of "no wish events", second one is a small sum of event
		 * priorities and the third one a small variance of the event sizes.
		 * 
		 * @param individual1
		 *            first individual
		 * @param individual2
		 *            second individual
		 * @return -1, 0 or 1 as the first individual is less than, equal to, or
		 *         greater than the second one
		 */
		public int compare(GAIndividual individual1, GAIndividual individual2)
		{
			if (individual1 == null)
			{
				throw new IllegalArgumentException(
						"'individual1' must not be null.");
			}
			if (individual2 == null)
			{
				throw new IllegalArgumentException(
						"'individual2' must not be null.");
			}

			int objectives = individual1.getNumberOfObjectives();
			for (int i = 0; i < objectives; i++)
			{
				if (individual1.getFitnessValue(i) < individual2
						.getFitnessValue(i))
				{
					return -1;
				}

				if (individual1.getFitnessValue(i) > individual2
						.getFitnessValue(i))
				{
					return 1;
				}
			}

			// both individuals are equal
			return 0;
		}
	}

}
