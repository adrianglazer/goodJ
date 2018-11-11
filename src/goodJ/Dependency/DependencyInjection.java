package goodJ.Dependency;

import java.awt.MenuItem;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import javax.swing.*;
import goodJ.Annotation.*;
import goodJ.Annotation.Action;
import goodJ.Enum.ViewType;
import goodJ.Exception.*;
import goodJ.Abstract.*;

/**
 * Main class that collect classes, create their instances and bind all together.
 * 
 * @author adrian.glazer
 */
public class DependencyInjection
{
	/**
	 * Map of controller classes
	 */
	private HashMap<String, AbstractController> controllers = new HashMap<String, AbstractController>();
	
	/**
	 * Map of service classes
	 */
	private HashMap<String, Object> services = new HashMap<String, Object>();
	
	/**
	 * Map of view classes
	 */
	private HashMap<String, AbstractView> views = new HashMap<String, AbstractView>();
	
	/**
	 * Map of built views
	 */
	private HashMap<String, ArrayList<AbstractView>> builtViews = new HashMap<String, ArrayList<AbstractView>>();
	
	/**
	 * Create class instances and binds all together via annotations.
	 * 
	 * @param directory
	 * @param packageName
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws BindException 
	 * @throws IllegalArgumentException 
	 */
	public DependencyInjection(String directory, String packageName) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, BindException
	{
		List<Class<?>> classes = this.findClasses(new File(directory), packageName);
		
		for ( Class<?> clazz : classes ) {
			if ( clazz.isAnnotationPresent(Controller.class) ) {
				AbstractController ac = (AbstractController) clazz.newInstance();
				ac.setDependencyInjection(this);
				this.controllers.put(clazz.getAnnotation(Controller.class).name(), ac);
			} else if ( clazz.isAnnotationPresent(View.class) ) {
				this.views.put(clazz.getAnnotation(View.class).name(), (AbstractView) clazz.newInstance());
				this.builtViews.put(clazz.getAnnotation(View.class).name(), new ArrayList<AbstractView>());
			} else if ( clazz.isAnnotationPresent(Service.class) ) {
				this.services.put(clazz.getAnnotation(Service.class).name(), clazz.newInstance());
			}
		}
		this.injectServices();
		this.bindActions();
	}
	
