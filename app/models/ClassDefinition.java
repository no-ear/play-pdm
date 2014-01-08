package models;

import play.i18n.Messages;

/**
 * Class definition class.
 */
public class ClassDefinition {
	/**
	 * Constructor.
	 * 
	 * @param cls
	 *            class info
	 */
	public ClassDefinition(final Class<?> cls) {
		name = cls.getSimpleName();

		String message = Messages.get(cls.getSimpleName());
		displayName = message;

		classType = cls;
	}

	/**
	 * Class name.
	 */
	public String name; // SUPPRESS CHECKSTYLE

	/**
	 * Display name in view.
	 */
	public String displayName; // SUPPRESS CHECKSTYLE

	/**
	 * Class object.
	 */
	public Class<?> classType; // SUPPRESS CHECKSTYLE
}
