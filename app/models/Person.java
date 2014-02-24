package models;

import java.lang.reflect.Field;
import java.util.List;
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

import com.fasterxml.jackson.databind.node.ObjectNode;

import annotation.PropertyAttribute;
import annotation.PropertyAttribute.InputType;
import play.api.libs.json.JsValue;
import play.db.ebean.Model;

/**
 * Person model class.<br>
 * Management login user.
 */
@Entity
@Table(name = "persons")
public final class Person extends Model implements Packetable {

	/**
	 * Surrogate key.
	 */
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id; // SUPPRESS CHECKSTYLE getter/setter create play framework.

	/**
	 * User name.<br>
	 * Require unique. Login Id.
	 */
	@Column(unique = true)
	@NotNull
	@PropertyAttribute(type = InputType.TEXT, isUpdate = false, priorityNumber = 2)
	public String name; // SUPPRESS CHECKSTYLE

	/**
	 * User name.
	 */
	@Column
	@PropertyAttribute(type = InputType.TEXT, priorityNumber = 1)
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
	@PropertyAttribute(type = InputType.TEXT, priorityNumber = 3)
	public String title; // SUPPRESS CHECKSTYLE

	/**
	 * password. property entry.
	 */
	@Transient
	@PropertyAttribute(type = InputType.PASSWORD, isRead = false, isUpdate = false, priorityNumber = 4)
	public String password; // SUPPRESS CHECKSTYLE

	/**
	 * Random seed string length.<br>
	 * sha256Hex complexity = 256^32=1.1579209e+77.<br>
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
			// Not found login user
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
			String key = "Person." + field.getName();
			if (!properties.containsKey(key)) {
				continue;
			}

			Object object = properties.get(key);
			try {
				field.set(person, object);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// No reachable code?
				e.printStackTrace();
			}
		}

		// Properties have password.
		if (!properties.containsKey("Person.password")) {
			return null;
		}

		String password = (String) properties.get("Person.password");

		person.salt = RandomStringUtils.randomAscii(RANDOM_STRING_LENGTH);
		person.passwordHash = DigestUtils.sha256Hex(password + person.salt);

		person.save();

		return person;
	}

	/**
	 * Select like input value.
	 * 
	 * @param attributeName
	 *            target attribute.
	 * @param value
	 *            like value.
	 * @return Person list.
	 */
	public static List<Person> selectLike(final String attributeName,
			final String value) {
		String likeString = value + "%";
		return find.where().like(attributeName, likeString).findList();
	}

	@Override
	public ObjectNode toJsonNode() {
		return ModelsUtility.toJsonNode(this);
	}

	@Override
	public JsValue toJsValue() {
		ObjectNode jsonObject = toJsonNode();
		return play.api.libs.json.Json.parse(jsonObject.toString());
	}

}
