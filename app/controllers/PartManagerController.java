package controllers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ArrayNode;

import models.AttributeDefinition;
import models.ClassDefinition;
import models.PacketUtility;
import models.Part;
import models.partversions.BoltPartVersion;
import models.partversions.FramePartVersion;
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
	private static Map<String, ClassDefinition> entityClassMap = new LinkedHashMap<String, ClassDefinition>() {
		{
			ClassDefinition classDefinition = new ClassDefinition(
					BoltPartVersion.class);
			put(BoltPartVersion.class.getSimpleName(), classDefinition);

			classDefinition = new ClassDefinition(FramePartVersion.class);
			put(FramePartVersion.class.getSimpleName(), classDefinition);
		}
	};

	/**
	 * Index page. Part manager.
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
		List<AttributeDefinition> attributeDefinitionList = new ArrayList<AttributeDefinition>();

		ClassDefinition partClassDefinition = new ClassDefinition(Part.class);
		Field[] partFields = Part.class.getFields();
		AttributeDefinition[] partAttributeDefinitions = AttributeDefinition
				.getAttributeDefinitions(partClassDefinition, partFields);
		for (AttributeDefinition attr : partAttributeDefinitions) {
			attributeDefinitionList.add(attr);
		}

		Field[] partVersionFields = entityClassMap.get(name).classType
				.getFields();
		ClassDefinition partVersionClassDefinition = new ClassDefinition(
				entityClassMap.get(name).classType);
		AttributeDefinition[] attributeDefinitions = AttributeDefinition
				.getAttributeDefinitions(partVersionClassDefinition,
						partVersionFields);
		for (AttributeDefinition attr : attributeDefinitions) {
			attributeDefinitionList.add(attr);
		}

		AttributeDefinition[] array = new AttributeDefinition[attributeDefinitionList
				.size()];
		attributeDefinitionList.toArray(array);

		ArrayNode jsonNode = PacketUtility.toArrayNode(array);
		return ok(jsonNode);
	}

	/**
	 * Simple search.
	 * 
	 * @param className
	 *            part version class name
	 * @param name
	 *            Person attribute name
	 * @param value
	 *            Like search value
	 * @return Http response(Json)
	 */
	@BodyParser.Of(BodyParser.Json.class)
	public static Result readLike(final String className, final String name,
			final String value) {
		if (className.equals("") || value.equals("") || name.equals("")) {
			return badRequest();
		}

		if (className.equals("BoltPartVersion")) {
			return BoltPartVersionController.readLike(name, value);
		}

		return badRequest();
	}
};
