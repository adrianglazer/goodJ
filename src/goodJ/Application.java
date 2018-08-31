package goodJ;

import goodJ.Dependency.*;
import goodJ.Exception.BindException;
import goodJ.Abstract.*;

/**
 * @author adrian.glazer
 */
public class Application
{
	/**
	 * @param appName
	 */
	public Application(String appName, String controllerName, String actionName)
	{
		try {
			DependencyInjection dependencyInjection = new DependencyInjection("bin/" + appName + "/", appName);
			AbstractController controller = (AbstractController) dependencyInjection.getController(controllerName);
			controller.invokeMethod(actionName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (BindException e) {
			e.printStackTrace();
		}
	}
}
