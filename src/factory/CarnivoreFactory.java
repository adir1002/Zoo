package factory;

import graphics.AddAnimalDialog;
import graphics.ZooPanel;

import java.awt.Color;

import animals.Animal;

/**
 * 
 *  @author Daniel Bromberg 209529882 and Adir Abuhazera 208903765 beer sheba
 *
 */
public class CarnivoreFactory implements AbstractZooFactory{
	public ZooPanel panel=null;
	public int size=0;
	public int verSpeed=0;
	public int horSpeed=0;
	public Color c=null;

	private AddAnimalDialog dial;
	
	/*
	 * (non-Javadoc)
	 * @see factory.AbstractZooFactory#produceAnimal()
	 */
	@Override
	public Animal produceAnimal() {

	   dial = new AddAnimalDialog(ZooPanel.getInstance(),"Add an animal to zoo","Carnivore");
	   dial.setVisible(true);
	   return dial.getAnimal();
	}

	/*
	 * (non-Javadoc)
	 * @see factory.AbstractZooFactory#getValues(int, int, int, java.awt.Color)
	 */
	@Override
	public void getValues(int size, int verSpeed, int horSpeed, Color c) {
		this.size=size;
		this.verSpeed=verSpeed;
		this.horSpeed=horSpeed;
		this.c=c;		
	}

	
}
