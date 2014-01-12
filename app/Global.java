import java.util.List;

import com.avaje.ebean.Ebean;

import models.Person;
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

/**
 * Application global configuration class.
 */
public final class Global extends GlobalSettings {

	@Override
	public void onStart(final Application app) {
		// Check if the database is empty
		if (Person.find.findRowCount() == 0) {
			List<?> load = (List<?>) Yaml.load("initial-data.yml");
			if (load != null && load.size() != 0) {
				Ebean.save(load);
			}
		}
	}
}
