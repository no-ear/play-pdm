package models.partversions;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import models.ModelsUtility;
import models.Part;
import com.fasterxml.jackson.databind.node.ObjectNode;

import annotation.PropertyAttribute;
import annotation.PropertyAttribute.InputType;
import play.db.ebean.Model;

/**
 * Example Bolt Part version in design view Model.
 */
@Entity
@DiscriminatorValue("BOLT_PART_VERSION")
public final class BoltPartVersion extends DesignPartVersion {

	/**
	 * Example unique attribute.
	 */
	@Column
	@PropertyAttribute(type = InputType.TEXT)
	public double diameter; // SUPPRESS CHECKSTYLE

	/**
	 * Example unique attribute.
	 */
	@Column
	@PropertyAttribute(type = InputType.TEXT)
	public String comment; // SUPPRESS CHECKSTYLE

	/**
	 * Finder.
	 */
	public static Model.Finder<Long, BoltPartVersion> find = new Model.Finder<Long, BoltPartVersion>( // SUPPRESS CHECKSTYLE
			Long.class, BoltPartVersion.class);

	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = -59223505116187178L;

	public ObjectNode toJsonNode() {
		return ModelsUtility.toJsonNode(this);
	}

	/**
	 * Build new part version.
	 * 
	 * @param partProperties
	 *            build properties
	 * @param partVersionProperties
	 *            build properties
	 * @return New part version
	 */
	public static BoltPartVersion buildPartVersion(
			final HashMap<String, Object> partProperties,
			final HashMap<String, Object> partVersionProperties) {

		Part part = Part.build(partProperties);

		BoltPartVersion partVersion = new BoltPartVersion();

		Field[] fields = BoltPartVersion.class.getFields();

		for (Field field : fields) {
			String key = "BoltPartVersion." + field.getName();

			if (!partVersionProperties.containsKey(key)) {
				continue;
			}

			Object object = partVersionProperties.get(key);

			Class<?> type = field.getType();

			if (type == double.class) {
				object = Double.parseDouble((String) object);
			}

			try {
				field.set(partVersion, object);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// No reachable code?
				e.printStackTrace();
			}
		}

		partVersion.part = part;

		partVersion.save();

		return partVersion;
	}

	public static List<BoltPartVersion> selectLike(final String name,
			final String value) {
		// Separete Name(Person(Const class name) + "." + field name )
		String[] strings = name.split("\\.");
		String className = strings[0];
		String fieldName = strings[strings.length - 1];
		String likeString = value + "%";

		if (className.equals("Part")) {
			return find.fetch("part").where()
					.like("part." + fieldName, likeString).findList();
		} else {
			return find.fetch("part").where().like(fieldName, likeString)
					.findList();
		}
	}
}
