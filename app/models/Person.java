package models;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import annotation.PropertyType;
import play.db.ebean.Model;

/**
 * Person model class.<br>
 * Management login user.
 */
@Entity
@Table(name = "persons")
public final class Person extends Model {

	/**
	 * Surrogate key.
	 */
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id; // SUPPRESS CHECKSTYLE getter/setter create play framework.

	/**
	 * Login name.<br>
	 * Require unique.
	 */
	@Column(unique = true)
	@NotNull
	@PropertyType(type = "text")
	public String name; // SUPPRESS CHECKSTYLE

	/**
	 * User name.
	 */
	@Column(unique = true)
	@PropertyType(type = "text")
	public String fullName; // SUPPRESS CHECKSTYLE

	/**
	 * Password hash.
	 */
	@Column
	public String passwordHash; // SUPPRESS CHECKSTYLE

	/**
	 * salt, when generate passwordHash.
	 */
	@Column
	public String salt; // SUPPRESS CHECKSTYLE
	/**
	 * title.<br>
	 * e.g. CEO, manager, officer, etc.
	 */
	@Column
	@PropertyType(type = "text")
	public String title; // SUPPRESS CHECKSTYLE

	/**
	 * password. property entry.
	 */
	@Transient
	@PropertyType(type = "text")
	public String password; // SUPPRESS CHECKSTYLE

	/**
	 * Random seed string length.<br>
	 * sha256Hex complexity 256^32=1.1579209e+77.<br>
	 * randomAscii generate 95 characters.<br>
	 * sha256Hex complexity < 95^39=1.3527595e+77
	 */
	private static final int RANDOM_STRING_LENGTH = 39;

	/**
	 * Finder.
	 */
	public static Model.Finder<Long, Person> find = new Model.Finder<Long, Person>( // SUPPRESS CHECKSTYLE
			Long.class, Person.class);

	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = -6872558365573836112L;

	/**
	 * Authenticate with name and password.
	 * 
	 * @param name
	 *            Login user name
	 * @param password
	 *            Input password
	 * @return null Error<br>
	 *         not null Success Person object
	 */
	public static Person authenticate(final String name, final String password) {

		Person person = find.where().eq("name", name).findUnique();

		if (person == null) {
			// ログイン名を発見できなかった。
			return null;
		}

		if (person.passwordHash.equals(DigestUtils.sha256Hex(password
				+ person.salt))) {
			return person;
		}

		return null;
	}

	/**
	 * Build new person.
	 * 
	 * @param properties
	 *            build properties
	 * @return New person object
	 */
	public static Person build(final Map<String, Object> properties) {

		Person person = new Person();

		Field[] fields = Person.class.getFields();

		for (Field field : fields) {
			if (!properties.containsKey(field.getName())) {
				continue;
			}

			Object object = properties.get(field.getName());
			try {
				field.set(person, object);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// No reachable code?
				e.printStackTrace();
			}
		}

		// Properties have password.
		if (!properties.containsKey("password")) {
			return null;
		}

		String password = (String) properties.get("password");

		person.salt = RandomStringUtils.randomAscii(RANDOM_STRING_LENGTH);
		person.passwordHash = DigestUtils.sha256Hex(password + person.salt);

		person.save();

		return person;
	}

	public static String setPropertiesForm() {

		String resultHtml = "";

		Field[] fields = Person.class.getFields();

		for (Field field : fields) {
			PropertyType propertyType = field.getAnnotation(PropertyType.class);
			if (propertyType == null) {
				continue;
			}

			boolean isRequired = false;
			if (field.getAnnotation(NotNull.class) != null) {
				isRequired = true;
			}

			String inputHtml = "<div class='form-group'>"
					+ "<label class='col-sm-2 control-label'>"
					+ field.getName() + "</label>" + "<div class='col-sm-10'>"
					+ "<input type='" + propertyType.type()
					+ "' class='form-control' name='" + field.getName() + "' ";

			if (isRequired) {
				inputHtml += "required";
			}

			inputHtml += " >" + "</div>" + "</div>";

			resultHtml += inputHtml;

		}

		return resultHtml;
	}
}
