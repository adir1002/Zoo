package graphics;


/***
 * 
 *  @author Daniel Bromberg 209529882 and Adir Abuhazera 208903765 beer sheba
 *
 */
public interface IAnimalBehavior {
	 abstract public String getName();
	 abstract public void setSuspend();
	 abstract public void setResume();
	 abstract public int getSize();
	 abstract public void eatInc();
	 abstract public int getEatCount();
	 abstract public boolean getChanges();
	 abstract public void setChanges(boolean state);
}

