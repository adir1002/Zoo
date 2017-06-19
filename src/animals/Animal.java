package animals;

import javax.swing.*;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

import DesPat.ZooObserver;
import DesPat.ColoredAnimal;
import diet.IDiet;
import food.EFoodType;
import food.IEdible;
import graphics.IAnimalBehavior;
import graphics.IDrawable;
import graphics.ZooPanel;
import mobility.Mobile;
import mobility.Point;


/**
 * @author Daniel Bromberg 209529882 and Adir Abuhazera 208903765 beer sheba
 * abstract class that represent animals 
 * and extends from Mobile and implements IEdible
 */
public abstract class Animal extends Observable implements IEdible,IDrawable,IAnimalBehavior,Runnable,ColoredAnimal,Cloneable {

	protected final int EAT_DISTANCE = 5;
	private IDiet diet;
	protected String name;
	private double weight;
	protected int size;
	public void setSize(int size) {
		this.size = size;
	}

	protected String col;
	protected int horSpeed;
	protected int verSpeed;
	protected boolean coordChanged;
	protected int x_dir;
	protected int y_dir;
	protected int eatCount;
	protected ZooPanel pan;
	protected boolean threadSuspended;	 
	protected BufferedImage img1, img2;
	protected int cor_x1, cor_x2, cor_x3, cor_x4, cor_x5, cor_x6;
	protected int cor_y1, cor_y2, cor_y3, cor_y4, cor_y5, cor_y6;
	protected int cor_w, cor_h;
	protected  Future<?> task;
	protected Point location;
	
	
	/**
	 * the function paints the animals
	 */
	public void paintAnimal(String color){
		
		if(this instanceof Lion)
		loadImages("lio");
		if(this instanceof Bear)
		loadImages("bea");
		if(this instanceof Giraffe)
		loadImages("grf");
		if(this instanceof Elephant)
		loadImages("elf");
		if(this instanceof Turtle)
		loadImages("trt");	}
	
	
	/**
	 * 
	 * @return task
	 */
	public Future<?> getTask() {
		return task;
	}

	/**
	 * the function sets task
	 * @param task
	 */
	public void setTask(Future<?> task) {
		this.task = task;
		setChanged();

	}

	
	/**
	 * animal constructor
	 * @param nm
	 * @param sz
	 * @param w
	 * @param hor
	 * @param ver
	 * @param c
	 * @param p
	 */
	public Animal(String nm, int sz, int w, int hor, int ver, String c, ZooPanel p) {
		super();
		setLocation(new Point(0,0));
		name = new String(nm);
		size = sz;
		weight = w;
		horSpeed = hor;
		verSpeed = ver;
		col = c;
		pan = p;
		x_dir = 1;
		y_dir = 1;
		cor_x1=cor_x3=cor_x5=cor_x6=0;
		cor_y1=cor_y3=cor_y5=cor_y6=0;
		cor_x2=cor_y2=cor_x4=cor_y4=-1;
		cor_w = cor_h = size;
		coordChanged = false;
		threadSuspended = false;
	}	
	
	
	public void PaintAnimal() {
		if(this instanceof Lion)
		loadImages("lio");
		if(this instanceof Bear)
		loadImages("bea");
		if(this instanceof Giraffe)
		loadImages("grf");
		if(this instanceof Elephant)
		loadImages("elf");
		if(this instanceof Turtle)
		loadImages("trt");
	}
	
	/**
	 * 
	 * @return location 
	 */
	public Point getLocation() {
		return location;
	}
	
	/**
	 * 
	 * @param location
	 */
	public void setLocation(Point location) {
		this.location = location;
	}
	
