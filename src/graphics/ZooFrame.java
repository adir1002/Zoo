package graphics;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 *  @author Daniel Bromberg 209529882 and Adir Abuhazera 208903765 beer sheba
 *
 */
public class ZooFrame extends JFrame implements ActionListener
{
   private static final long serialVersionUID = 1L;
   private ZooPanel panel;
   private String[] names = {"Exit","Image","Green","None","Help"};
   private JMenu m1, m2, m3;
   private JMenuItem[] mi;
   private JMenuBar mb;

   /**
    * the main of the program
    * @param args
    */
   public static void main(String[]args){
	   ZooFrame aqua = new ZooFrame();
	   aqua.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	   aqua.setSize(800,600);
	   aqua.setVisible(true);
   }

   /**
    * zooFrame constructor
    */
   public ZooFrame()
   {
	    super("Zoo");
	    panel = ZooPanel.getInstance();
	    panel.init(this);
	    
	    add(panel);
	    panel.setVisible(true);

		mb = new JMenuBar();
		m1 = new JMenu("File");
		m2 = new JMenu("Background");
		m3 = new JMenu("Help");
		mi = new JMenuItem[names.length];

		for(int i=0;i<names.length;i++)
		{
		    mi[i]=new JMenuItem(names[i]);
		    mi[i].addActionListener(this);
		}

		m1.add(mi[0]);

		m2.add(mi[1]);
		m2.addSeparator();
		m2.add(mi[2]);
		m2.addSeparator();
		m2.add(mi[3]);

		m3.add(mi[4]);

		mb.add(m1);
		mb.add(m2);
		mb.add(m3);
		setJMenuBar(mb);
   }

   /*
    * (non-Javadoc)
    * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
    */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == mi[0])
			destroy();
		else if(e.getSource() == mi[1])
			panel.setBackgr(2);
		else if(e.getSource() == mi[2])
			panel.setBackgr(1);
		else if(e.getSource() == mi[3])
			panel.setBackgr(0);
		else if(e.getSource() == mi[4])
			printHelp();
	}
	
	/*
	 * kills the panel
	 */
	public void destroy() {
		panel.destroy();
	}
	/*
	 * helper print 
	 */
	public void printHelp() {
		JOptionPane.showMessageDialog(this, "Home Work 4\nGUI @ Threadpool");
	}

}
