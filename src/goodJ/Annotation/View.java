package goodJ.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import goodJ.Enum.ViewType;

/**
 * @author adrian.glazer
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface View
{
	public static int SINGLE_VIEW = 1;
	String name();
	ViewType type() default ViewType.SINGLE_VIEW;
}
