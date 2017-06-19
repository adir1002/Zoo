package graphics;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;

import DesPat.ZooMemento;
import DesPat.ZooObserver;
import animals.Animal;
import animals.Bear;
import animals.Elephant;
import animals.Giraffe;
import animals.Lion;
import factory.AbstractZooFactory;
import factory.AnimalFactory;
import food.EFoodType;
import plants.Cabbage;
import plants.Lettuce;
import plants.Plant;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;



/**
 * 
 *  @author Daniel Bromberg 209529882 and Adir Abuhazera 208903765 beer sheba
 *
 */
public class ZooPanel extends JPanel implements ActionListener
{
   private static final long serialVersionUID = 1L;
   private static final String BACKGROUND_PATH = Animal.PICTURE_PATH+"savanna.jpg";
   private static final String MEAT_PATH = Animal.PICTURE_PATH+"meat.gif";
   private static ZooFrame frame;
   private static EFoodType Food;
   private static JPanel p1,p1_1,p2;
   private static JButton[] b_num, c_num;
   private static String[] names = {"Add Animal","Sleep","Wake up","Clear","Food","Info"};
   private static String[] names2={"Decorate","Duplicate","Save state", "Restore state","Exit"};
   private static ArrayList<Animal> animals;
   private static Plant forFood = null;
   private static JScrollPane scrollPane;
   private static boolean isTableVisible;
   private static int totalCount;
   private static BufferedImage img, img_m;
   private static boolean bgr;
   private AbstractZooFactory f;
   ExecutorService executor = Executors.newFixedThreadPool(5);
   private Future<?> task;
   public ZooObserver obs;
   private static final ZooPanel instance = new ZooPanel();
	private ArrayList<ZooMemento> States;

   /**
    * 
    * @return the instance of the panel
    */
   public static ZooPanel getInstance(){
       return instance;
   }
   
  
   /**
    * initiates the panel
    * @param f
    */
   public void init(ZooFrame f){
	    frame = f;
	    Food = EFoodType.NOTFOOD;
	    totalCount = 0;
	    isTableVisible = false;
	    
	    animals = new ArrayList<Animal>();

		obs = new ZooObserver(this);
		States=new ArrayList<ZooMemento>();
		 
	    obs.start();	    
	   
	    setBackground(new Color(255,255,255));
	    
	    p1=new JPanel();
		p1.setLayout(new GridLayout(1,6,0,0));
		p1.setBackground(new Color(0,150,255));
	    p1_1=new JPanel();
		p1_1.setLayout(new GridLayout(1,5,0,0));
		p1_1.setBackground(new Color(0,150,255));

		
		b_num=new JButton[names.length];
		c_num = new JButton[names2.length];
		
		for(int i=0;i<names.length;i++){
		    b_num[i]=new JButton(names[i]);
		    b_num[i].addActionListener(this);
		    b_num[i].setBackground(Color.lightGray);
		    p1.add(b_num[i]);		
		}
		for(int i=0;i<names2.length;i++){
		    c_num[i]=new JButton(names2[i]);
		    c_num[i].addActionListener(this);
		    c_num[i].setBackground(Color.lightGray);
		    p1_1.add(c_num[i]);		
		}
		
		setLayout(new BorderLayout());
		//p2.setLayout(new BorderLayout());
		//p2.add("North",p1);
		//p2.add("South",p1_1);
		//p2.add(p1);
		//p2.add(p1_1);
		p2=new JPanel();
		p2.setLayout(new BorderLayout());
		p2.add("North", p1);
		p2.add("South", p1_1);
		add("South", p2);	
		
		
		img = img_m = null;
		bgr = false;
		try { img = ImageIO.read(new File(BACKGROUND_PATH)); } 
		catch (IOException e) { System.out.println("Cannot load background"); }
		try { img_m = ImageIO.read(new File(MEAT_PATH)); } 
		catch (IOException e) { System.out.println("Cannot load meat"); }
   }		
   
   /*
    * (non-Javadoc)
    * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
    */
   public void paintComponent(Graphics g){
	   	super.paintComponent(g);	
	   	
	   	if(bgr && (img!=null))
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);

