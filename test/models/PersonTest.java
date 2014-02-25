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
		settings.put("db.default.driver", "org.postgresql.Driver");
		settings.put("db.default.url", "jdbc:postgresql://localhost/playpdmtest");
		settings.put("db.default.user", "postgres");
		settings.put("db.default.password", "postgres");

		start(fakeApplication(settings, fakeGlobal()));

		TestUtility.dropCreateDb();
	}

	/**
	 * build new part version.
	 */
	@Test
	public void buildPerson() {
		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("name", NAME);
		properties.put("password", PASSWORD);

		Person person = Person.build(properties);

		// System.out.println(person.passwordHash);
		// System.out.println(person.salt);

		Person result = Person.authenticate(NAME, PASSWORD);

		assertEquals(person, result);
	}
}
