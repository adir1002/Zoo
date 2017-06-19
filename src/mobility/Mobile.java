package mobility;

/**
 *  @author Daniel Bromberg 209529882 and Adir Abuhazera 208903765 beer sheba
 *
 */
public abstract class Mobile implements ILocatable {
	protected Point location;

	/**
	 * mobile constructor
	 * @param location
	 */
	public Mobile(Point location) {
		this.setLocation(location);
	}
	/*
	 * (non-Javadoc)
	 * @see mobility.ILocatable#getLocation()
	 */
	public Point getLocation() {
		return location;
	}
  
	/*
	 * (non-Javadoc)
	 * @see mobility.ILocatable#setLocation(mobility.Point)
	 */
	public boolean setLocation(Point newLocation) {
		this.location = newLocation;
		return true;

	}
}
