package testGA;

/**
 * This class implements an offered event.
 * 
 * @author Joachim Melcher, Institut AIFB, Universitaet Karlsruhe (TH), Germany
 * @version 1.0
 */
public class Event {

   private String name;
   
   /**
    * Constructor.
    * 
    * @param name event name
    */
   public Event(String name) {
      if (name == null) {
         throw new IllegalArgumentException("'name' must not be null.");
      }
      
      this.name = name;
   }
   
   /**
    * Gets the event name.
    * 
    * @return event name
    */
   public String getName() {
      return name;
   }
}