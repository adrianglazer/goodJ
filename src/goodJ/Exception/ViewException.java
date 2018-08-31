package goodJ.Exception;

/**
 * @author adrian.glazer
 */
public class ViewException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param viewName
	 */
	public ViewException(String viewName)
	{
		super("[ " + viewName + " ]: Wrong view name or view doesn't exist.");
	}
}
