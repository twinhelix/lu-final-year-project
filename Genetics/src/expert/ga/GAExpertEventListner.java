package expert.ga;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

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
	@Override
	public void performNSGA2Event(NSGA2Event nsga2event)
	{
		if (nsga2event.getNumberGeneration() % 100 == 0)
		{
			System.out.println();
			System.out.println("Generation: "
					+ nsga2event.getNumberGeneration());

			LinkedList<Individual> bestIndividuals = nsga2event
					.getBestIndividuals();

			LinkedList<GAIndividual> bestAssignments = new LinkedList<GAIndividual>();
			for (Individual individual : bestIndividuals)
			{
				bestAssignments.add((GAIndividual) individual);
			}

			printBestAssignments(bestAssignments);
		}
	}

	/**
	 * Prints the specified assignment individuals.
	 * 
	 * @param bestAssignments
	 *            assignment individuals
	 */
	private static void printBestAssignments(
			LinkedList<GAIndividual> bestAssignments)
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

		for (int i = 0; i < array.length; i++)
		{
			System.out.println("EVENT!");
			// System.out.print(" Number no wish event: "
			// + array[i].getFitnessValue(0));
			// System.out.print(" / Sum priorities: "
			// + array[i].getFitnessValue(1));
			// System.out.println(" / Sizes variance: "
			// + array[i].getFitnessValue(2));
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
