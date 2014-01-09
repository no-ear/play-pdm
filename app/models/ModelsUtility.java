package models;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import play.libs.Json;
import annotation.PropertyAttribute;

import com.fasterxml.jackson.databind.node.ObjectNode;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

/**
 * Models utility functions.
 */
public final class ModelsUtility {
	/**
	 * Parse method map.
	 */
	@SuppressWarnings("serial")
	private static Map<Class<?>, ParseObjectStrategy> strategyMap = new HashMap<Class<?>, ParseObjectStrategy>() {
		{
			put(String.class, new ParseString());
			put(Date.class, new ParseDate());
		}
	};

	/**
	 * Kill constructor.
	 */
	private ModelsUtility() {
	}

	/**
	 * Convert person list to json.
	 * 
	 * @param object
	 *            Convert json object.
	 * @return Json data(Java)
	 */
	public static ObjectNode toJsonNode(final Object object) {
		Class<? extends Object> clazz = object.getClass();

		Field[] fields = clazz.getFields();

		ObjectNode jsonObject = Json.newObject();
		for (Field field : fields) {
			PropertyAttribute propertyType = field
					.getAnnotation(PropertyAttribute.class);
			if (propertyType == null) {
				continue;
			}

			if (!propertyType.isRead()) {
				continue;
			}

			Class<?> type = field.getType();

			String key = clazz.getSimpleName() + "." + field.getName();

			if (strategyMap.containsKey(type)) {
				ParseObjectStrategy strategy = strategyMap.get(type);
				ObjectNode jsonNode = strategy.parse(key, field, object);

				if (jsonNode != null) {
					jsonObject.putAll(jsonNode);
				}
			} else {
				System.out.println("Not implement : " + type.getSimpleName());
			}
		}

		return jsonObject;
	}

	/**
	 * Parse object interface. Strategy pattern.
	 */
	private interface ParseObjectStrategy {
		/**
		 * Parse object.
		 * 
		 * @param key
		 *            Json key name
		 * @param field
		 *            get value field
		 * @param object
		 *            input object. Checked type.
		 * @return Json object
		 */
		ObjectNode parse(final String key, final Field field,
				final Object object);
	}

	/**
	 * Parse String.
	 */
	private static class ParseString implements ParseObjectStrategy {

		@Override
		public ObjectNode parse(final String key, final Field field,
				final Object object) {
			ObjectNode jsonObject = Json.newObject();

			String value = null;
			try {
				value = (String) field.get(object);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// Does not reach because it already checked
				e.printStackTrace();
			}

			if (value != null) {
				String string = escapeHtml4(value);
				jsonObject.put(key, string);
			} else {
				jsonObject.put(key, (String) null);
			}

			return jsonObject;
		}

	}

	/**
	 * Parse String.
	 */
	private static class ParseDate implements ParseObjectStrategy {

		@Override
		public ObjectNode parse(final String key, final Field field,
				final Object object) {
			ObjectNode jsonObject = Json.newObject();

			Date value = null;
			try {
				value = (Date) field.get(object);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// Does not reach because it already checked
				e.printStackTrace();
			}

			if (value != null) {
				jsonObject.put(key, value.toString());
			} else {
				jsonObject.put(key, (String) null);
			}

			return jsonObject;
		}
	}

	/**
	 * Force Parse Object.<br>
	 * May be used ... ?
	 */
	@SuppressWarnings("unused")
	private static class ParseObject implements ParseObjectStrategy {

		@Override
		public ObjectNode parse(final String key, final Field field,
				final Object object) {
			ObjectNode jsonObject = Json.newObject();

			Object value = null;
			try {
				value = field.get(object);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}

			if (value != null) {
				jsonObject.put(key, value.toString());
			} else {
				jsonObject.put(key, (String) null);
			}

			return jsonObject;
		}
	}

}
