package models;

import static org.junit.Assert.*;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeGlobal;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.PersistenceException;

import models.partversions.DesignPartVersion;

import org.junit.Before;
import org.junit.Test;

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

	/**
	 * build new part version.
	 */
	@Test
	public void buildPartVersion() {
		Part part = new Part();
		part.number = "SCREW-0001";

		DesignPartVersion partVersion = new DesignPartVersion();

		DesignPartVersion.buildPartVersion(part, partVersion);

		assertEquals(partVersion.part, part);
		assertEquals(partVersion.version, 1);
		assertNotNull(partVersion.createDate);
	}

	/**
	 * build new part version set null number error.
	 */
	@Test(expected = PersistenceException.class)
	public void buildPartVersionNumberNull() {
		Part part = new Part();

		DesignPartVersion partVersion = new DesignPartVersion();

		DesignPartVersion.buildPartVersion(part, partVersion);
	}
}
