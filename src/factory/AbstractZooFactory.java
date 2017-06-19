package factory;

import java.awt.Color;

import animals.Animal;

/**
 * 
 * @author Daniel Bromberg 209529882 and Adir Abuhazera 208903765 beer sheba
 *
 */
public interface AbstractZooFactory {
	/**
	 * Operate the add animal dialog and return an animal; 
	 */
	public Animal produceAnimal();
	public void getValues(int size, int verSpeed, int horSpeed , Color c);

}
