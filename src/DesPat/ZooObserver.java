package DesPat;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import animals.Animal;
import graphics.ZooPanel;

/**
 * 
 * @author Daniel Bromberg 209529882 and Adir Abuhazera 208903765 beer sheba 
 *
 */
public class ZooObserver extends Thread implements Observer{
	
private ZooPanel pan;

/**
 * zooObserver constructor
 * @param p
 */
	public ZooObserver(ZooPanel p) {
		pan=p;
	}

	@Override

	/**
	 * update function 
	 */
	public synchronized void update(Observable obs, Object str) {
		notify();
	}

	@Override
	public void run() {
		while(true)
		{
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					
				}
			}
			pan.eatAnimal();
			pan.repaint();
		}
		
		
		
	}
}
