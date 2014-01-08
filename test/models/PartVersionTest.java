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
		settings.put("db.default.driver", "com.mysql.jdbc.Driver");
		settings.put("db.default.url",
				"jdbc:mysql://localhost:3306/playpdmtest");
		settings.put("db.default.user", "playpdm");
		settings.put("db.default.password", "playpdm");

		start(fakeApplication(settings, fakeGlobal()));

		TestUtility.dropCreateDb();
	}


}
