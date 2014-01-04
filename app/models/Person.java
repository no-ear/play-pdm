package models;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
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

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import annotation.PropertyAttribute;
import annotation.PropertyAttribute.InputType;
import play.db.ebean.Model;
import play.i18n.Messages;
import play.libs.Json;

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

	/**
	 * Return Model field definition array.
	 * 
	 * @return Model field definition array. Meta data.
	 */
	public static AttributeDefinition[] getAttributeDefinitions() {

		ArrayList<AttributeDefinition> list = new ArrayList<AttributeDefinition>();

		Field[] fields = Person.class.getFields();

		for (Field field : fields) {
			PropertyAttribute propertyType = field
					.getAnnotation(PropertyAttribute.class);
			if (propertyType == null) {
				continue;
			}

			boolean isRequired = false;
			if (field.getAnnotation(NotNull.class) != null) {
				isRequired = true;
			}

			AttributeDefinition attributeData = new AttributeDefinition();

			attributeData.name = field.getName();
			String aliasKey = propertyType.aliasKey();
			if (!aliasKey.equals("")) {
				String message = Messages.get("person."
						+ propertyType.aliasKey());
				attributeData.displayName = message;
			} else {
				attributeData.displayName = null;
			}

			attributeData.isCreate = propertyType.isCreate();
			attributeData.isRead = propertyType.isRead();
			attributeData.isUpdate = propertyType.isUpdate();
			attributeData.isRequired = isRequired;
			attributeData.priorityNumber = propertyType.priorityNumber();

			list.add(attributeData);
		}

		Collections.sort(list);

		AttributeDefinition[] result = new AttributeDefinition[list.size()];
		return list.toArray(result);
	}

	/**
	 * Convert person list to json. <br>
	 * TODO これもどうなの？ここ？
	 * 
	 * @param list
	 *            Person array list.
	 * @return Person json data.
	 */
	public static ArrayNode toArrayNode(final List<Person> list) {
		ObjectNode json = Json.newObject();
		ArrayNode array = json.arrayNode();

		for (Person person : list) {
			Field[] fields = Person.class.getFields();

			ObjectNode jsonObject = Json.newObject();
			for (Field field : fields) {
				PropertyAttribute propertyType = field
						.getAnnotation(PropertyAttribute.class);
				if (propertyType == null) {
					continue;
				}

				if (!propertyType.isRead()) {
					continue;
				}

				Class<?> type = field.getType();

				if (long.class == type) {
					long value = 0;
					try {
						value = (long) field.get(person);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						// Does not reach because it already checked
						e.printStackTrace();
					}
					jsonObject.put(field.getName(), Long.toString(value));
				} else if (String.class == type) {
					String value = null;
					try {
						value = (String) field.get(person);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						// Does not reach because it already checked
						e.printStackTrace();
					}
					jsonObject.put(field.getName(), escapeHtml4(value));
				}
			}

			// Set id always.
			jsonObject.put("id", Long.toString(person.id));

			array.add(jsonObject);
		}

		return array;
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

	/**
	 * Model field meta info. like struct.
	 */
	public static final class AttributeDefinition implements
			Comparable<AttributeDefinition> {
		/**
		 * field name.
		 */
		public String name; // SUPPRESS CHECKSTYLE

		/**
		 * Display name in view.
		 */
		public String displayName; // SUPPRESS CHECKSTYLE

		/**
		 * Propertiable create flag.
		 */
		public boolean isCreate; // SUPPRESS CHECKSTYLE

		/**
		 * Propertiable read flag.
		 */
		public boolean isRead; // SUPPRESS CHECKSTYLE

		/**
		 * Propertiable update flag.
		 */
		public boolean isUpdate; // SUPPRESS CHECKSTYLE

		/**
		 * Property require flag.
		 */
		public boolean isRequired; // SUPPRESS CHECKSTYLE

		/**
		 * Numbering Display priority.
		 */
		public int priorityNumber; // SUPPRESS CHECKSTYLE

		@Override
		public int compareTo(final AttributeDefinition obj) {
			AttributeDefinition operand = (AttributeDefinition) obj;

			if (this.priorityNumber < operand.priorityNumber) {
				return -1;
			} else if (this.priorityNumber > operand.priorityNumber) {
				return 1;
			}

			return 0;
		}
	}

}
