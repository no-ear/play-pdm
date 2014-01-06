package controllers;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ArrayNode;

import annotation.ClassEntityAttribute;
import models.AttributeDefinition;
import models.Person;
import models.partversions.BoltPartVersion;
import models.partversions.FramePartVersion;
import play.i18n.Messages;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.PartManager;

/**
 * Part manager controller class.
 */
@Security.Authenticated(Secured.class)
public class PartManagerController extends Controller {

	/**
	 * Create entity class list.<br>
	 * ClassDefinition not serializable.
	 */
	@SuppressWarnings("serial")
	public static Map<String, ClassDefinition> entityClassMap = new LinkedHashMap<String, ClassDefinition>() { // SUPPRESS CHECKSTYLE
		{
			ClassDefinition classDefinition = new ClassDefinition(
					BoltPartVersion.class);
			put(BoltPartVersion.class.getName(), classDefinition);

			classDefinition = new ClassDefinition(FramePartVersion.class);
			put(FramePartVersion.class.getName(), classDefinition);
		}
	};

	/**
	 * Index page. User manager.
	 * 
	 * @return Http response
	 */
	public static Result index() {
		return ok(PartManager.render(entityClassMap));
	}

	/**
	 * Return part attribute definition index data.
	 * 
	 * @param name
	 *            select class name
	 * @return Http response
	 */
	@BodyParser.Of(BodyParser.Json.class)
	public static Result getPartIndex(final String name) {

		Field[] fields = entityClassMap.get(name).classType.getFields();
		AttributeDefinition[] attributeDefinitions = AttributeDefinition
				.getAttributeDefinitions(fields);

		ArrayNode jsonNode = AttributeDefinition
				.toArrayNode(attributeDefinitions);
		return ok(jsonNode);
	}

	/**
	 * Class definition class.
	 */
	public static class ClassDefinition {
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
				String message = Messages.get("person."
						+ propertyType.aliasKey());
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
};
