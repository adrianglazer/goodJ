package goodJ.Interface;

/**
 * @author adrian.glazer
 */
public interface ViewInterface extends Cloneable
{
	/**
	 * Method to output view
	 */
	public void buildView(Object... args);
	
	/**
	 * Method that returns information whether view is visible
	 * @return
	 */
	public boolean isVisible();
	
	/**
	 * Method to focus on particular view if SINGLE_VIEW type selected
	 */
	public void focus();
	
	/**
	 * Method to close particular view
	 */
	public void close();
}
