package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import models.AttributeDefinition;
import models.Person;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.usermanager;

/**
 * User manager controller class.
 */
@Security.Authenticated(Secured.class)
public class UserManagerController extends Controller {
	// /**
	// * Return no update to backbone model. <br>
	// * NOTE: Return ok() is strange behaivior. This problem that error log does not output.
	// */
	// private static final Result OK_NOT_UPDATE_JSON = ok("{}");

	/**
	 * Index page. User manager.
	 * 
	 * @return Http response
	 */
	public static Result index() {

		AttributeDefinition[] attributeDefinitions = Person
				.getAttributeDefinitions();

		return ok(usermanager.render(attributeDefinitions));
	}

	/**
	 * Create person.
	 * 
	 * @return Http response
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	@BodyParser.Of(BodyParser.Json.class)
	public static Result create() {

		JsonNode json = request().body().asJson();

		HashMap<String, Object> hashMap;
		try {
			hashMap = new ObjectMapper().readValue(json.toString(),
					HashMap.class);
		} catch (IOException e) {
			e.printStackTrace();
			return badRequest();
		}

		Person person = Person.build(hashMap);

		// 今のところ追加情報がなく、IDのみ返せばいいのでこれで
		ObjectNode jsonNode = Json.newObject();
		jsonNode.put("id", Long.toString(person.id));

		return ok(jsonNode);
	}

	/**
	 * Simple search.
	 * 
	 * @param name
	 *            Person attribute name
	 * @param value
	 *            Like search value
	 * @return Http response(Json)
	 */
	@BodyParser.Of(BodyParser.Json.class)
	public static Result readLike(final String name, final String value) {
		if (value.equals("") || name.equals("")) {
			return badRequest();
		}

		List<Person> list = Person.selectLike(name, value);

		ArrayNode jsonNode = Person.toArrayNode(list);

		return ok(jsonNode);
	}

}
