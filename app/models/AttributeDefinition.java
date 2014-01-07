package models;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.api.libs.json.JsValue;
import play.i18n.Messages;
import play.libs.Json;
import annotation.PropertyAttribute;

/**
 * Model field meta info.
 */
public final class AttributeDefinition implements
		Comparable<AttributeDefinition> {
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
	 * Constructor.
	 * 
	 * @param def
	 *            Class that has the field
	 * @param field
	 *            Class field
	 */
	public AttributeDefinition(final ClassDefinition def, final Field field) {

		classDefinition = def;

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

	/**
	 * To Json ArrayNode From AttributeDefinition array.
	 * 
	 * @param attributeDefinitions
	 *            Class attribute definition array
	 * @return Json(Java) array.
	 */
	public static ArrayNode toArrayNode(
			final AttributeDefinition[] attributeDefinitions) {
		ObjectNode json = Json.newObject();
		ArrayNode array = json.arrayNode();

		for (AttributeDefinition attr : attributeDefinitions) {
			array.add(attr.toJsonNode());
		}

		return array;
	}

	/**
	 * Convert to Json(Java).
	 * 
	 * @return Json(Java)
	 */
	private JsonNode toJsonNode() {
		ObjectNode jsonObject = Json.newObject();

		jsonObject.put("name", name);
		jsonObject.put("displayName", displayName);
		jsonObject.put("isCreate", isCreate);
		jsonObject.put("isRead", isRead);
		jsonObject.put("isUpdate", isUpdate);
		jsonObject.put("isRequired", isRequired);
		jsonObject.put("priorityNumber", priorityNumber);

		return jsonObject;
	}

	/**
	 * To JsValue(Json for Scala) From AttributeDefinition array.
	 * 
	 * @param attributeDefinitions
	 *            Class field attribute definition array
	 * @return Json(Scala)
	 */
	public static JsValue toJsArray(
			final AttributeDefinition[] attributeDefinitions) {

		ArrayNode jsonObject = toArrayNode(attributeDefinitions);

		return play.api.libs.json.Json.parse(jsonObject.toString());
	}

}