	/**
	 * Collect class names.
	 * 
	 * @param directory
	 * @param packageName
	 * @return List
	 * @throws ClassNotFoundException
	 */
	private List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException
	{
		List<Class<?>> classes = new ArrayList<Class<?>>();
		
		if ( !directory.exists() ) {
			return classes;
		}
		File[] files = directory.listFiles();
		
		for ( File file : files ) {
			if ( file.isDirectory() ) {
				classes.addAll(this.findClasses(file, packageName + "." + file.getName()));
			} else if ( file.getName().endsWith(".class") ) {
				classes.add(Class.forName(packageName + "." + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		
		return classes;
	}
	
	/**
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws BindException 
	 * @throws  
	 */
	private void bindActions() throws IllegalArgumentException, IllegalAccessException, BindException
	{
		for ( Map.Entry<String, AbstractView> view : this.views.entrySet() ) {
			for ( Field field : view.getValue().getClass().getDeclaredFields() ) {
				if ( field.isAnnotationPresent(Bind.class) ) {
					String bindName = field.getAnnotation(Bind.class).name();
					String[] bindData = bindName.split("\\.");
					this.validateBindData(bindData, bindName);
					Object o = field.get(view.getValue());
					
					if ( o.getClass().equals(MenuItem.class) ) {
						MenuItem mb = (MenuItem) o.getClass().cast(o);
						mb.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e)
							{
								invokeMethod(bindData[0], bindData[1]);
							}
						});
					} else if ( o.getClass().equals(JMenuItem.class) ) {
						JMenuItem mb = (JMenuItem) o.getClass().cast(o);
						mb.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e)
							{
								invokeMethod(bindData[0], bindData[1]);
							}
						});
					} else if ( o.getClass().equals(JButton.class) ) {
						JButton jb = (JButton) o.getClass().cast(o);
						jb.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e)
							{
								invokeMethod(bindData[0], bindData[1]);
							}
						});
					} else if ( o.getClass().equals(JComboBox.class) ) {
						JComboBox jb = (JComboBox) o.getClass().cast(o);
						jb.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e)
							{
								invokeMethod(bindData[0], bindData[1]);
							}
						});
					} else {
						throw new BindException();
					}
				}
			}
		}
	}
	
	/**
	 * @param controllerName
	 * @param actionName
	 */
	private void invokeMethod(String controllerName, String actionName)
	{
		AbstractController controller = controllers.get(controllerName);
		Method[] methods = controller.getClass().getMethods();
		
		for( Method method : methods ) {
			if ( method.isAnnotationPresent(Action.class) ) {
				String annotationActionName = method.getAnnotation(Action.class).name();
				
				if ( annotationActionName.equals(actionName) ) {
					try {
						method.invoke(controller);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * @param bindData
	 * @param bindName
	 * @throws BindException
	 */
	private void validateBindData(String[] bindData, String bindName) throws BindException
	{
		if ( bindData.length != 2 ) {
			throw new BindException(bindData.length, bindName);
		}
		AbstractController controller = controllers.get(bindData[0]);
		
		if ( controller == null ) {
			throw new BindException(bindData[0]);
		} else {
			Method[] methods = controller.getClass().getMethods();
			for( Method method : methods ) {
				if ( method.isAnnotationPresent(Action.class) &&
						method.getAnnotation(Action.class).name().equals(bindData[1])) {
					return;
				}
			}
		}
		throw new BindException(bindData[0], bindData[1]);
	}
	
	/**
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private void injectServices() throws IllegalArgumentException, IllegalAccessException
	{
		for ( Map.Entry<String, Object> service : this.services.entrySet() ) {
			for ( Field field : service.getValue().getClass().getDeclaredFields() ) {
				if ( field.isAnnotationPresent(Inject.class) ) {
					String name = field.getAnnotation(Inject.class).name();
					
					Object object = this.services.get(name);
					
					if ( object == null ) {
						try {
							throw new InvalidServiceException(name, service.getValue().getClass().getName());
						} catch (InvalidServiceException e) {
							e.printStackTrace();
						}
					}
					field.set(service.getValue(), object);
				}
			}
		}
	}
	
	/**
	 * @param name
	 * @return Controller object
	 */
	public AbstractController getController(String name)
	{
		return this.controllers.get(name);
	}
	
	/**
	 * @param name
	 * @return Service object
	 * @throws InvalidServiceException
	 */
	public Object getService(String name)
	{
		Object service = this.services.get(name);
		
		if ( service == null ) {
			try {
				throw new InvalidServiceException(name);
			} catch (InvalidServiceException e) {
				e.printStackTrace();
			}
		}
		return service; 
	}
	
	/**
	 * @param name
	 * @return View object
	 */
	public AbstractView getView(String name)
	{
		return this.views.get(name);
	}

	/**
	 * @param name
	 * @throws ViewException 
	 */
	public AbstractView buildView(String name, Object... args)
	{
		AbstractView view = null;
		try {
			view = this.views.get(name).clone();
		} catch (CloneNotSupportedException e1) {
			e1.printStackTrace();
		}
		
		if ( view == null ) {
			try {
				throw new ViewException(name);
			} catch ( ViewException e ) {
				e.printStackTrace();
			}
		} else {
			ViewType type = view.getClass().getAnnotation(View.class).type();
			ArrayList<AbstractView> views = this.getBuiltViews(name);
			
			for ( int i = 0; i < views.size(); i++ ) {
				if ( !views.get(i).isVisible() ) {
					views.remove(i);
					i--;
				}
			}
			
			if ( ( type.equals(ViewType.SINGLE_VIEW) && views.size() == 0 ) || type.equals(ViewType.MULTIPLE_VIEW) ) {
				view.buildView(args);
				views.add(view);
			}
		}
		
		return view;
	}
	
	/**
	 * @param name
	 * @return ArrayList of all views by name
	 */
	public ArrayList<AbstractView> getBuiltViews(String name)
	{
		return this.builtViews.get(name);
	}
	
	/**
	 * Close application
	 */
	public void close()
	{
		System.exit(0);
	}
}
