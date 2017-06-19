package mobility;

/**
 *  @author Daniel Bromberg 209529882 and Adir Abuhazera 208903765 beer sheba
 *
 */
public class Point {

	private int x; // the x value
	private int y; // the y value
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * 
	 * @return the place of x
	 */
	public int getX() {
		return x;
	}
	/**
	 * 
	 * @return the place of y
	 */
	public int getY() {
		return y;
	}
	/**
	 * 
	 * @param x
	 * @return coordinate x
	 */
	public boolean setX(int x) {
		this.x = x;
		return true;
	}
	/**
	 * 
	 * @param y
	 * @return coordinate y
	 */
	public boolean setY(int y) {
		this.y = y;
		return true;
	}
}
