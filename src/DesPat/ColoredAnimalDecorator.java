package DesPat;

import animals.Animal;


/**
 * 
 * @author Daniel Bromberg 209529882 and Adir Abuhazera 208903765 beer sheba 
 */
public class ColoredAnimalDecorator implements ColoredAnimal { 
	ColoredAnimal animal;

	
	/**
	 * sets the animal
	 * @param a
	 */
	public ColoredAnimalDecorator(Animal a){
		animal=a;
	}
	@Override
	public void paintAnimal(String color) {
		((Animal)animal).setColor(color);
		String name="";
		switch(((Animal)animal).getName())
			{case("bea"):
					name="Bear";
					break;
			case("elf"):
					name="Elephant";
					break;
			case("grf"):
					name="Giraffe";
					break;
			case("lio"):
					name="Lion";
					break;
			case("trt"):
					name="Turtle";
					break;
						}
		((Animal)animal).loadImages(name);


		}
				
}

