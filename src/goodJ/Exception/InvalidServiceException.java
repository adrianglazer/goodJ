package goodJ.Exception;

/**
 * @author adrian.glazer
 */
public class InvalidServiceException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param viewName
	 */
	public InvalidServiceException(String serviceName)
	{
		super("[ " + serviceName + " ]: Wrong service name or service doesn't exist.");
	}
	
	/**
	 * @param viewName
	 */
	public InvalidServiceException(String serviceName, String caller)
	{
		super("[ " + serviceName + " ]: Wrong service name or service doesn't exist. Found in class [ " + caller + " ].");
	}
}
