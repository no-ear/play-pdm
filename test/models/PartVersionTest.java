package models;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeGlobal;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import play.test.WithApplication;
import tests.TestUtility;

/**
 * Part version test.
 */
public final class PartVersionTest extends WithApplication {

	/**
	 * Before test database initialize.
	 */
	@Before
	public void setUp() {
		Map<String, String> settings = new HashMap<String, String>();
		settings.put("db.default.driver", "org.postgresql.Driver");
		settings.put("db.default.url",
				"jdbc:postgresql://localhost/playpdmtest");
		settings.put("db.default.user", "postgres");
		settings.put("db.default.password", "postgres");

		start(fakeApplication(settings, fakeGlobal()));

		TestUtility.dropCreateDb();
	}

}
