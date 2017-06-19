package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import animals.Bear;
import animals.Elephant;
import animals.Giraffe;
import animals.Lion;
import animals.Turtle;


/**
 * 
 *  @author Daniel Bromberg 209529882 and Adir Abuhazera 208903765 beer sheba
 *
 */
public class ChangeAnimalColor extends JDialog  implements ItemListener,ActionListener{
    /**
	 * 
	 */
	private ZooPanel ap;
    private JPanel p1,p2,p3,p4;
    private String[] colors = {"Red","Blue"};
    private JRadioButton[] rb;
    private String c;
    private ButtonGroup rbg;
    private JButton ok;
    private JComboBox<String> AnimalCombo ;
     



    /**
     * ChangeAnimalColor constructor
     * @param title
     * @param pan
     */
	public ChangeAnimalColor(String title,ZooPanel pan){
		super(new JFrame(),title,true);
		AnimalCombo=new JComboBox();
		AnimalCombo.add("No Animal", null);
		this.setPanel(pan);
    	setSize(550,300);
    	
    	p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4=new JPanel();
		setLayout(new GridLayout());
		
		add("East" , p1);
		add("West", p2);
		p2.setLayout(new GridLayout(1,3,5,5));

		rbg = new ButtonGroup();
		rb=new JRadioButton[colors.length];
		for(int i=0;i<colors.length;i++)
		{
		    rb[i] = new JRadioButton(colors[i],false);
		    rb[i].addItemListener(this);
		    rbg.add(rb[i]);
		    p2.add(rb[i]);

		}
		p4.setLayout(new BorderLayout());
		p1.setLayout(new GridLayout(1,3,3,5));
		p4.add(AnimalCombo,BorderLayout.NORTH);		
		p3.setLayout(new GridLayout(2,2,2,2));
		ok=new JButton("OK");
		ok.addActionListener(this);
		ok.setBackground(Color.lightGray);
		p3.add(ok);		

		p1.add(p4);
		p4.add(p3,BorderLayout.SOUTH);

		p2.setBorder(BorderFactory.createTitledBorder("Choose decoration color"));
		
		p1.setBorder(BorderFactory.createTitledBorder("Select Animal to decorate"));
	}

	
	/**
	 * 
	 * @return the panel
	 */
	public ZooPanel getPanel() {
		return ap;
	}

	/**
	 * sets the panel
	 * @param ap
	 */
	public void setPanel(ZooPanel ap) {
		this.ap = ap;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		for(int i=0;i<rb.length;i++)
			if(rb[i].isSelected())
		    {
		    	c = colors[i];
		    	break;
	        }		
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
 		if(e.getSource() == ok)
		{
		    setVisible(false);
		}
		else 
		    setVisible(false);
    	
	}

	/**
	 * 
	 * @return animal combo
	 */
	public JComboBox<String> getAnimalCombo() {
		return AnimalCombo;
	}

	/**
	 * adds Animal Combo(combo box)
	 * @param animalCombo
	 */
	public void setAnimalCombo(JComboBox<String> animalCombo) {
		AnimalCombo = animalCombo;
	}
	
	/**
	 * 
	 * @param s
	 */
	public void addAnimalCombo(JComboBox<String> s){
		AnimalCombo.add(s);
	}

	/**
	 * adds Animal Combo(string)
	 * @param string
	 */
	public void addAnimalCombo(String string) {
		AnimalCombo.addItem(string);		
	}



}
