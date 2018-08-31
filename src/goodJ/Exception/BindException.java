package goodJ.Exception;

/**
 * @author adrian.glazer
 */
public class BindException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor
	 */
	public BindException()
	{
		super("Field to which you try to bind action is not supported.");
	}
	
	/**
	 * 
	 * @param controllerName
	 */
	public BindException(String controllerName)
	{
		super("[ " + controllerName + " ]: Wrong controller name or controller doesn't exist.");
	}
	
	/**
	 * @param length
	 * @param name
	 */
	public BindException(int length, String name)
	{
		super("[ " + name + " ]: You used wrong bind name. It should be like eg. 'myController.myAction'. Separated only by single dot.");
	}
	
	/**
	 * Default constructor
	 */
	public BindException(String controllerName, String actionName)
	{
		super("[ " + actionName + " ]: Can't find action in controller [ " + controllerName + " ]");
	}
}
