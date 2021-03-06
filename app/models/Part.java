package models;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import annotation.PropertyAttribute;
import annotation.PropertyAttribute.InputType;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.api.libs.json.JsValue;
import play.db.ebean.Model;

/**
 * Part model class.
 */
@Entity
@Table(name = "parts")
public final class Part extends Model implements Packetable {

	/**
	 * Surrogate key.
	 */
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id; // SUPPRESS CHECKSTYLE getter/setter create play framework.

	/**
	 * Part name.
	 */
	@Column
	public String name; // SUPPRESS CHECKSTYLE

	/**
	 * Create date.
	 */
	@Column
	@NotNull
	@CreatedTimestamp
	@PropertyAttribute(type = InputType.TEXT, isCreate = false, isUpdate = false)
	public Date createDate; // SUPPRESS CHECKSTYLE

	/**
	 * Create user.
	 */
	@Column
	@NotNull
	@PropertyAttribute(type = InputType.TEXT, isCreate = false, isUpdate = false)
	public Person createPerson; // SUPPRESS CHECKSTYLE

	/**
	 * Modify date.
	 */
	@Column
	@PropertyAttribute(type = InputType.TEXT, isCreate = false, isUpdate = false)
	public Date modifyDate; // SUPPRESS CHECKSTYLE

	/**
	 * Modify user.
	 */
	@Column
	@PropertyAttribute(type = InputType.TEXT, isCreate = false, isUpdate = false)
	public Person modifyPerson; // SUPPRESS CHECKSTYLE

	/**
	 * Part number.
	 */
	@Column(unique = true)
	@NotNull
	@PropertyAttribute(type = InputType.TEXT, isUpdate = false)
	public String number; // SUPPRESS CHECKSTYLE

	/**
	 * Finder.
	 */
	public static Model.Finder<Long, Part> find = new Model.Finder<Long, Part>( // SUPPRESS CHECKSTYLE
			Long.class, Part.class);

	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = -7556779886341618589L;

	/**
	 * Build new part object.
	 * 
	 * @param partProperties
	 *            new part properties
	 * @return new part
	 */
	public static Part build(final HashMap<String, Object> partProperties) {
		Part part = new Part();

		Field[] fields = Part.class.getFields();

		for (Field field : fields) {
			String key = "Part." + field.getName();
			if (!partProperties.containsKey(key)) {
				continue;
			}

			Object object = partProperties.get(key);
			try {
				field.set(part, object);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// No reachable code?
				e.printStackTrace();
			}
		}

		part.save();

		return part;
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