	   	if(Food == EFoodType.MEAT)
	   		g.drawImage(img_m, getWidth()/2-20, getHeight()/2-20, 40, 40, this);
	    
	   	if((Food == EFoodType.VEGETABLE) && (forFood != null))
	   		forFood.drawObject(g);

	   /*	synchronized(this) {
		   	for(Animal an : animals)
		   	{
		   		if(!an.getSuspended())an.drawObject(g);
		   	}
	   	}*/
		synchronized (this) {
			for (int i=0;i<animals.size();i++) {
				if (!animals.get(i).getSuspended()) {
					animals.get(i).drawObject(g);
					
				}
			}
			//repaint();
		}
   }
	
	/**
	 * sets the background
	 * @param num
	 */
   public void setBackgr(int num) {
	   switch(num) {
	   case 0:
		   setBackground(new Color(255,255,255));
		   bgr = false; 
		   break;
	   case 1:
		   setBackground(new Color(0,155,0));
		   bgr = false; 
		   break;
	   default:
			bgr = true;   
	   }
	   repaint();
   }
   
   /**
    * 
    * @return the food(type)
    */
   synchronized public static EFoodType checkFood(){
	   return Food;
   }

   /**
    * returns whether the food is edible
    * @param an
    */
   synchronized public static void eatFood(Animal an)
   {
	   if(Food != EFoodType.NOTFOOD)
	   {
		    if(Food == EFoodType.VEGETABLE)
		    	forFood = null;
		   	Food = EFoodType.NOTFOOD;
	   		an.eatInc();
	   		totalCount++;
	   		System.out.println("The "+an.getName()+" with "+an.getColor()+" color and size "+an.getSize()+" ate food.");
	   }
	   else
	   {
		   System.out.println("The "+an.getName()+" with "+an.getColor()+" color and size "+an.getSize()+" missed food.");
	   }
   }
   

   /*
    * adds the addAnimal dialog
    */
   public void addDialog(){	

		   Object[] options = {"Herbivore","Omnivore","Carnivore"};
		   int n = JOptionPane.showOptionDialog(frame,
				   "Please choose animal factory",
				   "Animal factory",
				   JOptionPane.YES_NO_CANCEL_OPTION,
				   JOptionPane.QUESTION_MESSAGE,
				   null,
				   options,
				   options[2]);
		   AnimalFactory a = new AnimalFactory();
		   switch(n){
		   case 0:
			   f = a.createAnimalFactory("Herbivore");
			   addAnimal(f.produceAnimal());
			   break;
		   case 1:
			   f = a.createAnimalFactory("Omnivore");
			   addAnimal(f.produceAnimal());
			   break;
		   case 2:
			   f = a.createAnimalFactory("Carnivore");
			   addAnimal(f.produceAnimal());
			   break;
		   //}
	   }
   }
   
   /*
    * the function that we duplicate animal in
    */
	public void Duplic() {
		String[] AnimalStrings=new String[10];
		Animal Ani;
		int j=1;
		 synchronized(this){
			 AnimalStrings[0]="No Animal";
    	 for(int i=0;i<animals.size();i++)
    	 {
    		Ani=animals.get(i);
    			 AnimalStrings[j]=""+(i+1)+".["+Ani.getName()+": running="+Ani.getStatus()+", Weight="+Ani.getWeight()+", Color="+Ani.getColor();
    			 j++;

    	 }
		 }
		 JDialog DuplicateDialog = new JDialog();
		 DuplicateDialog.setTitle("DuplicateDialog");
			DuplicateDialog.setSize(700, 300);
			JSplitPane Dpane = new JSplitPane();
			JSlider VerSpinner = new JSlider(JSlider.HORIZONTAL,0, 10, 5);
			JSlider HorSpinner = new JSlider(JSlider.HORIZONTAL,0, 10, 5);
			VerSpinner.setMajorTickSpacing(2);
			VerSpinner.setMinorTickSpacing(1);
			VerSpinner.setPaintTicks(true);
			VerSpinner.setPaintLabels(true);
			HorSpinner.setMajorTickSpacing(2);
			HorSpinner.setMinorTickSpacing(1);
			HorSpinner.setPaintTicks(true);
			HorSpinner.setPaintLabels(true);
			JLabel HS = new JLabel("Horizontal speed");
			JLabel VS = new JLabel("Vertical speed");
			JButton OK = new JButton("OK");
			OK.setLayout(new GridLayout(1, 1,20,0));
			DuplicateDialog.add(Dpane);
			@SuppressWarnings("unchecked")
			JComboBox animalClone = new JComboBox(AnimalStrings);
		    animalClone.setPreferredSize(new Dimension(450, 25));
		    HS.setPreferredSize(new Dimension(100, 25));
		    VS.setPreferredSize(new Dimension(100, 25));
			JPanel p1=new JPanel();
			JPanel p2=new JPanel();
			p1.add(HS);
			p1.add(HorSpinner);
			p1.add(VS);
			p1.add(VerSpinner);
			Dpane.setLeftComponent(p2);
			Dpane.setRightComponent(p1);
		    Dpane.setDividerLocation(300);
		    Dpane.setDividerSize(10);
		    p1.setBorder(BorderFactory.createTitledBorder("Speed may be changed"));
		    p2.setBorder(BorderFactory.createTitledBorder("Select Animal to clone"));
		    p2.setLayout(new BorderLayout());
		    p2.add(OK,BorderLayout.SOUTH);
		    p2.add(animalClone,BorderLayout.NORTH);
		    Dimension minimumSize = new Dimension(400, 400);
		    p1.setPreferredSize(minimumSize);
		    p2.setPreferredSize(minimumSize);
		    OK.addActionListener(actionEvent->{
		    	DuplicateDialog.dispose();
		    	synchronized(this){
		    		int i=Character.getNumericValue(animalClone.getSelectedItem().toString().charAt(0))-1;
		    		Animal C=(animals.get(i).getPrototype(VerSpinner.getValue(),HorSpinner.getValue()));
					if(animals.size()==10)
					{
						JOptionPane.showMessageDialog(this, "you cannot add anymore animals");

					}
					else
						{
						animals.add(C);
						C.addObserver(obs);
						repaint();
						Start(C);
						}
					DuplicateDialog.dispose();
		    	}
			     });
	    		 DuplicateDialog.setVisible(true);
		    
	}
	//@SuppressWarnings("deprecation")
	@SuppressWarnings("rawtypes")
	public void changeColor() {
		
		if(animals.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "You have no animals to decorate");
			return;
		}
		
		int flag=0;
		String[] AnimalStrings=new String[11];
		 Animal A;
		 int j=1;
		 synchronized(this){
			 AnimalStrings[0]="No Animal";
    	 for(int i=0;i<animals.size();i++)
    	 {
    		A=animals.get(i);
    		 if(A.getColor().equals("Natural"))
    		 {
    			 AnimalStrings[j]=(i+1)+".["+A.getName()+": running="+A.getStatus()+", Weight="+A.getWeight()+", Color="+A.getColor();
    			 j++;
    		 }
    	 }
		 }
	    JRadioButton radio1 = new JRadioButton("Red");
	    JRadioButton radio2 = new JRadioButton("Blue");
	    ButtonGroup group = new ButtonGroup();
        group.add(radio1);
        group.add(radio2);

		JDialog DecorateDialog = new JDialog();
        DecorateDialog.setTitle("DecorateDialog");

		DecorateDialog.setSize(700, 300);
		JSplitPane pane = new JSplitPane();
		JButton OK = new JButton("OK");
		OK.setLayout(new GridLayout(1, 1,20,0));
		DecorateDialog.add(pane);
		JComboBox animalDecorate = new JComboBox(AnimalStrings);
		animalDecorate.setPreferredSize(new Dimension(450, 25));
		JPanel p1=new JPanel();
		JPanel p2=new JPanel();

		p1.add(radio1);
		p1.add(radio2);
		pane.setLeftComponent(p2);
		pane.setRightComponent(p1);
	    pane.setDividerLocation(500);
	    pane.setDividerSize(10);
	    p1.setBorder(BorderFactory.createTitledBorder("Choose decoration color"));
	    p2.setBorder(BorderFactory.createTitledBorder("Select Animal to decorate"));
	    p2.setLayout(new BorderLayout());
	    p2.add(OK,BorderLayout.SOUTH);
	    p2.add(animalDecorate,BorderLayout.NORTH);
	    Dimension minimumSize = new Dimension(400, 400);
	    p1.setPreferredSize(minimumSize);
	    p2.setPreferredSize(minimumSize);
	    OK.addActionListener(actionEvent->{

	    	synchronized(this){
	    		int i=Character.getNumericValue(animalDecorate.getSelectedItem().toString().charAt(0))-1;
	    		if(radio1.isSelected() && !radio2.isSelected())
	    		{	    	
	    			animals.get(i).setColor("Red");    	
	    		}
	    		if(radio2.isSelected() && !radio1.isSelected())
	    		{
	    			animals.get(i).setColor("Blue");		    		    	
	    		}
	    		animals.get(i).PaintAnimal();
	    		DecorateDialog.dispose();
	    	
	    	}
		     });
  		 DecorateDialog.setVisible(true);


    	
	}

	/**
	 * add animal function
	 * @param an
	 */
   public void addAnimal(Animal an)
   {
	   if (an != null){
		   task = executor.submit(an);
		   an.setTask(task);
		   animals.add(an);
		   an.addObserver(obs);
	   }
	  

   }

   /*
    * starts the animal movement after we paused it
    */
	public static void start() {
	    for(Animal an : animals)
	    	an.setResume();
   }

	/*
	 *stops the animal movement after we started it 
	 */
 	public static void stop() {
	    for(Animal an : animals)
	    	an.setSuspend();
   }

 	/*
 	 * clear function that cleans the screen from all the running animals 
 	 */
 	synchronized public void clear(){
 				   	 
	   		 int i=animals.size();
	   		 
	   		 if(i>5)i=5;
	   		 

   			 for(int j=0;j<i;j++)
   			 {
   				animals.get(0).Interrupt();
   				animals.remove(0);
   			 }
    		 repaint();
    		

	 }	    	 
	    	 
   
    /*
	 * clear function that cleans the screen from all the animals 
	 */
   synchronized public void clearAll(){
	    
		 for(Animal an: animals)
			 an.Interrupt();
		 
		 animals.clear();
		 repaint();
	    		
	
	}	    	 
	    	 

	   
  /**
   * starts the task
   * @param animal
   */
   	private void Start(Animal animal) {
		Future<?> task = ((ExecutorService)executor).submit(animal);
		animal.setTask(task);	
   	}

   	/**
   	 * eat counter
   	 * @param i
   	 */
	public void addEatCounter(int i) {
		   this.totalCount = this.totalCount + i;
		
	}

	/**
	 * perform eating for certain animal of another animal
	 * @param predator
	 * @param prey
	 */
	synchronized public void preyEating(Animal predator, Animal prey){
		   predator.eatInc();
		   totalCount -= (prey.getEatCount()-1);
	}

	/*
	 * add food function
	 */
	synchronized public void addFood(){
	   if(Food == EFoodType.NOTFOOD){
		   Object[] options = {"Meat", "Cabbage", "Lettuce"}; 
	   int n = JOptionPane.showOptionDialog(frame, 
	   		"Please choose food", "Food for animals", 
	   		JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
	   		null, options, options[2]);
	   switch(n) {
	   case 0: // Meat
		   Food = EFoodType.MEAT;
		   break;
	   case 1: // Cabbage
		   Food = EFoodType.VEGETABLE;
		   forFood = Cabbage.getInstance();
		   break;
	   case 2: // Lettuce
			   Food = EFoodType.VEGETABLE;
			   forFood = Lettuce.getInstance();
			   break;
			   default:
				   forFood = null;
				   break;
		   }
	   }
	   else {
		   Food = EFoodType.NOTFOOD;
		   forFood = null;
		   addFood();
	   }
	   repaint();
  }
   
	/*
	 *info function(info panel with all the information)
	 */
   public void info()
   {  	 
	   if(isTableVisible == false)
	   {
		  int i=0;
		  int sz = animals.size();

		  String[] columnNames = {"Animal","Color","Weight","Hor. speed","Ver. speed","Eat counter"};
	      String [][] data = new String[sz+1][columnNames.length];
		  for(Animal an : animals)
	      {
	    	  data[i][0] = an.getName();
	    	  data[i][1] = an.getColor();
	    	  data[i][2] = new Integer((int)(an.getWeight())).toString();
		      data[i][3] = new Integer(an.getHorSpeed()).toString();
		      data[i][4] = new Integer(an.getVerSpeed()).toString();
	    	  data[i][5] = new Integer(an.getEatCount()).toString();
	    	  i++;
	      }
	      data[i][0] = "Total";
	      data[i][5] = new Integer(totalCount).toString();
	      
	      JTable table = new JTable(data, columnNames);
	      scrollPane = new JScrollPane(table);
	      scrollPane.setSize(450,table.getRowHeight()*(sz+1)+24);
	      add( scrollPane, BorderLayout.CENTER );
	      isTableVisible = true;
	   }
	   else
	   {
		   isTableVisible = false;
	   }
	   scrollPane.setVisible(isTableVisible);
       repaint();
   }

   /*
    * kills the panel
    */
   public void destroy(){ 
	  obs.interrupt();
      System.exit(0);
   }
   
   /*
    * (non-Javadoc)
    * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
    */
   public void actionPerformed(ActionEvent e){
	if(e.getSource() == b_num[0]) // "Add Animal"
		{
		if (animals.size()<10)
			addDialog();
		else
			JOptionPane.showMessageDialog(this, "you cannot add anymore animals");
		}
	else if(e.getSource() == b_num[1]) // "Sleep"
    	synchronized(this){
			
	    for(int i=0;i<animals.size();i++)
	    {  	
			animals.get(i).setSuspended();				
	    }	    	
	  }
	else if(e.getSource() == b_num[2]) // "Wake up"
		synchronized(this)
		{
	    	 for(int i=0;i<animals.size();i++)
	    	 {
	    		 animals.get(i).setResume();
	    	 }
		}
	else if(e.getSource() == b_num[3]) // "Clear"
		clear();
	else if(e.getSource() == b_num[4]) // "Food"
		addFood();
	else if(e.getSource() == b_num[5]) // "Info"
		info();
	else if(e.getSource() == c_num[0]) 	// "Decorate"

	{
		changeColor();
	}
	else if(e.getSource() == c_num[1])//Duplicate
	{if (animals.size()<10)
		Duplic();
	else
		JOptionPane.showMessageDialog(this, "you cannot add anymore animals");

	}
	else if(e.getSource() == c_num[2])//"Save state"
	{
		synchronized(this){
		if(States.size()<3)
		{
			if(checkFood() != EFoodType.NOTFOOD)
			{
				ZooMemento ZMem=new ZooMemento(animals, forFood);
				States.add(ZMem);	
				JOptionPane.showMessageDialog(this, "State saved!");
				for(int i=0;i<ZMem.getAnimals().size();i++)
					System.out.println(ZMem.getAnimals().get(i).getName());

				}
			else
			{
				ZooMemento ZMem=new ZooMemento(animals, null);
				States.add(ZMem);	
				JOptionPane.showMessageDialog(this, "State saved!");
				for(int i=0;i<ZMem.getAnimals().size();i++)
					System.out.println(ZMem.getAnimals().get(i).getName());


				}
		
		}
		else
			JOptionPane.showMessageDialog(this, "Cannot save anymore states!");
		}
	}
	else if(e.getSource() == c_num[3])//"Restore state"
	{
		restore();
		}
	else if(e.getSource() == c_num[4]) // "Exit"
		destroy();
   

   }

   
   /**
    * restore function- restores up to 3 states at a time
    */
   public  void restore(){
	   if(States.size()==0)
		{
			JOptionPane.showMessageDialog(this, "There are no saved states!");
		}
		else
		{
			
		clearAll();
		JLabel S = new JLabel("Please choose state for restore:");
		JButton state1=new JButton("State 1");
		JButton state2=new JButton("State 2");
		JButton state3=new JButton("State 3");	
		JButton Cancel=new JButton("Cancel");
		JDialog RestoreDialog = new JDialog();
		JPanel pane=new JPanel();
		RestoreDialog.setSize(250, 150);
		RestoreDialog.setResizable(false);
		pane.setLayout(new FlowLayout());
		pane.add(S);
		if(States.size()>=1)
			pane.add(state1);
		if(States.size()>=2)
			pane.add(state2);
		if(States.size()==3)
			pane.add(state3);
		pane.add(Cancel);
		RestoreDialog.add(pane);
		RestoreDialog.setVisible(true);
		Cancel.addActionListener(actionEvent->{
			 RestoreDialog.dispose();
		 });
		state1.addActionListener(actionevent->{
			
		synchronized(this){
			 animals=new ArrayList<Animal>(States.get(0).getAnimals().size());
			 for(int i=0;i<States.get(0).getAnimals().size();i++)
			 {
				 animals.add(States.get(0).getAnimals().get(i));
			 }
			 forFood=States.get(0).getP();
			
			 for (Animal ani :animals)
			 {
					ani.addObserver(obs);
					

				 Start(ani);
				 
			 }
			 repaint();
		 
		}
			
			
		this.forFood=States.get(0).getP();
		States.remove(0);
		RestoreDialog.dispose();
		});
		state2.addActionListener(actionevent->{
			
		synchronized(this){
			
			 animals=new ArrayList<Animal>(States.get(1).getAnimals().size());
			 for(int i=0;i<States.get(1).getAnimals().size();i++)
			 {
				 animals.add(States.get(1).getAnimals().get(i));
			 }
			 forFood=States.get(1).getP();
					
			 for (Animal ani :animals)
			 {
					ani.addObserver(obs);
	
				 Start(ani);
				 
			 }
			 repaint();
		}
		
		
		this.forFood=States.get(1).getP();
		States.remove(1);
		RestoreDialog.dispose();
		});
		
		state3.addActionListener(actionevent->{
			
		synchronized(this){
			 animals=new ArrayList<Animal>(States.get(2).getAnimals().size());
			 for(int i=0;i<States.get(2).getAnimals().size();i++)
			 {
				 animals.add(States.get(2).getAnimals().get(i));
			 }
			 forFood=States.get(2).getP();
			
		 for (Animal ani :animals)
		 {
				ani.addObserver(obs);

			 Start(ani);
			 
		 }
		 	repaint();
		}
		this.forFood=States.get(2).getP();
		States.remove(2);
		RestoreDialog.dispose();
		});
		
		
		}
   }
   
   /**
    * performs eating of an animal
    */
	public void eatAnimal()
	{
		boolean prey_eaten = false;
		synchronized(this) {
			for(Animal predator : animals) {
				for(Animal prey : animals) {
					if(predator != prey && predator.getDiet().canEat(prey) && predator.getWeight()/prey.getWeight() >= 2 &&
					   (Math.abs(predator.getLocation().getX() - prey.getLocation().getX()) < prey.getSize()) &&
					   (Math.abs(predator.getLocation().getY() - prey.getLocation().getY()) < prey.getSize())) {
							preyEating(predator,prey);
							System.out.print("The "+predator+" cought up the "+prey+" ==> ");
							//prey.interrupt();
							//prey.setSuspend();
							prey.getTask().cancel(true);
							animals.remove(prey);
							repaint();
							prey_eaten = true;
							break;
					}
				}
				if(prey_eaten)
					break;
				
				
			}
		}

	}

	/**
	 * 
	 * @return whether there were change or not
	 */
	public static boolean isChange() {
		boolean rc = false;
		for(Animal an : animals) {
		    if(an.getChanges()){
		    	rc = true;
		    	an.setChanges(false);
			}
	    }
		return rc;
	}
}