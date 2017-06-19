package plants;

import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import food.EFoodType;
import food.IEdible;
import graphics.IDrawable;
import graphics.ZooPanel;
import mobility.ILocatable;
import mobility.Point;

/**
 * 
 *  @author Daniel Bromberg 209529882 and Adir Abuhazera 208903765 beer sheba
 *
 */

public abstract class Plant implements IEdible, ILocatable, IDrawable {

	private Point location;
	protected ZooPanel pan;
	protected BufferedImage img;
	
		
   /* A private Constructor prevents any other
    * class from instantiating.
    */
   protected Plant(){
	   this.pan = ZooPanel.getInstance();
	   this.location = new Point(pan.getWidth()/2,pan.getHeight()/2);
   }

   /*
    * (non-Javadoc)
    * @see graphics.IDrawable#loadImages(java.lang.String)
    */
	public void loadImages(String nm){
			try { 
				img = ImageIO.read(new File(PICTURE_PATH + nm + ".png"));
			}
			catch (IOException e) { System.out.println("Cannot load picture"); }
	}

	/*
	 * (non-Javadoc)
	 * @see graphics.IDrawable#drawObject(java.awt.Graphics)
	 */
	public void drawObject(Graphics g) {
		g.drawImage(img, location.getX()-20, location.getY()-20, 40, 40, pan);
	}
	/*
	 * (non-Javadoc)
	 * @see food.IEdible#getFoodtype()
	 */
	public EFoodType getFoodtype() { return EFoodType.VEGETABLE; }
	
	/*
	 * (non-Javadoc)
	 * @see graphics.IDrawable#getColor()
	 */
	public String getColor() { return "Green"; }	 
	
	/*
	 * (non-Javadoc)
	 * @see mobility.ILocatable#getLocation()
	 */
	public Point getLocation() { return null; }
	
	/*
	 * (non-Javadoc)
	 * @see mobility.ILocatable#setLocation(mobility.Point)
	 */
	public boolean setLocation(Point location) { return false; }
}
