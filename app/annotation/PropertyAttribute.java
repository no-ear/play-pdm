package annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * Property annotation class.
 */
@Target(ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PropertyAttribute {

	/**
	 * Html input tag type.<br>
	 * NOTE Not equal in strict. <br>
	 * e.g. TEXTARIA -> replace textarea tag. <br>
	 * NUMBER_INT -> type="number" and add html5 pattern attr.
	 */
	public enum InputType {
		/**
		 * Simple type="text".
		 */
		TEXT,
		/**
		 * Simple type="password".
		 */
		PASSWORD
	}

	/**
	 * Input html tag type.
	 */
	InputType type();

	/**
	 * i18n file key when Property display.
	 */
	String aliasKey() default "";

	/**
	 * Propertiable on creating Flag.
	 */
	boolean isCreate() default true;

	/**
	 * Propertiable on reading Flag.
	 */
	boolean isRead() default true;

	/**
	 * Propertiable on updating Flag.
	 */
	boolean isUpdate() default true;

	/**
	 * Numbering Display priority.
	 */
	int priorityNumber() default 0;
}