	public EFoodType getFoodtype() { return EFoodType.MEAT;	}	
	public IDiet getDiet() { return diet; }
	public String getName() { return this.name;	}
	public double getWeight() {	return this.weight;	}
	public void setWeight(double w) { weight = w; }
	protected void setDiet(IDiet diet) { this.diet = diet;}
	public int getSize() { return size; }
	public int getHorSpeed() { return horSpeed; }
	public void setHorSpeed(int hor) { horSpeed  = hor; }
	public int getVerSpeed() { return verSpeed; }
	public void setVerSpeed(int ver) { verSpeed  = ver; }
	public void eatInc() { eatCount++; }
	public int getEatCount() { return eatCount; }
	synchronized public void setSuspend() { threadSuspended = true; }
	synchronized public void setResume() { threadSuspended = false;
	notify(); }
	synchronized public boolean getChanges(){ return coordChanged; }
	synchronized public void setChanges(boolean state){ coordChanged = state; }	 
	public String getColor() { return col; }
	public void setColor(String colo){this.col=colo;}
	
	/**
	 * loads the images
	 */
	public void loadImages(String nm){
		 switch(getColor()){
			 case "Red":
				 try { img1 = ImageIO.read(new File(PICTURE_PATH + nm + "_r_1.png"));
				 	   img2 = ImageIO.read(new File(PICTURE_PATH + nm + "_r_2.png"));} 
				 catch (IOException e) { System.out.println("Cannot load picture"); }
				 break;
			 case "Blue":
				 try { img1 = ImageIO.read(new File(PICTURE_PATH + nm + "_b_1.png"));
				 	   img2 = ImageIO.read(new File(PICTURE_PATH + nm + "_b_2.png"));} 
				 catch (IOException e) { System.out.println("Cannot load picture"); }
				 break;
			 default:
				 try { img1 = ImageIO.read(new File(PICTURE_PATH + nm + "_n_1.png"));
			 	       img2 = ImageIO.read(new File(PICTURE_PATH + nm + "_n_2.png"));} 
				 catch (IOException e) { System.out.println("Cannot load picture"); }			 
		 }
	}

	/**
	 * the function that runs this class
	 */
    public void run() 
    {
       while (true) 
       {
           try 
           {
               Thread.sleep(50);
               
               synchronized(this) {
                   while (threadSuspended)
   						wait();
   				}  
          } 
           catch (InterruptedException e) 
           {
           	System.out.println(getName()+ " dead...");
           	return;
           }
                      
           if(this.getDiet().canEat(pan.checkFood()))
           {
           		double oldSpead = Math.sqrt(horSpeed*horSpeed+verSpeed*verSpeed);
           		double newHorSpeed = oldSpead*(location.getX() - pan.getWidth()/2)/
           				   (Math.sqrt(Math.pow(location.getX() - pan.getWidth()/2,2)+
           						      Math.pow(location.getY() - pan.getHeight()/2,2)));
           		double newVerSpeed = oldSpead*(location.getY() - pan.getHeight()/2)/
           				   (Math.sqrt(Math.pow(location.getX() - pan.getWidth()/2,2)+
           						      Math.pow(location.getY() - pan.getHeight()/2,2)));
              	int v = 1;
                if(newVerSpeed<0) { v=-1; newVerSpeed = -newVerSpeed; }
              	if(newVerSpeed > 10)
              		newVerSpeed = 10;
              	else if(newVerSpeed < 1) {
              	   if(location.getY() != pan.getHeight()/2)
              		newVerSpeed = 1;   
              	   else
              		newVerSpeed = 0;  
              	}
              	int h = 1;
                if(newHorSpeed<0) { h=-1; newHorSpeed = -newHorSpeed; }
              	if(newHorSpeed > 10)
              		newHorSpeed = 10;
              	else if(newHorSpeed < 1) {
              	   if(location.getX() != pan.getWidth()/2)
              		newHorSpeed = 1;   
              	   else
              		newHorSpeed = 0;  
              	}
               	location.setX((int)(location.getX() - newHorSpeed*h));
               	location.setY((int)(location.getY() - newVerSpeed*v));
              	if(location.getX()<pan.getWidth()/2)
              		x_dir = 1;
              	else
              		x_dir = -1;
              	if((Math.abs(location.getX()-pan.getWidth()/2)<EAT_DISTANCE) && 
              	   (Math.abs(location.getY()-pan.getHeight()/2)<EAT_DISTANCE))
              	{
              		pan.eatFood(this);
              	}
           }
           else
           {
			    location.setX(location.getX() + horSpeed*x_dir);
			    location.setY(location.getY() + verSpeed*y_dir);
           }

		    if(location.getX() > pan.getWidth()+cor_x1)
		    {
		    	x_dir = -1;
		    	if(cor_x2!=-1)
		    		location.setX(location.getX()+cor_x2);
		    }
		    else if(location.getX() < cor_x3)
		    {
		    	x_dir = 1;
		    	if(cor_x4!=-1)
		    		location.setX(cor_x4);
		    }
	
		    if(location.getY() > (pan.getHeight() + cor_y1))
		    {
		    	y_dir = -1;
		    	if(cor_y2!=-1)
		    		location.setY(location.getY()+cor_y2);
		    }
		    else if(location.getY() < cor_y3)
		    {
		    	y_dir = 1;
		    	if(cor_y4!=-1)
		    		location.setY(cor_y4);
		    }
		    
		    this.setChanged();
		    this.notifyObservers();
      }
   }
 
    
    /**
     * the function that draws the object the it required to draw
     */
    public void drawObject(Graphics g)
    {
 		if(x_dir==1) // an animal goes to right side
 		{
 			g.drawImage(img1, location.getX()+cor_x5, location.getY()+cor_y5, cor_w, cor_h, pan);
 		}
 		else // an animal goes to left side
 		{
 			g.drawImage(img2, location.getX()+cor_x6, location.getY()+cor_y6, cor_w, cor_h, pan);
 		}



    }
    
