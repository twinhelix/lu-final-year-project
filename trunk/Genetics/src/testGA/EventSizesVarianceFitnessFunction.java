package testGA;

import de.uka.aifb.com.jnsga2.*;
import java.util.*;

/**
 * This class implements a fitness function rating the variance of the number of assigned registrants
 * for all the different events (event sizes). A smaller value is better.
 * 
 * @author Joachim Melcher, Institut AIFB, Universitaet Karlsruhe (TH), Germany
 * @version 1.0
 */
public class EventSizesVarianceFitnessFunction implements FitnessFunction {
   
   private HashSet<Event> events;
   
   /**
    * Constructor.
    * 
    * @param events set of offered events
    */
   public EventSizesVarianceFitnessFunction(HashSet<Event> events) {
      if (events == null) {
         throw new IllegalArgumentException("'events' must not be null.");
      }
      
      this.events = events;
   }
   
   /**
    * Evaluates the fitness value (variance of the event sizes) of the specified individual.
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
      
      Event[] assignments = aIndividual.getAssignments();
      
      // initialize event to event size mapping
      HashMap<Event, Integer> event2eventSize = new HashMap<Event, Integer>();
      for (Event event : events) {
         event2eventSize.put(event, 0);
      }
      
      // compute event sizes
      for (Event assignment : assignments) {
         event2eventSize.put(assignment, event2eventSize.get(assignment) + 1);
      }
      
      // compute event sizes variance
      double mean = (double)assignments.length / events.size();
      
      double sum = 0.0;
      for (Event event : event2eventSize.keySet()) {
         sum += Math.pow(event2eventSize.get(event) - mean, 2);
      }
      
      return ((double)sum) / (events.size() - 1);
   }
}