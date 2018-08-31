package goodJ.Abstract;

import java.lang.reflect.*;
import goodJ.Dependency.*;

/**
 * @author adrian.glazer
 */
public abstract class AbstractController
{
	/**
	 * @see DependencyInjection
	 */
	private DependencyInjection dependencyInjection;

	/**
	 * @return DependencyInjection
	 */
	public DependencyInjection getApplication()
	{
		return dependencyInjection;
	}

	/**
	 * @param dependencyInjection
	 */
	public void setDependencyInjection(DependencyInjection dependencyInjection)
	{
		this.dependencyInjection = dependencyInjection;
	}
	
	/**
	 * @param route
	 */
	public void invokeMethod(String route, Object... args)
	{
		String[] bindData = route.split(".");
		try {
			Method method = this.getClass().getMethod(bindData.length > 0 ? bindData[1] : route);
			method.invoke(this, args);
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}
	}
}
