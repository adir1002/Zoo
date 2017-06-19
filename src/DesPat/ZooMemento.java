package DesPat;
import java.util.ArrayList;

import animals.Animal;
import plants.Plant;

/**
 * 
 * @author Daniel Bromberg 209529882 and Adir Abuhazera 208903765 beer sheba 
 *
 */
public class ZooMemento {



		protected ArrayList<Animal> Animals;
		protected Plant P;
		
		/**
		 * zooMemento class
		 * @param animals
		 * @param p
		 */
		public ZooMemento(ArrayList<Animal> animals, Plant p) {
			synchronized(this){
				
				Animals=new ArrayList<Animal>(animals.size());
		   	 	for(int i=0;i<animals.size();i++)
		   	 	{
		   	 		Animal a=((Animal)animals.get(i).clone());
		   	 	Animals.add(a);
		   	 	}

				}
		   	 	if(p!=null)
		   	 		this.P=p;
		   }	
		
/**
 * 
 * @return the animals
 */
		public ArrayList<Animal> getAnimals() {
			return Animals;
		}


		/**
		 * 
		 * @return food(type)
		 */
		public Plant getP()
		{
			return P;
		}
		
	

}
