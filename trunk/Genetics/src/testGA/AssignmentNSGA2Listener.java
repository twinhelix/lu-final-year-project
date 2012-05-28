package testGA;

import de.uka.aifb.com.jnsga2.*;
import java.util.*;

/**
 * This class implements an NSGA-II event listener.
 * 
 * @author Joachim Melcher, Institut AIFB, Universitaet Karlsruhe (TH), Germany
 * @version 1.0
 */
public class AssignmentNSGA2Listener implements NSGA2Listener {
   
   /**
    * Performs the specified NSGA-II event.
    * <p>
    * Every 100 generations, the best individuals found so far are printed.
    * 
    * @param nsga2event NSGA-II event
    */
   public void performNSGA2Event(NSGA2Event nsga2event) {
      if (nsga2event.getNumberGeneration() % 100 == 0) {
         System.out.println();
         System.out.println("Generation: " + nsga2event.getNumberGeneration());
         
         LinkedList<Individual> bestIndividuals = nsga2event.getBestIndividuals();
      
         LinkedList<AssignmentIndividual> bestAssignments = new LinkedList<AssignmentIndividual>();
         for (Individual individual : bestIndividuals) {
            bestAssignments.add((AssignmentIndividual)individual);
         }
      
         printBestAssignments(bestAssignments);
      }
   }
   
   /**
    * Prints the specified assignment individuals.
    * 
    * @param bestAssignments assignment individuals
    */
   private static void printBestAssignments(LinkedList<AssignmentIndividual> bestAssignments) {
      if (bestAssignments == null) {
         throw new IllegalArgumentException("'bestAssignments' must not be null.");
      }
      
      // sort best assignments
      AssignmentIndividual[] array =
         bestAssignments.toArray(new AssignmentIndividual[bestAssignments.size()]);
      Arrays.sort(array, new AssignmentIndividualComparator());
      
      System.out.println();
      System.out.println("Number of offered solutions: " + bestAssignments.size());
      
      for (int i = 0; i < array.length; i++) {
         System.out.print(" Number no wish event: " + array[i].getFitnessValue(0));
         System.out.print(" / Sum priorities: " + array[i].getFitnessValue(1));
         System.out.println(" / Sizes variance: " + array[i].getFitnessValue(2));
      }
   }
   
   /**
    * This inner class implemens a comparator for two assignment individuals.
    */
   private static class AssignmentIndividualComparator implements Comparator<AssignmentIndividual> {
      
      /**
       * Compares the two specified assignment individuals. First criterion is small number of
       * "no wish events", second one is a small sum of event priorities and the third one a small
       * variance of the event sizes. 
       * 
       * @param individual1 first individual
       * @param individual2 second individual
       * @return -1, 0 or 1 as the first individual is less than, equal to, or greater than the
       *         second one
       */
      public int compare(AssignmentIndividual individual1, AssignmentIndividual individual2) {
         if (individual1 == null) {
            throw new IllegalArgumentException("'individual1' must not be null.");
         }
         if (individual2 == null) {
            throw new IllegalArgumentException("'individual2' must not be null.");
         }
         
         // (1) number of "no wish events" (objective 0)
         if (individual1.getFitnessValue(0) < individual2.getFitnessValue(0)) {
            return -1;
         }
         
         if (individual1.getFitnessValue(0) > individual2.getFitnessValue(0)) {
            return 1;
         }
         
         // (2) sum priorities (objective 1)
         if (individual1.getFitnessValue(1) < individual2.getFitnessValue(1)) {
            return -1;
         }
         
         if (individual1.getFitnessValue(1) > individual2.getFitnessValue(1)) {
            return 1;
         }
         
         // (3) event sizes variance (objective 2)
         if (individual1.getFitnessValue(2) < individual2.getFitnessValue(2)) {
            return -1;
         }
         
         if (individual1.getFitnessValue(2) > individual2.getFitnessValue(2)) {
            return 1;
         }
         
         // both individuals are equal
         return 0;
      }
   }
}