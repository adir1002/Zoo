package factory;


/**
 * 
 *  @author Daniel Bromberg 209529882 and Adir Abuhazera 208903765 beer sheba
 *  
 */
public class AnimalFactory {
	/**
	 * by the type creating the factory;
	 * @param foodtype - string represent factory;
	 * @return factory type
	 */
	public AbstractZooFactory createAnimalFactory(String foodtype){
		if(foodtype == null){
	         return null;
	      }		
	      if(foodtype.equalsIgnoreCase("Carnivore")){
	         return new CarnivoreFactory();
	         
	      } else if(foodtype.equalsIgnoreCase("Omnivore")){
	         return new OmnivoreFactory();
	         
	      } else if(foodtype.equalsIgnoreCase("Herbivore")){
	         return new HerbivoreFactory();
	      }
	      
	      return null;
	}
}
