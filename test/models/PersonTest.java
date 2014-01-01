package models;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeGlobal;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import play.test.WithApplication;
import tests.TestUtility;

/**
 * Part version test.
 */
public final class PersonTest extends WithApplication {

	/**
	 * Test name. Const value.
	 */
	private static final String NAME = "administrator";

	/**
	 * Test password. Const value.
	 */
	private static final String PASSWORD = "test";

	/**
	 * Before test database initialize.
	 */
	@Before
	public void setUp() {
		Map<String, String> settings = new HashMap<String, String>();
		settings.put("db.default.driver", "com.mysql.jdbc.Driver");
		settings.put("db.default.url",
				"jdbc:mysql://localhost:3306/playpdmtest");
		settings.put("db.default.user", "playpdm");
		settings.put("db.default.password", "playpdm");

		start(fakeApplication(settings, fakeGlobal()));

		TestUtility.dropCreateDb();
	}

	/**
	 * build new part version.
	 */
	@Test
	public void buildPerson() {
		Person personProperty = new Person();

		personProperty.name = NAME;
		personProperty.password = PASSWORD;

		Person person = Person.build(personProperty);

		// System.out.println(person.passwordHash);
		// System.out.println(person.salt);

		Person result = Person.authenticate(NAME, PASSWORD);

		assertEquals(person, result);
	}

	/**
	 * build new part version.
	 */
	@Test
	public void buildPersonProperties() {
		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("name", NAME);
		properties.put("password", PASSWORD);

		Person person = Person.build(properties);

		Person result = Person.authenticate(NAME, PASSWORD);

		assertEquals(person, result);
	}
}