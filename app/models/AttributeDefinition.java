package models;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

import javax.validation.constraints.NotNull;

import play.i18n.Messages;
import annotation.PropertyAttribute;

/**
 * Model field meta info.
 */
public final class AttributeDefinition implements
		Comparable<AttributeDefinition> {
	/**
	 * Field name.
	 */
	public String name; // SUPPRESS CHECKSTYLE

	/**
	 * Display name in view.
	 */
	public String displayName; // SUPPRESS CHECKSTYLE

	/**
	 * Propertiable create flag.
	 */
	public boolean isCreate; // SUPPRESS CHECKSTYLE

	/**
	 * Propertiable read flag.
	 */
	public boolean isRead; // SUPPRESS CHECKSTYLE

	/**
	 * Propertiable update flag.
	 */
	public boolean isUpdate; // SUPPRESS CHECKSTYLE

	/**
	 * Property require flag.
	 */
	public boolean isRequired; // SUPPRESS CHECKSTYLE

	/**
	 * Numbering Display priority.
	 */
	public int priorityNumber; // SUPPRESS CHECKSTYLE

	/**
	 * Constructor.
	 * 
	 * @param field
	 *            Class field
	 */
	public AttributeDefinition(final Field field) {

		name = field.getName();
		
		PropertyAttribute propertyType = field
				.getAnnotation(PropertyAttribute.class);
		String aliasKey = propertyType.aliasKey();
		if (!aliasKey.equals("")) {
			String message = Messages.get("person." + propertyType.aliasKey());
			displayName = message;
		} else {
			displayName = null;
		}

		isCreate = propertyType.isCreate();
		isRead = propertyType.isRead();
		isUpdate = propertyType.isUpdate();

		isRequired = false;
		if (field.getAnnotation(NotNull.class) != null) {
			isRequired = true;
		}

		priorityNumber = propertyType.priorityNumber();
	}

	@Override
	public int compareTo(final AttributeDefinition obj) {
		AttributeDefinition operand = (AttributeDefinition) obj;

		if (this.priorityNumber < operand.priorityNumber) {
			return -1;
		} else if (this.priorityNumber > operand.priorityNumber) {
			return 1;
		}

		return 0;
	}

	/**
	 * Create class AttributeDefinition Array.
	 * 
	 * @param fields
	 *            Class field informations
	 * @return AttributeDefinition Array
	 */
	public static AttributeDefinition[] getAttributeDefinitions(
			final Field[] fields) {

		ArrayList<AttributeDefinition> list = new ArrayList<AttributeDefinition>();

		for (Field field : fields) {
			PropertyAttribute propertyType = field
					.getAnnotation(PropertyAttribute.class);
			if (propertyType == null) {
				continue;
			}

			AttributeDefinition attributeData = new AttributeDefinition(field);

			list.add(attributeData);
		}

		Collections.sort(list);

		AttributeDefinition[] result = new AttributeDefinition[list.size()];
		return list.toArray(result);
	}
}
