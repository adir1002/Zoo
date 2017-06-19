package plants;


/**
 * 
 *  @author Daniel Bromberg 209529882 and Adir Abuhazera 208903765 beer sheba
 *
 */
public class Cabbage extends Plant {

	private static Cabbage inst = new Cabbage();

   /* A private Constructor prevents any other
    * class from instantiating.
    */
   protected Cabbage() { loadImages("cabbage"); }
   
   /* Static 'instance' method */
   public static Cabbage getInstance( ) {
      return inst;
   }
}
