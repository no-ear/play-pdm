package models;

import play.i18n.Messages;
import annotation.ClassEntityAttribute;

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
		name = cls.getName();

		ClassEntityAttribute propertyType = cls
				.getAnnotation(ClassEntityAttribute.class);
		String aliasKey = propertyType.aliasKey();
		if (!aliasKey.equals("")) {
			String message = Messages.get("person." + propertyType.aliasKey());
			displayName = message;
		} else {
			displayName = null;
		}

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
