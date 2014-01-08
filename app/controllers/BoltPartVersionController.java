package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import models.partversions.BoltPartVersion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.db.ebean.Transactional;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Part manager controller class.
 */
@Security.Authenticated(Secured.class)
public class BoltPartVersionController extends Controller {
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

		// Get part property and partversion property.
		HashMap<String, Object> partProperties = divideProperties("Part",
				hashMap);
		HashMap<String, Object> partVersionProperties = divideProperties(
				"BoltPartVersion", hashMap);

		System.out.println(partProperties);
		System.out.println(partVersionProperties);

		BoltPartVersion partVersion = BoltPartVersion.buildPartVersion(
				partProperties, partVersionProperties);

		ObjectNode jsonNode = partVersion.toJsonNode();

		return ok("{}");
	}

	private static HashMap<String, Object> divideProperties(String className,
			HashMap<String, Object> hashMap) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
			String key = entry.getKey();

			if (key.startsWith(className + ".")) {
				result.put(key, entry.getValue());
			}
		}

		return result;
	}
}