    /**
     * function that we use in the clear button(interrupts the task)
     */
	public void Interrupt() {
		task.cancel(true);
	}
	
	/**
	 * @return the info of the animal
	 */
    public String toString(){
    	return "["+getName() + ": weight=" + weight + ", color="+col+"]";
    }


    /**
     * clones the animal
     */
    public Object clone()
    {
    	Object clone=null;
    	try
    	{
    		clone=super.clone();
    	    	
			((Animal)clone).setLocation(new Point(0,0));
    		
			((Animal)clone).setHorSpeed(3 + (int)(Math.random()*10));
			((Animal)clone).setVerSpeed(3 + (int)(Math.random()*10));
		
		
    	}
    	catch(CloneNotSupportedException e)
    	{
    		e.printStackTrace();
    	}
    	
    	return clone;
    	
    }
    
   
    
    /**
     * gets the prototype of the animal 
     * @param verspeed
     * @param horspeed
     * @return the animal 
     */
	public Animal getPrototype(int verspeed, int horspeed)  {
			Animal A=(Animal) clone();
			A.setHorSpeed(horspeed);
			A.setVerSpeed(verspeed);
			//A.setColor(this.col);
			//A.setSize(this.size);
			//A.setWeight(this.weight);
			if(getSuspended())
				{
				A.setSuspended();
				}

			return A;	
	}

	/**
	 * sets the thread to be suspended
	 */
	public void setSuspended() {
		threadSuspended = true;
		setChanges(false);
	}

	/**
	 * 
	 * @return the thread status(boolean)
	 */
	public boolean getSuspended() {
		return threadSuspended;
	}

	/**
	 * 
	 * @return the status of the thread(string)
	 */
	public String getStatus() {
		if(threadSuspended==false)
			return "true";
		return "false";
	}

	/**
	 * 
	 * @param location2
	 * @return the distance
	 */
	public double calcDistance(Point location2) {
		return Math.sqrt(Math.pow(location2.getX() - this.getLocation().getX(), 2) + Math.pow(location2.getY() - this.getLocation().getY(), 2));
	}

	
}
