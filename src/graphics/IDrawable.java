package graphics;

import java.awt.Graphics;


/**
 * 
 *  @author Daniel Bromberg 209529882 and Adir Abuhazera 208903765 beer sheba
 *
 */
public interface IDrawable {
	 public final static String PICTURE_PATH = "src/Pictures/";
	 public void loadImages(String nm);
	 public void drawObject(Graphics g);
	 public String getColor();	 
}