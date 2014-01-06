package models.partversions;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import annotation.ClassEntityAttribute;
import annotation.PropertyAttribute;
import annotation.PropertyAttribute.InputType;
import play.db.ebean.Model;

/**
 * Example Bolt Part version in design view Model.
 */
@Entity
@DiscriminatorValue("BOLT_PART_VERSION")
@ClassEntityAttribute()
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
}
