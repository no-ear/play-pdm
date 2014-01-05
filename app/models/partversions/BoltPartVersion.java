package models.partversions;

import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import models.AttributeDefinition;
import annotation.PropertyAttribute;
import annotation.PropertyAttribute.InputType;
import play.db.ebean.Model;
import play.i18n.Messages;

/**
 * Example Bolt Part version in design view Model.
 */
@Entity
@DiscriminatorValue("BOLT_PART_VERSION")
public class BoltPartVersion extends DesignPartVersion {

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

	/**
	 * Return Model field definition array.
	 * 
	 * @return Model field definition array. Meta data.
	 */
	public static AttributeDefinition[] getAttributeDefinitions() {

		ArrayList<AttributeDefinition> list = new ArrayList<AttributeDefinition>();

		Field[] fields = BoltPartVersion.class.getFields();

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

			list.add(attributeData);
		}

		AttributeDefinition[] result = new AttributeDefinition[list.size()];
		return list.toArray(result);
	}
}
