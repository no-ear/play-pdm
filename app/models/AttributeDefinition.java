package models;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.node.ObjectNode;

import play.api.libs.json.JsValue;
import play.i18n.Messages;
import play.libs.Json;
import annotation.PropertyAttribute;
import annotation.PropertyAttribute.InputType;

/**
 * Model field meta info.
 */
public final class AttributeDefinition implements
		Comparable<AttributeDefinition>, Packetable {
	/**
	 * Class definition.
	 */
	public ClassDefinition classDefinition; // SUPPRESS CHECKSTYLE

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
	 * Type of html input tag.
	 */
	public InputType inputType; // SUPPRESS CHECKSTYLE

	/**
	 * Constructor.
	 * 
	 * @param def
	 *            Class that has the field
	 * @param field
	 *            Class field
	 */
	public AttributeDefinition(final ClassDefinition def, final Field field) {

		classDefinition = def;

		name = classDefinition.name + "." + field.getName();

		String message = Messages.get(name);
		displayName = message;

		PropertyAttribute propertyType = field
				.getAnnotation(PropertyAttribute.class);
		isCreate = propertyType.isCreate();
		isRead = propertyType.isRead();
		isUpdate = propertyType.isUpdate();

		isRequired = false;
		if (field.getAnnotation(NotNull.class) != null) {
			isRequired = true;
		}

		priorityNumber = propertyType.priorityNumber();

		inputType = propertyType.type();
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
	 * @param def
	 *            Class meta informations
	 * @param fields
	 *            Class field informations
	 * @return AttributeDefinition Array
	 */
	public static AttributeDefinition[] getAttributeDefinitions(
			final ClassDefinition def, final Field[] fields) {

		ArrayList<AttributeDefinition> list = new ArrayList<AttributeDefinition>();

		for (Field field : fields) {
			PropertyAttribute propertyType = field
					.getAnnotation(PropertyAttribute.class);
			if (propertyType == null) {
				continue;
			}

			AttributeDefinition attributeData = new AttributeDefinition(def,
					field);

			list.add(attributeData);
		}

		Collections.sort(list);

		AttributeDefinition[] result = new AttributeDefinition[list.size()];
		return list.toArray(result);
	}

	@Override
	public ObjectNode toJsonNode() {
		ObjectNode jsonObject = Json.newObject();

		jsonObject.put("className", classDefinition.name);
		jsonObject.put("name", name);
		jsonObject.put("displayName", displayName);
		jsonObject.put("isCreate", isCreate);
		jsonObject.put("isRead", isRead);
		jsonObject.put("isUpdate", isUpdate);
		jsonObject.put("isRequired", isRequired);
		jsonObject.put("priorityNumber", priorityNumber);

		return jsonObject;
	}

	@Override
	public JsValue toJsValue() {
		ObjectNode jsonObject = toJsonNode();
		return play.api.libs.json.Json.parse(jsonObject.toString());
	}

}
