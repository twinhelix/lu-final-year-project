package testGA;

import de.uka.aifb.com.jnsga2.*;

/**
 * This class implements a fitness function rating the number of registrants that have not been
 * assigned one of their wish events. A smaller value is better.
 * 
 * @author Joachim Melcher, Institut AIFB, Universitaet Karlsruhe (TH), Germany
 * @version 1.0
 */
public class NoWishEventFitnessFunction implements FitnessFunction {

   /**
    * Evaluates the fitness value (number of registrants that have not been assigned one of their
    * wish events) of the specified individual.
    * 
    * @param individual individual
    * @return fitness value
    */
   public double evaluate(Individual individual) {
      if (individual == null) {
         throw new IllegalArgumentException("'individual' must not be null.");
      }
      if (!(individual instanceof AssignmentIndividual)) {
         throw new IllegalArgumentException("'individual' must be of type 'AssignmentIndividual'.");
      }
      
      AssignmentIndividual aIndividual = (AssignmentIndividual)individual;
      
      Registration[] registrations = aIndividual.getRegistrations();
      Event[] assignments = aIndividual.getAssignments();
      
      int numberNoWishEvents = 0;
      
      for (int i = 0; i < registrations.length; i++) {
         Priority priority = registrations[i].getPriorityForEvent(assignments[i]);
         if (priority == null) {
            numberNoWishEvents++;
         }
      }
      
      return numberNoWishEvents;
   }
}