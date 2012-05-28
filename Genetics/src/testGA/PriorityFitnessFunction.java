package testGA;

import de.uka.aifb.com.jnsga2.*;

/**
 * This class implements a fitness function rating the sum of priorities of the
 * events assigned to the different registrants. A smaller value is better.
 * 
 * @author Joachim Melcher, Institut AIFB, Universitaet Karlsruhe (TH), Germany
 * @version 1.0
 */
public class PriorityFitnessFunction implements FitnessFunction
{

	private static final double NON_EXISTING_PRIORITY = 10.0;

	/**
	 * Evaluates the fitness value (sum of priorities of the events assigned to
	 * the different registrans) of the specified individual.
	 * 
	 * @param individual
	 *            individual
	 * @return fitness value
	 */
	public double evaluate(Individual individual)
	{
		if (individual == null)
		{
			throw new IllegalArgumentException("'individual' must not be null.");
		}
		if (!(individual instanceof AssignmentIndividual))
		{
			throw new IllegalArgumentException(
					"'individual' must be of type 'AssignmentIndividual'.");
		}

		AssignmentIndividual aIndividual = (AssignmentIndividual) individual;

		Registration[] registrations = aIndividual.getRegistrations();
		Event[] assignments = aIndividual.getAssignments();

		double sumPriorities = 0.0;
		for (int i = 0; i < registrations.length; i++)
		{
			Priority priority = registrations[i]
					.getPriorityForEvent(assignments[i]);
			if (priority == null)
			{
				sumPriorities += NON_EXISTING_PRIORITY;
			}
			else
			{
				sumPriorities += priority.ordinal() + 1; // because ordinal()
															// starts with 0
															// instead of 1!
			}
		}

		return sumPriorities;
	}
}