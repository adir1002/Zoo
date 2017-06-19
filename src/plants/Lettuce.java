package plants;


/**
 * 
 *  @author Daniel Bromberg 209529882 and Adir Abuhazera 208903765 beer sheba
 *
 */
public class Lettuce extends Plant {

	private static Lettuce inst = new Lettuce();

	   /* A private Constructor prevents any other
	    * class from instantiating.
	    */
	   protected Lettuce() { loadImages("Lettuce"); }
	   
	   /* Static 'instance' method */
	   public static Lettuce getInstance( ) {
	      return inst;
	   }
}
